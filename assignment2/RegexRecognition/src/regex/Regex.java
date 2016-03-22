package regex;

import errors.ParseError;
import regex.Lexer;

public class Regex {
	
	//modify this 
	public boolean parse(String[] input) {
		Lexer lex = new Lexer(input);
		boolean matching = false;
		try {
			matching = parse0(lex);
		} catch (ParseError e) {
			matching = false;
		}
		return matching && lex.done();
	}
	public boolean parse0(Lexer lex) throws ParseError {
		if (lex.token().equals(Alphabet.A)) {
			lex.mustBe(Alphabet.A);
			return parse1(lex);
		}
		else if (lex.token().equals(Alphabet.B)) {
			lex.mustBe(Alphabet.B);
			return parse2(lex);
		}
		else if (lex.token().equals(Alphabet.C)) {
			lex.mustBe(Alphabet.C);
			return parse6(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse1(Lexer lex) throws ParseError {
		if (lex.token().equals(Alphabet.B)) {
			lex.mustBe(Alphabet.B);
			return parse3(lex);
		}
		else if (lex.token().equals(Alphabet.A)) {
			lex.mustBe(Alphabet.A);
			return parse4(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse2(Lexer lex) throws ParseError {
		if (lex.token().equals(Alphabet.C)) {
			lex.mustBe(Alphabet.C);
			return parse6(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse3(Lexer lex) throws ParseError {
		if (lex.token().equals(Alphabet.A)) {
			lex.mustBe(Alphabet.A);
			return parse5(lex);
		}
		else if (lex.token().equals(Alphabet.C)) {
			lex.mustBe(Alphabet.C);
			return parse6(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse4(Lexer lex) throws ParseError {
		if (lex.token().equals(Alphabet.A)) {
			lex.mustBe(Alphabet.A);
			return parse4(lex);
		}
		else if (lex.token().equals(Alphabet.B)) {
			lex.mustBe(Alphabet.B);
			return parse2(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse5(Lexer lex) throws ParseError {
		if (lex.token().equals(Alphabet.B)) {
			lex.mustBe(Alphabet.B);
			return parse3(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse6(Lexer lex) throws ParseError {
		if (lex.done()) {
			return true;
		} else {
			return false;
		}
	}
}
