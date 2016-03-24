package jminusminus;

import static jminusminus.CLConstants.GOTO;

import java.util.ArrayList;

public class JForeachStatement extends JStatement {

    private JFormalParameter forParam;
    private JExpression expression;
    private JStatement body;

    private JForStatement forStatement;

    /**
     * Construct an AST node for a foreach loop.
     * This new foreach will use the classical For loop.
     *
     * @param line
     *            line in which the expression occurs in the source file.
     * @param forParam
     *            the first part of the foreach loop: a FormalParameter.
     * @param expression
     *            the second part of the foreach loop: an Expression
     * @param statement
     *            the body.
     */
    protected JForeachStatement(int line, JFormalParameter forParam,
                                    JExpression expression,
                                    JStatement body) {
        super(line);
        this.forParam = forParam;
        this.expression = expression;
        this.body = body;

        /**
         * DECLARATION PART OF A STANDARD FOR-LOOP
         */

        ArrayList<JVariableDeclarator> initDeclarators = new ArrayList<JVariableDeclarator>(); // init the tmp variable and the param

        /* Temporarly creating a variable to iterate */
        JVariable iteratorVariable = new JVariable(line, "__iteratorVariable__" + line);
        JLiteralInt zeroLiteralInt = new JLiteralInt(line, "0");
        JVariableDeclarator initVariableDeclarator = new JVariableDeclarator(line, iteratorVariable.name(), Type.INT, zeroLiteralInt);
        initDeclarators.add(initVariableDeclarator);

        /* Part between the left parenthesis and the colon */
        JVariable iterationVariable = new JVariable(line, forParam.name()); // we need a JExpression for JVariableDeclarator
        JVariableDeclarator foreachVarDecl = new JVariableDeclarator(line, iterationVariable.name(), forParam.type(), iterationVariable);
        initDeclarators.add(foreachVarDecl);

        /**
         * CONDITION PART OF A STANDARD FOR-LOOP
         */

        JExpression condition;
        // Get the length:
        JFieldSelection length = new JFieldSelection(line, expression, "length");
        // iteratorVariable <= length
        JLessEqualOp lhs = new JLessEqualOp(line, iteratorVariable, length);
        // iteratorVariable == length
        JEqualOp equalOp = new JEqualOp(line, iteratorVariable, length);
        // ! iteratorVariable == length
        JExpression rhs = new JLogicalNotOp(line, equalOp);
        // iteratorVariable <= length ^ ! iteratorVariable == length
        condition = new JLogicalAndOp(line, lhs, rhs);

        /**
         * ITERATION PART OF A STANDARD FOR-LOOP
         */

        ArrayList<JStatement> endStatements = new ArrayList<JStatement>();
        // retrieve data in an array (expression) at position iteratorVariable
        JArrayExpression valueInArray = new JArrayExpression(line, expression, iteratorVariable);
        // assign value retrieved in the array
        JAssignOp assignment = new JAssignOp(line, iterationVariable, valueInArray); 
        endStatements.add(assignment);

        // now the third part of the for loop: incrementing + assignment
        // incrementing part
        JPreIncrementOp incrementOp = new JPreIncrementOp(line, iteratorVariable);
        endStatements.add(incrementOp);

        // easier to directly use the code from the JForStatement...
        forStatement = new JForStatement(line, initDeclarators, condition,
                endStatements, body);
    }

    /**
     * Analyze all 3 parts of foreach loop
     *
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */
    public JAST analyze(Context context) {
        forStatement.analyze(context);
        return this;
    }

    public void codegen(CLEmitter output) {
        // idem, we can also use it to generate code
        forStatement.codegen(output);
    }

    /**
     * a writeToStdOut like the other
     */
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JForeachStatement line=\"%d\">\n", line());
        p.indentRight();
            p.println("<FormalParam>");
            p.indentRight();
                if (forParam != null)
                    forParam.writeToStdOut(p);
            p.indentLeft();
            p.println("</FormalParam>");
            p.println("<Expression>");
            p.indentRight();
                if (expression != null)
                    expression.writeToStdOut(p);
            p.indentLeft();
            p.println("</Expression>");
            p.println("<Statement>");
            p.indentRight();
                body.writeToStdOut(p);
            p.indentLeft();
            p.println("</Statement>");
        p.indentLeft();
        p.println("</JForeachStatement>");
    }

}
