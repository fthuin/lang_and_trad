package grammar;

/**
 * DO NOT MODIFY
 */
public class Grammar {
	/**
	 * Grammar 
	 * E -> E and T 
	 * E -> E nand T 
	 * E -> T 
	 * T -> T or F 
	 * T -> T nor F 
	 * T -> F
	 * F -> ( E ) 
	 * F -> !F 
	 * F -> id 
	 * id -> true | false
	 */

	public final static String AND = "and";
	public final static String NAND = "nand";
	public final static String OR = "or";
	public final static String NOR = "nor";
	public final static String NEG = "!";
	public final static String LEFTPAR = "(";
	public final static String RIGHTPAR = ")";
	public final static String TRUE = "true";
	public final static String FALSE = "false";

}
