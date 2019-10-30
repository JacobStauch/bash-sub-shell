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
                        "FNameArg" + " " +
                        "(" + this.term.visit(indentLevel+1) + ")" + "\n"
        );
    }

}
