package com.example.wheresmystuff;

public class User {
	private String user;
	private String hash;
	
	public User(String user, String hash){
		this.user = user;
		this.hash = hash;
	}
	
	public String getName(){
		return user;
	}
	
	public String getHash(){
		return hash;
	}
}
