package com.example.dikstras.implementation;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.dikstras.model.Edge;
import com.example.dikstras.model.Vertex;

import draw.GraphPanel;

public class HibernateMain {
	public static void main(String[] args) {
		
		Vertex v1=new Vertex();
		v1.setName("a");
		v1.setLatCode(23.343);
		v1.setLongCode(75.343);
		Vertex v2=new Vertex();
		v2.setName("b");
		v2.setLatCode(23.343);
		v2.setLongCode(75.343);
		Edge e=new Edge();
		e.setDestination(v2);
		e.setSource(v1);
		e.setName("a-b");
		e.setWeight(5.555);
		e.setOneway(false);
		try{
		SessionFactory sf=new Configuration().configure().buildSessionFactory();
		Session session=sf.openSession();
		session.beginTransaction();
		session.save(e);
		session.getTransaction().commit();
		session.close();
		sf.close();
		}catch(HibernateException ex){
			System.out.println(ex.getMessage());
		}
	}

}
