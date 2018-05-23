package com.example.dikstras.implementation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.example.MySql.Database;
import com.example.dikstras.model.Edge;
import com.example.dikstras.model.Vertex;

public class MainClass {

	public static void main(String[] argv) throws IOException, SQLException {
		Scanner scanner;
		while (true) {
			
			Database db = new Database();
			scanner = new Scanner(System.in);
			System.out.println("Enter Source");
			String source = scanner.nextLine();
			System.out.println("Enter Destination");
			String dest = scanner.nextLine();
			int sid = 0, did = 0;
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
			DikstrasAlgorithm imp = new DikstrasAlgorithm(vertexes, edges);
			imp.execute(vertexes.get(sid), vertexes.get(did));
			path = imp.getPathEdge(vertexes.get(did));
			
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