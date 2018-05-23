package com.example.dikstras.model;


/**
 * This model implements Edge of Graph
 * Source and destinations of the edge is mapped from Vertex class
 * oneway defines if the Edge is unidirectional of bidirectional
 * weight defines the distance associated with the vertex
 * @author Som
 *
 */
public class Edge {
	
	private int id;
	private Vertex source;
	private Vertex destination;
	private String name;
	private double weight;
	private boolean oneway;

	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public void setDestination(Vertex destination) {
		this.destination = destination;
	}

	public void setOneway(boolean oneway) {
		this.oneway = oneway;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public Vertex getDestination() {
		return destination;
	}

	public Vertex getSource() {
		return source;
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return source.toString() + " - " + destination.toString();
	}

	public boolean isOneway() {
		return oneway;
	}
	

}
