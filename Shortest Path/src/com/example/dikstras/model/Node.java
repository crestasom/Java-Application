package com.example.dikstras.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Node implements Comparable<Node> {

	private final char label;
	private int distanceFromSource;
	private final Map<Node, Integer> neighbourList;

	public Node(char label, int distanceFromSource) {
		this.label = label;
		this.distanceFromSource = distanceFromSource;
		this.neighbourList = new LinkedHashMap<>();
	}

	public void addNeighbourer(Node node, int distance) {
		neighbourList.put(node, distance);
	}

	public char getLabel() {
		return label;
	}

	public Map<Node, Integer> getNeighbourerList() {
		return neighbourList;
	}

	public int getDistanceFromSource() {
		return distanceFromSource;
	}

	public void setDistanceFromSource(int distanceFromSource) {
		this.distanceFromSource = distanceFromSource;
	}

	@Override
	public int compareTo(Node o) {
		return Integer.compare(this.getDistanceFromSource(),
				o.getDistanceFromSource());
	}

}
