package bashShell.ast;

import bashShell.Checker;

import java.util.Collections;

/**
 *
 */
public class NullArg extends Argument {
    private Argument arg = null;

    public NullArg() {
        this.arg = null;
    }

    public Argument getArg() {
        return null;
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
                        "NullArg" + "\n"
        );
    }

    @Override
    public Object accept(Checker c, Object o) {
        return c.visitNullArg(this, o);
    }
}
