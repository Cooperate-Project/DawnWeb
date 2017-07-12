package de.cooperateproject.cdo.dawn.rest.api;

import org.eclipse.gmf.runtime.notation.Diagram;

public interface DiagramService {

	Diagram getDiagram(String projectId, String modelId);

	String getAbsolutePath(String projectId, String modelId);
	
	boolean deleteView(String projectId, String modelId, String uuid);
	
	boolean changeFeature(String projectId, String modelId, String uuid, String featureId, String value);
	
	boolean addClass(String projectId, String modelId, String className, int x, int y);
	
	long getLastChanged(String projectId, String modelId);

}
