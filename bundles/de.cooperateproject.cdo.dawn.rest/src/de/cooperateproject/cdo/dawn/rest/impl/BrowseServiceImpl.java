package de.cooperateproject.cdo.dawn.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.eresource.CDOResourceFolder;
import org.eclipse.emf.cdo.eresource.CDOResourceNode;
import org.eclipse.emf.cdo.eresource.util.EresourceSwitch;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import de.cooperateproject.cdo.dawn.dto.Model;
import de.cooperateproject.cdo.dawn.dto.Project;
import de.cooperateproject.cdo.dawn.rest.api.BrowseService;
import de.cooperateproject.cdo.dawn.session.CDOConnectionManager;

@Produces(MediaType.APPLICATION_JSON)
public class BrowseServiceImpl implements BrowseService {

	private EresourceSwitch<CDOResourceFolder> folderSwitch = new EresourceSwitch<CDOResourceFolder>() {
		@Override
		public CDOResourceFolder caseCDOResourceFolder(CDOResourceFolder folder) {
			return folder;
		}
	};

	private EresourceSwitch<CDOResourceNode> fileSwitch = new EresourceSwitch<CDOResourceNode>() {
		@Override
		public CDOResourceNode caseCDOResourceNode(CDOResourceNode node) {
			return node;
		}
	};

	@Override
	public List<Project> getProjects() {
		final List<Project> projects = new ArrayList<Project>();

		// Get Session
		CDOConnectionManager connectionManager = CDOConnectionManager.INSTANCE;
		CDOSession session = connectionManager.acquireSession();

		// Get Content
		CDOView view = session.openView();
		CDOResource root = view.getRootResource();
		EList<EObject> rootContents = root.getContents();

		// Get folders ( = projects)
		for (EObject rootNode : rootContents) {
			CDOResourceFolder folder = folderSwitch.doSwitch(rootNode);

			if (folder != null) {

				Project project = new Project();
				project.setName(folder.getName());

				// Get files ( = models)
				for (CDOResourceNode node : folder.getNodes()) {
					CDOResourceNode file = fileSwitch.doSwitch(node);

					if (file != null && file.getURI().fileExtension().equalsIgnoreCase(Model.MODEL_FILE_EXTENSION)) {

						Model model = new Model();
						model.setName(file.trimExtension());

						project.addModel(model);

					}
				}
				projects.add(project);
			}
		}
		return projects;

	}

	@Override
	public Project getProject(String projectId) {
		return getProjects().stream().filter((project) -> project.getName().equals(projectId)).findFirst().orElse(null);
	}

	@Override
	public List<Model> getModels(String projectId) {

		Project project = getProject(projectId);

		if (project != null) {
			return project.getModels();
		}
		return null;
	}

	@Override
	public Model getModel(String projectId, String modelId) {
		return getModels(projectId).stream().filter((model) -> model.getName().equals(modelId)).findFirst()
				.orElse(null);
	}

}
