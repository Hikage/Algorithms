package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sudoku.Cell;
import sudoku.Clique;

public class CellTest {

/**	@Before
	public static void setup(){
		Clique clique = new Clique();
		Cell cell = new Cell(clique, clique, clique);
		clique.addCell(cell);
	}
**/	
	@Test
	public void testInit() {
		Clique clique = new Clique();
		Cell cell = new Cell(clique, clique, clique);
		
		assertEquals(cell.getColor(), '.');
		assertEquals(cell.getValidColors().size(), 9);
		for(int i = 1; i <= 9; i++){
			char c = Character.forDigit(i, 10);
			assertTrue(cell.getValidColors().contains(c));
		}
		char c = '5';
		assertTrue(cell.getValidColors().contains(c));
	}
	
	@Test
	public void testRemoveValidColor(){
		Clique clique = new Clique();
		Cell cell = new Cell(clique, clique, clique);
		
		char c = '7';		
		assertTrue(cell.getValidColors().contains(c));
		cell.removeValidColor(c);
		assertEquals(cell.getValidColors().size(), 8);
		assertFalse(cell.getValidColors().contains(c));
	}
	
	@Test
	public void testAddValidColor(){
		Clique clique = new Clique();
		Cell cell = new Cell(clique, clique, clique);
		clique.addCell(cell);
		
		char c = '6';
		//add element that already exists
		assertTrue(cell.getValidColors().contains(c));
		assertEquals(cell.getValidColors().size(), 9);
		cell.addValidColor(c);
		assertTrue(cell.getValidColors().contains(c));
		assertEquals(cell.getValidColors().size(), 9);
		
		//remove and add element
		cell.removeValidColor(c);
		assertEquals(cell.getValidColors().size(), 8);
		assertFalse(cell.getValidColors().contains(c));
		cell.addValidColor(c);
		assertTrue(cell.getValidColors().contains(c));
		assertEquals(cell.getValidColors().size(), 9);
	}
	
	@Test
	public void testColorUncolor(){
		Clique clique = new Clique();
		Cell cell = new Cell(clique, clique, clique);
		clique.addCell(cell);
		
		char c = '3';
		
		//color
		cell.color(c);
		assertTrue(cell.getValidColors().isEmpty());
		for(Cell otherCell : clique.getCells())
			assertFalse(otherCell.getValidColors().contains(c));
		assertEquals(clique.getNumUncolored(), -2);
		
		//uncolor
		cell.uncolor();
		assertEquals(cell.getValidColors().size(), 9);
		for(Cell otherCell : clique.getCells())
			assertTrue(otherCell.getValidColors().contains(c));
		assertEquals(clique.getNumUncolored(), 1);
	}

}
