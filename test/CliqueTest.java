package test;

import static org.junit.Assert.*;

import org.junit.Test;

import sudoku.Cell;
import sudoku.Clique;

public class CliqueTest {

	@Test
	public void testInit() {
		Clique clique = new Clique();
		
		assertTrue(clique.getCells().isEmpty());
		assertEquals(clique.getNumUncolored(), 0);
	}

	@Test
	public void testAddCell() {
		Clique clique = new Clique();
		Cell cell = new Cell(clique, clique, clique);
		clique.addCell(cell);
		
		assertEquals(clique.getCells().size(), 1);
		assertTrue(clique.getCells().contains(cell));
		assertEquals(clique.getNumUncolored(), 1);
	}
	
	@Test
	public void testCalcNumUncolored() {
		Clique clique = new Clique();
		
		//should stay 0 for empty clique
		assertEquals(clique.getNumUncolored(), 0);
		clique.calcNumUncolored();
		assertEquals(clique.getNumUncolored(), 0);
		
		Cell cell = new Cell(clique, clique, clique);
		clique.addCell(cell);
		assertEquals(clique.getNumUncolored(), 1);
		clique.calcNumUncolored();
		assertEquals(clique.getNumUncolored(), 1);
		assertEquals(clique.getUncoloredCells().size(), 1);
        assertEquals(clique.getUncoloredCells().get(0), cell);
		
		//instead of creating a separate test, easier here
		clique.decrementUncolored();
		assertEquals(clique.getNumUncolored(), 0);
		clique.incrementUncolored();
		assertEquals(clique.getNumUncolored(), 1);
	}
	
	@Test
	public void testAddRemoveColor() {
		Clique clique1 = new Clique();
		Clique clique2 = new Clique();
		Clique clique3 = new Clique();
		
		Cell cell1 = new Cell(clique1, clique2, clique3);
		Cell cell2 = new Cell(clique1, clique2, clique3);
		Cell cell3 = new Cell(clique1, clique2, clique3);
		
		clique1.addCell(cell1);
		clique1.addCell(cell2);
		clique1.addCell(cell3);
		clique2.addCell(cell1);
		clique2.addCell(cell2);
		clique2.addCell(cell3);
		clique3.addCell(cell1);
		clique3.addCell(cell2);
		clique3.addCell(cell3);
		
		char c = '8';
		
		//remove
		clique1.removeColor(c);
		for(Cell cell : clique1.getCells()){
			assertEquals(cell.getValidColors().size(), 8);
			assertFalse(cell.getValidColors().contains(c));
		}
		for(Cell cell : clique2.getCells()){
			assertEquals(cell.getValidColors().size(), 8);
			assertFalse(cell.getValidColors().contains(c));
		}
		for(Cell cell : clique3.getCells()){
			assertEquals(cell.getValidColors().size(), 8);
			assertFalse(cell.getValidColors().contains(c));
		}
		
		//remove again and make sure nothing bad happens
		clique2.removeColor(c);
		for(Cell cell : clique1.getCells()){
			assertEquals(cell.getValidColors().size(), 8);
			assertFalse(cell.getValidColors().contains(c));
		}
		for(Cell cell : clique2.getCells()){
			assertEquals(cell.getValidColors().size(), 8);
			assertFalse(cell.getValidColors().contains(c));
		}
		for(Cell cell : clique3.getCells()){
			assertEquals(cell.getValidColors().size(), 8);
			assertFalse(cell.getValidColors().contains(c));
		}
		
		//add back
		clique3.addColor(c);
		for(Cell cell : clique1.getCells()){
			assertEquals(cell.getValidColors().size(), 9);
			assertTrue(cell.getValidColors().contains(c));
		}
		for(Cell cell : clique2.getCells()){
			assertEquals(cell.getValidColors().size(), 9);
			assertTrue(cell.getValidColors().contains(c));
		}
		for(Cell cell : clique3.getCells()){
			assertEquals(cell.getValidColors().size(), 9);
			assertTrue(cell.getValidColors().contains(c));
		}
		
		//add again
		clique2.addColor(c);
		for(Cell cell : clique1.getCells()){
			assertEquals(cell.getValidColors().size(), 9);
			assertTrue(cell.getValidColors().contains(c));
		}
		for(Cell cell : clique2.getCells()){
			assertEquals(cell.getValidColors().size(), 9);
			assertTrue(cell.getValidColors().contains(c));
		}
		for(Cell cell : clique3.getCells()){
			assertEquals(cell.getValidColors().size(), 9);
			assertTrue(cell.getValidColors().contains(c));
		}
	}
	
}
