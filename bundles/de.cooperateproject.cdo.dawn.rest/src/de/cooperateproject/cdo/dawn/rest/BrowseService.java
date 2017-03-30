package de.cooperateproject.cdo.dawn.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

import de.cooperateproject.cdo.dawn.dto.Project;
import de.cooperateproject.cdo.dawn.session.CDOConnectionManager;

@Path("/browse")
@Produces(MediaType.APPLICATION_JSON)
public class BrowseService {

	@GET
	public List<Project> getProjects() {
		CDOConnectionManager cman = CDOConnectionManager.INSTANCE;
		CDOSession session = cman.acquireSession();
		final List<Project> result = new ArrayList<Project>();

		CDOView view = session.openView();
		CDOResource root = view.getRootResource();
		EList<EObject> rootContents = root.getContents();

		EresourceSwitch<Project> folderSwitch = new EresourceSwitch<Project>() {
			@Override
			public Project caseCDOResourceFolder(CDOResourceFolder folder) {
				Project p = new Project();
				p.setName(folder.getName());
				return p;
			}
		};

		
		for (EObject node : rootContents) {
			result.add(folderSwitch.doSwitch(node));
			
		}

		return result;
	}

	@GET
	@Path("/{projectid}")
	public Project getProject(@PathParam("projectid") String projectId) {
		return null;
	}

}
