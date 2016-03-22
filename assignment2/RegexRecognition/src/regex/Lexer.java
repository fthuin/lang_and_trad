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

		if (idx < input.length && t.equals(token())) {
			advance();
			return true;
		} else {
			return false;
		}
	}

	public void advance() {
		idx++;
	}

	public String token() throws ParseError {
		if (idx >= input.length) throw new ParseError();
		return input[idx];
	}

	public boolean done() {
		return input.length <= idx;
	}
}