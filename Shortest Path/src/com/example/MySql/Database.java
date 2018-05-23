package com.example.MySql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.example.dikstras.model.Edge;
import com.example.dikstras.model.Route;
import com.example.dikstras.model.Vertex;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


/**
 *  This class work as data source for the application
 *  It uses MySQL database as database server
 * @author crestaSom
 *
 */
public class Database {
	String url = "jdbc:mysql://localhost:3306/college_project";
	String username = "root";
	String password = "";
	String sql;
	String stop = "";
	Connection con;

	public Database() {
		try {
			con = (Connection) DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	public List<Vertex> getAllVertex() throws SQLException {
		List<Vertex> vertexes = new LinkedList<Vertex>();
		Vertex v;

		String sql = "select * from stops";
		PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (rs.next()) {
			v = new Vertex();
			v.setId(rs.getInt(1));
			v.setName(rs.getString(2));
			v.setLatCode(rs.getDouble(3));
			v.setLatCode(rs.getDouble(4));
			vertexes.add(v);
		}
		rs.close();
		return vertexes;

	}

	public Vertex getVertex(int id) throws SQLException {
		Vertex v = null;

		String sql = "select * from stops where id=" + id;
		// String stop="";
		PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			v = new Vertex();
			v.setId(rs.getInt(1));
			v.setName(rs.getString(2));
			v.setLatCode(rs.getDouble(3));
			v.setLatCode(rs.getDouble(4));
		}
		rs.close();

		return v;
	}

	public Edge getEdge(int id) throws SQLException {
		Edge e = null;
		int tempId;
		Vertex source, dest;
		if (id < 0) {
			tempId = -id;
		} else {
			tempId = id;
		}
		String sql = "select * from edges where id=" + tempId;
		PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			e = new Edge();
			e.setId(rs.getInt(1));
			e.setName(rs.getString(2));
			source = getVertex(Integer.parseInt(rs.getString(3)));
			dest = getVertex(Integer.parseInt(rs.getString(4)));
			if (id < 0) {
				e.setSource(dest);
				e.setDestination(source);
			} else {
				e.setSource(source);
				e.setDestination(dest);
			}
		}
		rs.close();

		return e;
	}

	public List<Edge> getAllEdge() {
		List<Edge> edges = new LinkedList<Edge>();
		Edge e;
		try {
			String sql = "select * from edges";
			PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				e = new Edge();
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setSource(getVertex(rs.getInt(3)));
				e.setDestination(getVertex(rs.getInt(4)));
				e.setWeight(rs.getDouble(5));
				e.setOneway(rs.getBoolean(6));
				edges.add(e);
			}
			rs.close();
			return edges;
		} catch (SQLException ex) {
			System.out.println(ex.getLocalizedMessage());

		}
		return edges;
	}

	public List<Route> getallRoute() {
		List<Route> routes = new ArrayList<Route>();
		List<Integer> edges;
		List<String> edgeNames = new ArrayList<String>();
		List<String> items = null;
		Route r;
		String ids, edgeDetail;
		try {
			String sql = "select * from route";

			PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				edges = new ArrayList<Integer>();
				items = new ArrayList<String>();
				r = new Route();
				r.setId(rs.getInt(1));
				r.setName(rs.getString(2));
				ids = rs.getString(3);

				items = Arrays.asList(ids.split("\\s*,\\s*"));
				for (String id : items) {
					edgeDetail = getEdgeDetail(Integer.parseInt(id));
					edgeNames.add(edgeDetail);
					edges.add(Integer.parseInt(id));
				}
				r.setAllEdges(edges);
				routes.add(r);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return routes;
	}

	public String getEdgeDetail(int id) throws SQLException {
		String detail = "";
		Edge e = getEdge(id);
		detail = e.getSource().getName() + "-" + e.getDestination().getName();
		return detail;
	}

}
