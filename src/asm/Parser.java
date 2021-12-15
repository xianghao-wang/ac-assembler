package asm;

import asm.exceptions.CInstructionException;
import asm.exceptions.IllegalSymbolException;
import asm.exceptions.InvalidCommandException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The class for parsing the assembly source codes
 * */
public class Parser {
    public enum CommandType {
        A_COMMAND,
        C_COMMAND,
        L_COMMAND
    }

    private static class Line {
        int lineNumber;
        String code;
        Line(int lineNumber, String code) {
            this.lineNumber = lineNumber;
            this.code = code;
        }
    }

    private List<Line> commands;
    private int idx;

    /**
     * Construct a parser for assembly code
     * @param source the lines of codes
     * */
    public Parser(List<String> source) {
        // filter all of meaningless lines
        this.commands = IntStream.rangeClosed(1, source.size())
                .mapToObj(i -> new Line(i, source.get(i - 1)))
                .map(line -> new Line(line.lineNumber, clean(line.code)))
                .filter(line -> line.code.length() != 0)
                .collect(Collectors.toList());
        this.idx = -1;
    }

    /**
     * Clean a line of code by removing white characters and comments
     * @param s the code
     * */
    private String clean(String s) {
        return s.replaceAll("\s", "")
                .replaceAll("//.*", "");
    }

    /**
     * Check whether there are more commands
     * @return true if there are more commands for parsing; otherwise, false
     * */
    public boolean hasMoreCommands() {
        return idx < commands.size() - 1;
    }

    /**
     * Move to the next command
     * */
    public void advance() {
        this.idx += 1;
    }

    /**
     * Get the type of the current command
     * @return return A_COMMAND if it is an A instruction; return C_COMMAND if it is a C instruction; return L_COMMAND if it is a label
     * @throws InvalidCommandException detecting an invalid command
     * */
    public CommandType commandType() throws InvalidCommandException {
        String a_pattern = "@.*";
        String c_pattern = "(.*=.*)|(.*;.*)";
        String l_pattern = "\\(.*\\)";

        Line line = commands.get(idx);
        if (line.code.matches(a_pattern)) {
            return CommandType.A_COMMAND;
        } else if (line.code.matches(c_pattern)) {
            return CommandType.C_COMMAND;
        } else if (line.code.matches(l_pattern)) {
            return CommandType.L_COMMAND;
        } else {
            throw new InvalidCommandException("Line " + line.lineNumber + ": invalid command \"" + line.code + "\".");
        }
    }

    /**
     * Get the symbol of A_COMMAND i.e. @value or L_COMMAND i.e. (label)
     * This command can be only applied to the command of A_COMMAND or L_COMMAND type
     * @return the symbol
     * */
    public String symbol() throws IllegalSymbolException {
        String symbol_pattern = "[_a-zA-Z]+[0-9]*[_a-zA-Z]*";
        Line line = commands.get(idx);
        String command = line.code;

        try {
            switch (commandType()) {
                case A_COMMAND -> {
                    String value = command.substring(1);

                    // the value is deciaml
                    if (value.matches("[0-9]+")) {
                        return value;
                    }

                    // the value is symbol
                    if (value.matches(symbol_pattern)) {
                        return value;
                    }

                    // non-sense value
                    throw new IllegalSymbolException(line.lineNumber, value);
                }

                case L_COMMAND -> {
                    String symbol = command.substring(1, command.length() - 1);
                    if (symbol.matches(symbol_pattern)) {
                        return symbol;
                    }

                    throw new IllegalSymbolException(line.lineNumber, symbol);
                }
            }
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get the destination of C instruction
     * @return the destination; if no destination, return an empty string
     * @throws CInstructionException the destinantion is illegal
     * */
    public String dest() throws CInstructionException {
        String dest_pattern = "A|D|M|(AD)|(DM)|(AM)|(ADM)";
        if (command().contains("=")) {
            String[] pieces = command().split("=");
            String destination = pieces[0];
            if (destination.matches(dest_pattern)) {
                return destination;
            } else {
                throw new CInstructionException(lineNumber(), "destination", destination);
            }
        } else {
            return "";
        }
    }

    /**
     * Get the string of this command
     * @return the command
     * */
    private String command() {
        return commands.get(idx).code;
    }

    /**
     * Get the current line number
     * */
    private int lineNumber() {
        return commands.get(idx).lineNumber;
    }
}
