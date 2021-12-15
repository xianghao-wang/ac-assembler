package asm.exceptions;

public class CInstructionException extends Exception {
    public CInstructionException(int lineNumber, String part, String actual) {
        super(String.format("Line %d: \"%s\" is illegal %s of C instruction.", lineNumber, actual, part));
    }
}
