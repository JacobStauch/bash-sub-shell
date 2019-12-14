package bashShell;

/**
 * Contains the type of an attribute and the scope level
 * at which it is found.
 */

public class Attribute {
    private Type type;
    private int curScope;

    enum Type {
        STRING, NUMERIC, EXECUTABLE, NULL
    }

    public Attribute(Type t) {
        this.type = t;
    }

    public void setScope(int level) {
        this.curScope = level;
    }

    public int getScope() {
        return this.curScope;
    }

    public Type getType() {
        return this.type;
    }
}
