package bashShell.ast;

import bashShell.Checker;

import java.util.Collections;

public class VarArg extends SingleArg {
    private Terminal variable;

    public VarArg(Terminal variable) {
        this.variable = variable;
    }

    public Terminal getVar() {
        return this.variable;
    }

    @Override
    public String visit(int indentLevel) {
        return(
                String.join("", Collections.nCopies(indentLevel, "\t")) +
                        "VarArg" + " " +
                        "(" + this.variable.visit(indentLevel+1) + ")" + "\n"
        );
    }

    @Override
    public Object accept(Checker c, Object o) {
        return c.visitVarArg(this, o);
    }
}