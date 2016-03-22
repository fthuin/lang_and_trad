package regex;

import regex.Alphabet;
import errors.ParseError;

//Source : Slide of lecture 3, around page 45
public class Parser {

	public boolean parse(String [] input) throws ParseError {
		// System.out.println("Parser.parse");
		Lexer lex = new Lexer(input);
		int i = 0;
		while (lex.token().equals("") && i < input.length) {
			lex.mustBe("");
			i++;
		}
		return parse0(lex) && lex.done();
	}

	public boolean parse0(Lexer lex) throws ParseError {
		if (lex.token().equals("")) {
			return parse6(lex);
		}
		else if (lex.token().equals(Alphabet.A)) {
			// System.out.println("Parser.parse0 - A");
			lex.mustBe(Alphabet.A);
			return parse1(lex);
		}
		else if (lex.token().equals(Alphabet.B)) {
			// System.out.println("Parser.parse0 - B");
			lex.mustBe(Alphabet.B);
			return parse2(lex);
		}
		else if (lex.token().equals(Alphabet.C)) {
			// System.out.println("Parser.parse0 - C");
			lex.mustBe(Alphabet.C);
			return parse6(lex);
		}
		else {
			// System.out.println("Parser.parse0 - else");
			throw new ParseError();
		}
	}

	public boolean parse1(Lexer lex) throws ParseError {
		// System.out.println("Parser.parse1");
		if (lex.token().equals(Alphabet.B)) {
			// System.out.println("Parser.parse1 - B");
			lex.mustBe(Alphabet.B);
			return parse3(lex);
		}
		else if (lex.token().equals(Alphabet.A)) {
			// System.out.println("Parser.parse1 - A");
			lex.mustBe(Alphabet.A);
			return parse4(lex);
		}
		else {
			// System.out.println("Parser.parse1 - else");
			// System.out.flush();
			throw new ParseError();
		}
	}

	public boolean parse2(Lexer lex) throws ParseError {
		// System.out.println("Parser.parse2");
		if (lex.token().equals(Alphabet.C)) {
			lex.mustBe(Alphabet.C);
			return parse6(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse3(Lexer lex) throws ParseError {
		// System.out.println("Parser.parse3");
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
		// System.out.println("Parser.parse4");
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
		// System.out.println("Parser.parse5");
		if (lex.token().equals(Alphabet.B)) {
			lex.mustBe(Alphabet.B);
			return parse3(lex);
		}
		else {
			throw new ParseError();
		}
	}

	public boolean parse6(Lexer lex) throws ParseError {
		// System.out.println("Parser.parse6");
		return true;
	}
}
