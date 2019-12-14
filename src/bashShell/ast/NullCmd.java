package bashShell.ast;

import bashShell.Checker;

import java.util.Collections;

/**
 *
 */
public class NullCmd extends Command {
    private Command cmd = null;

    public NullCmd() {
        this.cmd = null;
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
                        "NullCmd" + "\n"
        );
    }

    @Override
    public Object accept(Checker c, Object o) {
        return c.visitNullCmd(this, o);
    }
}
