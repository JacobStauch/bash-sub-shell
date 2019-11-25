package bashShell;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Compile2C {
    /**
     * Creates instance of Parser class and parses the input
     * @param args Command line arguments for the program
     *             compile2C (-d | -p) *file*
     *             -d : AST is printed in the console
     *             -p: AST is written to a file
     *             file: Input script
     */
    public static void main(String [] args) throws IOException {
        String file = null;

        file = args[1];

        if (args[0].equals("-d")) {
            Parser.displayAST(file);
        }
        else if (args[0].equals("-p")) {
            Parser.writeAST(file);
        }
        else {
            Parser.parseOnly(file);
        }
    }
}
