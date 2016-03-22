package grammar;

import grammar.Parser;
import grammar.Generator;

public class Test {
	public static void main(String[] args) {
		Parser parser = new Parser();
		for (int i = 0; i < 5; i++) {
			Generator gen = new Generator(i);
			String[] tab = gen.generate(10);
			System.out.println(arrayToString(tab) + " " + parser.parse(tab));
		}
	}
	
	public static String arrayToString(String[] tab) {
		String res = "";
		for (String s : tab) {
			res += s;
		}
		return res;
	}
}
