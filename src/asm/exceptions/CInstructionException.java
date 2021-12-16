package asm.exceptions;

public class CInstructionException extends InstructionException {
    public CInstructionException(int lineNumber, String part, String actual) {
        super(lineNumber, String.format("\"%s\" is illegal %s of C instruction.", actual, part));
    }
}
