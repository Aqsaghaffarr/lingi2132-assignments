// Palindrome.java

package pass;

import java.lang.System;

// Determines whether a given string is a palindrome (reads the same in either direction).
// If yes, returns the string, otherwise returns an empty string.

public class Palindrome {

	public String palindrome(String s) {
		String str = s.toLowerCase();
	    int n = str.length();
	    int i  = 0;
	    while (i < n/2) {
	    	if (str.charAt(i) == str.charAt(n-i-1)) {
	    		// Do nothing.
	    	} else {
	    		return "";
	    	}
	    	++i;
	    }
	    return s;
	}

    public static void main(String[] args) {
        Palindrome palindrome = new Palindrome();
        palindrome.palindrome("kayak");
        palindrome.palindrome("Racecar");
        palindrome.palindrome("java");
    }

}