package com.example.dikstras.model;

import java.util.List;

public class Route {
	private int id;
	private String name;
	private List<Integer> allEdges;
	
	
	public List<Integer> getAllEdges() {
		return allEdges;
	}
	public void setAllEdges(List<Integer> allEdges) {
		this.allEdges = allEdges;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Route [name=" + name + ", allEdges=" + allEdges + "]";
	}
	
	
}
