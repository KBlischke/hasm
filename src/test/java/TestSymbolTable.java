import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * <p>
 * The <code>TestSymbolTable</code> class tests the {@link SymbolTable} class inside a <code>JUnit</code> test.
 * </p>
 * 
 * @author  Kevin Blischke
 * @version 1.0
 */
public class TestSymbolTable {
    /**
     * <p>
     * Tests the {@link Coder#addEntry()} and {@link Coder#deleteEntry()} methods 
     * inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testAddDelete() {
        SymbolTable testTable = new SymbolTable();
        testTable.addEntry("a", "1");
        testTable.addEntry("b", "2");
        testTable.addEntry("c", "3");
        testTable.addEntry("d", "4");
        testTable.addEntry("e", "5");
        testTable.deleteEntry("b");
        testTable.deleteEntry("d");

        assertEquals("1", testTable.getAddress("a"));
        assertEquals(null, testTable.getAddress("b"));
        assertEquals("3", testTable.getAddress("c"));
        assertEquals(null, testTable.getAddress("d"));
        assertEquals("5", testTable.getAddress("e"));
    }

    /**
     * <p>
     * Tests the {@link Coder#getAddres()} method inside a <code>JUnit</code> test.
     * </p>
     */
    @Test
    void testGetAddress() {
        SymbolTable testTable = new SymbolTable();
        testTable.addEntry("a", "1");
        testTable.addEntry("b", "2");
        testTable.addEntry("c", "3");

        assertEquals("1", testTable.getAddress("a"));
        assertEquals("2", testTable.getAddress("b"));
        assertEquals("3", testTable.getAddress("c"));
    }
}
