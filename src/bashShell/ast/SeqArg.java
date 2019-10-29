package bashShell.ast;

import java.util.Collections;

/**
 *
 */
public class SeqArg extends Argument {
    private Argument a1;
    private Argument a2;

    public SeqArg(Argument a1, Argument a2) {
        this.a1 = a1;
        this.a2 = a2;
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
                        this.a1.visit(indentLevel++) +
                        this.a2.visit(indentLevel++)
        );
    }
}