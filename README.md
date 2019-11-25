# Bash-Sub-Shell
"Example of a parser for a simple bash script recursive decent parser.  Grammar conforms to problem 4.10 from Programming Language Processors in Java by Watt and Brown."

Regular Expressions:

Variable `[a-zA-Z](([a-zA-Z]|[0-9]|\_|\.)*)`
Literal: `-(-?)((([a-zA-Z]|[0-9])*))|[0-9]*`

# How to run
The "compile2C" script is the primary way of running this program.

Note that this script simply points to a Compile2C jar. The version included in the repo may or may not be up to date with the source.

The CLI arguments are as follows:

```bash
compile2C (-d | -p) file
-d                    Produced AST is printed to the console.
-p                    Produced AST is written to a file.
file:                 The input script to parse.

Example scripts can be found in the scripts directory.
