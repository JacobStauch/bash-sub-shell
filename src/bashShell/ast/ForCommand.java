package bashShell.ast;

import java.util.Collections;

/**
 *
 */
public class ForCommand extends Command {
    private VarArg var;
    private Argument args;
    private Command doBody;

    public ForCommand(VarArg var, Argument args, Command doBody) {
        this.var = var;
        this.args = args;
        this.doBody = doBody;
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
                        "ForCommand" + "\n" +
                        this.var.visit(indentLevel+1) +
                        this.args.visit(indentLevel+1) +
                        this.doBody.visit(indentLevel+1)
        );
    }
}
