package com.example.dikstras.implementation;

import java.awt.Dimension;
import java.io.Console;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import com.example.MySql.Database;
import com.example.dikstras.model.Edge;
import com.example.dikstras.model.Graph;
import com.example.dikstras.model.Route;
import com.example.dikstras.model.Vertex;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class MainClass {

	public static void main(String[] argv) throws IOException, SQLException {
		while (true) {
			@SuppressWarnings("resource")
			Database db = new Database();
			List<Route> routes = new ArrayList<Route>();
			routes=db.getallRoute();
			//System.out.println(routes);
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter Source");
			String source = scanner.nextLine().toString();
			System.out.println("Enter Destination");
			String dest = scanner.nextLine().toString();
			int sid = 0, did = 0;
//			List<Vertex> path = new LinkedList<Vertex>();
			List<Edge> path = new LinkedList<Edge>();
			List<Edge> edges = new ArrayList<Edge>();
			List<Vertex> vertexes = new ArrayList<Vertex>();
			
			
			vertexes = db.getAllVertex();
			edges = db.getAllEdge();
			for (Vertex v : vertexes) {
				if (v.getName().equals(source)) {
					sid = vertexes.indexOf(v);
				} else if (v.getName().equals(dest)) {
					did = vertexes.indexOf(v);
				}
			}
			Graph graph = new Graph(vertexes, edges);
			DikstrasAlgorithm imp = new DikstrasAlgorithm(graph);
			imp.execute(vertexes.get(sid), vertexes.get(did));
			path = imp.getPathEdge(vertexes.get(did));
		//	db.closeSessionFactory();
		//	Vertex v = vertexes.get(8);
//			if (path != null) {
//				for (Vertex nextPoint : path) {
//					System.out.println(nextPoint.getName() + " ");
//				}
//				System.out.println("\nTotal Path Cost is "
//						+ imp.getTotalDistance());
//				System.out.println("Total iteration no is " + imp.getI());
//			} else {
//				System.out.println("No Path exists");
//			}
			
			//System.out.println(path);
			
			if (path != null) {
				for (Edge nextEdge : path) {
					System.out.println(nextEdge.toString() + "\n");
				}
				System.out.println("\nTotal Path Cost is "
						+ imp.getTotalDistance());
				System.out.println("Total iteration no is " + imp.getI());
			} else {
				System.out.println("No Path exists");
			}
			
		}

	}
}
