package sudoku;

import java.io.IOException;

public class SudokuSolver {

	private static String colors = ".123456789";
	
	
	
	public static void main(String[] args) {
		Puzzle sudoku;
		
		try {
			sudoku = new Puzzle("src/sudoku/sudoku-problem.txt");
			System.out.println(sudoku.toString());
			
			sudoku.orderCliques();
		}
		catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
	}

}
