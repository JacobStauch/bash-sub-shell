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
                        String.class.getSimpleName() + "\n" +
                        this.var.visit(indentLevel++) +
                        this.args.visit(indentLevel++) +
                        this.doBody.visit(indentLevel++)
        );
    }
}
