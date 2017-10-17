package de.cooperateproject.cdo.dawn.rest.api;

/**
 * The test service is only used to test the server connection.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public interface TestService {

	/**
	 * Connection testing method, returns a simple string.
	 * 
	 * @return a simple, non-empty string
	 */
	public String getTestResponse();

}
