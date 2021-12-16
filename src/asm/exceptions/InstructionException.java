package asm.exceptions;

public class InstructionException extends Exception {
    public InstructionException(int lineNumber, String msg) {
        super("Line " + lineNumber + ": " + msg);
    }
}
