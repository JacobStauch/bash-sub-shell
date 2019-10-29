package bashShell.ast;

import java.util.Collections;

public class SeqCmd extends Command {
    private Command c1;
    private Command c2;

    public SeqCmd(Command c1, Command c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public String visit(int indentLevel) {
        return(
                String.join("", Collections.nCopies(indentLevel, "\t")) +
                        String.class.getSimpleName() + "\n" +
                        this.c1.visit(indentLevel++) +
                        this.c2.visit(indentLevel++)
        );
    }
}
