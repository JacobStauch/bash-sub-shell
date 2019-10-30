package bashShell;

import bashShell.ast.*;

/**
 *
 */
public class Parser {
    private Token currentToken = null;
    private MyScanner scanner = new MyScanner();
    private boolean hadError = false;
    private Script myAST;

    //------------- AST Methods -------------

    /**
     * Prints the AST to the console by recursively visiting the tree
     * @return AST line by line to the console
     */
    private void ASTToString() {
        String outAST = this.myAST.visit(0);
        System.out.println(outAST);
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
        Command myCom = parseCommand();
        return new Script(myCom);
    }

    /**
     *
     * @param c2
     * @return A sequential Command if a SeqCmd can be constructed,
     * otherwise return input Command
     */
    private Command parseCommands(Command c2) {
        if (currentToken.kind != Token.EOT &&
                currentToken.kind != Token.EOL &&
                currentToken.kind != Token.ELSE &&
                currentToken.kind != Token.FI &&
                currentToken.kind != Token.OD) {
            Command c1;
            c1 = parseCommand();
            return new SeqCmd(c1, c2);
        }
        else {
            return c2;
        }

    }

    private Command parseCommand() {
        switch (currentToken.kind) {
            // Exec-Cmd Case
            case Token.FName: {
                FNameArg ECFName;
                Argument ECArg = null;

                ECFName = parseFileName();
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    ECArg = parseArguments();
                accept(Token.EOL);
                ExecCmd EC = new ExecCmd(ECFName, ECArg);
                return parseCommands(EC);
            }

            // Assign-Cmd Case
            case Token.VAR: {
                VarArg ACVarArg;
                SingleArg ACSingleArg = null;

                ACVarArg = parseVariable();
                accept(Token.ASSIGN);
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    ACSingleArg = parseSingleArg();
                accept(Token.EOL);
                AssignCmd AC = new AssignCmd(ACVarArg, ACSingleArg);
                return parseCommands(AC);
            }

            // If-Cmd Case
            case Token.IF: {
                FNameArg ICCom;
                Argument ICArg = null;
                Command ICThen = null;
                Command ICElse = null;

                accept(Token.IF);

                ICCom = parseFileName();

                // Starter set for argument
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    ICArg = parseArguments();

                accept(Token.THEN);
                accept(Token.EOL);

                // Starter set for command
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.VAR
                        || currentToken.kind == Token.IF
                        || currentToken.kind == Token.FOR) {
                    ICThen = parseCommand();
                }

                accept(Token.ELSE);
                accept(Token.EOL);

                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.VAR
                        || currentToken.kind == Token.IF
                        || currentToken.kind == Token.FOR) {
                    ICElse = parseCommand();
                }

                accept(Token.FI);
                accept(Token.EOL);
                IfCmd IC = new IfCmd(ICCom, ICArg, ICThen, ICElse);
                return parseCommands(IC);
            }

            // For-Cmd Case
            case Token.FOR: {
                VarArg FCVar;
                Argument FCArg = null;
                Command FCDo = null;

                accept(Token.FOR);
                FCVar = parseVariable();
                accept(Token.IN);

                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    FCArg = parseArguments();

                accept(Token.EOL);
                accept(Token.DO);
                accept(Token.EOL);

                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.VAR
                        || currentToken.kind == Token.IF
                        || currentToken.kind == Token.FOR) {
                    FCDo = parseCommand();
                }

                accept(Token.OD);
                accept(Token.EOL);
                ForCommand FC = new ForCommand(FCVar, FCArg, FCDo);
                return parseCommands(FC);
            }

            // Default: Empty-Cmd Case
            default:
                return new NullCmd();
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
        this.myAST = parseScript();
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
        myParser.ASTToString();
    }
}
