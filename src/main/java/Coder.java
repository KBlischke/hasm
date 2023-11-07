/**
 * <p>
 * The <code>Coder</code> class translates Hack assembly mnemonics into Hack machine instructions.
 * </p>
 * 
 * @author  Kevin Blischke
 * @version 1.0
 */
public class Coder {
    /**
     * <p>
     * Initializes a new {@link Coder} object.
     * </p>
     */
    public Coder () {}

    /**
     * <p>
     * Returns the machine instruction for the mnemonic of a Hack assembly destination field.
     * </p>
     * <p>
     * When the mnemonic isn't a mnemonic of a Hack assembly destination field, 
     * the machine instruction for <code>null</code> is returned per default.
     * </p>
     * 
     * @param dest The mnemonic of a Hack assembly destination field
     * @return The translated machine Instruction of the mnemonic
     * 
     * @see java.lang.String
     */
    public static String getDest(String dest) {
        try {
            switch (dest) {
                case "M":
                    return "001";
                case "D":
                    return "010";
                case "DM":
                    return "011";
                case "MD":
                    return "011";
                case "A":
                    return "100";
                case "AM":
                    return "101";
                case "MA":
                    return "101";
                case "AD":
                    return "110";
                case "DA":
                    return "110";
                case "ADM":
                    return "111";
                case "AMD":
                    return "111";
                case "DMA":
                    return "111";
                case "DAM":
                    return "111";
                case "MDA":
                    return "111";
                case "MAD":
                    return "111";
                default:
                    return "000";
            }
        }
        catch (NullPointerException e) {
            return "000";
        }
    }

    /**
     * <p>
     * Returns the machine instruction for the mnemonic of a Hack assembly computation field.
     * </p>
     * <p>
     * When the mnemonic isn't a mnemonic of a Hack assembly computation field, 
     * the machine instruction for <code>0</code> is returned per default.
     * </p>
     * 
     * @param dest The mnemonic of a Hack assembly computation field
     * @return The translated machine Instruction of the mnemonic
     * 
     * @see java.lang.String
     */
    public static String getComp(String dest) {
        try {
            switch (dest) {
                case "1":
                    return "0111111";
                case "-1":
                    return "0111010";
                case "D":
                    return "0001100";
                case "A":
                    return "0110000";
                case "M":
                    return "1110000";
                case "!D":
                    return "0001101";
                case "!A":
                    return "0110001";
                case "!M":
                    return "1110001";
                case "-D":
                    return "0001111";
                case "-A":
                    return "0110011";
                case "-M":
                    return "1110011";
                case "D+1":
                    return "0011111";
                case "A+1":
                    return "0110111";
                case "M+1":
                    return "1110111";
                case "D-1":
                    return "0001110";
                case "A-1":
                    return "0110010";
                case "M-1":
                    return "1110010";
                case "D+A":
                    return "0000010";
                case "A+D":
                    return "0000010";
                case "D+M":
                    return "1000010";
                case "M+D":
                    return "1000010";
                case "D-A":
                    return "0010011";
                case "D-M":
                    return "1010011";
                case "A-D":
                    return "0000111";
                case "M-D":
                    return "1000111";
                case "D&A":
                    return "0000000";
                case "A&D":
                    return "0000000";
                case "D&M":
                    return "1000000";
                case "M&D":
                    return "1000000";
                case "D|A":
                    return "0010101";
                case "A|D":
                    return "0010101";
                case "D|M":
                    return "1010101";
                case "M|D":
                    return "1010101";
                default:
                    return "0101010";
            }
        }
        catch (NullPointerException e) {
            return "0101010";
        }
    }

    /**
     * <p>
     * Returns the machine instruction for the mnemonic of a Hack assembly jump field.
     * </p>
     * <p>
     * When the mnemonic isn't a mnemonic of a Hack assembly jump field, 
     * the machine instruction for <code>null</code> is returned per default.
     * </p>
     * 
     * @param dest The mnemonic of a Hack assembly jump field
     * @return The translated machine Instruction of the mnemonic
     * 
     * @see java.lang.String
     */
    public static String getJump(String dest) {
        try {
            switch (dest) {
                case "JGT":
                    return "001";
                case "JEQ":
                    return "010";
                case "JGE":
                    return "011";
                case "JLT":
                    return "100";
                case "JNE":
                    return "101";
                case "JLE":
                    return "110";
                case "JMP":
                    return "111";
                default:
                    return "000";
            }
        }
        catch (NullPointerException e) {
            return "000";
        }
    }

    /**
     * <p>
     * Returns the binary value of a decimal address value.
     * </p>
     * <p>
     * When the address value isn't a decimal number, 
     * <code>null</code> is returned instead.
     * </p>
     * 
     * @param address A decimal address value
     * @return The binary value of a decimal address value
     * 
     * @see java.lang.String
     */
    public static String getAddress(String address) {
        try {
            return String.format(
                "%15s",
                Integer.toBinaryString(Integer.parseInt(address))
            ).replace(" ", "0");
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
}
