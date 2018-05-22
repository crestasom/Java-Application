package com.example.MySql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import antlr.CharStreamException;
import antlr.InputBuffer;

import com.example.dikstras.model.Edge;
import com.example.dikstras.model.Route;
import com.example.dikstras.model.Vertex;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import edu.uci.ics.jung.graph.util.Pair;

public class Database {
	Session session;
	SessionFactory sf;
	String url = "jdbc:mysql://localhost:3306/college_project";
	String username = "root";
	String password = "shrestha";
	String sql;
	String stop = "";
	Connection con;

	// Vertex stop;

	public Database() {

		url = "jdbc:mysql://localhost:3306/college_project";
		username = "root";
		password = "shrestha";

		try {
			con = (Connection) DriverManager.getConnection(url, username,
					password);
			System.out.println("Database connected!");
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
		//
	}

	public void closeSessionFactory() {
		sf.close();
	}

	public Session getSession() {
		if (session == null) {
			sf = new Configuration().configure().buildSessionFactory();
			return sf.openSession();
		}

		return sf.openSession();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Vertex> getAllVertex() throws SQLException {
		// try{
		List<Vertex> vertexes = new LinkedList<Vertex>();
		Vertex v;

		// try {
		// session = getSession();
		//
		// session.beginTransaction();
		// vertexes = session.createCriteria(Vertex.class).list();
		// session.getTransaction().commit();
		// session.close();
		// return vertexes;
		// } catch (HibernateException ex) {
		// System.out.println(ex.getMessage());
		// }
		// return null;

		String sql = "select * from stops";
		// String stop="";
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
			// stop=rs.
		}
		// System.out.println(stop);
		rs.close();
		return vertexes;
		// }catch(SQLException ex){
		// System.out.println(ex.getCause());
		// }

	}

	public Vertex getVertex(int id) throws SQLException {
		Vertex v = null;
		// session = getSession();
		// session.beginTransaction();
		// Criteria criteria = session.createCriteria(Vertex.class).add(
		// Restrictions.eq("id", id));
		// Object result = criteria.uniqueResult();
		// if (result != null) {
		// v = (Vertex) result;
		// }
		//
		// // vertexes=session.createCriteria(Vertex.class).list();
		// session.getTransaction().commit();
		// session.close();
		//
		// return v;
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
		// String stop="";
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
		// session = getSession();
		// session.beginTransaction();
		// @SuppressWarnings("deprecation")
		// Criteria criteria = session.createCriteria(Edge.class).add(
		// Restrictions.eq("id", id));
		// Object result = criteria.uniqueResult();
		// if (result != null) {
		// e = (Edge) result;
		// }
		//
		// // vertexes=session.createCriteria(Vertex.class).list();
		// session.getTransaction().commit();
		// session.close();
		return e;
	}

	public List<Edge> getAllEdge() {
		List<Edge> edges = new LinkedList<Edge>();
		Edge e;
		// try{
		// Session session=getSession();
		//
		// session.beginTransaction();
		// edges=session.createCriteria(Edge.class).list();
		// session.getTransaction().commit();
		// session.close();
		// return edges;
		// }catch(HibernateException ex){
		// System.out.println(ex.getMessage());
		// }
		// return null;
		try {
			String sql = "select * from edges";
			// String stop="";
			PreparedStatement stmt = (PreparedStatement) con
					.prepareStatement(sql);
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
		Edge e = null;
		List<String> items = null;
		Route r;
		// int id;
		String ids, edgeDetail;
		try {
			String sql = "select * from route";

			PreparedStatement stmt = (PreparedStatement) con
					.prepareStatement(sql);
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
				//System.out.println(edgeNames);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return routes;
	}

	public String getEdgeDetail(int id) throws SQLException {
		//System.out.println("" + id);
		String detail = "";
		Edge e = getEdge(id);
		//System.out.println(e);
		detail = e.getSource().getName()+"-"+e.getDestination().getName();

		return detail;
	}

}
