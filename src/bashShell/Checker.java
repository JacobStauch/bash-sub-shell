package bashShell;

import bashShell.ast.*;

/**
 * Context checker for an AST after a single pass
 * through the program. An instance of this class
 * provides methods needed for a Visitor for the AST.
 * As we traverse the tree we create the appropriate
 * Attribute entries in the ID Table. Since variable
 * scope is only relevant in a for loop (otherwise global),
 * assume the scope level is 0.
 */

public class Checker implements Visitor {
    private IDTable idTable;

    public Checker() {
        this.idTable = new IDTable();
    }

    @Override
    public Object visitAssignCmd(AssignCmd assignCmd, Object o) {
        return null;
    }

    @Override
    public Object visitExecCmd(ExecCmd execCmd, Object o) {
        return null;
    }

    @Override
    public Object visitFNameArg(FNameArg fNameArg, Object o) {
        return null;
    }

    @Override
    public Object visitForCommand(ForCommand forCommand, Object o) {
        return null;
    }

    @Override
    public Object visitIfCmd(IfCmd ifCmd, Object o) {
        return null;
    }

    @Override
    public Object visitLiteralArg(LiteralArg literalArg, Object o) {
        return null;
    }

    @Override
    public Object visitNullArg(NullArg nullArg, Object o) {
        return null;
    }

    @Override
    public Object visitNullCmd(NullCmd nullCmd, Object o) {
        return null;
    }

    @Override
    public Object visitScript(Script script, Object o) {
        return null;
    }

    @Override
    public Object visitSeqArg(SeqArg seqArg, Object o) {
        return null;
    }

    @Override
    public Object visitSeqCmd(SeqCmd seqCmd, Object o) {
        return null;
    }

    @Override
    public Object visitTerminal(Terminal terminal, Object o) {
        return null;
    }

    @Override
    public Object visitVarArg(VarArg varArg, Object o) {
        return null;
    }
}
