package asm.exceptions;

public class IllegalSymbolException extends Exception {
    public IllegalSymbolException(int lineNumber, String symbol) {
        super("Line " + lineNumber + ": symbol \"" + symbol + "\" is illegal.");
    }
}
