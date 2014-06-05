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

			//order the cliques to tackle the most colored first
			sudoku.orderCliques();
			
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
		if(sudoku.solved()) return sudoku;

		//start with the most colored clique
		for(String label : sudoku.getOrderedCliques()){
			Clique clique = sudoku.getClique(label);
			for(Cell cell : clique.getCells()){
				if(cell.getColor() != '.') continue;				//skip over any cells already colored
				
				if(cell.getValidColors().size() == 0) return null;	//if there are no valid colors for a cell, we've hit an invalid arrangement
				
				//pull out the colors, as Java doesn't like changing data
				ArrayList<Character> colors = new ArrayList<Character>();
				colors.addAll(cell.getValidColors());
				for(int i = colors.size()-1; i >= 0; i--){			//iterate backwards in case indexes change
					cell.color(colors.get(i));
					Puzzle solution = colorPuzzle(sudoku);
					if(solution != null && solution.solved()) return solution;
					cell.uncolor();
				}				
			}
		}
		//reached the end, no solution found
		return null;
	}

}
