

public class Location {
	protected String city;
	protected String state;
	protected String country;
	protected double latitude;
	protected double longitude;
	
	public Location(String city, String state, String country, double latitude, double longitude){
		this.city = city;
		this.state = state;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Location (String city, String state, String country){
		this(city, state, country, -1, -1);
	}
	
	public Location (String city, String state){
		this(city, state, "United States", -1, -1);
	}
	
	public String toString(){
		return city + ", " + state; 
	}
	
	public String serialize(){
		return city + "," + state + "," + country + "," + latitude + "," + longitude;
	}
	
}
