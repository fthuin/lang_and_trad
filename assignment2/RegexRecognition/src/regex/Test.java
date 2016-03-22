package regex;

import regex.Generator;
import regex.Regex;

public class Test {
	public static void main(String[] args) {
		Regex reg = new Regex();
		for (int i = 0; i < 100; i++) {
			Generator gen = new Generator(i);
			String[] tab = gen.generate(10);
			System.out.println(arrayToString(tab) + " " + reg.parse(tab));
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
