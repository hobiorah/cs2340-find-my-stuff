package com.example.wheresmystuff;


// TODO: Auto-generated Javadoc
/**
 * Represents a date 
 */
public class Date {
	
	protected int year;
	
	protected int date;
	
	protected int month;
	
	/**
	 * Instantiates a new date.
	 *
	 * @param month the month
	 * @param date the date
	 * @param year the year
	 */
	public Date(int month, int date, int year){
		this.year = year;
		this.date = date;
		this.month = month;
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public int getDate(){
		return date;
	}
	
	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear(){
		return year;
	}
	
	/**
	 * Gets the month.
	 *
	 * @return the month
	 */
	public int getMonth(){
		return month;
	}
	
	/**
	 * Gets the month, date, and year.
	 *
	 * @return the all
	 */
	public int[] getAll(){
		return new int[] {month, date, year};
	}
	
	/**
	 * String representation of the date
	 */
	public String toString(){
		return month + " " + date + ", " + year;
	}
	
	/**
	 * Serialize.
	 *
	 * @return the string
	 */
	public String serialize(){
		return month + "/" + date + "/" + year;
	}
//	
//	public boolean before(Date date){
//		if (year == date.getYear()){
////			if (date.get)
//		}
//	}
	
	/**
	 * To compare if to dates match
	 * @param compareDate date to compare with
	 * @return
	 */
	public boolean compare (Date compareDate){
		return year == compareDate.getYear() && month == compareDate.getMonth() && date == compareDate.getDate();
	}
}
