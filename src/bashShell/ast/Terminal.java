package bashShell.ast;

import bashShell.Checker;

import java.util.Collections;

public class Terminal extends AST {
    private String spelling;

    public Terminal(String spelling) {
        this.spelling = spelling;
    }

    public String getSpelling() {
        return this.spelling;
    }

    @Override
    public String visit(int indentLevel) {
        return this.spelling;
    }

    @Override
    public Object accept(Checker c, Object o) {
        return c.visitTerminal(this, o);
    }
}