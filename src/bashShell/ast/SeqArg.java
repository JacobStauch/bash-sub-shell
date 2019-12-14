package bashShell.ast;

import bashShell.Checker;

import java.util.Collections;

/**
 *
 */
public class SeqArg extends Argument {
    private Argument a1;
    private Argument a2;

    public SeqArg(Argument a1, Argument a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    public Argument getArg1() {
        return this.a1;
    }

    public Argument getArg2() {
        return this.a2;
    }

    /**
     *
     * @param indentLevel Dictates the pretty-printing of the AST.
     *                    Works by repeating a tab character the number
     *                    of times provided by indentLevel.
     * @return
     */
    @Override
    public String visit(int indentLevel) {
        return(
                String.join("", Collections.nCopies(indentLevel, "\t")) +
                        "SeqArg" + "\n" +
                        this.a1.visit(indentLevel+1) +
                        this.a2.visit(indentLevel+1)
        );
    }

    @Override
    public Object accept(Checker c, Object o) {
        return c.visitSeqArg(this, o);
    }
}
