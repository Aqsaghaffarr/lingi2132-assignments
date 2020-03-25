package regex;

import static regex.Alphabet.*;

public class Regex {
	
	private static int[][] table = {{ 1, -1,  2},
			                        {-1,  3,  4},
			                        {-1, -1, -1},
			                        { 5,  6,  4},
			                        {-1, -1,  4},
			                        {-1,  7, -1},
			                        {-1,  6,  4},
			                        { 5, -1,  2}};

	public boolean parse(String[] input) {
		int state = 0;
		for (String s: input) {
			if (state == -1) return false;
			switch (s) {
				case A:
					state = table[state][0];
					break;
				case B:
					state = table[state][1];
					break;
				case C:
					state = table[state][2];
					break;
				default:
					return false;
			}
		}
		return state == 1 || state == 2 || state == 3 || state == 4 || state == 6;
	}
	/*
	public static void main(String[] args) {
		Regex r = new Regex();
		System.out.println(r.parse(new String[]{"a"})); // ok
		System.out.println(r.parse(new String[]{"a", "b"})); // ok
		System.out.println(r.parse(new String[]{"a", "b", "c"})); // ok
		System.out.println(r.parse(new String[]{"a", "b", "b", "c"})); // ok
		System.out.println(r.parse(new String[]{"a", "b", "a", "b", "c"})); // ok
		System.out.println(r.parse(new String[]{"a", "b", "a", "b", "c", "d"})); // nok
		System.out.println(r.parse(new String[]{"b"})); // nok
		System.out.println(r.parse(new String[]{"a", "b", "a", "b"})); // nok
		System.out.println(r.parse(new String[]{})); // nok
	}
	*/
}
