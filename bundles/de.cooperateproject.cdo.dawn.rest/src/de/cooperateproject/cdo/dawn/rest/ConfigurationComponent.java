package de.cooperateproject.cdo.dawn.rest;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class ConfigurationComponent {

	ConfigurationAdmin ca;
    @Reference void setCA(ConfigurationAdmin ca) { this.ca = ca; }
	
	@Activate void activate() {
		System.out.println("Hello Component!");
	}
	
}
