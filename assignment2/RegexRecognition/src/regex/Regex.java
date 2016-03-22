package regex;

import regex.Parser;
import errors.ParseError;

public class Regex {

	//modify this
	public boolean parse(String[] input) {
		if (input.length == 0) {
			return true;
		}
		try {
			return (new Parser()).parse(input);
		} catch (ParseError e) {
			return false;
		}
	}


}
