package regex;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static regex.Alphabet.*;

/*
 * Random generation of a valid derivation
 * 
 * Implement here a generator allowing you to test your regex parser
 * (not mandatory)
 */
public class Generator {
	
	
	private int seed;
	private final Random rand;
	
	public Generator(int seed) {
		this.seed = seed;
		rand = new Random(seed);
	}
		
	public  String[] generate(int maxDepth) {
		List<String> res = new LinkedList<String>();
		generateRegex(res, maxDepth, 0);
		return res.toArray((new String[res.size()]));
	}
	public void generateA(List<String> output){
		//a
		output.add(A);
	}
	public void generateB(List<String> output){
		//b
		output.add(B);
	}
	public void generateC(List<String> output){
		//c
		output.add(C);
	}
	public void generateRegex(List<String> output, int maxDepth, int currentDepth) {

	}
	
	
	
}
