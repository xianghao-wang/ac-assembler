package asm;

import asm.exceptions.InstructionException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<String> read(String src) throws FileNotFoundException {
        List<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(new File(src));
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        return lines;
    }

    private static void write(List<String> lines, String des) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(des))) {
            for (String line : lines) {
                out.write(line + "\n");
            }
        } catch (IOException e) {
            throw e;
        }
    }

    private static List<String> assemble(List<String> lines) throws InstructionException {
        Parser parser = new Parser(lines);
        Code code = new Code();
        SymbolTable symbolTable = new SymbolTable();
        List<String> output = new ArrayList<>();

        // first scan for finding all of labels
        int idx = 0; // record the line of label
        while (parser.hasMoreCommands()) {
            parser.advance();

            if (parser.commandType() == Parser.CommandType.L_COMMAND) {
                String symbol = parser.symbol();
                if (symbolTable.contains(symbol)) {
                    throw new InstructionException(parser.lineNumber(), "label \"" + symbol + "\" is already being used.");
                } else {
                    symbolTable.addEntry(symbol, idx);
                }
            } else {
                idx += 1;
            }
        }

        // second sacn for replacing symbol with actual values
        parser.reset();
        while (parser.hasMoreCommands()) {
            parser.advance();

            if (parser.commandType() == Parser.CommandType.A_COMMAND) {
                String value = parser.symbol();
                int address;
                try {
                    address = Integer.parseInt(value);
                } catch (Exception e) {
                    if (!symbolTable.contains(value)) {
                        symbolTable.addEntry(value);
                    }
                    address = symbolTable.getAddress(value);
                }

                output.add("0" + toBinary15(address));

            } else if (parser.commandType() == Parser.CommandType.C_COMMAND) {
                String a = parser.comp().contains("M") ? "1" : "0";
                output.add(
                        "111" + a + code.comp(parser.comp()) + code.dest(parser.dest()) + code.jump(parser.jump())
                );
            }
        }

        return output;
    }

    private static String toBinary15(int x) {
        String bits = "";
        while (x != 0) {
            bits = (x % 2) + bits;
            x /= 2;
        }

        return "0".repeat(15 - bits.length()) + bits;
    }


    public static void main(String[] args) throws Exception {
        String usage = "Usage: java -jar assemble.jar <input_file.asm>";
        String src;
        String des;
        if (args.length == 0) {
            System.out.println(usage);
            return;
        } else {
            src = args[0];
            if (src.matches(".*\\.asm")) {
                des = src.substring(0, src.length() - 4) + ".hack";
            } else {
                System.out.println(usage);
                return;
            }
        }

        List<String> lines = read(src);
        List<String> ouput = assemble(lines);
        write(ouput, des);
    }
}
