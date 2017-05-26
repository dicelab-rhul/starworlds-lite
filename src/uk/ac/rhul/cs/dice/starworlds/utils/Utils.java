package uk.ac.rhul.cs.dice.starworlds.utils;

public class Utils {
	
	private Utils(){}
	
	public static boolean equalsHelper(Object first, Object second) {
		if (first == second) {
			return true;
		}
			
		if (second == null) {
			return false;
		}
			
		if (first.getClass() != second.getClass()) {
			return false;
		}
		
		return true;
	}
	
	public static boolean checkBothNull(Object first, Object second) {
		return first == null && second == null;
	}
}