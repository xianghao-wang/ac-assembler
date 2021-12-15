package asm;

import asm.exceptions.InvalidCommandException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The class for parsing the assembly source codes
 * */
public class Parser {
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
        this.commands = IntStream.range(0, source.size())
                .mapToObj(i -> new Line(i, source.get(i)))
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
    public String commandType() throws InvalidCommandException {
        String a_pattern = "@.*";
        String c_pattern = "(.*=.*)|(.*;.*)";
        String l_pattern = "\\(.*\\)";

        Line line = commands.get(idx);
        if (line.code.startsWith(a_pattern)) {
            return "A_COMMAND";
        } else if (line.code.matches(c_pattern)) {
            return "C_COMMAND";
        } else if (line.code.matches(l_pattern)) {
            return "L_COMMAND";
        } else {
            throw new InvalidCommandException("Line " + line.lineNumber + ": invalid command \"" + line.code + "\".");
        }
    }

    /**
     * Get the raw string of this command
     * */
    public String raw() {
        return commands.get(idx).code;
    }
}
