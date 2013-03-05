package com.example.wheresmystuff;


public class Utils {
	public static String convertCategory(Category cat){
		switch(cat){
		case MISCELLANEOUS:
			return "Miscellaneous";
		case KEEPSAKES:
			return "Keepsakes";
		case HEIRLOOMS:
			return "Heirlooms";
		}
		
		return "Miscellaneous";
	}
	
	public static Category convertCategoryBack(String s){
		if (s.equalsIgnoreCase("miscellaneous"))
			return Category.MISCELLANEOUS;
		else if (s.equalsIgnoreCase("keepsakes"))
			return Category.KEEPSAKES;
		else if (s.equalsIgnoreCase("heirlooms"))
			return Category.HEIRLOOMS;
		else 
			return Category.MISCELLANEOUS;
	}
}
