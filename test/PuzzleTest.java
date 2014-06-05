package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import sudoku.Clique;
import sudoku.Puzzle;

public class PuzzleTest {

	@Test
	public void testInit() {
		Puzzle puzzle = new Puzzle(2);
		
		for(int r = 0; r < 4; r++)
			for(int c = 0; c < 4; c++)
				assertEquals(puzzle.getColor(r, c), '.');
		
		assertEquals(puzzle.getCliques().length, 3);
		for(Clique[] cliques : puzzle.getCliques()){
			assertEquals(cliques.length, 4);
			for(Clique clique : cliques)
				assertEquals(clique.getCells().size(), 4);
		}
	}
	
	@Test
	public void testInitializeCliques() {
		Puzzle puzzle = new Puzzle(2);
		
		assertEquals(puzzle.getCliques().length, 3);
		for(Clique[] cliques : puzzle.getCliques()){
			assertEquals(cliques.length, 4);
			for(Clique clique : cliques)
				assertEquals(clique.getCells().size(), 4);
		}
	}
	
	@Test
	public void testGetBox() {
		Puzzle puzzle = new Puzzle(2);
		
		assertEquals(puzzle.getBox(0, 0), 0);
		assertEquals(puzzle.getBox(1, 3), 1);
		assertEquals(puzzle.getBox(3, 1), 2);
		assertEquals(puzzle.getBox(2, 2), 3);
	}
	
	@Test
	public void testPopulatePuzzle() {
		Puzzle puzzle = new Puzzle(3);
		
		try {
			puzzle.populatePuzzle("src/sudoku/sudoku-problem.txt");
		}
		catch(IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
		
		assertEquals(puzzle.getColor(0, 0), '5');
		assertEquals(puzzle.getColor(4, 1), '.');
		assertEquals(puzzle.getColor(8, 4), '8');
		assertEquals(puzzle.getColor(6, 2), '.');
		assertEquals(puzzle.getColor(1, 6), '.');
	}
	
	@Test
	public void testSetColor() {
		Puzzle puzzle = new Puzzle(2);
		
		puzzle.setColor(3, 3, '2');
		assertEquals(puzzle.getColor(3, 3), '2');
		puzzle.setColor(1, 0, '5');
		assertEquals(puzzle.getColor(1, 0), '5');
		puzzle.setColor(3, 3, '1');
		assertEquals(puzzle.getColor(3, 3), '1');
		puzzle.setColor(3, 3, '.');
		assertEquals(puzzle.getColor(3, 3), '.');
		puzzle.setColor(1, 3, '.');
		assertEquals(puzzle.getColor(3, 3), '.');
	}
	
	@Test
	public void testOrderCliques() {
		Puzzle puzzle = new Puzzle(3);
		
		try {
			puzzle.populatePuzzle("src/sudoku/sudoku-problem.txt");
		}
		catch(IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
		
		puzzle.orderCliques();		
		ArrayList<String> orderedCliques = puzzle.getOrderedCliques();
		assertEquals(orderedCliques.size(), 27);
		for(int i = 0; i < orderedCliques.size()-1; i++){
			assertTrue(puzzle.getClique(orderedCliques.get(i)).getNumUncolored() <= 
					puzzle.getClique(orderedCliques.get(i+1)).getNumUncolored());
		}
	}

}
