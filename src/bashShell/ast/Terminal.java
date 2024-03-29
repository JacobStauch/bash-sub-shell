package bashShell.ast;

import java.util.Collections;

public class Terminal extends AST {
    public String spelling;

    public Terminal(String spelling) {
        this.spelling = spelling;
    }

    @Override
    public String visit(int indentLevel) {
        return this.spelling;
    }
}