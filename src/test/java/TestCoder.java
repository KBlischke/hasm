import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * <p>
 * The <code>TestCoder</code> class tests the {@link Coder} class inside a <code>JUnit</code> test.
 * </p>
 * 
 * @author  Kevin Blischke
 * @version 1.0
 */
public class TestCoder {
    /**
     * <p>
     * Tests the {@link Coder#getDest()} method inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testGetDest() {
        assertEquals("010", Coder.getDest("D"));
        assertEquals("011", Coder.getDest("DM"));
        assertEquals("111", Coder.getDest("ADM"));
        assertEquals("000", Coder.getDest("null"));
        assertEquals("000", Coder.getDest("noMnemonic"));
    }

    /**
     * <p>
     * Tests the {@link Coder#getComp()} method inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testGetComp() {
        assertEquals("0111111", Coder.getComp("1"));
        assertEquals("1110001", Coder.getComp("!M"));
        assertEquals("0000010", Coder.getComp("D+A"));
        assertEquals("0101010", Coder.getComp("0"));
        assertEquals("0101010", Coder.getComp("noMnemonic"));
    }

    /**
     * <p>
     * Tests the {@link Coder#getJump()} method inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testGetJump() {
        assertEquals("001", Coder.getJump("JGT"));
        assertEquals("111", Coder.getJump("JMP"));
        assertEquals("110", Coder.getJump("JLE"));
        assertEquals("000", Coder.getJump("null"));
        assertEquals("000", Coder.getJump("noMnemonic"));
    }

    /**
     * <p>
     * Tests the {@link Coder#getAddress()} method inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testGetAddress() {
        assertEquals("000000000000000", Coder.getAddress("0"));
        assertEquals("000000000000111", Coder.getAddress("7"));
        assertEquals("100000000000000", Coder.getAddress("16384"));
        assertEquals(null, Coder.getAddress("symbol"));
    }
}
