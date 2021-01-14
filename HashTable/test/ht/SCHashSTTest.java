package ht;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests SCHashST methods.
 * @author Riley Harris
 *
 */
public class SCHashSTTest {

	/**
	 * Tests put and get methods (in addition to size).
	 */
	@Test
	public void testPutAndGet() {
		SCHashST<String, Integer>  st = new SCHashST<>();
		assertEquals(0, st.size());
		// test valid adds
		st.put("A", 0);
		assertEquals(1, st.size());
		assertTrue(st.get("A").equals(0));
		st.put("B", 1);
		assertEquals(2, st.size());
		assertTrue(st.get("B").equals(1));
		
		// test getting non-existent key
		assertTrue(st.get("C") == null);
		
		// test get method with null parameter
		try {
			st.get(null);
			fail("Exception should have been thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("Cannot search for null key.", e.getMessage());
		}
		
		// test putting null key
		try {
			st.put(null, 5);
			fail("Exception should have been thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("Key and/or Data may not be null.", e.getMessage());
			assertEquals(2, st.size());
			assertTrue(st.get("A").equals(0));
			assertTrue(st.get("B").equals(1));
		}
		
		// test putting null data
		try {
			st.put("C", null);
			fail("Exception should have been thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("Key and/or Data may not be null.", e.getMessage());
			assertEquals(2, st.size());
			assertTrue(st.get("A").equals(0));
			assertTrue(st.get("B").equals(1));
		}
	}
	
	/**
	 * Tests remove method.
	 */
	@Test
	public void testRemove() {
		SCHashST<String, Integer>  st = new SCHashST<>();
		assertEquals(0, st.size());
		st.put("A", 0);
		st.put("B", 1);
		st.put("C", 2);
		st.put("AA", 3);
		assertEquals(4, st.size());
		
		// test remove
		assertTrue(st.remove("C").equals(2));
		assertEquals(3, st.size());
		assertTrue(st.get("C") == null);
		
		assertTrue(st.remove("A").equals(0));
		assertEquals(2, st.size());
		assertTrue(st.get("A") == null);
		
		// test remove null key
		try {
			st.remove(null);
			fail("Exception should have been thrown.");
		} catch (IllegalArgumentException e) {
			assertEquals("Cannot remove null key.", e.getMessage());
			assertEquals(2, st.size());
			assertTrue(st.get("B").equals(1));
			assertTrue(st.get("AA").equals(3));
		}
		
		// test removing non-existent key
		try {
			st.remove("G");
			fail("Exception should have been thrown.");
		} catch (NoSuchElementException n) {
			assertEquals("Key does not exist.", n.getMessage());
			assertEquals(2, st.size());
			assertTrue(st.get("B").equals(1));
			assertTrue(st.get("AA").equals(3));
		}
	}

}
