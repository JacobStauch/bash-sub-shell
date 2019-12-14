package bashShell;

import bashShell.Attribute;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Creates a hash map that stores the name
 * of the identifier as the key, and an
 * attribute as the value
 */
public class IDTable {
    private int curScope;
    private HashMap<String, Attribute> idTable;

    public IDTable() {
         idTable = new HashMap<>();
    }

    // Inserts an attribute at the global scope.
    public void insertGlobal(String name, Attribute a) {
        a.setScope(0);
        idTable.put(name, a);
    }

    // Inserts an attribute at the current operating scope.
    public void insertScope(String name, Attribute a) {
        a.setScope(this.curScope);
        idTable.put(name, a);
    }

    public void openScope() {
        this.curScope++;
    }

    /**
     * Remove entries from the identification table
     * that match our current scope
     */
    public void closeScope() {
        Iterator it = idTable.entrySet().iterator();

        /**
         * Iterate through the identification table,
         * removing any Attributes whose scope level
         * matches the most nested scope (the one
         * we are currently in)
         */
        while (it.hasNext()) {
            Map.Entry ele = (Map.Entry)it.next();
            Attribute a = (Attribute)ele.getValue();
            if (a.getScope() == this.curScope) {
                it.remove();
            }
        }

        /**
         * Move up a scope as there should be no
         * more entries in the ID Table for that scope
         */
        this.curScope--;
    }
}
