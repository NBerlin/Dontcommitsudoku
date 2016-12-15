package test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import phonebook.*;

public class TestPhoneBook {
	PhoneBook pb;
	
	@Before
	public void setUp() throws Exception {
	pb = new MapPhoneBook();
	}

	@After
	public void tearDown() throws Exception {
		pb = null;
	}
	
	/* Fills the phonebokk with this content:
		a: 1, 2, 3
		aaa: 111
		b: 1, 2
		c: 2, 3
	 */
	private void fill() {
		pb.put("a", "1");
		pb.put("c", "3");
		pb.put("b", "2");
		pb.put("a", "2");
		pb.put("b", "1");
		pb.put("a", "3");
		pb.put("c", "2");
		pb.put("aaa", "111");
	}
	
	/* Test empty */
	
	@Test
	public final void testEmpty() {
		assertTrue("isEmpty false for empty phone book", pb.isEmpty());
		pb.put("a", "1");
		assertFalse("isEmpty true for non empty phone book", pb.isEmpty());
	}
	
	/* Test put */
	
	@Test
	public final void testPutInEmptyPhoneBook() {
		assertTrue("put false for empty phone book", pb.put("a", "1"));
		assertEquals("Wrong size:", 1, pb.size());
		assertTrue("Name not found in phonebook: a", pb.findNumbers("a").contains("1"));
	}
	
	@Test
	public final void testPutSameNameSameNumber() {
		assertTrue("put false for empty phone book", pb.put("a", "1"));
		assertFalse("put true for duplicate", pb.put("a", "1"));
		assertEquals("Wrong size:", 1, pb.size());
	}
	
	@Test
	public final void testPutSameNameDifferentNumber() {
		assertTrue("put false for empty phone book", pb.put("a", "1"));
		assertTrue("put false for same name, different number", pb.put("a", "2"));
		assertEquals("Wrong size:", 1, pb.size());
		assertEquals("Wrong size for numbers:", 2, pb.findNumbers("a").size());
		assertTrue("Number not found in phonebook: 1", pb.findNumbers("a").contains("1"));
		assertTrue("Number not found in phonebook: 2", pb.findNumbers("a").contains("2"));
	}
	
	/* Test size */
	
	@Test
	public final void testSize() {
		assertEquals("Wrong size for empty phonebook:", 0, pb.size());
		pb.put("a", "1");
		assertEquals("Wrong size for phonebook with one name:", 1, pb.size());
		fill();
		assertEquals("Wrong size for non empty phonebook:", 4, pb.size());
	}
	
	/* Test remove */
	
	@Test
	public final void testRemoveInEmptyPhoneBook() {
		assertFalse("Remove true for empty phone book", pb.remove("a"));
	}
	
	@Test
	public final void testRemoveOneName() {
		pb.put("a", "1");
		assertFalse("remove true for nonexisting name", pb.remove("b"));
		assertTrue("remove false for existing name: a", pb.remove("a"));
		assertEquals("Wrong size for empty phonebook:", 0, pb.size());
		assertFalse("Name still found in phonebook: a", pb.findNumbers("a").contains("1"));
	}
	
	public final void testRemove() {
		fill();
		assertTrue("remove false for existing name: a", pb.remove("a"));
		assertTrue("remove false for existing name: c", pb.remove("c"));
		assertTrue("remove false for existing name: b", pb.remove("b"));
		assertTrue("remove false for existing name: aaa", pb.remove("aaa"));
		assertEquals("wrong size for empty phonebook:", 0, pb.size());
		assertFalse("Name still found in phonebook: a", pb.findNumbers("a").contains("2"));
		assertFalse("Name still found in phonebook: b", pb.findNumbers("b").contains("2"));
		assertFalse("Name still found in phonebook: c", pb.findNumbers("c").contains("2"));
		assertFalse("Name still found in phonebook: aaa", pb.findNumbers("aaa").contains("1"));
	}

	
	@Test
	public final void testRemoveNonExistingName() {
		fill();
		assertEquals("Wrong size for non empty phonebook:", 4, pb.size());
		assertFalse("remove true for non existing name:: aa", pb.remove("aa"));
		assertEquals("Wrong size for non empty phonebook:", 4, pb.size());
		assertTrue("Name not found in phonebook: a", pb.findNumbers("a").contains("2"));
		assertTrue("Name not found in phonebook: b", pb.findNumbers("b").contains("2"));
		assertTrue("Name not found in phonebook: c", pb.findNumbers("c").contains("2"));
		assertTrue("Name not found in phonebook: aaa", pb.findNumbers("aaa").contains("111"));
	}
	
