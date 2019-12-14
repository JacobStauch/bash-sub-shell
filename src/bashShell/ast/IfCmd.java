package bashShell.ast;

import bashShell.Checker;

import java.util.Collections;

/**
 *
 */
public class IfCmd extends Command {
    private FNameArg command;
    private Argument args;
    private Command thenBlock;
    private Command elseBlock;

    public IfCmd(FNameArg cmd, Argument args, Command thenBlock, Command elseBlock) {
        this.command = cmd;
        this.args = args;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public FNameArg getCommand() {
        return this.command;
    }

    public Argument getArgs() {
        return this.args;
    }

    public Command getThenBlock() {
        return this.thenBlock;
    }

    public Command getElseBlock() {
        return this.elseBlock;
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
                        "IfCmd" + "\n" +
                        this.command.visit(indentLevel+1) +
                        this.args.visit(indentLevel+1) +
                        this.thenBlock.visit(indentLevel+1) +
                        this.elseBlock.visit(indentLevel+1)
        );
    }

    @Override
    public Object accept(Checker c, Object o) {
        return c.visitIfCmd(this, o);
    }
}
