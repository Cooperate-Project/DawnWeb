package de.cooperateproject.cdo.dawn.rest.impl;

import java.util.Date;

import de.cooperateproject.cdo.dawn.rest.api.UtilService;

public class UtilServiceImpl implements UtilService {

	@Override
	public long getCurrentServerTimestamp() {
		return new Date().getTime();
	}

}
