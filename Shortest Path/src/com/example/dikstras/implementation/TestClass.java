package com.example.dikstras.implementation;

import java.util.ArrayList;
import java.util.List;

import com.example.MySql.Database;
import com.example.dikstras.model.Route;

public class TestClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Route> routes=new ArrayList<Route>();
		Database db=new Database();
		routes=db.getallRoute();
		for(Route r:routes){
			System.out.println(r);
		}
		//System.out.println(routes);
	}

}
