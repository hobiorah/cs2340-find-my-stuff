package com.example.wheresmystuff;


public class LostItem extends Item{

	private boolean found;

	
	public LostItem(String name, String des, Category cat, String rew, Date date){
		super(name, des,cat,rew,date,false);
		found = false;
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
