package com.example.wheresmystuff;

/**
 * Represents a user in the system.
 */
public class User{
	private String user;
	private String hash;
	private String email;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param user the user
	 * @param hash the hash
	 */
	public User(String user, String hash){
		this.user = user;
		this.hash = hash;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName(){
		return user;
	}
	
	/**
	 * Gets the hash.
	 *
	 * @return the hash
	 */
	public String getHash(){
		return hash;
	}
}
