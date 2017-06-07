package de.cooperateproject.cdo.dawn.dto;

import java.util.ArrayList;
import java.util.List;

public class Model {
	
	public Model() {
		this.diagrams = new ArrayList<Diagram>();
	}

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	List<Diagram> diagrams;
	
	public void addDiagram(Diagram diagram) {
		this.diagrams.add(diagram);
	}
	
	public List<Diagram> getDiagrams() {
		return diagrams;
	}

}
