package bashShell.ast;

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
}
