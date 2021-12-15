package asm.exceptions;

/**
 * The exception will be thrown when detecting an invalid command
 * */
public class InvalidCommandException extends Exception {
    public InvalidCommandException(String msg) {
        super(msg);
    }
}
