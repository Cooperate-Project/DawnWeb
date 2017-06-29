package de.cooperateproject.cdo.dawn.rest.api;

import org.eclipse.gmf.runtime.notation.Diagram;

public interface DiagramService {

	Diagram getDiagram(String projectId, String modelId);

	String getPath(String projectId, String modelId);

}
