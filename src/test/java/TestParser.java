import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * The <code>TestParser</code> class tests the {@link Parser} class inside a <code>JUnit</code> test.
 * </p>
 * 
 * @author  Kevin Blischke
 * @version 1.0
 */
public class TestParser {
    /**
     * <p>
     * Tests the constructors of the {@link Parser} object inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testInit() {
        String forFirstParser = "1\n2\n3";
        Parser firstParser = new Parser(forFirstParser);
        assertArrayEquals(new String[] {"1", "2", "3"}, firstParser.getInstructions());

        String[] forSecondParser = {"1", "2", "3"};
        Parser secondParser = new Parser(forSecondParser);
        assertArrayEquals(new String[] {"1", "2", "3"}, secondParser.getInstructions());

        try {
            File testFile = new File("test.asm");
            FileWriter testWriter = new FileWriter("test.asm");
            testWriter.write("1\n2\n3");
            testWriter.close();

            assertArrayEquals(new String[] {"1", "2", "3"}, secondParser.getInstructions());

            testFile.delete();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Tests the {@link Parser#getInstructionType()} method inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testGetInstructionType() {
        String[] instructions = {
            "@abcdef",
            "@123456",
            "@abcdef123456",
            "@123456abcdef",
            "   @abcdef   \n",
            "(abcdef)",
            "(123456)",
            "(abcdef123456)",
            "(123456abcdef)",
            "   (abcdef)   \n",
            "A=D+M;JGE",
            "A=D+M",
            "AM=D+A",
            "ADM=D+A",
            "M;JLT",
            "D",
            "AM=",
            ";JMP",
            "abc",
            "//comment D=M;JMP",
            "A=D+M;JGE // comment",
            "@abcdef123456 // comment",
            "(abcdef123456) // comment",
            "",
            "  \t  \n",
            "\tADM = D + A"
        };
        Parser testParser = new Parser(instructions);

        while (testParser.hasMoreInstructions()) {
            switch (testParser.getCurrentLine()) {
                case 0:
                    assertEquals("A_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 1:
                    assertEquals("A_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 2:
                    assertEquals("A_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 3:
                    assertEquals(null, testParser.getInstructionType());
                    break;
                case 4:
                    assertEquals("A_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 5:
                    assertEquals("LABEL", testParser.getInstructionType());
                    break;
                case 6:
                    assertEquals(null, testParser.getInstructionType());
                    break;
                case 7:
                    assertEquals("LABEL", testParser.getInstructionType());
                    break;
                case 8:
                    assertEquals(null, testParser.getInstructionType());
                    break;
                case 9:
                    assertEquals("LABEL", testParser.getInstructionType());
                    break;
                case 10:
                    assertEquals("C_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 11:
                    assertEquals("C_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 12:
                    assertEquals("C_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 13:
                    assertEquals("C_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 14:
                    assertEquals("C_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 15:
                    assertEquals("C_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 16:
                    assertEquals(null, testParser.getInstructionType());
                    break;
                case 17:
                    assertEquals(null, testParser.getInstructionType());
                    break;
                case 18:
                    assertEquals(null, testParser.getInstructionType());
                    break;
                case 19:
                    assertEquals("EMPTY", testParser.getInstructionType());
                    break;
                case 20:
                    assertEquals("C_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 21:
                    assertEquals("A_INSTRUCTION", testParser.getInstructionType());
                    break;
                case 22:
                    assertEquals("LABEL", testParser.getInstructionType());
                    break;
                case 23:
                    assertEquals("EMPTY", testParser.getInstructionType());
                    break;
                case 24:
                    assertEquals("EMPTY", testParser.getInstructionType());
                    break;
                case 25:
                    assertEquals("C_INSTRUCTION", testParser.getInstructionType());
                    break;
            }

            testParser.advance();
        }
    }

    /**
     * <p>
     * Tests the {@link Parser#getSymbol()} method inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testGetSymbol() {
        String[] instructions = {
            "@123456",
            "@abcdef",
            "(123456)",
            "(abcdef)",
            "123456",
            "abcdef",
            "//comment @abcdef",
            "@abcdef // comment",
            "(abcdef) // comment"
        };
        Parser testParser = new Parser(instructions);

        while (testParser.hasMoreInstructions()) {
            switch (testParser.getCurrentLine()) {
                case 0:
                    assertEquals("123456", testParser.getSymbol());
                    break;
                case 1:
                    assertEquals("abcdef", testParser.getSymbol());
                    break;
                case 2:
                    assertEquals(null, testParser.getSymbol());
                    break;
                case 3:
                    assertEquals("abcdef", testParser.getSymbol());
                    break;
                case 4:
                    assertEquals(null, testParser.getSymbol());
                    break;
                case 5:
                    assertEquals(null, testParser.getSymbol());
                    break;
                case 6:
                    assertEquals(null, testParser.getSymbol());
                    break;
                case 7:
                    assertEquals("abcdef", testParser.getSymbol());
                    break;
                case 8:
                    assertEquals("abcdef", testParser.getSymbol());
                    break;
            }

            testParser.advance();
        }
    }

    /**
     * <p>
     * Tests the {@link Parser#getDest()} method inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testGetDest() {
        String[] instructions = {
            "A=D+M",
            "ADM=D",
            "D=M;JMP",
            "M=",
            "D+1",
            "JGT",
            "//comment D=M;JMP",
            "D=M;JMP // comment",
            "\tD = M + 1;JMP"
        };
        Parser testParser = new Parser(instructions);

        while (testParser.hasMoreInstructions()) {
            switch (testParser.getCurrentLine()) {
                case 0:
                    assertEquals("A", testParser.getDest());
                    break;
                case 1:
                    assertEquals("ADM", testParser.getDest());
                    break;
                case 2:
                    assertEquals("D", testParser.getDest());
                    break;
                case 3:
                    assertEquals(null, testParser.getDest());
                    break;
                case 4:
                    assertEquals(null, testParser.getDest());
                    break;
                case 5:
                    assertEquals(null, testParser.getDest());
                    break;
                case 6:
                    assertEquals(null, testParser.getDest());
                    break;
                case 7:
                    assertEquals("D", testParser.getDest());
                    break;
                case 8:
                    assertEquals("D", testParser.getDest());
                    break;
            }

            testParser.advance();
        }
    }

    /**
     * <p>
     * Tests the {@link Parser#getComp()} method inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testGetComp() {
        String[] instructions = {
            "A=D+M",
            "ADM=D",
            "D=M;JMP",
            "M=",
            "D+1",
            "JGT",
            "//comment A=D+M",
            "A=D+M //comment",
            "\tA = D + M"
        };
        Parser testParser = new Parser(instructions);

        while (testParser.hasMoreInstructions()) {
            switch (testParser.getCurrentLine()) {
                case 0:
                    assertEquals("D+M", testParser.getComp());
                    break;
                case 1:
                    assertEquals("D", testParser.getComp());
                    break;
                case 2:
                    assertEquals("M", testParser.getComp());
                    break;
                case 3:
                    assertEquals(null, testParser.getComp());
                    break;
                case 4:
                    assertEquals("D+1", testParser.getComp());
                    break;
                case 5:
                    assertEquals(null, testParser.getComp());
                    break;
                case 6:
                    assertEquals(null, testParser.getComp());
                    break;
                case 7:
                    assertEquals("D+M", testParser.getComp());
                    break;
                case 8:
                    assertEquals("D+M", testParser.getComp());
                    break;
            }

            testParser.advance();
        }
    }

    /**
     * <p>
     * Tests the {@link Parser#getJump()} method inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testGetJump() {
        String[] instructions = {
            "A=D+M",
            "ADM=D",
            "D=M;JMP",
            "M=",
            "D+1",
            "JGT",
            ";JGT",
            "ADM=D;JEQ",
            "//comment D=M;JMP",
            "D=M;JMP // comment",
            "\tADM = D ; J E Q"
        };
        Parser testParser = new Parser(instructions);

        while (testParser.hasMoreInstructions()) {
            switch (testParser.getCurrentLine()) {
                case 0:
                    assertEquals(null, testParser.getJump());
                    break;
                case 1:
                    assertEquals(null, testParser.getJump());
                    break;
                case 2:
                    assertEquals("JMP", testParser.getJump());
                    break;
                case 3:
                    assertEquals(null, testParser.getJump());
                    break;
                case 4:
                    assertEquals(null, testParser.getJump());
                    break;
                case 5:
                    assertEquals(null, testParser.getJump());
                    break;
                case 6:
                    assertEquals(null, testParser.getJump());
                    break;
                case 7:
                    assertEquals("JEQ", testParser.getJump());
                    break;
                case 8:
                    assertEquals(null, testParser.getJump());
                    break;
                case 9:
                    assertEquals("JMP", testParser.getJump());
                    break;
                case 10:
                    assertEquals("JEQ", testParser.getJump());
                    break;
            }

            testParser.advance();
        }
    }
}
