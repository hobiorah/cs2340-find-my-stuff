package com.example.wheresmystuff;


public class Item {
	
	
	protected String description;
	protected String category;
	protected String reward;
	protected String dateEntered;
	protected String name;
	protected String city;
	protected String state;
	
	
	public Item(String des, String cat, String rew, String date, 
			String name){
		description = des;
		category = cat;
		reward = rew;
		dateEntered = date;
		this.name = name;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}


	/**
	 * @return the reward
	 */
	public String getReward() {
		return reward;
	}


	/**
	 * @return the dateEntered
	 */
	public String getDateEntered() {
		return dateEntered;
	}

	public String toString(){
		String data = "";
		data = name + " " + description;
		return data;
	}
	

	
	
	public String getName(){
		return name;
	}
	
	

}
