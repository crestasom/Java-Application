package com.example.dikstras.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.example.dikstras.model.Edge;
import com.example.dikstras.model.Route;
import com.example.dikstras.model.Vertex;
import com.example.dikstras.model.Graph;

/**
 * This class implements the dijkstra's algorithm for finding the shortest route
 * @author Som
 *
 */
public class DikstrasAlgorithm {

	private final List<Vertex> nodes;
	private final List<Edge> edges;
	/**
	 * settledNodes stores all the nodes for which the shortest path has been determined
	 */
	private Set<Vertex> settledNodes;
	/**
	 * unSettledNodes stores all the nodes for which the shortest path is yet to be determined
	 */
	private Queue<Vertex> unSettledNodes;
	/**
	 * predecessors store the immediate predecessor. 
	 * This is used for backtracking the path,
	 *  once shortest path is determined from source to destination.
	 */
	private Map<Vertex, Vertex> predecessors;
	private Map<Vertex, Double> distance;
	private double totalDistance;
	private List<Route> allRoutes;
	
	int i=0;

	public DikstrasAlgorithm(Graph graph) {
		// create a copy of the array so that we can operate on this array
		this.nodes = new ArrayList<Vertex>(graph.getVertexes());
		this.edges = new ArrayList<Edge>(graph.getEdges());
		totalDistance=0.00;
	}

	public int getI() {
		return i;
	}

	/**
	 * This method returns the total distance of the shortest route calculated
	 * @return
	 */
	public double getTotalDistance() {
		return totalDistance;
	}

	/**
	 * This method is responsible for calculating the shortest path from source to destination
	 * @param source: Starting vertex
	 * @param destination: Ending vertex
	 */
	public void execute(Vertex source,Vertex destination) {
		settledNodes = new HashSet<Vertex>();
		//unSettledNodes = new HashSet<Vertex>();
		unSettledNodes=new PriorityQueue<Vertex>();
		distance = new HashMap<Vertex, Double>();
		predecessors = new HashMap<Vertex, Vertex>();
		//distance.put(source, 0.00);
		//unsetteledNodes.add(source);
		source.setDistanceFromSource(0.00);
		unSettledNodes.add(source);
		//while (!unSettledNodes.isEmpty() && !isSettled(destination) ) {
		while (!unSettledNodes.isEmpty() ) {
			Vertex node = unSettledNodes.poll();
			//node.setDistanceFromSource(0.00);
			settledNodes.add(node);
			//unSettledNodes.remove(node);
			findMinimalDistances(node);
			//System.out.println(node+"\n");
			//System.out.println(unSettledNodes);
		}
		System.out.println("1"+distance);
	}

	/**
	 * This method calculates and updates the minimum distance of all the adjacent vertex from source for a given vertex
	 * @param node
	 */
	private void findMinimalDistances(Vertex node) {
		List<Vertex> adjacentNodes = getNeighbors(node);
		//System.out.println(adjacentNodes);
		for (Vertex target : adjacentNodes) {
			//System.out.println(getShortestDistance(target));
			if (getShortestDistance(target) > getShortestDistance(node)
					+ getDistance(node, target)) {
				if(!unSettledNodes.contains(target)){
				target.setDistanceFromSource(getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				
				unSettledNodes.add(target);
				}
			}
		}

	}

	private double getDistance(Vertex node, Vertex target) {
		for (Edge edge : edges) {
			if (edge.getSource().equals(node)
					&& edge.getDestination().equals(target)) {
				return edge.getWeight();
			}else if(edge.getSource().equals(target)
					&& edge.getDestination().equals(node)){
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	/**
	 * This method returns the neighbor vertices of the given vertex
	 * @param node
	 * @return
	 */
	private List<Vertex> getNeighbors(Vertex node) {
		List<Vertex> neighbors = new ArrayList<Vertex>();
		Iterator<Edge> edgeIterator=edges.iterator();
		while(edgeIterator.hasNext()){
			Edge edge=edgeIterator.next();
			if (edge.getSource().equals(node)
					&& !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}else if(edge.getDestination().equals(node) && !isSettled(edge.getSource()) && !edge.isOneway()){
				neighbors.add(edge.getSource());
			}	
		}
		return neighbors;
		
	}

	private Vertex getMinimum(Queue<Vertex> vertexes) {
		Vertex minimum = null;
		minimum=vertexes.poll();
		
//		for (Vertex vertex : vertexes) {
//			if (minimum == null) {
//				minimum = vertex;
//			} else {
//				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
//					minimum = vertex;
//				}
//			}
//		}
		//System.out.println(String.valueOf(minimum));
		
		return minimum;
	}

	private boolean isSettled(Vertex vertex) {
		return settledNodes.contains(vertex);
	}

	private double getShortestDistance(Vertex destination) {
		Double d = destination.getDistanceFromSource();
		if (d == null) {
			return Double.MAX_VALUE;
		} else {
			return d;
		}
	}
	
public double getTotalDistance(Vertex source,Vertex dest){
	return totalDistance;
		
	}

	/*
	 * This method returns the path from the source to the selected target and
	 * NULL if no path exists
	 */
	public LinkedList<Vertex> getPath(Vertex target) {
		double distance;
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		Vertex step = target,temp;
		Edge e=new Edge();
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			temp=step;
			step = predecessors.get(step);
			distance=getDistance(temp,step);
			totalDistance+=distance;
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}
	public LinkedList<Edge> getPathEdge(Vertex target) {
		double distance;
		LinkedList<Edge> path = new LinkedList<Edge>();
		Vertex step = target,temp;
		Edge e=new Edge();
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		//path.add(step.getName());
		while (predecessors.get(step) != null) {
			temp=step;
			step = predecessors.get(step);
			distance=getDistance(temp,step);
			e=getEdge(temp, step);
			path.add(e);
			totalDistance+=distance;
			
			//path.add(step.toString());
			//System.out.println("i++"+step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}
	
	public Edge getEdge(Vertex source,Vertex dest){
		Edge e=null;
		for(Edge e1:edges){
			if(e1.getSource().equals(source) && e1.getDestination().equals(dest) || (e1.getSource().equals(dest) && e1.getDestination().equals(source))){
				e=e1;
			}
		}
		return e;
	}
	
	public Route getRoute(int id){
		Route r = null;
		return r;
	}
	public List<Route> getRealRoute(){
		List<Route> routes=null;
		return routes;
	}

}
