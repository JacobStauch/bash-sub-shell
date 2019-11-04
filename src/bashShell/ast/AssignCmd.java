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
     * @param indentLevel Dictates the pretty-printing of the AST.
     *                    Works by repeating a tab character the number
     *                    of times provided by indentLevel.
     * @return
     */
    @Override
    public String visit(int indentLevel) {
        return(
                String.join("", Collections.nCopies(indentLevel, "\t")) +
                   "AssignCmd" + "\n" +
                        this.lValue.visit(indentLevel+1) +
                        this.rValue.visit(indentLevel+1)
                );
    }
}
