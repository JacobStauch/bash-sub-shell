package bashShell.ast;

import java.util.Collections;

public class VarArg extends SingleArg {
    private Terminal variable;

    public VarArg(Terminal variable) {
        this.variable = variable;
    }

    @Override
    public String visit(int indentLevel) {
        return(
                String.join("", Collections.nCopies(indentLevel, "\t")) +
                        "VarArg" + "\n" +
                        this.variable.visit(indentLevel+1)
        );
    }
}