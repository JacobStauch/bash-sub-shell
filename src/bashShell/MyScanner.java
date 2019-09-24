package bashShell;

import javax.print.attribute.SetOfIntegerSyntax;
import java.util.Scanner;

// Scans code and returns the next token if given token is actually a token

public class MyScanner {

    public Scanner sc;

    MyScanner() {
        System.out.println("Enter a bash command: ");
        Scanner scan = new Scanner(System.in);
        String command = scan.nextLine();
        Scanner sc = new Scanner(command);
        // Handle whitespace
        sc.useDelimiter(" ");
    }

    public Token scan() {
        // Create an empty Token
        Token token = null;

        // Read a thing
        String potentialToken = this.sc.next();

        // See if it's a token

        // If it's a terminal let's set it to the appropriate type
        // "The lexical scanner should identify any terminal that matches a Variable and create a Token for a Variable"

        // If it's EOT then we want to see if that's what comes out in Parser

        // Great let's return the next token

        return token;
    }

    public static void main(String [] args) {

    }
}
