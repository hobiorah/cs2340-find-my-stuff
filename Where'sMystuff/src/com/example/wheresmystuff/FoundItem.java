package com.example.wheresmystuff;

/**
 * represents a found item in the system
 * @author HarryO
 *
 */
public class FoundItem extends Item{


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
		public FoundItem(String name, String des, Category cat, String rew, Date date){
			super(name, des,cat,rew,date,true);
			found = true;
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
		public FoundItem(String name, String des, Category cat, String rew, Date date, Location l){
			super(name, des,cat,rew,date,true);
			found = true;
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
		
		
		
		
	
}
