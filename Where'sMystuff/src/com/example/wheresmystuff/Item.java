package com.example.wheresmystuff;


public class Item {
	
	
	protected String description;
	protected String reward;
	protected String name;
	
	protected Location loc;
	/**
	 * @return the loc
	 */
	public Location getLoc() {
		return loc;
	}

	/**
	 * @return the cat
	 */
	public Category getCat() {
		return cat;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the found
	 */
	public boolean isFound() {
		return found;
	}

	protected Category cat;
	protected Date date;
	protected boolean found;
	
	/**
	 * Instantiates a new item.
	 *
	 * @param name the name
	 * @param des the des
	 * @param cat the cat
	 * @param rew the rew
	 * @param date the date
	 * @param found the found
	 */
	public Item(String name, String des, Category cat, String rew, Date date, boolean found){
		description = des;
		this.cat = cat;
		reward = rew;
		this.date = date;
		this.name = name;
		this.found = found;
	}
	
	/**
	 * Gets the description of the item
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * Gets the category of the item
	 * @return the category
	 */
	public Category getCategory() {
		return cat;
	}


	/**
	 * Gets the reward of the item
	 * @return the reward
	 */
	public String getReward() {
		return reward;
	}
	
	/**
	 * gets the location of the item
	 * @return the location
	 */
	public Location getLocation(){
		return loc;
	}


	/**
	 * Gets the date of when the item was lost
	 * @return the dateEntered
	 */
	public Date getDateEntered() {
		return date;
	}

	/**
	 * String representation of the item
	 * @return string representation of the item
	 */
	public String toString(){
		String data = "";
		data = name + " " + description;
		return data;
	}
	
	/**
	 * 
	 * @return name of item
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gets the status of the item whether it is lost or found
	 * @return status of item 
	 */
	public String getStatus(){
		String status = "";
		if(found == false){
			status = "Not found";
		}else{
			status = "Found";
		}
		return status;
	}
	
	public boolean compareCategory(Item item){
		return this.cat == item.getCat();
	}

}
