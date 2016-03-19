package regex;

import regex.Alphabet;
import errors.ParseError;

//Source : Slide of lecture 3, around page 45
public class Parser {

	public boolean parse(String [] input) throws ParseError {
		Lexer lex = new Lexer(input);
		return parse0(lex) && lex.done();
	}

	public boolean parse0(Lexer lex) throws ParseError {
		if (lex.token() == Alphabet.A) {
			lex.mustBe(Alphabet.A);
			return parse1(lex);
		}
		else if (lex.token() == Alphabet.B) {
			lex.mustBe(Alphabet.B);
			return parse2(lex);
		}
		else if (lex.token() == Alphabet.C) {
			lex.mustBe(Alphabet.C);
			return parse6(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse1(Lexer lex) throws ParseError {
		if (lex.token() == Alphabet.B) {
			lex.mustBe(Alphabet.B);
			return parse3(lex);
		}
		else if (lex.token() == Alphabet.A) {
			lex.mustBe(Alphabet.A);
			return parse4(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse2(Lexer lex) throws ParseError {
		if (lex.token() == Alphabet.C) {
			lex.mustBe(Alphabet.C);
			return parse3(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse3(Lexer lex) throws ParseError {
		if (lex.token() == Alphabet.A) {
			lex.mustBe(Alphabet.A);
			return parse5(lex);
		}
		else if (lex.token() == Alphabet.C) {
			lex.mustBe(Alphabet.C);
			return parse6(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse4(Lexer lex) throws ParseError {
		if (lex.token() == Alphabet.A) {
			lex.mustBe(Alphabet.A);
			return parse4(lex);
		}
		else if (lex.token() == Alphabet.B) {
			lex.mustBe(Alphabet.B);
			return parse2(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse5(Lexer lex) throws ParseError {
		if (lex.token() == Alphabet.B) {
			lex.mustBe(Alphabet.B);
			return parse3(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse6(Lexer lex) throws ParseError {
		return true;
	}
}
