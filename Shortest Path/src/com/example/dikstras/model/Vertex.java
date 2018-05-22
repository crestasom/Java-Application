package com.example.dikstras.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="stops")
public class Vertex implements Comparable<Vertex>{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	 private int id=0;
	 private String name;
	 @Transient
	 private double latCode;
	 @Transient
	 private double longCode;
	 
	 @Transient
	 private Double distanceFromSource=999.99;

//	public Vertex(String name, double latCode, double longCode) {
//		super();
//		this.name = name;
//		this.latCode = latCode;
//		this.longCode = longCode;
//	}
	
	
	

	public Double getDistanceFromSource() {
		return distanceFromSource;
	}




	public void setDistanceFromSource(Double distanceFromSource) {
		this.distanceFromSource = distanceFromSource;
	}




	public void setId(int id) {
		this.id = id;
	}




	public void setName(String name) {
		this.name = name;
	}




	public void setLatCode(double latCode) {
		this.latCode = latCode;
	}




	public void setLongCode(double longCode) {
		this.longCode = longCode;
	}




	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getLatCode() {
		return latCode;
	}


	public double getLongCode() {
		return longCode;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
//		return "id:" + id + " \nName:" + name;
		return name;
	}




	@Override
	public int compareTo(Vertex o) {
		// TODO Auto-generated method stub
		if(this.equals(o)){
			return 0;
		}else if(this.distanceFromSource>o.distanceFromSource){
			return 1;
			
		}else{
			return -1;
		}
		
	}

}
