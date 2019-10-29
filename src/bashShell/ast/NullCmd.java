package bashShell.ast;

import java.util.Collections;

/**
 *
 */
public class NullCmd extends Command {
    private Command cmd = null;

    public NullCmd() {
        this.cmd = null;
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
                        String.class.getSimpleName() + "\n"
        );
    }
}
