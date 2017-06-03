package de.cooperateproject.cdo.dawn.test;

import org.junit.Test;

import de.cooperateproject.cdo.dawn.rest.BrowseService;

public class TestSession {

	
	@Test
	public void testBrowse() {
		BrowseService ser = new BrowseService();
		
		ser.getProjects();
		
	}
	
}
