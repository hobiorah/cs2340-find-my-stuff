package com.example.wheresmystuff;


public class LostItem extends Item{

	private boolean found;

	
	public LostItem(String des, String cat, String rew, String date, 
			String name){
		super(des,cat,rew,date,name);
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
