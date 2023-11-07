import java.util.HashMap;

/**
 * <p>
 * The <code>SymbolTable</code> class keeps a correspondence between symbolic labels and numeric addresses.
 * </p>
 * 
 * @author  Kevin Blischke
 * @version 1.0
 */
public class SymbolTable {
    /**
     * <p>
     * The <code>instructions</code> property contains every instruction of the Hack assembly file.
     * </p>
     * 
     * @see java.util.HashMap
     * @see java.lang.String
     */
    private HashMap<String, String> table = new HashMap<>();

    /**
     * <p>
     * Initializes a new {@link SymbolTable} object.
     * </p>
     * <p>
     * The following entries are added per default:
     * <ul>
     * <li>R0 = 0</li>
     * <li>R1 = 1</li>
     * <li>R2 = 2</li>
     * <li>R3 = 3</li>
     * <li>R4 = 4</li>
     * <li>R5 = 5</li>
     * <li>R6 = 6</li>
     * <li>R7 = 7</li>
     * <li>R8 = 8</li>
     * <li>R9 = 9</li>
     * <li>R10 = 10</li>
     * <li>R11 = 11</li>
     * <li>R12 = 12</li>
     * <li>R13 = 13</li>
     * <li>R14 = 14</li>
     * <li>R15 = 15</li>
     * <li>SCREEN = 16384</li>
     * <li>KBD = 24576</li>
     * <li>SP = 0</li>
     * <li>LCL = 1</li>
     * <li>ARG = 2</li>
     * <li>THIS = 3</li>
     * <li>THAT = 4</li>
     * </ul>
     * </p>
     */
    public SymbolTable() {
        this.table.put("R0", "0");
        this.table.put("R1", "1");
        this.table.put("R2", "2");
        this.table.put("R3", "3");
        this.table.put("R4", "4");
        this.table.put("R5", "5");
        this.table.put("R6", "6");
        this.table.put("R7", "7");
        this.table.put("R8", "8");
        this.table.put("R9", "9");
        this.table.put("R10", "10");
        this.table.put("R11", "11");
        this.table.put("R12", "12");
        this.table.put("R13", "13");
        this.table.put("R14", "14");
        this.table.put("R15", "15");
        this.table.put("SCREEN", "16384");
        this.table.put("KBD", "24576");
        this.table.put("SP", "0");
        this.table.put("LCL", "1");
        this.table.put("ARG", "2");
        this.table.put("THIS", "3");
        this.table.put("THAT", "4");
    }

    /**
     * <p>
     * Adds a new entry to the {@link #table} property.
     * </p>
     * 
     * @param symbol  The symbol symbolic label to keep track of
     * @param address The numeric address of the symbolic label
     * 
     * @see   java.lang.String
     */
    public void addEntry(String symbol, String address) {
        this.table.put(symbol, address);
    }

    /**
     * <p>
     * Deletes an entry in the {@link #table} property.
     * </p>
     * 
     * @param symbol  The symbolic label to delete
     * 
     * @see   java.lang.String
     */
    public void deleteEntry(String symbol) {
        this.table.remove(symbol);
    }

    /**
     * <p>
     * Returns the value of the {@link #table} property.
     * </p>
     * 
     * @return The value of the {@link #table} property
     * 
     * @see    java.util.HashMap
     * @see    java.lang.String
     */
    public HashMap<String, String> getTable() {
        return this.table;
    }

    /**
     * <p>
     * Checks if a symbol exists in the {@link table} property.
     * </p>
     * 
     * @param  symbol The symbolic label to check
     * @return Wether a symbol exists in the {@link table} property
     * 
     * @see    java.lang.String
     */
    public boolean contains(String symbol) {
        return this.table.containsKey(symbol);
    }

    /**
     * <p>
     * Get the address associated with a symbolic label in the {@link #table} property.
     * </p>
     * <p>
     * When the symbol isn't contained in the {@link #table} property, <code>null</code> is returned instead.
     * </p>
     * 
     * @param  symbol The symbolic label to get
     * @return The address associated with the symbolic label
     * 
     * @see java.lang.String
     */
    public String getAddress(String symbol) {
        if (this.contains(symbol))
            return this.table.get(symbol);
        else {
            return null;
        }
    }
}
