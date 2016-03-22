package regex;

import errors.ParseError;

//Source : slides of lecture 3, page 44;
class Lexer {

	String[] input = null;
	int idx = 0;

	Lexer(String[] input) {
		this.input = input;
	}

	public boolean mustBe(String t) throws ParseError {
		// System.out.println("Lexer.mustBe " + t);

		if (idx < input.length && t.equals(token())) {
			advance();
			return true;
		} else {
			throw new ParseError();
		}
	}

	public boolean have(String t) {

		if (idx < input.length && t.equals(token())) {
			advance();
			return true;
		} else {
			return false;
		}
	}

	public void advance() {
		idx++;
		while (idx < input.length && input[idx].equals("")) {
			idx++;
		}
	}

	public String token() {
		// System.out.println("Lexer.token");
		if (idx >= input.length) return ""; //Empty string instead of exception, as to accept epsilon in parser
		return input[idx];
	}

	public boolean done() {

		return input.length == idx;
	}
}
