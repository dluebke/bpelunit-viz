package net.bpelunit.bpmn_bptslogfile_visualization.bptsbpmncolors;

import java.util.HashMap;
import java.util.Map;

import com.digitalsolutionarchitecture.bpmn.di.bpmn.Color;

import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.WithPassState.PassStatus;

/**
 * Class with default colors and a template method mechanism to easily derive own color schemas: Simply create a subclass
 * and override the createXXXColor methods to return a different color
 */
public class DefaultColorScheme implements ITestColorScheme {

	private Map<PassStatus, Color> borderColorForPassStatus = new HashMap<>();
	private Map<PassStatus, Color> backgroundColorForPassStatus = new HashMap<>();

	public DefaultColorScheme() {
		borderColorForPassStatus.put(PassStatus.PASSED, createPassedBorderColor());
		borderColorForPassStatus.put(PassStatus.ERROR, createErrorBorderColor());
		borderColorForPassStatus.put(PassStatus.FAILED, createFailedBorderColor());
		borderColorForPassStatus.put(PassStatus.ABORTED, createAbortedBorderColor());
		borderColorForPassStatus.put(PassStatus.NOTYETSPECIFIED, createNotYetSpecifiedBorderColor());

		backgroundColorForPassStatus.put(PassStatus.PASSED, createPassedBackgroundColor());
		backgroundColorForPassStatus.put(PassStatus.ERROR, createErrorBackgroundColor());
		backgroundColorForPassStatus.put(PassStatus.FAILED, createFailedBackgroundColor());
		backgroundColorForPassStatus.put(PassStatus.ABORTED, createAbortedBackgroundColor());
		backgroundColorForPassStatus.put(PassStatus.NOTYETSPECIFIED, createNotYetSpecifiedBackgroundColor());
	}

	protected Color createNotYetSpecifiedBackgroundColor() {
		return new Color(255, 255, 255);
	}

	protected Color createAbortedBackgroundColor() {
		return new Color(255, 224, 178);
	}
	
	protected Color createFailedBackgroundColor() {
		return new Color(255, 205, 210);
	}

	protected Color createErrorBackgroundColor() {
		return new Color(255, 205, 210);
	}

	protected Color createPassedBackgroundColor() {
		return new Color(200, 230, 201);
	}

	protected Color createNotYetSpecifiedBorderColor() {
		return new Color(150, 150, 150);
	}

	protected Color createAbortedBorderColor() {
		return new Color(251, 140, 0);
	}

	protected Color createErrorBorderColor() {
		return new Color(229, 57, 53);
	}
	
	protected Color createFailedBorderColor() {
		return new Color(229, 57, 53);
	}

	protected Color createPassedBorderColor() {
		return new Color(67, 160, 71);
	}

	@Override
	public final Color getBackgroundColorForPassStatus(PassStatus extension) {
		return backgroundColorForPassStatus.get(extension);
	}

	@Override
	public final Color getBorderColorForPassStatus(PassStatus extension) {
		return borderColorForPassStatus.get(extension);
	}

}
