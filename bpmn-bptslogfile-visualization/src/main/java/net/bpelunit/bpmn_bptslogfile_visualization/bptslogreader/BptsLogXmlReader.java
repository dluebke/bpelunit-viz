package net.bpelunit.bpmn_bptslogfile_visualization.bptslogreader;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.Activity;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.Activity.ActivityType;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.Assertion;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.PartnerTrack;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.TestCase;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.TestSuite;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.WithPassState;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.WithPassState.PassStatus;
import net.bpelunit.bpmn_bptslogfile_visualization.util.XmlUtil;

public class BptsLogXmlReader implements BptsLogReader {

	private static final String PREFIX_TESTCASE_NAME = "Test Case ";
	private static final String PREFIX_PARTNERTRACK_NAME = "Partner Track ";
	private static final String NS_BPTSLOG = "http://www.bpelunit.org/schema/testResult";
	private static final Map<String, ActivityType> ACTIVITY_TYPE_CONSTANTS;
	
	static {
		Map<String, ActivityType> tmp = new HashMap<>();
		tmp.put("ReceiveSendSync", ActivityType.RECEIVE_SEND_SYNC);
		tmp.put("SendReceiveSync", ActivityType.SEND_RECEIVE_SYNC);
		tmp.put("ReceiveSendAsync", ActivityType.RECEIVE_SEND_ASYNC);
		tmp.put("SendReceiveAsync", ActivityType.SEND_RECEIVE_ASYNC);
		tmp.put("SendAsync", ActivityType.SEND_ASYNC);
		tmp.put("ReceiveAsync", ActivityType.RECEIVE_ASYNC);
		tmp.put("Wait", ActivityType.WAIT);
		ACTIVITY_TYPE_CONSTANTS = Collections.unmodifiableMap(tmp);
	}
	
	@Override
	public TestSuite readLog(String fileName) throws IOException {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			
			Document bptsLogDocument = docBuilder.parse(new File(fileName));
			TestSuite result = parseTestSuite(bptsLogDocument.getDocumentElement(), fileName);
			
			parseTestCases(bptsLogDocument, result);
			
			return result;
		} catch (ParserConfigurationException | SAXException e) {
			throw new IOException(e);
		}
	}

	private void parseTestCases(Document bptsLogDocument, TestSuite result) {
		for(Element testCaseElement : XmlUtil.getElementsByQName(bptsLogDocument.getDocumentElement(), NS_BPTSLOG, "testCase")) {
			TestCase tc = new TestCase();
			result.getTestCases().add(tc);
			String name = testCaseElement.getAttribute("name");
			if(name.startsWith(PREFIX_TESTCASE_NAME)) {
				name = name.substring(PREFIX_TESTCASE_NAME.length());
			}
			tc.setName(name);
			setStatus(tc, testCaseElement);
			
			parsePartnerTracks(testCaseElement, tc);
		}
	}

	private void parsePartnerTracks(Element testCaseElement, TestCase tc) {
		for(Element partnerTrackElement : XmlUtil.getElementsByQName(testCaseElement, NS_BPTSLOG, "partnerTrack")) {
			PartnerTrack pt = new PartnerTrack();
			String name = partnerTrackElement.getAttribute("name");
			if(name.startsWith(PREFIX_PARTNERTRACK_NAME)) {
				name = name.substring(PREFIX_PARTNERTRACK_NAME.length());
			}
			pt.setName(name);
			setStatus(pt, partnerTrackElement);
			
			parseActivities(partnerTrackElement, pt);
			
			if(pt.getActivities().size() > 0) {
				tc.getPartnerTracks().add(pt);
			}
		}
	}

	private void parseActivities(Element ptElement, PartnerTrack pt) {
		for(Element activityElement : XmlUtil.getElementsByQName(ptElement, NS_BPTSLOG, "activity")) {
			Activity a = new Activity();
			pt.getActivities().add(a);
			setStatus(a, activityElement);
			a.setType(ACTIVITY_TYPE_CONSTANTS.get(activityElement.getAttribute("type")));
			
			for(Element dataPackageElement : XmlUtil.getElementsByQName(activityElement, NS_BPTSLOG, "dataPackage")) {
				switch(dataPackageElement.getAttribute("name")) {
				case "Receive Data Package":
					if(a.isCalling()) {
						a.setResponse(extractSoapEnvelopeFromSoapMessageData(dataPackageElement));
					} else {
						a.setRequest(extractSoapEnvelopeFromSoapMessageData(dataPackageElement));
					}
					
					for(Element receiveConditionElement : XmlUtil.getElementsByQName(dataPackageElement, NS_BPTSLOG, "receiveCondition")) {
						Element conditionElement = XmlUtil.getElementsByQName(receiveConditionElement, NS_BPTSLOG, "condition").get(0);
						Assertion assertion = new Assertion();
						setStatus(assertion, receiveConditionElement);
						assertion.setCondition(XmlUtil.getElementsByQName(conditionElement, NS_BPTSLOG, "expression").get(0).getTextContent());
						String expectedValue = XmlUtil.getElementsByQName(conditionElement, NS_BPTSLOG, "expectedValue").get(0).getTextContent();
						assertion.setExpected(expectedValue);
						String actualValue = XmlUtil.getElementsByQName(conditionElement, NS_BPTSLOG, "actualValue").get(0).getTextContent();
						if(actualValue != null && !"".equals(actualValue)) {
							assertion.setActual(actualValue);
						} else {
							assertion.setActual(expectedValue);
						}
						a.getAssertions().add(assertion);
					}
					break;
				case "Send Data Package":
					if(!a.isCalling()) {
						a.setResponse(extractSoapEnvelopeFromSoapMessageData(dataPackageElement));
					} else {
						a.setRequest(extractSoapEnvelopeFromSoapMessageData(dataPackageElement));
					}
					break;
				}
			}
		}
	}

	private Element extractSoapEnvelopeFromSoapMessageData(Element dataPackageElement) {
		for(Element xmlDataElement : XmlUtil.getElementsByQName(dataPackageElement, NS_BPTSLOG, "xmlData")) {
			if("SOAP Message data".equals(xmlDataElement.getAttribute("name"))) {
				NodeList children = xmlDataElement.getChildNodes();
				for(int i = 0; i < children.getLength(); i++) {
					if(children.item(i).getNodeType() == Node.ELEMENT_NODE) {
						return (Element)(children.item(i)).cloneNode(true);
					}
				}
			}
		}
		
		return null;
	}

	private void setStatus(WithPassState object, Element e) {
		List<Element> states = XmlUtil.getElementsByQName(e, NS_BPTSLOG, "state");
		for(Element state : states) {
			switch(state.getAttribute("name")) {
			case "Status Code":
				object.setPassStatus(PassStatus.valueOf(state.getTextContent()));
				break;
			case "Status Message": 
				object.setStatusMessage(state.getTextContent());
				break;
			case "Exception": 
				object.setException(state.getTextContent());
				break;
			}
		}
	}
	
	private TestSuite parseTestSuite(Element e, String fileName) {
		TestSuite result = new TestSuite();
		result.setName(fileName);
		
		setStatus(result, e);
		
		return result;
	}
	
}
