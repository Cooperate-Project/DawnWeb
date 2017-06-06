package de.cooperateproject.cdo.dawn.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;
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

import de.cooperateproject.cdo.dawn.dto.Diagram;
import de.cooperateproject.cdo.dawn.dto.Model;
import de.cooperateproject.cdo.dawn.dto.Project;
import de.cooperateproject.cdo.dawn.rest.api.BrowseService;
import de.cooperateproject.cdo.dawn.session.CDOConnectionManager;

@Path("/browse")
@Produces(MediaType.APPLICATION_JSON)
public class BrowseServiceImpl implements BrowseService {

	@Override
	public List<Project> getProjects() {
		final List<Project> result = new ArrayList<Project>();

		// Get Session
		CDOConnectionManager cman = CDOConnectionManager.INSTANCE;
		CDOSession session = cman.acquireSession();

		// Get Content
		CDOView view = session.openView();
		CDOResource root = view.getRootResource();
		EList<EObject> rootContents = root.getContents();

		// Get Folders
		EresourceSwitch<Project> folderSwitch = new EresourceSwitch<Project>() {
			
//			@Override
//			public Project caseCDOResource(CDOResource object) {
//				// TODO Auto-generated method stub
//				return super.caseCDOResource(object);
//			}

			@Override
			public Project caseCDOResourceFolder(CDOResourceFolder folder) {
				Project p = new Project();
				p.setName(folder.getName());

				// Get Files ( = models)
				EList<CDOResourceNode> files = folder.getNodes();

				for (CDOResourceNode file : files) {
					if (file.getName().endsWith(".notation")) {
						Model model = new Model();
						// TODO: file.getURI().trimFileExtension()
						model.setName(file.getName().substring(0, file.getName().lastIndexOf(".notation")));

						CDOResource res = view.getResource(file.getURI().path()); // TODO: Cast
						
						// FIXME: Java 8 Style using Streams?
						
						// TODO: Fix project setup
						List<EObject> diagrams = res.getContents();
						
//						for (Object o : res.getContents())
//					    {
//							System.out.println(o);
//					      if (o instanceof org.eclipse.gmf.runtime.notation.Diagram)
//					      {
//					    	  diagrams.add(o);
//					      }
//					    }
						
						p.addModel(model);
					}
				}

				return p;
			}
		};

		for (EObject node : rootContents) {

			// Only add projects ( = folders)
			Project project = folderSwitch.doSwitch(node);

			if (project != null)
				result.add(project);

		}

		return result;
	}

	@Override
	public Project getProject(String projectId) {

		List<Project> allProjects = getProjects();

		for (Project project : allProjects) {
			if (project.getName().equals(projectId)) {
				return project;
			}

		}
		return null;
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
		
		List<Model> allModels = getModels(projectId);
		
		for (Model model: allModels) {
			if(model.getName().equals(modelId)) {
				return model;
			}
		}
		return null;
	}

	@Override
	public List<Diagram> getDiagrams(String projectId, String modelId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Diagram getDiagram(String projectId, String modelId, String diagramId) {
		// TODO Auto-generated method stub
		return null;
	}

}
