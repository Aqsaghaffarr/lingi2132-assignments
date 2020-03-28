package grammar;

import java.util.*;

import static grammar.Grammar.*;

public class Parser {

	/*
	Updated LL(1) grammar:
	(0)       E  -> TE'
	(1, 2)    E' -> or TE' | epsilon
	(3)       T  -> FT'
	(4, 5)    T' -> and FT' | epsilon
	(6, 7, 8) F  -> not F | (E) | id

	First-sets:
	first(E)  = {not, (, id}
	first(E') = {or, epsilon}
	first(T)  = {not, (, id}
	first(T') = {and, epsilon}
	first(F)  = {not, (, id}

	Follow-sets:
	follow(E)  = {#, )}
	follow(E') = {#, )}
	follow(T)  = {or, #, )}
	follow(T') = {or, #, )}
	follow(F)  = {and, or, #, )}
	 */

	// Rows:    E, E', T, T', F.
	// Columns: or, and, not, (, ), id, #
	public final int[][] table = new int[][]{
			{-1, -1,  0,  0, -1,  0, -1},
			{ 1, -1, -1, -1,  2, -1,  2},
			{-1, -1,  3,  3, -1,  3, -1},
			{ 5,  4, -1, -1,  5, -1,  5},
			{-1, -1,  6,  7, -1,  8, -1}};

	public final static String HASH = "#";



	public boolean parse(String[] input) {
		Stack<String> inp = new Stack<>();
		inp.push("#");
		for (int i = input.length - 1; i >= 0; i--) {
			inp.push(input[i]);
		}
		Stack<String> S = new Stack<>();
		S.push("#");
		S.push("E");
		int state = 0;
		String s = inp.peek();
		while (!inp.isEmpty()) {
			// Match top of stack.
			String alpha = S.pop();
			int row = -1;
			boolean consume = false;
			switch (alpha) {
				case "E":
					row = 0;
					break;
				case "E'":
					row = 1;
					break;
				case "T":
					row = 2;
					break;
				case "T'":
					row = 3;
					break;
				case "F":
					row = 4;
					break;
				default:
					if (alpha.equals(HASH)) return true; // Both characters are equal to the end of input.
					consume = true;
					break;
			}

			// Match start of input string.
			if (!consume) {
				switch (s) {
					case OR:
						state = table[row][0];
						break;
					case AND:
						state = table[row][1];
						break;
					case NOT:
						state = table[row][2];
						break;
					case LEFTPAR:
						state = table[row][3];
						break;
					case RIGHTPAR:
						state = table[row][4];
						break;
					case ID:
						state = table[row][5];
						break;
					case HASH:
						state = table[row][6];
						break;
				}
				// Apply grammar rule.
				if (state == -1) return false;

				// Speed up by not doing useless push/pop's.
				switch (state) {
					case 0:
						S.push("E'");
						S.push("T");
						break;
					case 1:
						S.push("E'");
						S.push("T");
						inp.pop();
						s = inp.peek();
						break;
					case 2:
						break;
					case 3:
						S.push("T'");
						S.push("F");
						break;
					case 4:
						S.push("T'");
						S.push("F");
						inp.pop();
						s = inp.peek();
						break;
					case 5:
						break;
					case 6:
						S.push("F");
						inp.pop();
						s = inp.peek();
						break;
					case 7:
						S.push(")");
						S.push("E");
						inp.pop();
						s = inp.peek();
						break;
					case 8:
						inp.pop();
						s = inp.peek();
						break;
					default:
						return false;
				}
			}
		}
		return S.isEmpty();
	}

	/*
	public static void main(String[] args) {
		Generator g = new Generator(69);
		Parser p = new Parser();
		int maxDepth = 100;
		for (int i = 0; i < 1; i++) {
			String[] s = g.generate(maxDepth);
			long st = System.currentTimeMillis();
			System.out.println(p.parse(s));
			// System.out.println(Arrays.toString(s) + ": " + p.parse(s) + "\n");
			System.out.println(System.currentTimeMillis() - st);
		}

		String[] s = new String[]{"not"};
		System.out.println(Arrays.toString(s) + ": " + p.parse(s) + "\n");
	}
	 */
}