package com.example.wheresmystuff;


public class Item {
	
	
	protected String description;
	protected String reward;
	protected String name;
	
	protected Location loc;
	protected Category cat;
	protected Date date;
	protected boolean found;
	
	public Item(String name, String des, Category cat, String rew, Date date, boolean found){
		description = des;
		this.cat = cat;
		reward = rew;
		this.date = date;
		this.name = name;
		this.found = found;
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
	public Category getCategory() {
		return cat;
	}


	/**
	 * @return the reward
	 */
	public String getReward() {
		return reward;
	}
	
	public Location getLocation(){
		return loc;
	}


	/**
	 * @return the dateEntered
	 */
	public Date getDateEntered() {
		return date;
	}

	public String toString(){
		String data = "";
		data = name + " " + description;
		return data;
	}

	public String getName(){
		return name;
	}
	
	public String getStatus(){
		String status = "";
		if(found == false){
			status = "Not found";
		}
		return status;
	}
	

}
