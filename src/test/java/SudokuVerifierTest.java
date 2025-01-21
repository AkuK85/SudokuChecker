import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class SudokuVerifierTest {

	// Test strings

	// Correct Sudoku string
	String c = "417369825632158947958724316825437169791586432346912758289643571573291684164875293";

	// Invalid Sudoku string with invalid character (Violates R1)
	String r1 = "417369825A3215894795872431682543A169791586432346912758289643571573291684A648752A3";
	// 3 invalid characters: 3xA

	// Invalid Sudoku string with duplicate in sub-grid (Violates R2)
	String r2 = "417469825632158947958724316825437169791586432346912758289643571573291684164875293";
	// 3 duplicates in different sub-grids: 4(in sub-grid 1), 3(in sub-grid 4), 2(in sub-grid 5)

	// Invalid Sudoku string with duplicate in row (Violates R3)
	String r3 = "17417825632158947958724316825437169791586432346912758289643571573291684164875293";
	// 3 duplicates in a row: 4, 1, 7 (all in first row)

	// Invalid Sudoku string with duplicate in column (Violates R4)
	String r4 = "417369825632158947458724316825437169791586432346912758289643571573291684164875293";
	// 3 duplicates in a column: 4, 1, 7 (all in first column)

	// Invalid Sudoku string with invalid character in sub-grid (Violates R2 and R1)
	String r5 = "41A369825632158947958724316825437169791586432346912758289643571573291684164875293";
	// Invalid character in first sub-grid

	// Invalid Sudoku string with invalid character in row (Violates R3 and R1)
	String r6 = "41A417825632158947958724316825437169791586432346912758289643571573291684164875293";
	// Invalid character in first row

	// Invalid Sudoku string with invalid character in column (Violates R4 and R1)
	String r7 = "417369825632158947A58724316825437169791586432346912758289643571573291684164875293";
	// Invalid character in first column

	SudokuVerifier v = new SudokuVerifier();

	/* SudokuVerifier Return Codes:
	 *  0: Valid Sudoku
	 *  1: Invalid character (non-numeric, or not in range 1-9)
	 * -1: Invalid input string length (not 81 characters) or a number is out of range 1-9
	 * -2: Duplicate in sub-grid
	 * -3: Duplicate in row
	 * -4: Duplicate in column
	 */

	@Before
	public void setUp() {
		v = new SudokuVerifier();
	}

	@After
	public void tearDown() {
		v = null;
	}

	// Sanity test
	@Test
	public void testTheTruth() {
		assertTrue(true);
	}


	// Input String Tests (Tagged as S1-S14 in Condition Table)

	@Test
	public void testInputStringForValidLengthAndCharacters() { // Tag S1
		// Expected result: 0 (Valid Sudoku - assuming valid grid)
		String validString = c;

		// Check the length of the string
		assertEquals("Input String Length not 81", 81, validString.length());

		// Check for valid characters and return code
		int a = v.verify(validString);
		assertEquals("Input String Valid Characters and Length 81 ", 0, a);

	}

	@Test
	public void testInputStringLengthLessThan81() { // Tag S2
		// Expected result: -1 (Invalid input string length)
		String s = "123456789";
		int a = v.verify(s);
		assertEquals("Input String Length Less Than 81", -1, a);
	}

	@Test
	public void testInputStringLengthGreaterThan81() { // Tag S3
		// Expected result: -1 (Invalid input string length)
		String l = c + "1";
		int a = v.verify(l);
		assertEquals("Input String Length Greater Than 81", -1, a);
	}

	@Test
	public void testInputStringValidCharacters() { // Tag S4
		// Expected result: 0 (Valid Sudoku - assuming valid grid)
		int a = v.verify(c);
		assertEquals("Input String Valid Characters", 0, a);
	}

	@Test
	public void testInputStringInvalidCharacters() { // Tag S5
		// Expected result: 1 (Invalid character)
		String invalidString = c.substring(0, 80) + "A";
		int a = v.verify(invalidString);
		assertEquals("Input String Invalid Characters", 1, a);
	}

	@Test
	public void testInputStringEmptyString() { // Tag S6
		// Expected result: -1 (Invalid input string length)
		String emptyString = "";
		int a = v.verify(emptyString);
		assertEquals("Input String Empty String", -1, a);
	}

	@Test
	public void testInputStringNull() { // Tag S7
		// Expected result: -1 (Invalid input string length)
		String nullString = null;
		int a = v.verify(nullString);
		assertEquals("Input String Null", -1, a);
	}

	@Test
	public void testInputStringAllSpaces() { // Tag S8
		// Expected result: 1 (Invalid character)
		StringBuilder allSpacesString = new StringBuilder();
		for (int i = 0; i < 81; i++) {
			allSpacesString.append(" ");
		}
		int a = v.verify(allSpacesString.toString());
		assertEquals("Input String All Spaces", 1, a);
	}

	@Test
	public void testInputStringVeryLongString() { // Tag S9
		// Expected result: -1 (Invalid input string length)
		StringBuilder veryLongString = new StringBuilder();
		for (int i = 0; i < 300; i++) {
			veryLongString.append("1");
		}
		int a = v.verify(veryLongString.toString());
		assertEquals("Input String Very Long String", -1, a);
	}

	@Test
	public void testInputStringUnicodeCharacters() { // Tag S10
		// Expected result: 1 (Invalid character)
		String unicodeString = "Ω≈ç√∫˜µ≤≥÷Ω≈ç√∫˜µ≤≥÷Ω≈ç√∫˜µ≤≥÷Ω≈ç√∫˜µ≤≥÷Ω≈ç√∫˜µ≤≥÷Ω≈ç√∫˜µ≤≥÷Ω≈ç√∫˜µ≤≥÷Ω≈ç√∫˜µ≤≥÷Ω";
		int a = v.verify(unicodeString);
		assertEquals("Input String Unicode Characters", 1, a);
	}

	@Test
	public void testInputStringSpecialCharacters() { // Tag S11
		// Expected result: 1 (Invalid Character)
		String imSpecialString = "~!@#$%^&*()_+=-`~!@#$%^&*()_+=-`~!@#$%^&*()_+=-`~!@#$%^&*()_+=-`~!@#$%^&*()_+=-`~!@#$%^&*()_+=-`~!@#$%^&*()_+=-`~!@#$%^&";
		int a = v.verify(imSpecialString);
		assertEquals("Input String Special Characters", 1, a);
	}

	@Test
	public void testInputStringValidCharactersWithSpaces() { // Tag S12
		// Expected result: 1 (Invalid characters)
		String validStringWithSpaces = "4 1 7 3 6 9 8 2 5 6 3 2 1 5 8 9 4 7 9 5 8 7 2 4 3 1 6 8 2 5 4 3 7 1 6 9 7 9 1 5 8 6 4 3 2 3 4 6 9 1 2 7 5 8 2 8 9 6 4 3 5 7 1 5 7 3 2 9 1 6 8 4 1 6 4 8 7 5 2 9 3";
		int a = v.verify(validStringWithSpaces);
		assertEquals("Input String Valid Characters With Spaces", 1, a);
	}

	@Test
	public void testInputStringValidCharactersWithNewLines() { // Tag S13
		// Expected result: 1 (Invalid characters)
		String validStringWithNewLines = "4\n1\n7\n3\n6\n9\n8\n2\n5\n6\n3\n2\n1\n5\n8\n9\n4\n7\n9\n5\n8\n7\n2\n4\n3\n1\n6\n8\n2\n5\n4\n3\n7\n1\n6\n9\n7\n9\n1\n5\n8\n6\n4\n3\n2\n3\n4\n6\n9\n1\n2\n7\n5\n8\n2\n8\n9\n6\n4\n3\n5\n7\n1\n5\n7\n3\n2\n9\n1\n6\n8\n4\n1\n6\n4\n8\n7\n5\n2\n9\n3";
		int a = v.verify(validStringWithNewLines);
		assertEquals("Input String Valid Characters With New Lines", 1, a);
	}

	@Test
	public void testInputStringValidCharactersWithTabs() { // Tag S14
		// Expected result: 1 (Invalid characters)
		String validStringWithTabs = "4\t1\t7\t3\t6\t9\t8\t2\t5\t6\t3\t2\t1\t5\t8\t9\t4\t7\t9\t5\t8\t7\t2\t4\t3\t1\t6\t8\t2\t5\t4\t3\t7\t1\t6\t9\t7\t9\t1\t5\t8\t6\t4\t3\t2\t3\t4\t6\t9\t1\t2\t7\t5\t8\t2\t8\t9\t6\t4\t3\t5\t7\t1\t5\t7\t3\t2\t9\t1\t6\t8\t4\t1\t6\t4\t8\t7\t5\t2\t9\t3";
		int a = v.verify(validStringWithTabs);
		assertEquals("Input String Valid Characters With Tabs", 1, a);
	}


	// Row class tests (Tagged as RO1-RO3 in Condition Table)

	@Test
	public void testValidRow() { // Tag RO1
		// Expected result: 0 (Valid Sudoku - assuming valid grid)
		int a = v.verify(c);
		assertEquals("Valid Row", 0, a);
	}

	@Test
	public void testInvalidRowWithDuplicate() { // Tag RO2
		// Expected result: -3 (Duplicate in row)
		int a = v.verify(r3);
		assertEquals("Invalid Row With Duplicate", -3, a);
	}

	@Test
	public void testInvalidRowWithInvalidCharacter() { // Tag RO3
		// Expected result: 1 (Invalid character)
		int a = v.verify(r6);
		assertEquals("Invalid Row With Invalid Character", 1, a);
	}

	// Column class tests (Tagged as C1-C3 in Condition Table)

	@Test
	public void testValidColumn() { // Tag CO1
		// Expected result: 0 (Valid Sudoku - assuming valid grid)
		int a = v.verify(c);
		assertEquals("Valid Column", 0, a);
	}

	@Test
	public void testInvalidColumnWithDuplicate() { // Tag CO2
		// Expected result: -4 (Duplicate in column)
		int a = v.verify(r4);
		assertEquals("Invalid Column With Duplicate", -4, a);
	}

	@Test
	public void testInvalidColumnWithInvalidCharacter() { // Tag CO3
		// Expected result: 1 (Invalid character)
		int a = v.verify(r7);
		assertEquals("Invalid Column With Invalid Character", 1, a);
	}

	// Sub-grid class tests (Tagged as SG1-SG3 in Condition Table)

	@Test
	public void testValidSubGrid() { // Tag SG1
		// Expected result: 0 (Valid Sudoku - assuming valid grid)
		int a = v.verify(c);
		assertEquals("Valid Sub-Grid", 0, a);
	}

	@Test
	public void testInvalidSubGridWithDuplicate() { // Tag SG2
		// Expected result: -2 (Duplicate in sub-grid)
		int a = v.verify(r2);
		assertEquals("Invalid Sub-grid with duplicate", -2, a);
	}

	@Test
	public void testInvalidSubGridWithInvalidCharacter() { // Tag SG3
		// Expected result: 1 (Invalid character)
		int a = v.verify(r5);
		assertEquals("Invalid Sub-grid with invalid character", 1, a);
	}

	// Entire grid tests (Tagged as G1-G2 in Condition Table)

	@Test
	public void testValidEntireGrid() { // Tag G1
		// Expected result: 0 (Valid Sudoku)
		int a = v.verify(c);
		assertEquals("Valid Entire Grid", 0, a);
	}

	@Test
	public void testInvalidEntireGridRandom() { // Tag G2
		// Expected result: 1, -2, -3, -4 (Depending on randomly chosen string)

		String[] randomInvalidGridStrings = {r1, r2, r3, r4};

		Random random = new Random();
		int randomIndex = random.nextInt(randomInvalidGridStrings.length);
		String randomInvalidGridString = randomInvalidGridStrings[randomIndex];

		int a = v.verify(randomInvalidGridString);
		assertTrue("Invalid Entire Grid", a == 1 || a == -2 || a == -3 || a == -4);


	}

}
