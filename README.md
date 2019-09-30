# Bash-Sub-Shell
"Example of a parser for a simple bash script recursive decent parser.  Grammar conforms to problem 4.10 from Programming Language Processors in Java by Watt and Brown."

Regular Expressions:

Variable `[a-zA-Z](([a-zA-Z]|[0-9]|\_|\.)*)`
Literal: `-(-?)((([a-zA-Z]|[0-9])*))|[0-9]*`

# How to run
Pasrer.java is the main class for this project. When running it, you will be prompted to enter a command. Commands must be formed on a single line, inserting "eol" and "eot" where appropriate (eg. "touch myfile.txt eol eot").
