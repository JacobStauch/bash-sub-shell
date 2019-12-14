package bashShell.ast;

import bashShell.Checker;

import java.util.Collections;

/**
 *
 */
public class ExecCmd extends Command {
    private FNameArg command;
    private Argument args;

    public ExecCmd(FNameArg command, Argument args) {
        this.command = command;
        this.args = args;
    }

    public FNameArg getCommand() {
        return this.command;
    }

    public Argument getArgs() {
        return this.args;
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
                        "ExecCmd" + "\n" +
                        this.command.visit(indentLevel+1) +
                        this.args.visit(indentLevel+1)
        );
    }

    @Override
    public Object accept(Checker c, Object o) {
        return c.visitExecCmd(this, o);
    }
}
