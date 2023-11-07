import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * The <code>Parser</code> class breaks every Hack assembly instruction into its underlying fields.
 * </p>
 * 
 * @author  Kevin Blischke
 * @version 1.0
 */
public class Parser {
    /**
     * <p>
     * The <code>instructions</code> property contains every instruction of the Hack assembly file.
     * </p>
     * <p>
     * It is implemented as an array list with every line of the Hack assembly file as elements.
     * </p>
     * 
     * @see java.util.ArrayList
     * @see java.lang.String
     */
    private ArrayList<String> instructions = new ArrayList<>();

    /**
     * <p>
     * The <code>current</code> property points to the current line in Hack assembly file.
     * </p>
     * 
     * @see java.lang.Integer
     */
    private Integer current;

    /**
     * <p>
     * Initializes a new {@link Parser} object.
     * </p>
     * 
     * @param instructions The content of a Hack asm file to translate
     */
    public Parser(String instructions) {
        this.instructions = new ArrayList<>(Arrays.asList(instructions.split("\n")));

        this.current = 0;
    }

    /**
     * <p>
     * Initializes a new {@link Parser} object.
     * </p>
     * 
     * @param instructions The content of a Hack asm file to translate
     */
    public Parser(String[] instructions) {
        this.instructions = new ArrayList<>(Arrays.asList(instructions));

        this.current = 0;
    }

