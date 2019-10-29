package bashShell;

import bashShell.ast.*;

/**
 *
 */
public class Parser {
    private Token currentToken = null;
    private MyScanner scanner = new MyScanner();
    private boolean hadError = false;
    public Script AST;

    //------------- AST Methods -------------

    /**
     * Prints the AST to the console by recursively visiting the tree
     * @return AST line by line to the console
     */
    private String ASTToString() {
        return this.AST.visit(0);
    }

    //------------- Utility Methods -------------

    /**
     * Accept a specified token if it matches the
     * current Token.  Acceptance entails setting
     * currentToken to the next token in the input
     * stream.
     *
     * @param expectedKind The expected type of token.
     */
    private void accept(byte expectedKind) {
        if (currentToken.kind == expectedKind) {
            if (currentToken.kind != Token.EOT) {
                currentToken = scanner.scan();
            }
        }
        else {
            writeError("Expected: " + Token.kindString(expectedKind) +
                    "\nFound: " + Token.kindString(currentToken.kind));
            hadError = true;
        }
    }

    /**
     * Accept the current token by setting currentToken
     * to the next token in the input stream.
     */
    private void acceptIt() {
        if (currentToken.kind != Token.EOT) {
            currentToken = scanner.scan();
        }
    }

    private void writeError(String s) {
        System.out.println(s);
    }

    //---------------- Parsing Methods ---------------
    private Script parseScript() {
        currentToken = scanner.scan();
        while (currentToken.kind == Token.FName
                || currentToken.kind == Token.VAR
                || currentToken.kind == Token.IF
                || currentToken.kind == Token.FOR)
            return new Script(parseCommand());
        return null;
    }

    private Command parseCommand() {
        switch (currentToken.kind) {
            case Token.FName: {
                acceptIt();
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    parseArguments();
                accept(Token.EOL);
                break;
            }

            case Token.VAR: {
                acceptIt();
                accept(Token.ASSIGN);
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    parseArguments();
                accept(Token.EOL);
                break;
            }

            case Token.IF: {
                accept(Token.IF);

                parseFileName();

                // Starter set for argument
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    parseArguments();

                accept(Token.THEN);
                accept(Token.EOL);

                // Starter set for command
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.VAR
                        || currentToken.kind == Token.IF
                        || currentToken.kind == Token.FOR) {
                    parseCommand();
                }

                accept(Token.ELSE);
                accept(Token.EOL);

                // The original classic thing
                // More of the same
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.VAR
                        || currentToken.kind == Token.IF
                        || currentToken.kind == Token.FOR) {
                    parseCommand();
                }

                accept(Token.FI);
                accept(Token.EOL);
                break;
            }

            case Token.FOR: {
                acceptIt();
                parseVariable();
                accept(Token.IN);

                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    parseArguments();

                accept(Token.EOL);
                accept(Token.DO);
                accept(Token.EOL);

                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.VAR
                        || currentToken.kind == Token.IF
                        || currentToken.kind == Token.FOR) {
                    parseCommand();
                }

                accept(Token.OD);
                accept(Token.EOL);
                break;
            }
        }
    }

    private Argument parseArguments() {
        Argument a1;
        Argument a2;
        // First, we process the first argument
        switch (currentToken.kind) {
            case Token.FName: {
              a1 = parseFileName();
              break;
            }
            case Token.LIT: {
              a1 = parseLiteral();
              break;
            }
            case Token.VAR: {
              a1 = parseVariable();
              break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + currentToken.kind);
        }
        // Now we see if there's another argument (haven't reached EOL, therefore SeqArg)
        if (currentToken.kind != Token.EOL) {
            a2 = parseArguments();
            return new SeqArg(a1, a2);
        }
        // Else just return the single argument we found.
        return (SingleArg)a1;
    }

    private SingleArg parseSingleArg() {
        switch (currentToken.kind) {
            case Token.FName: {
                return parseFileName();
            }
            case Token.LIT: {
                return parseLiteral();
            }
            case Token.VAR: {
                return parseVariable();
            }
        }
        return null;
    }

    /**
     * Stores the spelling of the current token (Var,FName,Literal),
     * accepts it, and returns the appropriate AST node, visiting
     * it and its terminal
     * @return Appropriate AST node (Var,FName,Literal)
     */
    private VarArg parseVariable() {
        Terminal t = new Terminal(currentToken.spelling);
        acceptIt();
        return new VarArg(t);
    }

    private FNameArg parseFileName() {
        Terminal t = new Terminal(currentToken.spelling);
        acceptIt();
        return new FNameArg(t);
    }

    private LiteralArg parseLiteral() {
        Terminal t = new Terminal(currentToken.spelling);
        acceptIt();
        return new LiteralArg(t);
    }

    //------------- Main Methods -------------

    /**
     * Parses the input script, storing it in the Parser class's AST
     */
    public void parse() {
        this.AST = parseScript();
        if (currentToken.kind == Token.EOT && !hadError) {
            System.out.println("Correctly parsed.");
        }
        else
            writeError("Not a bash command.");
    }

    /**
     * Creates instance of Parser class and parses the input
     * @param args
     */
    public static void main(String [] args) {
        Parser myParser = new Parser();
        myParser.parse();
    }
}
