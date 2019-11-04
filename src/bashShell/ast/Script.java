package bashShell.ast;

import java.util.Collections;

/**
 *
 */
public class Script extends AST {
    public Command c;

    public Script(Command c) {
        this.c = c;
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
                        "Script" + "\n" +
                        this.c.visit(indentLevel+1)
        );
    }
}