    /**
     * <p>
     * Initializes a new {@link Parser} object.
     * </p>
     * 
     * @param file The Hack asm file to translate
     */
    public Parser(File file) {
        try {
            this.instructions = new ArrayList<>(Files.readAllLines(file.toPath()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        this.current = 0;
    }

    /**
     * <p>
     * Returns the value of the {@link #current} property.
     * </p>
     * 
     * @return The value of the {@link #current} property
     */
    public int getCurrentLine() {
        return this.current;
    }

    /**
     * <p>
     * Returns the instruction from the {@link #instructions} property 
     * that the {@link #current} property points to.
     * </p>
     * 
     * @return The instruction from the {@link #instructions} property 
     * that the {@link #current} property points to
     */
    public String getCurrentInstruction() {
        return this.instructions.get(this.current).trim();
    }

    /**
     * <p>
     * Returns the value of the {@link #instructions} property.
     * </p>
     * 
     * @return The value of the {@link #current} property
     */
    public String[] getInstructions() {
        return this.instructions.toArray(new String[this.instructions.size()]);
    }

    /**
     * <p>
     * Checks if the {@link #current} property doesn't point 
     * to the last instruction of the {@link #instructions} property.
     * </p>
     * 
     * @return Wether the {@link #current} property doesn't point 
     * to the last instruction of the {@link #instructions} property
     */
    public boolean hasMoreInstructions() {
        if (this.current < this.instructions.size()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * <p>
     * Advances the {@link #current} property forward.
     * </p>
     * If the {@link #current} property would exceed the the number of elements 
     * in the {@link #instructions} property, it won't execute.
     */
    public void advance() {
        if (hasMoreInstructions()) {
            ++this.current;
        }
    }

    /**
     * <p>
     * Sets the value of the {@link #current} property.
     * </p>
     * <p>
     * If the inputted value is larger than or equal to the number of elements 
     * in the {@link #instructions} property or is less than zero, it won't execute.
     * </p>
     * 
     * @param index The number to set the {@link #current} property to
     */
    public void setCurrent(int index) {
        if (index < this.instructions.size() && index > 0) {
            this.current = index;
        }
    }

    /**
     * <p>
     * Returns the Hack assembly instruction type of the current instruction 
     * in the {@link instructions} property
     * </p>
     * 
     * <p>
     * The following Strings are defined, that can be returned by the <code>getInstructionType</code> method:
     * <ul>
     * <li>
     * <code>"A_INSTRUCTION"</code>: The current instruction is an A-instruction
     * </li>
     * <li>
     * <code>"C_INSTRUCTION"</code>: The current instruction is an C-instruction
     * </li>
     * <li>
     * <code>"LABEL"</code>: The current instruction is a label
     * </li>
     * <li>
     * <code>"EMPTY"</code>: The current instruction contains only comments, whitespaces or is empty
     * </li>
     * </ul>
     * </p>
     * <p>
     * When the line isn't a valid Hacck assembly instruction, <code>null</code> is returned instead.
     * </p>
     * 
     * @return The Hack assembly instruction type of the current instruction in the {@link instructions} property
     * 
     * @see java.lang.String
     */
    public String getInstructionType() {
        String instruction = this.getCurrentInstruction().replaceAll(
            "//.*", ""
        ).replaceAll(
            "\s", ""
        );

        if (instruction.matches("^@(?:(?:[a-zA-Z_:$][\\w\\.:$]+)|(?:\\d+))$")) {
            return "A_INSTRUCTION";
        }
        else if (instruction.matches("^\\((?:[a-zA-Z_:$][\\w\\.:$]+)\\)$")) {
            return "LABEL";
        }
        else if (instruction.matches("""
            ^(?:(?:(?:null)|(?:ADM)|(?:AMD)|(?:DMA)|(?:DAM)|(?:DM)|(?:MD)|(?:AM)|(?:MA)|(?:AD)|(?:DA)|M|D|A)=)?
            (?:(?:0)|(?:-1)|(?:1)|(?:D\\+1)|(?:A\\+1)|(?:M\\+1)|(?:D-1)|(?:A-1)|(?:M-1)|
            (?:D\\+A)|(?:A\\+D)|(?:D\\+M)|(?:M\\+D)|(?:D-A)|(?:A-D)|(?:D-M)|(?:M-D)|
            (?:D&A)|(?:A&D)|(?:D&M)|(?:M&D)|(?:D\\|A)|(?:A\\|D)|(?:D\\|M)|(?:M\\|D)|
            (?:!D)|(?:!A)|(?:!M)|(?:-D)|(?:-A)|(?:-M)|(?:D)|(?:A)|(?:M))
            (?:;(?:(?:null)|(?:JGT)|(?:JEQ)|(?:JGE)|(?:JLT)|(?:JNE)|(?:JLE)|(?:JMP)))?$
            """.replace("\n", "")
        )) {
            return "C_INSTRUCTION";
        }
        else if (instruction.matches("^\s*$")) {
            return "EMPTY";
        }
        else {
            return null;
        }
    }

    /**
     * <p>
     * Returns the symbol or address inside the current A-instruction or label.
     * </p>
     * <p>
     * When the current instruction doesn't contain an address or symbol, <code>null</code> is returned instead.
     * </p>
     * 
     * @return The symbol or address inside the current A-instruction or label
     * 
     * @see java.lang.String
     */
    public String getSymbol() {
        String instruction = this.getCurrentInstruction().replaceAll(
            "//.*", ""
        ).replaceAll(
            "\s", ""
        );

        if (this.getInstructionType() == "A_INSTRUCTION") {
            return instruction.substring(1);
        }
        else if (this.getInstructionType() == "LABEL") {
            return instruction.substring(instruction.indexOf("(") + 1, instruction.indexOf(")"));
        }
        else {
            return null;
        }
    }

    /**
     * <p>
     * Returns the mnemmonic of the destination field inside the current C-instruction.
     * </p>
     * <p>
     * When the current instruction isn't a C-instruction or 
     * doesn't contain a destination field, <code>null</code> is returned instead.
     * </p>
     * 
     * @return The mnemmonic of the destination field inside the current C-instruction
     * 
     * @see java.lang.String
     */
    public String getDest() {
        try {
            String instruction = this.getCurrentInstruction().replaceAll(
                "//.*", ""
            ).replaceAll(
                "\s", ""
            );

            Pattern pattern = Pattern.compile(
                "^((null)|(ADM)|(AMD)|(DMA)|(DAM)|(MDA)|(MAD)|(DM)|(MD)|(AM)|(MA)|(AD)|(DA)|M|D|A)="
            );
            Matcher matcher = pattern.matcher(instruction);

            if (this.getInstructionType() == "C_INSTRUCTION" && matcher.find()) {
                return matcher.group(1);
            }
            else {
                return null;
            }
        }
        catch (IllegalStateException e) {
            return null;
        }
    }

    /**
     * <p>
     * Returns the mnemmonic of the computation field inside the current C-instruction.
     * </p>
     * <p>
     * When the current instruction isn't a C-instruction or 
     * doesn't contain a computation field, <code>null</code> is returned instead.
     * </p>
     * 
     * @return The mnemmonic of the computation field inside the current C-instruction
     * 
     * @see java.lang.String
     */
    public String getComp() {
        try {
            String instruction = this.getCurrentInstruction().replaceAll(
                "//.*", ""
            ).replaceAll(
                "\s", ""
            );

            Pattern pattern = Pattern.compile("""
                ^(?:(?:(?:null)|(?:ADM)|(?:AMD)|(?:DMA)|(?:DAM)|(?:MDA)|(?:MAD)|
                (?:DM)|(?:MD)|(?:AM)|(?:MA)|(?:AD)|(?:DA)|M|D|A)=)?
                ((0)|(-1)|(1)|(D\\+1)|(A\\+1)|(M\\+1)|(D-1)|(A-1)|(M-1)|
                (D\\+A)|(A\\+D)|(D\\+M)|(M\\+D)|(D-A)|(A-D)|(D-M)|(M-D)|
                (D&A)|(A&D)|(D&M)|(M&D)|(D\\|A)|(A\\|D)|(D\\|M)|(M\\|D)|
                (!D)|(!A)|(!M)|(-D)|(-A)|(-M)|(D)|(A)|(M))
                (?:;(?:(?:null)|(?:JGT)|(?:JEQ)|(?:JGE)|(?:JLT)|(?:JNE)|(?:JLE)|(?:JMP)))?$
                """.replace("\n", "")
            );
            Matcher matcher = pattern.matcher(instruction);

            if (this.getInstructionType() == "C_INSTRUCTION" && matcher.find()) {
                return matcher.group(1);
            }
            else {
                return null;
            }
        }
        catch (IllegalStateException e) {
            return null;
        }
    }

    /**
     * <p>
     * Returns the mnemmonic of the jump field inside the current C-instruction.
     * </p>
     * <p>
     * When the current instruction isn't a C-instruction or 
     * doesn't contain a jump field, <code>null</code> is returned instead.
     * </p>
     * 
     * @return The mnemmonic of the jump field inside the current C-instruction
     * 
     * @see java.lang.String
     */
    public String getJump() {
        try {
            String instruction = this.getCurrentInstruction().replaceAll(
                "//.*", ""
            ).replaceAll(
                "\s", ""
            );

            Pattern pattern = Pattern.compile(";((JGT)|(JEQ)|(JGE)|(JLT)|(JNE)|(JLE)|(JMP))$");
            Matcher matcher = pattern.matcher(instruction);

            if (this.getInstructionType() == "C_INSTRUCTION" && matcher.find()) {
                return matcher.group(1);
            }
            else {
                return null;
            }
        }
        catch (IllegalStateException e) {
            return null;
        }
    }
}
