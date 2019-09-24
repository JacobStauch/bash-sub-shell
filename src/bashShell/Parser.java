package bashShell;

public class Parser {
    private Token currentToken = null;
    private MyScanner scanner = new MyScanner();

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
        if (currentToken.kind == expectedKind)
            currentToken = scanner.scan();
        else
            writeError("Expected:  " + Token.kindString(expectedKind) +
                    "Found :" + Token.kindString(currentToken.kind));
    }

    /**
     * Accept the current token by setting currentToken
     * to the next token in the input stream.
     */
    private void acceptIt() {
        currentToken = scanner.scan();
    }

    private void writeError(String s) {
        System.out.print(s);
    }

    //---------------- Parsing Methods ---------------
    private void parseScript() {
        while (currentToken.kind == Token.FName
                || currentToken.kind == Token.VAR
                || currentToken.kind == Token.IF
                || currentToken.kind == Token.FOR)
            parseCommand();
    }

    private void parseCommand() {
        switch (currentToken.kind) {

            case Token.FName: {
                acceptIt();
                //parseFileName();
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    parseArgument();
                accept(Token.EOL);
            }

            case Token.VAR: {
                acceptIt();
                accept(Token.ASSIGN);
                parseArgument();
                accept(Token.EOL);
            }

            case Token.IF: {
                accept(Token.IF);

                parseFileName();

                // Starter set for argument
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    parseArgument();

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
            }

            case Token.FOR: {
                acceptIt();
                parseVariable();
                acceptIt(Token.IN);

                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    parseArgument();

                acceptIt(Token.EOL);
                acceptIt(Token.DO);

                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.VAR
                        || currentToken.kind == Token.IF
                        || currentToken.kind == Token.FOR) {
                    parseCommand();
                }

                accept(Token.OD);
                accept(Token.EOL);
            }
        }
    }

    private void parseArgument() {
        switch (currentToken.kind) {
            case Token.FName: {
              parseFileName();
            }
            case Token.LIT: {
              parseLiteral();
            }
            case Token.VAR: {
              parseVariable();
            }
        }
    }

    private void parseVariable() {
      acceptIt();
    }

    private void parseFileName() {
      acceptIt();
    }

    private void parseLiteral() {
      acceptIt();
    }

    public void parse() {
        parseScript();
        if(currentToken.equals("eot")) {
            System.out.println("Correctly parsed.");
        }
        else
            writeError("Not a bash command.");
    }

    public static void main(String [] args) {
        Parser myParser = new Parser();
        myParser.parse();
    }
}
