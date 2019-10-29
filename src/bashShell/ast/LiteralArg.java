package bashShell.ast;

import java.util.Collections;

/**
 *
 */
public class LiteralArg extends SingleArg {
    private Terminal literal;

    public LiteralArg(Terminal literal) {
        this.literal = literal;
    }

    /**
     *
     * @param indentLevel
     * @return
     */
    @Override
    public String visit(int indentLevel) {
        return(
                String.join("", Collections.nCopies(indentLevel, "\t")) +
                        String.class.getSimpleName() + "\n" +
                        this.literal.visit(indentLevel++)
        );
    }
}