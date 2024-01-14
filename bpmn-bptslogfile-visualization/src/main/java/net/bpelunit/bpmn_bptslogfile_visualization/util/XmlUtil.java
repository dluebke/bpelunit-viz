package net.bpelunit.bpmn_bptslogfile_visualization.util;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtil {

	public static List<Element> getElementsByQName(Element e, String namespaceURI,
			String localName) {
		List<Element> result = new ArrayList<>();
				
		NodeList children = e.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if(
				n.getNodeType() == Node.ELEMENT_NODE && 
				namespaceURI.equals(n.getNamespaceURI()) && 
				localName.equals(n.getLocalName())
			) {
				result.add((Element)n);
			}
		}
				
		return result;
	}

}
