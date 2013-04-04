package com.example.wheresmystuff.model;


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
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	
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
	
	public boolean weakCompare(Location location){
		return city.equalsIgnoreCase(location.getCity());
		
	}
	
	public boolean compare(Location location){
		return state.equalsIgnoreCase(location.getState()) && country.equals(location.getCountry()) && weakCompare(location);
	}
	
	public boolean strongCompare(Location location){
		return location.getLatitude() == latitude && location.getLongitude() == longitude && compare(location);
	}
	
}
