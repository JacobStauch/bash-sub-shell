# Bash-Sub-Shell
"Example of a parser for a simple bash script recursive decent parser.  Grammar conforms to problem 4.10 from Programming Language Processors in Java by Watt and Brown."

Regular Expressions:

Variable `[a-zA-Z](([a-zA-Z]|[0-9]|\_|\.)*)`
Literal: `-(-?)((([a-zA-Z]|[0-9])*))|[0-9]*`

# How to run
Parser.java is the main class for this project. When running it, you will be prompted to enter a command.
Commands must be formed on a single line, inserting "eol" and "eot" where appropriate (eg. "touch myfile.txt eol eot").
Tokens will be logged to the console as they are tokenized, resulting in a correctly or incorrectly parsed message log.

# Examples

## One line example
`touch myfile.txt eol eot`

## If example
`if test -e apples then eol else eol touch apples eol fi eol mkdir basket eol fruit = apples eol for file in fruit eol do eol mv file basket eol od eol eot`

## For example
`for apples in fruit eol do eol mv apples basket eol od eol eot`

## If Else example
`if test -e cal.txt then eol cat cal.txt eol else eol ls -l eol fi eol eot`
