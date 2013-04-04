package com.example.wheresmystuff.util;

import com.example.wheresmystuff.model.Category;

/**
 * converts Enum type of categories into string and strings into Enums
 * @author HarryO
 *
 */
public class Utils {
	/**
	 * converts enum type to a string
	 * @param cat Category
	 * @return String representation of the enum
	 */
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
	
	/**
	 * converts a string into an enum type
	 * @param s enum type in String format
	 * @return Enum type fo the string
	 */
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
