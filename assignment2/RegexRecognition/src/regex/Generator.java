package regex;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static regex.Alphabet.*;

/**
 * Random generation of a valid derivation
 * DO NOT MODIFY
 * Version 2
 */
public class Generator {
	
	//(a*b)|(ab)*c
	private int seed;
	private final Random rand;
	
	public Generator(int seed) {
		this.seed = seed;
		rand = new Random(this.seed);
	}
	
	
	
	public  String[] generate(int maxDepth) {
		List<String> res = new LinkedList<String>();
		generateA1(res, maxDepth, 0);
		generateC(res);
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
	public void generateA1(List<String> output, int maxDepth, int currentDepth) {
		//(a*b)|(ab)*
		int i = rand.nextInt(2);
		switch(i) {
			case 0 : {
				generateB1(output, maxDepth, currentDepth);
				break;
			} 
			case 1 : {
				generateB2(output, maxDepth, currentDepth + 1);
				break;
			}

		}
		
	}
	
	public void generateB1(List<String> output, int maxDepth, int currentDepth) {
		//(a*b)
		if(currentDepth > maxDepth) { //prevent from exploding memory
			generateB(output);
		} else {
			int i = rand.nextInt(4);
			if (i != 0) {
				generateA(output);
				generateB1(output, maxDepth, currentDepth + 1);
			} else {
				generateB(output);
			}
		}
		
	}
	public void generateB2(List<String> output, int maxDepth, int currentDepth) {
		//(ab)*
		if(currentDepth <= maxDepth) { //prevent from exploding memory
			int i = rand.nextInt(4);
			if (i != 0) {
					generateA(output);
					generateB(output);
					generateB2(output, maxDepth, currentDepth + 1);
			}
		}
		
	}
	
	
}
