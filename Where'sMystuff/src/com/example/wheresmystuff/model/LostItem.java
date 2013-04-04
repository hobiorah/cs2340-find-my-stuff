package com.example.wheresmystuff.model;



/**
 * Represents a lost item in system.
 */
public class LostItem extends Item{

	private boolean found;

	
	/**
	 * Instantiates a new lost item.
	 *
	 * @param name the name
	 * @param des the des
	 * @param cat the cat
	 * @param rew the rew
	 * @param date the date
	 */
	public LostItem(String name, String des, Category cat, String rew, Date date){
		super(name, des,cat,rew,date,false);
		found = false;
	}
	
	/**
	 * Instantiates a new lost item.
	 *
	 * @param name the name
	 * @param des the des
	 * @param cat the cat
	 * @param rew the rew
	 * @param date the date
	 * @param l the l
	 */
	public LostItem(String name, String des, Category cat, String rew, Date date, Location l){
		super(name, des,cat,rew,date,false);
		found = false;
		this.loc = l;
	}
	
	/**
	 * Checks if is found.
	 *
	 * @return the found
	 */
	public boolean isFound() {
		return found;
	}
	
	/**
	 * Sets if its found or not.
	 */
	public void setFound(){
		found = true;
	}
	
	
}
