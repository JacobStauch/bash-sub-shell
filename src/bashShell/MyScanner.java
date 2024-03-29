package bashShell;

import javax.print.attribute.SetOfIntegerSyntax;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;

// Scans code and returns the next token if given token is actually a token

class MyScanner {

    private Scanner sc;
    private String[] filenames;
    private String literalRegex;
    private String variableRegex;

    MyScanner(String file) throws IOException {
        // Read in file as a string
        String fileContents = new String(Files.readAllBytes(Paths.get(file)));

        // Remove all tabs and extraneous spaces
        // Replace newline with explicit eol
        // Append eot to end of file
        fileContents = fileContents.replaceAll("\\n", " eol ");
        fileContents = fileContents.replaceAll("\\t", "");
        fileContents += "eot";
        fileContents = fileContents.trim().replaceAll(" +", " ");

        Scanner scan = new Scanner(fileContents);
        String command = scan.nextLine();
        this.sc = new Scanner(command);

        // List of filenames and terminal regexes
        this.filenames = new String[] {"cat", "ls", "pwd", "touch", "cp", "mv", "rm", "chmod", "man", "ps", "bg", "mkdir", "test", "cd"};
        this.literalRegex = "(-(-?)((([a-zA-Z]|[0-9])*)))|[0-9]*";
        this.variableRegex = "[a-zA-Z](([a-zA-Z]|[0-9]|\\_|\\.)*)";
    }

    public Token scan() {
        // Create an empty Token
        Token token = new Token();

        // Read a thing
        String potentialToken = this.sc.next();

        // If it's a terminal let's set it to the appropriate type
        // "The lexical scanner should identify any terminal that matches a Variable and create a Token for a Variable"
        // If it's not one of our terminals, let's check if it's one of the basic syntax items
        switch (potentialToken) {
            case "=":
                token.kind = Token.ASSIGN;
                return token;
            case "if":
                token.kind = Token.IF;
                return token;
            case "then":
                token.kind = Token.THEN;
                return token;
            case "else":
                token.kind = Token.ELSE;
                return token;
            case "fi":
                token.kind = Token.FI;
                return token;
            case "for":
                token.kind = Token.FOR;
                return token;
            case "in":
                token.kind = Token.IN;
                return token;
            case "do":
                token.kind = Token.DO;
                return token;
            case "od":
                token.kind = Token.OD;
                return token;
            case "eol":
                token.kind = Token.EOL;
                return token;
            case "eot":
                token.kind = Token.EOT;
                return token;
            default:
                if (Arrays.asList(this.filenames).contains(potentialToken)) {
                    token.kind = Token.FName;
                    token.name = potentialToken;
                    return token;
                }
                else if (potentialToken.matches(this.variableRegex)) {
                    token.kind = Token.VAR;
                    token.name = potentialToken;
                    return token;
                }
                else if (potentialToken.matches(this.literalRegex)) {
                    token.kind = Token.LIT;
                    token.name = potentialToken;
                    return token;
                }
                else {
                    System.out.println("Unexpected token.");
                }
        }
        return token;
    }
}
