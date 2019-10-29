package bashShell.ast;

import java.util.Collections;

/**
 *
 */
public class NullArg extends Argument {
    private Argument arg = null;

    public NullArg() {
        this.arg = null;
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
                        String.class.getSimpleName() + "\n"
        );
    }
}
