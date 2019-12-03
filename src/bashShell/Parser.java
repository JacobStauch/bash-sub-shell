package bashShell;

import bashShell.ast.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 */
class Parser {
    private Token currentToken;
    private MyScanner scanner;
    private boolean hadError;
    private Script myAST;
    private String myFile;

    Parser(String file) throws IOException {
        currentToken = null;
        hadError = false;
        setMyFile(file);
        scanner = new MyScanner(file);
    }


    /**
     * Parses the input script, storing it in the Parser class's AST
     */
    private void parse() {
        this.myAST = parseScript();
        if (currentToken.kind == Token.EOT && !hadError) {
            System.out.println("Correctly parsed.");
        }
        else
            writeError("Not a bash command.");
    }

    private void setMyFile(String file) {
        myFile = file;
    }

    //------------- AST Methods -------------

    /**
     * Prints the AST to the console by recursively visiting the tree
     * @return AST line by line to the console
     */
    private String ASTToString() {
        String outAST = this.myAST.visit(0);
        return outAST;
    }

    /**
     * Creates a file based on the input script and writes the AST to it
     * @param AST String containing the AST
     * @param fileName Name of the target file, based on name of script
     * @throws FileNotFoundException
     */
    private void writeFile(String AST, String fileName) throws FileNotFoundException {
        File targetFile = new File("AST" + ".txt");
        PrintWriter out = new PrintWriter(targetFile);
        out.println(AST);
        out.close();
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
                accept(Token.IF);

                FNameArg ICCom;
                Argument ICArg = null;
                Command ICThen = null;
                Command ICElse = null;

                /* if Filename Argument* then eol
                / Token's name must be test or ps
                / otherwise it can't resolve to
                / a boolean, set hadError to true
                */

                if (!currentToken.name.equals("ps") && !currentToken.name.equals("test"))
                    hadError = true;

                ICCom = parseFileName();

                // Starter set for argument
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    ICArg = parseIfArguments();

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

                if (currentToken.kind == Token.FName
                        || currentToken.kind == Token.VAR
                        || currentToken.kind == Token.IF
                        || currentToken.kind == Token.FOR) {
                    ICElse = parseCommand();
                }
                else {
                    ICElse = new NullCmd();
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

    private Argument parseIfArguments() {
        Argument a1;
        Argument a2;
        // First, we process the first argument
        switch (currentToken.kind) {
            case Token.FName: {
                if (currentToken.name.equals("ps") || currentToken.name.equals("test")) {
                    a1 = parseFileName();
                    break;
                }
                else {
                    hadError = true;
                    a1 = parseFileName();
                    break;
                }
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
        if (currentToken.kind != Token.EOL &&
                currentToken.kind != Token.THEN &&
                currentToken.kind != Token.FI &&
                currentToken.kind != Token.OD &&
                currentToken.kind != Token.ELSE) {
            a2 = parseArguments();
            return new SeqArg(a1, a2);
        }
        // Else just return the single argument we found.
        return (SingleArg)a1;
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
        if (currentToken.kind != Token.EOL &&
                currentToken.kind != Token.THEN &&
                currentToken.kind != Token.FI &&
                currentToken.kind != Token.OD &&
                currentToken.kind != Token.ELSE) {
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
        Terminal t = new Terminal(currentToken.name);
        acceptIt();
        return new VarArg(t);
    }

    private FNameArg parseFileName() {
        Terminal t = new Terminal(currentToken.name);
        acceptIt();
        return new FNameArg(t);
    }

    private LiteralArg parseLiteral() {
        Terminal t = new Terminal(currentToken.name);
        acceptIt();
        return new LiteralArg(t);
    }

    //------------- Main Methods -------------

    /**
     * Creates instance of Parser class and only parses the input
     * @param file Filename to parse provided by Compile2C
     * @throws FileNotFoundException
     */
    static void parseOnly(String file) throws IOException {
        Parser myParser = new Parser(file);
        myParser.parse();
    }

    /**
     * Creates instance of Parser class and parses the input,
     * writing it the console
     * @param file Filename to parse provided by Compile2C
     * @throws FileNotFoundException
     */
    static void displayAST(String file) throws IOException {
        Parser myParser = new Parser(file);
        myParser.parse();
        String out = myParser.ASTToString();
        System.out.println(out);
    }

    /**
     * Creates instance of Parser class and parses the input,
     * writing it to a file
     * @param file Filename to parse provided by Compile2C
     * @throws FileNotFoundException
     */
    static void writeAST(String file) throws IOException {
        Parser myParser = new Parser(file);
        myParser.parse();
        String out = myParser.ASTToString();
        myParser.writeFile(out, file);
    }
}
