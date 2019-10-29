package bashShell.ast;

import java.util.Collections;

/**
 *
 */
public class AssignCmd extends Command {
    private VarArg lValue;
    private SingleArg rValue;

    public AssignCmd(VarArg lValue, SingleArg rValue) {
        this.lValue = lValue;
        this.rValue = rValue;
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
                        this.lValue.visit(indentLevel++) +
                        this.rValue.visit(indentLevel++)
                );
    }
}
