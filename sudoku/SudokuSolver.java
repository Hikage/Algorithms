package sudoku;

import java.io.IOException;

public class SudokuSolver {

	private static String colors = ".123456789";	
	
	public static void main(String[] args) {
		Puzzle sudoku;
		
		try {
			sudoku = new Puzzle(3);
			sudoku.populatePuzzle("src/sudoku/sudoku-problem.txt");
			System.out.println(sudoku.toString());
			
			//color(sudoku);
		}
		catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
	}
	
	public static void color(Puzzle sudoku){
		//order the cliques to tackle the most colored first
		sudoku.orderCliques();
		
		for(String label : sudoku.getOrderedCliques()){
			//get the appropriate clique
			Clique clique = sudoku.getClique(label);
			//start coloring the uncolored cells
			for(Cell cell : clique.getCells()){
				for(char color : cell.getValidColors()){
					//check color is also valid for
				}
			}
		}
	}

}
