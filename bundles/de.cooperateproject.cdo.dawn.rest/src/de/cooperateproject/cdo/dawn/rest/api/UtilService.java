package de.cooperateproject.cdo.dawn.rest.api;

/**
 * A util service provides miscellaneous utility methods.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public interface UtilService {

	/**
	 * Returns the current time stamp of the running service instance.
	 * 
	 * @return
	 */
	long getCurrentServerTimestamp();

}
