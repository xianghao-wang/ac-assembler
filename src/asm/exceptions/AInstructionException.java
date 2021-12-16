package asm.exceptions;

public class AInstructionException extends InstructionException {
    public AInstructionException(int lineNumber, String symbol) {
        super(lineNumber, "symbol \"" + symbol + "\" is illegal.");
    }
}
