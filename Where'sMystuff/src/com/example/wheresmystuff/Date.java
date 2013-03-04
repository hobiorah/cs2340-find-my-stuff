

public class Date {
	protected int year;
	protected int date;
	protected int month;
	
	public Date(int month, int date, int year){
		this.year = year;
		this.date = date;
		this.month = month;
	}
	
	public int getDate(){
		return date;
	}
	
	public int getYear(){
		return year;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int[] getAll(){
		return new int[] {month, date, year};
	}
	
	public String toString(){
		return month + " " + date + ", " + year;
	}
	
	public String serialize(){
		return month + "," + date + "," + year;
	}
	
	
}
