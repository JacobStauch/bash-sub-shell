package bashShell.ast;

/**
 *
 */
public abstract class AST {
    /**
     *
     * @param indentLevel
     * @return
     */
    public abstract String visit(int indentLevel);
}
