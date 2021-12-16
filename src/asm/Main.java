package asm;

import asm.exceptions.CInstructionException;
import asm.exceptions.IllegalSymbolException;
import asm.exceptions.InvalidCommandException;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InvalidCommandException, IllegalSymbolException, CInstructionException {
        List<String> lines = List.of(
                "ADM=1",
                "A=A&D"
        );

        Parser parser = new Parser(lines);
        while (parser.hasMoreCommands()) {
            parser.advance();
            if (parser.commandType() == Parser.CommandType.A_COMMAND || parser.commandType() == Parser.CommandType.L_COMMAND) {
                System.out.println(parser.symbol());
            } else {
                System.out.println(parser.comp());
            }
        }
    }
}
