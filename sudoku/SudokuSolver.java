package sudoku;

/**
 * "Sudoku" Sudoku Solver
 * Copyright © 2014 Brianna Shade
 * bshade@pdx.edu
 *
 * SudokuSolver.java
 * Driver for solving Sudoku puzzles
 */

import java.io.IOException;
import java.util.ArrayList;

public class SudokuSolver {	
	
	public static void main(String[] args) {
		Puzzle sudoku;
		
		try {
			sudoku = new Puzzle(3);
			sudoku.populatePuzzle("src/sudoku/sudoku-problem2.txt");
			System.out.println(sudoku.toString());
			
			Puzzle solution = colorPuzzle(sudoku);
			if(solution == null)
				System.out.println("Sorry! No solution exists :(");
			else
				System.out.println("Solved!\n" + solution.toString());
		}
		catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
	}
	
	/**
	 * Colors the puzzle per clique constraints
	 * @param sudoku: puzzle to be colored
	 * @return: the solution puzzle
	 */
	public static Puzzle colorPuzzle(Puzzle sudoku){
		System.out.println("\n" + sudoku.toString());
		if(sudoku.solved()) return sudoku;

		//make a copy of the puzzle's sorted cliques
		ArrayList<Clique> orderedCliques = new ArrayList<Clique>();
		orderedCliques.addAll(sudoku.getOrderedCliques(sudoku.getCliques()));
		
		//start with the most colored clique, resorting after each is completed		
		while(!orderedCliques.isEmpty()){
			Clique clique = orderedCliques.remove(0);
			for(Cell cell : clique.getCells()){
				if(cell.getColor() != '.') continue;				//skip over any cells already colored
				
				if(cell.getValidColors().size() == 0) return null;	//if there are no valid colors for a cell, we've hit an invalid arrangement
				
				//pull out the colors, as Java doesn't like changing data
				ArrayList<Character> colors = new ArrayList<Character>();
				colors.addAll(cell.getValidColors());
				for(int j = colors.size()-1; j >= 0; j--){			//iterate backwards in case indexes change
					cell.color(colors.get(j));
					Puzzle solution = colorPuzzle(sudoku);
					if(solution != null && solution.solved()) return solution;
					cell.uncolor();
				}				
			}
			//reorder Cliques
			//orderedCliques = sudoku.getOrderedCliques(orderedCliques);
		}
		//reached the end, no solution found
		return null;
	}

}
