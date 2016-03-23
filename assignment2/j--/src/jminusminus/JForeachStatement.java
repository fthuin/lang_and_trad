package jminusminus;

import static jminusminus.CLConstants.GOTO;

import java.util.ArrayList;

public class JForeachStatement extends JStatement {

    private JFormalParameter forParam;
    private JExpression expression;
    private JStatement statement;

    // no need to copy-paste the code from JForStatement => we can use it...
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
                                    JStatement statement) {
        super(line);
        this.forParam = forParam;
        this.expression = expression;
        this.statement = statement;

        /* hidden to the user: the condition of the real loop,
         * the initial statement (with a new variable),
         * the statement used at the end of the loop to increment the new variable
         */
        JExpression condition;
        ArrayList<JStatement> forInit; // init the tmp variable and the param
        ArrayList<JStatement> forIncrement; // increment and assign

        // init all parts
        ArrayList<JVariableDeclarator> vars =
                new ArrayList<JVariableDeclarator>();
        forInit = new ArrayList<JStatement>();
        forIncrement = new ArrayList<JStatement>();

        // we need a temporary variable (which will be incremented)
        JVariable tmpIncVariable = new JVariable(line, "__tmp__" + line);
        JLiteralInt zero = new JLiteralInt(line, "0");
        JVariableDeclarator tmpIncVarDecl = new JVariableDeclarator(
                line,
                tmpIncVariable.name(),
                Type.INT,
                zero);
        vars.add(tmpIncVarDecl);

        // the variable which will contain the statement
        JVariable foreachVar = new JVariable(line, forParam.name());
        JVariableDeclarator foreachVarDecl = new JVariableDeclarator(
                line,
                foreachVar.name(),
                forParam.type(),
                foreachVar);
        vars.add(foreachVarDecl);

        // forInit => like the one in the Parser
        ArrayList<String> mods = new ArrayList<String>();
        JVariableDeclaration forInitVarDecl = new JVariableDeclaration(line,
                mods, vars);
        forInit.add(forInitVarDecl);


        /* the condition: 'tmpIncVariable < array.length' but...
         * '<' is not available, only '>' or '<=' are available... => ! a == b
         */
        // Get the length:
        JFieldSelection length = new JFieldSelection(line,
                expression, "length");
        // ==
        JEqualOp equalOp = new JEqualOp(line, tmpIncVariable, length);
        // ! ==
        JExpression lhs = new JLogicalNotOp(line, equalOp);

        // if the condition is true: assign the right value to foreachVar
        JArrayExpression array = new JArrayExpression(line, expression,
                tmpIncVariable);
        JAssignOp assignment = new JAssignOp(line, foreachVar, array);
        /* little hack to add this new assignment just after this condition
         * and still used the 'classical' for loop :-)
         */
        JExpression rhs = new JEqualOp(line, assignment, assignment);
        condition = new JLogicalAndOp(line, lhs, rhs);


        // now the third part of the for loop: incrementing + assignment
        // incrementing part
        JPreIncrementOp incr = new JPreIncrementOp(line, tmpIncVariable);
        forIncrement.add(incr);

        // assign from the array at the index 'tmpIncVariable'
        forIncrement.add(assignment);

        // easier to directly use the code from the JForStatement...
        forStatement = new JForStatement(line, forInit, condition,
                forIncrement, statement);
    }

    /**
     * Analyze all 3 parts of foreach loop
     *
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */
    public JAST analyze(Context context) {
        /*
        // expression
        if (expression != null) {
            expression.analyze(context);
        }
        // forParam
        if (forParam != null) {
            forParam.analyze(context);
        }
        // hidden statements
        if (forInit != null) {
            for (JStatement init : forInit) {
                init.analyze(context);
            }
        }

        // condition: a boolean (only one)
        if (condition != null) {
            condition.analyze(context);
            condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        }

        // incr
        if (forIncr != null) {
            for (JStatement incr : forIncr) {
                incr.analyze(context);
            }
        }
        // the body
        statement.analyze(context);
        */

        // idem, we can also use it to analyse
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
        p.printf("<JForStatement line=\"%d\">\n", line());
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
                statement.writeToStdOut(p);
            p.indentLeft();
            p.println("</Statement>");
        p.indentLeft();
        p.println("</JForStatement>");
    }

}
