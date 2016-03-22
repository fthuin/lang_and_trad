package grammar;

import grammar.Grammar;
import errors.ParseError;

/**
 * Grammar
 * E -> T E1
 * E1 -> and T E1 | nand T E1 | epsilon
 * T -> F T1
 * T1 -> or F T1 | nor F T1 | epsilon
 * F -> ( E ) | !F | id
 * id -> true | false
 */

//Source : Slide of lecture 3, around page 45
public class Parser {

	public boolean parse(String [] input) {
		Lexer lex = new Lexer(input);
		boolean match = false;
		try {
			match = parseE(lex);
		} catch (ParseError e) {
			match = false;
		}
		return match && lex.done();
	}
	
	//E -> T E1
	public boolean parseE(Lexer lex) throws ParseError {

		parseT(lex);
		parseE1(lex);
		return true;
	}

	//E1 -> and T E1 | nand T E1 | epsilon
	public boolean parseE1(Lexer lex) throws ParseError {

		if (lex.token().equals(Grammar.AND)){
			lex.mustBe(Grammar.AND);
			parseT(lex);
			parseE1(lex);
		}
		else if (lex.token().equals(Grammar.NAND)){
			lex.mustBe(Grammar.NAND);
			parseT(lex);
			parseE1(lex);
		}
		return true;
	}

	//T -> F T1
	public boolean parseT(Lexer lex) throws ParseError {

		parseF(lex);
		parseT1(lex);
		return true;
	}

	//T1 -> or F T1 | nor F T1 | epsilon
	public boolean parseT1(Lexer lex) throws ParseError {

		if (lex.token().equals(Grammar.OR)){
			lex.mustBe(Grammar.OR);
			parseF(lex);
			parseT1(lex);
		}
		else if (lex.token().equals(Grammar.NOR)){
			lex.mustBe(Grammar.NOR);
			parseF(lex);
			parseT1(lex);
		}
		return true;
	}

	//F -> ( E ) | !F | id
	public boolean parseF(Lexer lex) throws ParseError {

		if (lex.token().equals(Grammar.LEFTPAR)){
			lex.mustBe(Grammar.LEFTPAR);
			parseE(lex);
			lex.mustBe(Grammar.RIGHTPAR);
		}
		else if (lex.token().equals(Grammar.NEG)){
			lex.mustBe(Grammar.NEG);
			parseF(lex);
		}
		else{
			parseID(lex);
		}
		return true;
	}

	//id -> true | false
	public boolean parseID(Lexer lex) throws ParseError {
		if (lex.have(Grammar.TRUE) || lex.have(Grammar.FALSE)) return true;
		throw new ParseError();
	}		
}