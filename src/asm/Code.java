package asm;

import java.util.HashMap;
import java.util.Map;

public class Code {
    // the table of machine codes related to computation
    private static final Map<String, String> COMPUTATION_CODES = new HashMap<>() {
        {
            put("0", "101010");
            put("1", "111111");
            put("-1", "111010");
            put("D", "001100");
            put("A", "110000"); put("M", "110000");
            put("!D", "001101");
            put("!A", "110001"); put("!M", "110001");
            put("-D", "001111");
            put("-A", "110011"); put("-M", "110011");
            put("D+1", "011111");
            put("A+1", "110111"); put("M+1", "110111");
            put("D-1", "001110");
            put("A-1", "110010"); put("M-1", "110010");
            put("D+A", "000010"); put("D+M", "000010");
            put("D-A", "010011"); put("D-M", "010011");
            put("A-D", "000111"); put("M-D", "000111");
            put("D&A", "000000"); put("D&M", "000000");
            put("D|A", "010101"); put("D|M", "010101");
        }
    };

    // the table of machine codes related to jump
    private static final Map<String, String> JUMP_CODES = new HashMap<>() {
        {
            put(null, "000");
            put("JMP", "111");
            put("JEQ", "010");
            put("JNE", "101");
            put("JGT", "001");
            put("JLT", "100");
            put("JGE", "011");
            put("JLE", "110");
        }
    };

    /**
     * Code destination to machine codes
     * @param destination the destination
     * @return the binary codes
     * */
    public String dest(String destination) {
        if (destination == null) {
            return "000";
        }

        return (destination.contains("A") ? "1" : "0") +
                (destination.contains("D") ? "1" : "0") +
                (destination.contains("M") ? "1" : "0");
    }

    /**
     * Code computation to machine codes
     * @param computation the computation
     * @return the binary codes
     * */
    public String comp(String computation) {
        return COMPUTATION_CODES.get(computation);
    }

    /**
     * Code jump to machine codes
     * @param jump the jump
     * @return the binary codes
     * */
    public String jump(String jump) {
        return JUMP_CODES.get(jump);
    }
}
