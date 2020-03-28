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
	private final int[][] table = new int[][]{
			{-1, -1,  0,  0, -1,  0, -1},
			{ 1, -1, -1, -1,  2, -1,  2},
			{-1, -1,  3,  3, -1,  3, -1},
			{ 5,  4, -1, -1,  5, -1,  5},
			{-1, -1,  6,  7, -1,  8, -1}};

	private final static String HASH = "#";

	public boolean parse(String[] input) {
		Deque<String> S = new ArrayDeque<>();
		S.push("#");
		S.push("E");
		int state;
		int index = 0;
		// System.out.println("Input: " + Arrays.toString(input) + "\n");
		while (index < input.length) {
			// Match top of stack.
			// System.out.println("Current stack: " + S + "\nInput left: " + Arrays.toString(Arrays.copyOfRange(input, index, input.length)) + "\n");
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
					if (S.isEmpty()) return false;
					consume = true;
					index++;
					break;
			}

			// Match start of input string.
			if (!consume) {
				switch (input[index]) {
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
					default:
						return false;
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
						index++;
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
						index++;
						break;
					case 5:
						break;
					case 6:
						S.push("F");
						index++;
						break;
					case 7:
						S.push(")");
						S.push("E");
						index++;
						break;
					case 8:
						index++;
						break;
					default:
						return false;
				}
			}
		}

		// See if we can reduce stack to "#".
		while (!S.isEmpty()) {
			String alpha = S.pop();
			if (alpha.equals(HASH)) return true;
			if (!(alpha.equals("E'") || alpha.equals("T'"))) return false;
		}
		return false; // Never reached.
	}

	/*
	public static void main(String[] args) {
		Generator g = new Generator(69);
		Parser p = new Parser();
		int maxDepth = 1;
		for (int i = 0; i < 1; i++) {
			String[] s = g.generate(maxDepth);
			System.out.println("Input of length " + s.length + " generated\n");
			long st = System.currentTimeMillis();
			System.out.println("Result: " + p.parse(s));
			System.out.println("Time: " + (System.currentTimeMillis() - st) + "\n\n");
		}

		String[] s = new String[]{"not"};
		System.out.println("Result: " + p.parse(s));
	}
	 */
}