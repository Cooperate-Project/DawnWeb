package de.cooperateproject.cdo.dawn.rest.impl;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;

import de.cooperateproject.cdo.dawn.dto.Model;
import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.session.CDOConnectionManager;

@Produces(MediaType.APPLICATION_JSON)
public class DiagramServiceImpl implements DiagramService {

	@Override
	public Diagram getDiagram(String projectId, String modelId) {

		// Get session
		CDOConnectionManager connectionManager = CDOConnectionManager.INSTANCE;
		CDOSession session = connectionManager.acquireSession();

		// Get content
		CDOView view = session.openView();
		String path = String.format("%s/%s.%s", projectId, modelId, Model.MODEL_FILE_EXTENSION);

		if (view.hasResource(path)) {
			CDOResource ressource = view.getResource(path);

			// Get diagram
			for (EObject eObject : ressource.getContents()) {
				if (eObject instanceof Diagram) {

					Diagram diagram = (Diagram) eObject;

					return diagram;
				}
			}
		}
		return null;
	}

}
