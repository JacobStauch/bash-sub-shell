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
     * @param indentLevel Dictates the pretty-printing of the AST.
     *                    Works by repeating a tab character the number
     *                    of times provided by indentLevel.
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