	/* Test removeNumber */
	
	@Test
	public final void testRemoveNumberInEmptyPhoneBook() {
		assertFalse("removeNumber true for empty phone book:", pb.removeNumber("a", "1"));
	}
	
	@Test
	public final void testRemoveNumberOneNameNumber() {
		pb.put("a", "1");
		assertFalse("removeNumber true for nonexisting number:", pb.removeNumber("a","2"));
		assertTrue("Name not found in phonebook: a", pb.findNumbers("a").contains("1"));
		assertTrue("removeNumber false for existing number: a, 1", pb.removeNumber("a","1"));
		assertEquals("Wrong size for empty number set:", 0, pb.findNumbers("a").size());
	}
	
	@Test
	public final void testRemoveNumber() {
		fill();
		assertEquals("Wrong size for empty number set:", 3, pb.findNumbers("a").size());
		assertTrue("removeNumber false for existing number: a, 1", pb.removeNumber("a","1"));
		assertTrue("removeNumber false for existing number: a, 3", pb.removeNumber("a","3"));
		assertTrue("removeNumber false for existing number: a, 2", pb.removeNumber("a","2"));
		assertEquals("Wrong size for empty number set to a:", 0, pb.findNumbers("a").size());
		assertEquals("Wrong size for number set to b:", 2, pb.findNumbers("b").size());
		assertEquals("Wrong size for number set to c:", 2, pb.findNumbers("c").size());
		assertEquals("Wrong size for number set to aaa:", 1, pb.findNumbers("aaa").size());
	}

	
	@Test
	public final void testRemoveNumberNonExistingName() {
		fill();
		assertFalse("removeNumber true for nonexisting number:", pb.removeNumber("d","2"));
		assertEquals("Wrong size for phonebook:", 4, pb.size());
		assertEquals("Wrong size for number set to a:", 3, pb.findNumbers("a").size());
		assertEquals("Wrong size for number set to b:", 2, pb.findNumbers("b").size());
		assertEquals("Wrong size for ety number set to c:", 2, pb.findNumbers("c").size());
		assertEquals("Wrong size for number set to aaa:", 1, pb.findNumbers("aaa").size());
	}
	
	/* Test findNumbers */
	
	@Test
	public final void testFindNumbersInEmptyMap() {
		assertEquals("Wrong size for empty number set:", 0, pb.findNumbers("a").size());
	}
	
	@Test
	public final void testFindNumbers() {
		fill();
		assertEquals("Wrong size for number set to a:", 3, pb.findNumbers("a").size());
		assertTrue("Number not found in phonebook: a, 1", pb.findNumbers("a").contains("1"));
		assertTrue("Number not found in phonebook: a, 2", pb.findNumbers("a").contains("2"));
		assertTrue("Number not found in phonebook: a, 3", pb.findNumbers("a").contains("3"));
		assertEquals("Wrong size for number set to b:", 2, pb.findNumbers("b").size());
		assertTrue("Number not found in phonebook: b, 1", pb.findNumbers("b").contains("1"));
		assertTrue("Number not found in phonebook: b, 2", pb.findNumbers("b").contains("2"));
		assertEquals("Wrong size for number set to c:", 2, pb.findNumbers("c").size());
		assertTrue("Number not found in phonebook: c, 2", pb.findNumbers("c").contains("2"));
		assertTrue("Number not found in phonebook: c, 3", pb.findNumbers("c").contains("3"));
		assertEquals("Wrong size for number set to aaa:", 1, pb.findNumbers("aaa").size());
		assertTrue("Number not found in phonebook: aaa, 111", pb.findNumbers("aaa").contains("111"));
	}
	
	@Test
	public final void testFindNumbersNonExistingName() {
		fill();
		assertEquals("Wrong size for empty number set to non existing name:", 0, pb.findNumbers("aa").size());
	}
	
	
	@Test
	public final void testFindNumbersNamesNoInternalReferencesReturned() {
		fill();
		Set<String> numbers = pb.findNumbers("a");
		assertEquals("Wrong size for number set to a:", 3, pb.findNumbers("a").size());
		numbers.add("4");
		assertEquals("Reference to internal set returned. Wrong size for number set to a:", 3, pb.findNumbers("a").size());
		assertFalse("Reference to internal set returned. Number found in phonebook: a", pb.findNumbers("a").contains("4"));
	}
	
