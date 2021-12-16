package asm;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class SymbolTable {

    // built-in symbols
    private static final Map<String, Integer> BUILT_IN_SYMBOLS = new HashMap<>() {
        {
            // for VM
            put("SP", 0);
            put("LCL", 1);
            put("ARG", 2);
            put("THIS", 3);
            put("THAT", 4);

            // registers
            IntStream.rangeClosed(0, 15).forEach(i -> put("R" + i, i));

            // IO
            put("SCREEN", 0x4000);
            put("KBD", 0x6000);
        }
    };

    private final Map<String, Integer> symbols; // the symbol table

    public SymbolTable() {
        this.symbols = new HashMap<>();
    }

    /**
     * Check whether the symbol table contains the symbol
     * @param symbol the symbol
     * @return true if this symbol is in the table; otherwise, false
     * */
    private boolean contains(String symbol) {
        return BUILT_IN_SYMBOLS.containsKey(symbol) || symbols.containsKey(symbol);
    }

    /**
     * Return the address corresponding to the symbol
     * This method should be called only when the table contains the symbol
     * @param symbol the symbol
     * @return the address for this symbol
     * */
    private int getAddress(String symbol) {
        return BUILT_IN_SYMBOLS.containsKey(symbol) ? BUILT_IN_SYMBOLS.get(symbol) : symbols.get(symbol);
    }

    /**
     * Add (symbol, address) pair to symbol table
     * @param symbol the symbol
     * @param address the address
     * */
    public void addEntry(String symbol, int address) {
        symbols.put(symbol, address);
    }
}
