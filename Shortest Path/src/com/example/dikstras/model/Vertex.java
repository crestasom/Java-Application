package com.example.dikstras.model;

/**
 * This class implements the vertex model for graph
 * @distanceFromSource is used to determine the nearest vertex in Dijkastras Algorithm
 * @author Som
 *
 */
public class Vertex implements Comparable<Vertex>{
	
	 private int id=0;
	 private String name;
	 private double latCode;
	 private double longCode;
	 
	 private Double distanceFromSource=999.99;

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
