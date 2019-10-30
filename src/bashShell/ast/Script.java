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
     * @param indentLevel
     * @return
     */
    @Override
    public String visit(int indentLevel) {
        return(
                String.join("", Collections.nCopies(indentLevel, "\t")) +
                        String.class.getSimpleName() + "\n" +
                        this.c.visit(indentLevel+1)
        );
    }
}