	/* Test findNames */
	@Test
	public final void testFindNamesInEmptyMap() {
		assertEquals("Wrong size for empty name set:", 0, pb.findNames("1").size());
	}
	
	@Test
	public final void testFindNamesOneElement() {
		pb.put("a", "1");
		assertEquals("Wrong size for name set:", 1, pb.findNames("1").size());
		assertTrue("Name not found in phonebook: a", pb.findNames("1").contains("a"));
	}
	
	@Test
	public final void testFindNames() {
		fill();
		assertEquals("Wrong size for name set to 1:", 2, pb.findNames("1").size());
		assertTrue("Name not found in phonebook: 1, a", pb.findNames("1").contains("a"));
		assertTrue("Name not found in phonebook: 1, b", pb.findNames("1").contains("b"));
		assertFalse("Name found in phonebook: 1", pb.findNames("1").contains("c"));
		assertFalse("Name found in phonebook: 1", pb.findNames("1").contains("aaa"));
		
		assertEquals("Wrong size for name set to 2:", 3, pb.findNames("2").size());
		assertTrue("Name not found in phonebook: 2", pb.findNames("2").contains("a"));
		assertTrue("Name not found in phonebook: 2", pb.findNames("2").contains("b"));
		assertTrue("Name not found in phonebook: 2", pb.findNames("2").contains("c"));
		assertFalse("Name found in phonebook: 2", pb.findNames("2").contains("aaa"));
		
		assertEquals("Wrong size for name set to 3:", 2, pb.findNames("3").size());
		assertTrue("Name not found in phonebook: 3", pb.findNames("3").contains("a"));
		assertFalse("Name found in phonebook: 3", pb.findNames("3").contains("b"));
		assertTrue("Name not found in phonebook: 3", pb.findNames("3").contains("c"));
		assertFalse("Name found in phonebook: 3", pb.findNames("3").contains("aaa"));
		
		assertEquals("Wrong size for name set to 111:", 1, pb.findNames("111").size());
		assertTrue("Name not found in phonebook: 111", pb.findNames("111").contains("aaa"));	
	}

	
	@Test
	public final void testFindNamesNonExistingNumber() {
		fill();
		assertEquals("Wrong size for name set to non existing number:", 0, pb.findNames("4").size());
	}
	
	/* Test Names */
	@Test
	public final void testNamesInEmptyMap() {
		assertEquals("Wrong size for empty name set:", 0, pb.names().size());
	}
	
	@Test
	public final void testNamesOneElement() {
		pb.put("a", "1");
		assertEquals("Wrong size for name set:", 1, pb.names().size());
		assertTrue("Name not found in phonebook: a", pb.names().contains("a"));
	}
	
	@Test
	public final void testNames() {
		fill();
		assertEquals("Wrong size for name set:", 4, pb.names().size());
		assertTrue("Name not found in phonebook: a", pb.names().contains("a"));
		assertTrue("Name not found in phonebook: b", pb.names().contains("b"));	
		assertTrue("Name not found in phonebook: c", pb.names().contains("c"));	
		assertTrue("Name not found in phonebook: aaa", pb.names().contains("aaa"));	
	}
	
	@Test
	public final void testNamesNonExistingName() {
		fill();
		assertFalse("Name found in phonebook: aa", pb.names().contains("aa"));
	}
	
	@Test
	public final void testNamesNoInternalReferencesReturned() {
		fill();
		Set<String> names = pb.names();
		assertEquals("Wrong size for name set:", 4, pb.names().size());
		names.remove("a");
		assertEquals("Reference to internal set returned. Wrong size for name set:", 4, pb.names().size());
	}
	
	/* Test many */

	@Test
	public final void testManyPutAndRemove() {
		java.util.Random random = new java.util.Random(123456);
		HashSet<String> randStrings = new HashSet<>();
		for (int i = 0; i < 10000; i++) {
			int r = random.nextInt(10000);
			String s = String.valueOf(r);
			pb.put(s, s);
			randStrings.add(s);
		}
		assertEquals("Wrong size:", randStrings.size(), pb.size());
		for (String s : randStrings) {			
			assertTrue("Number not found in phoneBook:" + s, pb.removeNumber(s, s));
		}
		for (String s : randStrings) {			
			assertTrue("Name not found in phoneBook:" + s, pb.remove(s));
		}
		assertEquals("Wrong size:", 0, pb.size());
	}
	
}

