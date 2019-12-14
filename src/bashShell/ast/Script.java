package bashShell.ast;

import bashShell.Checker;

import java.util.Collections;

/**
 *
 */
public class Script extends AST {
    private Command command;

    public Script(Command c) {
        this.command = c;
    }

    public Command getCommand() {
        return this.command;
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
                        "Script" + "\n" +
                        this.command.visit(indentLevel+1)
        );
    }

    @Override
    public Object accept(Checker c, Object o) {
        return c.visitScript(this, o);
    }
}
