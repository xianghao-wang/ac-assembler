package asm;

import asm.exceptions.CInstructionException;
import asm.exceptions.IllegalSymbolException;
import asm.exceptions.InvalidCommandException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InvalidCommandException, IllegalSymbolException, CInstructionException, FileNotFoundException {
        Scanner scanner = new Scanner(new File("test.asm"));
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        Parser parser = new Parser(lines);
        while (parser.hasMoreCommands()) {
            parser.advance();
            System.out.print(parser.lineNumber() + ": ");
            if (parser.commandType() == Parser.CommandType.A_COMMAND) {
                System.out.println("@<\"" + parser.symbol() + "\">");
            } else if (parser.commandType() == Parser.CommandType.L_COMMAND) {
                System.out.println("(<\"" + parser.symbol() + "\">)");
            } else {
                System.out.println("<\"" + parser.dest() + "\">=" + "<\"" + parser.comp() + "\">;" + "<\"" + parser.jump() + "\">");
            }
        }
    }
}
