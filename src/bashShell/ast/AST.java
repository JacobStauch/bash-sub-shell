package bashShell.ast;

import bashShell.*;

/**
 *
 */
public abstract class AST {
    /**
     *
     * @param indentLevel Dictates the pretty-printing of the AST.
     *                    Works by repeating a tab character the number
     *                    of times provided by indentLevel
     * @return The string representing the AST.
     */
    public abstract String visit(int indentLevel);

    public abstract Object accept(Checker c, Object o);
}
