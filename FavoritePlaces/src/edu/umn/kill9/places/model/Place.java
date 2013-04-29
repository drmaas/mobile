package edu.umn.kill9.places.model;

public class Place {
	private String name;
	private String reference;
	private String vicinity;
	private String strDistance;
	
	public void setName(String lname){
		name = lname;
	}
	public String getName(){
		return name;
	}
	
	public void setReference(String lreference){
		reference = lreference;
	}
	public String getReference(){
		return reference;
	}
	
	public void setVicinity(String lvicinity){
		vicinity = lvicinity;
	}
	public String getVicinity(){
		return vicinity;
	}
	
	public void setDistance(float ldistance){
		// convert the distance from meters to miles
		double distanceInMiles = (ldistance/1000)*0.62137;
		strDistance = String.format("%.2f miles", distanceInMiles);	
	}
	public String getDistance(){
		return strDistance;
	}
}
