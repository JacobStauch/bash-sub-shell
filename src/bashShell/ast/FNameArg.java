package bashShell.ast;

import java.util.Collections;

/**
 *
 */
public class FNameArg extends SingleArg  {
    private Terminal term;

    public FNameArg(Terminal term) {
        this.term = term;
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
                        this.term.visit(indentLevel++)
        );
    }

}
