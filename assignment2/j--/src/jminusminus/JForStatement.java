package jminusminus;

import static jminusminus.CLConstants.GOTO;

import java.util.ArrayList;

public class JForStatement extends JStatement{

    private ArrayList<JStatement> initStatements;
    private JExpression condition;
    private ArrayList<JStatement> forIncr;
    private JStatement statement;

    /**
     * Construct an AST node for a for loop.
     * 
     * @param line
     *            line in which the expression occurs in the source file.
     * @param forInit
     *            the first part of the for-loop: initialisation.
     * @param condition
     *            the second part of the for-loop: true to continue.
     * @param forIncr
     *            the third part of the for-loop: what is modified each time.
     * @param statement
     *            the body.
     */

    public JForStatement(int line, ArrayList<JStatement> forInit,
                         JExpression condition, ArrayList<JStatement> forIncr,
                         JStatement statement) {
        super(line);
        this.forInit = forInit;
        this.condition = condition;
        this.forIncr = forIncr;
        this.statement = statement;
    }

    /**
     * Analyze all 4 parts of the for expression: init, cond, incr, body
     *
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */
    public JAST analyze(Context context) {
        // init
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

        return this;
    }

    public void codegen(CLEmitter output) {
        // Like the ConditionalExpression, we need two labels:
        //  where is the 'condition' statement and the 'exit' one
        String cond = output.createLabel();
        String exit = output.createLabel();
        if (forInit != null) {
            for (JStatement init : forInit)
                init.codegen(output);
        }

        // Branch out of the loop on the test condition
        // being false
        output.addLabel(cond);
        if (condition != null)
            condition.codegen(output, exit, false);

        // the body
        statement.codegen(output);
        if (forIncr != null) {
            for (JStatement update : forIncr)
                update.codegen(output);
        }

        // End of the loop block, go back to the conditional part
        output.addBranchInstruction(GOTO, cond);

        // Here it's the end
        output.addLabel(exit);
    }

    /**
     * a writeToStdOut like the other
     */
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JForStatement line=\"%d\">\n", line());
        p.indentRight();
            p.println("<Init>");
            p.indentRight();
                if (forInit != null) {
                    for (JStatement init : forInit) {
                        init.writeToStdOut(p);
                    }
                }
            p.indentLeft();
            p.println("</Init>");
            p.println("<Condition>");
            p.indentRight();
                if (condition != null) {
                    condition.writeToStdOut(p);
                }
            p.indentLeft();
            p.println("</Condition>");
            p.println("<Increment>");
            p.indentRight();
                if (forIncr != null) {
                    // hehe, we can use a foreach here :-)
                    for (JStatement incr : forIncr) {
                        incr.writeToStdOut(p);
                    }
                }
            p.indentLeft();
            p.println("</Increment>");
            p.println("<Statement>");
            p.indentRight();
                statement.writeToStdOut(p);
            p.indentLeft();
            p.println("</Statement>");
        p.indentLeft();
        p.println("</JForStatement>");
    }
}
