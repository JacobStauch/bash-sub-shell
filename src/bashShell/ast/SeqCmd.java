package bashShell.ast;

import bashShell.Checker;

import java.util.Collections;

public class SeqCmd extends Command {
    private Command c1;
    private Command c2;

    public SeqCmd(Command c1, Command c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public Command getCom1() {
        return this.c1;
    }

    public Command getCom2() {
        return this.c2;
    }

    @Override
    public String visit(int indentLevel) {
        return(
                String.join("", Collections.nCopies(indentLevel, "\t")) +
                        "SeqCmd" + "\n" +
                        this.c1.visit(indentLevel+1) +
                        this.c2.visit(indentLevel+1)
        );
    }

    @Override
    public Object accept(Checker c, Object o) {
        return c.visitSeqCmd(this, o);
    }
}
