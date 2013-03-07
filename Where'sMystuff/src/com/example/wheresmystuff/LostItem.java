package com.example.wheresmystuff;


public class LostItem extends Item{

	private boolean found;

	
	public LostItem(String name, String des, Category cat, String rew, Date date){
		super(name, des,cat,rew,date,false);
		found = false;
	}
	
	public LostItem(String name, String des, Category cat, String rew, Date date, Location l){
		super(name, des,cat,rew,date,false);
		found = false;
		this.loc = l;
	}
	
	/**
	 * @return the found
	 */
	public boolean isFound() {
		return found;
	}
	
	public void setFound(){
		found = true;
	}
	
	
}
