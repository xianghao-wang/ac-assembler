package asm;

import asm.exceptions.InvalidCommandException;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InvalidCommandException {
        List<String> lines = List.of(
                "abc",
                "@A",
                "ADM=1",
                "0;JMP",
                "(LOOP)",
                "// hello"
        );

        Parser parser = new Parser(lines);
        while (parser.hasMoreCommands()) {
            parser.advance();
            System.out.println(parser.commandType());
        }
    }
}
