package com.example.wheresmystuff;


/**
 * The Class Location.
 */
public class Location {
	protected String city;
	protected String state;
	protected String country;
	protected double latitude;
	protected double longitude;
	
	/**
	 * Instantiates a new location.
	 *
	 * @param city the city
	 * @param state the state
	 * @param country the country
	 * @param latitude the latitude
	 * @param longitude the longitude
	 */
	public Location(String city, String state, String country, double latitude, double longitude){
		this.city = city;
		this.state = state;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Instantiates a new location.
	 *
	 * @param city the city
	 * @param state the state
	 * @param country the country
	 */
	public Location (String city, String state, String country){
		this(city, state, country, -1, -1);
	}
	
	/**
	 * Instantiates a new location.
	 *
	 * @param city the city
	 * @param state the state
	 */
	public Location (String city, String state){
		this(city, state, "United States", -1, -1);
	}
	
	/**
	 * String representation of the location
	 * @return the string
	 */
	public String toString(){
		return city + ", " + state; 
	}
	
	/**
	 * Serialize.
	 *
	 * @return the string
	 */
	public String serialize(){
		return city + "," + state + "," + country + "," + latitude + "," + longitude;
	}
	
}
