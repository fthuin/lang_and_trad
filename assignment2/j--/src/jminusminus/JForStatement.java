package jminusminus;

import static jminusminus.CLConstants.GOTO;

import java.util.ArrayList;

public class JForStatement extends JStatement{

    /* ArrayList of all the statements between the right parenthesis and the
     * first semi-colon.
     */
    private ArrayList<JVariableDeclarator> initStatements;
    /* Expression between the two semi-colons */
    private JExpression condition;
    /* ArrayList of all the statement between the last semi-colon and the
     * right parenthesis.
     */
    private ArrayList<JStatement> endStatements;
    /* Statement of the body of the loop */
    private JStatement bodyStatement;

    /**
     * Constructor of a for-loop of type for( ... ; ... ; ...) in the AST.
     * @param line line where the for-loop is written in the parsed class.
     * @param initStatements part between the right parenthesis and the first
     * semi-colon, reserved for the initialization.
     * @param condition parts between the two semi-colons reserved for the
     * condition expression.
     * @param endStatements the statements executed at the end of each iteration
     * @param bodyStatement the body of the for-loop
     */

    public JForStatement(int line,
                         ArrayList<JVariableDeclarator> initStatements,
                         JExpression condition,
                         ArrayList<JStatement> endStatements,
                         JStatement bodyStatement) {
        super(line);
        this.initStatements = initStatements;
        this.condition = condition;
        this.endStatements = endStatements;
        this.bodyStatement = bodyStatement;
    }

    /**
     * Implements JAST abstract method to perform semantic analysis on this AST.
     *
     * This method analyzes the three parts of the for-loop and its body.
     *
     * @param context the environment (scope) in which code is analyzed.
     * @return this object as a JAST after the analyzation.
     */
    public JAST analyze(Context context) {
        /* Analyzation of the statement(s) */
        if (initStatements != null) {
            for (JVariableDeclarator initStatement : initStatements) {
                initStatement.analyze(context);
            }
        }

        /* Analyzation of the condition */
        if (condition != null) {
            condition.analyze(context);
            condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        }

        /* Analyzation of the statement(s) executed after each iteration */
        if (endStatements != null) {
            for (JStatement incr : endStatements) {
                incr.analyze(context);
            }
        }

        /* Analyzation of the body of the loop */
        bodyStatement.analyze(context);

        return this;
    }

    /**
     * Implements the JAST abstract method to perform code generation.
     *
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        /*
         *
         */
        if (initStatements != null) {
            for (JVariableDeclarator initStatement : initStatements)
                initStatement.codegen(output);
        }

        /* Code generation for the condition */
        String conditionalLabel = output.createLabel();
        String endLoopLabel = output.createLabel();
        output.addLabel(conditionalLabel);
        if (this.condition != null) {
            this.condition.codegen(output, endLoopLabel, false);
        }

        /* Code generation for the body */
        bodyStatement.codegen(output);

        /* Code generation for the statements executed after each iteration */
        if (endStatements != null) {
            for (JStatement endStatement : endStatements)
                endStatement.codegen(output);
        }

        /* Adding a branch to go back to the condition */
        output.addBranchInstruction(GOTO, conditionalLabel);

        /* Adding the label of the end of the loop */
        output.addLabel(endLoopLabel);
    }

    /**
     * Implements JAST abstract method to write the information pertaining to
     * this AST to STDOUT.
     *
     * @param p
     *            for pretty printing with indentation.
     */

    public void writeToStdOut(PrettyPrinter p) {
        // I don't know where it is used, just re-writing code from JWhileStatement
        p.printf("<JForStatement line=\"%d\">\n", line());
        p.indentRight();
        p.println("<Init>");
        p.indentRight();
        if (initStatements != null) {
                    for (JVariableDeclarator initStatement : initStatements) {
                        initStatement.writeToStdOut(p);
                    }
                }
            p.indentLeft();
            p.println("</Init>");
            p.println("<TestExpression>");
            p.indentRight();
                if (condition != null) {
                    condition.writeToStdOut(p);
                }
            p.indentLeft();
            p.println("</TestExpression>");
            p.println("<Increment>");
            p.indentRight();
                if (endStatements != null) {
                    // hehe, we can use a foreach here :-)
                    for (JStatement endStatement : endStatements) {
                        endStatement.writeToStdOut(p);
                    }
                }
            p.indentLeft();
            p.println("</Increment>");
            p.println("<Statement>");
            p.indentRight();
                bodyStatement.writeToStdOut(p);
            p.indentLeft();
            p.println("</Statement>");
        p.indentLeft();
        p.println("</JForStatement>");
    }
}
