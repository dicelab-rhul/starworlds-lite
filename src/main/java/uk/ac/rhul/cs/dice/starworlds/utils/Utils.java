package uk.ac.rhul.cs.dice.starworlds.utils;

public class Utils {

    private Utils() {
    }

    public static boolean equalsHelper(Object first, Object second) {
	if (first == second) {
	    return true;
	}

	if (second == null) {
	    return false;
	}

	return first.getClass() == second.getClass();
    }

    public static boolean checkBothNull(Object first, Object second) {
	return first == null && second == null;
    }
    
    public static boolean checkBothNonNull(Object first, Object second) {
	return first != null && second != null;
    }
    
    public static boolean checkNullMismatch(Object first, Object second) {
	return !checkBothNull(first, second) && !checkBothNonNull(first, second);
    }
}