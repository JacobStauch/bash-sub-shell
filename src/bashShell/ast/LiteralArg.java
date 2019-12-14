package bashShell.ast;

import bashShell.Checker;

import java.util.Collections;

/**
 *
 */
public class LiteralArg extends SingleArg {
    private Terminal literal;

    public LiteralArg(Terminal literal) {
        this.literal = literal;
    }

    public Terminal getLiteral() {
        return this.literal;
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
                        "LiteralArg" + " " +
                        "(" + this.literal.visit(indentLevel+1) + ")" + "\n"
        );
    }

    @Override
    public Object accept(Checker c, Object o) {
        return c.visitLiteralArg(this, o);
    }
}
