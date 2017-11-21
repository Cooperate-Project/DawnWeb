package de.cooperateproject.cdo.dawn.rest.util;

import java.util.Collections;
import java.util.Optional;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.notation.View;

import de.cooperateproject.cdo.dawn.dto.Model;
import de.cooperateproject.cdo.dawn.registry.DawnResourceRegistry;
import de.cooperateproject.cdo.dawn.session.CDOConnectionManager;
import de.cooperateproject.cdo.dawn.session.DawnServerConfig;

public class DiagramUtil {

	private static final String sessionId = "0";

	public static Optional<CDOResource> getResource(String projectId, String modelId) {

		Optional<CDOResource> returnValue = Optional.empty();

		// Get session
		CDOConnectionManager connectionManager = CDOConnectionManager.INSTANCE;
		CDOSession session = connectionManager.acquireSession();

		// Get content
		CDOView view = session.openView();
		String path = String.format("%s/%s.%s", projectId, modelId, Model.MODEL_FILE_EXTENSION);

		if (view.hasResource(path)) {
			returnValue = Optional.of(view.getResource(path));
		}

		return returnValue;
	}

	public static URI getRessourceURI(String projectId, String modelId) {
		Optional<CDOResource> resource = getResource(projectId, modelId);

		if (resource.isPresent()) {
			return URI.createURI(
					"cdo://" + DawnServerConfig.getInstance().getCDORepository() + resource.get().getURI().path());
		} else {
			return null;
		}
	}

	/**
	 * Adds a new class with the given class name to the diagram at the
	 * specified position.
	 *
	 * @param newClassName
	 *            The name of the class to be created.
	 * @param posX
	 *            The position of the class to be created on the x-axis
	 *            (horizontal).
	 * @param posY
	 *            The position of the class to be created on the y-axis
	 *            (vertical).
	 * @return <code>true</code> if operation succeeded, <code>false</code>
	 *         otherwise
	 */
	public static boolean addClass(URI resourceURI, String newClassName, int posX, int posY) {
		CDOResource resource = DawnResourceRegistry.instance.getResource(resourceURI, sessionId);
		CDOView cdoView = resource.cdoView();

		if (!checkTransaction(cdoView)) {
			return false;
		}

		DawnWebGMFUtil.addClassToResource(resource, newClassName, posX, posY);

		try {
			resource.save(Collections.EMPTY_MAP);
			((CDOTransaction) cdoView).commit();
		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	/**
	 * Changes a feature to a specified value of the element with the given
	 * UUID.
	 *
	 * @param uuid
	 *            The UUID of the element to be altered.
	 * @param featureId
	 *            The ID of the feature to be altered according to
	 *            EStructuralFeatures.
	 * @param value
	 *            The target value of the feature.
	 * @return <code>true</code> if operation succeeded, <code>false</code>
	 *         otherwise
	 */
	public static boolean changeFeature(URI resourceURI, String uuid, int featureId, String value) {
		CDOResource resource = DawnResourceRegistry.instance.getResource(resourceURI, sessionId);
		CDOView cdoView = resource.cdoView();

		if (!checkTransaction(cdoView)) {
			return false;
		}

		EObject element = CDOUtil.getEObject(DawnWebUtil.getObjectFromId(uuid, cdoView));
		((View) element).getElement().eSet(getFeatureFromId(element, featureId), value);

		try {
			((CDOTransaction) cdoView).commit();
		} catch (CommitException ex) {
			return false;
		}

		return true;
	}

	/**
	 * Deletes a node in the diagram (= class).
	 *
	 * @param uuid
	 *            The UUID of the element to be deleted.
	 * @return <code>true</code> if operation succeeded, <code>false</code>
	 *         otherwise
	 */
	public static boolean deleteNode(URI resourceURI, String uuId) {
		CDOResource resource = DawnResourceRegistry.instance.getResource(resourceURI, sessionId);
		CDOID cdoId = CDOIDUtil.read(uuId);
		CDOView cdoView = resource.cdoView();

		if (!checkTransaction(cdoView)) {
			return false;
		}

		View view = (View) CDOUtil.getEObject(cdoView.getObject(cdoId));
		DawnWebGMFUtil.deleteViewInResource(resource, view);

		try {
			((CDOTransaction) cdoView).commit();
		} catch (CommitException ex) {
			return false;
		}

		return true;
	}

	/**
	 * Gets the corresponding EStructuralFeature from a EObject with a specified
	 * ID.
	 *
	 * @param element
	 *            The EObject to get the EStructuralFeature from.
	 * @param id
	 *            The ID of the EStructuralFeature to get.
	 * @return The EStructuralFeature with the specified ID.
	 */
	private static EStructuralFeature getFeatureFromId(EObject element, int id) {
		for (EStructuralFeature attr : element.eClass().getEAllStructuralFeatures()) {
			if (attr.getFeatureID() == id) {
				return attr;
			}
		}
		return null;
	}

	/**
	 * Checks whether the given CDO View is a transaction.
	 *
	 * @param cdoView
	 *            The CDO View to check for.
	 */
	private static boolean checkTransaction(CDOView cdoView) {
		return cdoView instanceof CDOTransaction;
	}

	public static long getLastChanged(URI resourceURI) {

		DawnResourceRegistry.instance.getResource(resourceURI, sessionId);

		CDOResource resource = DawnResourceRegistry.instance.getResource(resourceURI, sessionId, false);// view.getResource(uri.path());

		if (resource == null) {
			throw new RuntimeException("Resource could not be found: " + resourceURI.path());
		}

		return DawnResourceRegistry.instance.getLastChanged(resourceURI, sessionId);
	}

}
