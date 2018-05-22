package com.example.dikstras.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

public class Edge {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne(cascade=javax.persistence.CascadeType.ALL)
	private Vertex source;
	@ManyToOne(cascade=javax.persistence.CascadeType.ALL)
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
