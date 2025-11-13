package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test class for IntegerSet to verify its behavior and correctness.
 * This class tests various set operations such as add, remove, union,
 * intersection, and more using JUnit 5.
 */
public class IntegerSetTest {

    /**
     * Tests the add() method and the toString() method.
     * Adds elements to the set and verifies the correct representation
     * of the set (no duplicates, correct ordering).
     */
    @Test
    public void testAddAndToString() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(2); // Duplicate element
        assertEquals("[1, 2]", set.toString(), "The set should contain 1 and 2, with no duplicates.");
    }

    /**
     * Tests the clear() method and the isEmpty() method.
     * Clears the set and then verifies that the set is empty.
     */
    @Test
    public void testClearAndIsEmpty() {
        IntegerSet set = new IntegerSet();
        set.add(10);
        set.clear();
        assertTrue(set.isEmpty(), "The set should be empty after clear() is called.");
    }

    /**
     * Tests the length() method.
     * Verifies that the correct length is returned after adding elements to the
     * set.
     */
    @Test
    public void testLength() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        set.add(7);
        assertEquals(2, set.length(), "The set should contain two elements.");
    }

    /**
     * Tests the equals() method.
     * Verifies that two sets are equal if they contain the same elements,
     * regardless of the order.
     */
    @Test
    public void testEquals() {
        IntegerSet a = new IntegerSet();
        a.add(1);
        a.add(2);

        IntegerSet b = new IntegerSet();
        b.add(2);
        b.add(1);

        assertTrue(a.equals(b), "The sets should be equal because they contain the same elements.");
    }

    /**
     * Tests the contains() method.
     * Verifies that the set correctly identifies whether a value is contained.
     */
    @Test
    public void testContains() {
        IntegerSet set = new IntegerSet();
        set.add(10);
        assertTrue(set.contains(10), "The set should contain the element 10.");
        assertFalse(set.contains(5), "The set should not contain the element 5.");
    }

    /**
     * Tests the largest() and smallest() methods.
     * Verifies that the correct largest and smallest elements are returned
     * after adding elements to the set.
     */
    @Test
    public void testLargestAndSmallest() {
        IntegerSet set = new IntegerSet();
        set.add(3);
        set.add(8);
        set.add(1);
        assertEquals(8, set.largest(), "The largest element should be 8.");
        assertEquals(1, set.smallest(), "The smallest element should be 1.");
    }

    /**
     * Tests the largest() method throws an exception when the set is empty.
     * Verifies that an IllegalStateException is thrown when calling largest() on an
     * empty set.
     */
    @Test
    public void testLargestThrowsExceptionOnEmpty() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, () -> {
            set.largest();
        }, "An IllegalStateException should be thrown when largest() is called on an empty set.");
    }

    /**
     * Tests the union() method.
     * Verifies that the union of two sets is correct and that all unique elements
     * from both sets are present in the result.
     */
    @Test
    public void testUnion() {
        IntegerSet a = new IntegerSet();
        a.add(1);
        a.add(2);
        IntegerSet b = new IntegerSet();
        b.add(2);
        b.add(3);

        a.union(b);
        // a should contain 1, 2, and 3 after union with b
        assertTrue(a.equals(b) || a.contains(1) && a.contains(2) && a.contains(3),
                "The union of sets should contain all unique elements.");
    }

    /**
     * Tests the intersect() method.
     * Verifies that the intersection of two sets contains only the elements
     * that are common to both sets.
     */
    @Test
    public void testIntersect() {
        IntegerSet a = new IntegerSet();
        a.add(1);
        a.add(2);
        a.add(3);
        IntegerSet b = new IntegerSet();
        b.add(2);
        b.add(3);
        b.add(4);

        a.intersect(b);
        assertEquals("[2, 3]", a.toString(), "The intersection of sets should only contain 2 and 3.");
    }

    /**
     * Tests the diff() method.
     * Verifies that the difference of two sets removes all elements found in the
     * second set
     * from the first set.
     */
    @Test
    public void testDiff() {
        IntegerSet a = new IntegerSet();
        a.add(1);
        a.add(2);
        a.add(3);
        IntegerSet b = new IntegerSet();
        b.add(2);

        a.diff(b);
        assertEquals("[1, 3]", a.toString(), "The difference of sets should remove element 2 from set a.");
    }

    /**
     * Tests the complement() method.
     * Verifies that the complement of two sets returns all elements from the second
     * set
     * that are not present in the first set.
     */
    @Test
    public void testComplement() {
        IntegerSet a = new IntegerSet();
        a.add(1);
        a.add(2);
        IntegerSet b = new IntegerSet();
        b.add(2);
        b.add(3);
        b.add(4);

        a.complement(b);
        assertEquals("[3, 4]", a.toString(), "The complement of set a with respect to set b should contain 3 and 4.");
    }

    /**
     * Tests the remove() method.
     * Verifies that elements are correctly removed from the set.
     */
    @Test
    public void testRemove() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.remove(1);
        assertEquals("[2]", set.toString(), "The element 1 should be removed from the set.");
    }
}
