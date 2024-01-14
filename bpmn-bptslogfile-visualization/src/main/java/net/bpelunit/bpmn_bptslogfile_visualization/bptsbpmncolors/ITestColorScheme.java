package net.bpelunit.bpmn_bptslogfile_visualization.bptsbpmncolors;

import com.digitalsolutionarchitecture.bpmn.di.bpmn.Color;

import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.WithPassState.PassStatus;

public interface ITestColorScheme {

	Color getBackgroundColorForPassStatus(PassStatus extension);

	Color getBorderColorForPassStatus(PassStatus extension);

}
