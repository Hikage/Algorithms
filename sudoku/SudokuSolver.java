package sudoku;

import java.io.IOException;
import java.util.ArrayList;

public class SudokuSolver {	
	
	public static void main(String[] args) {
		Puzzle sudoku;
		
		try {
			sudoku = new Puzzle(3);
			sudoku.populatePuzzle("src/sudoku/sudoku-problem.txt");
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
	
	public static Puzzle colorPuzzle(Puzzle sudoku){
		//if solved, return solution
		if(sudoku.solved()) return sudoku;

		//choose uncolored vertex
		for(String label : sudoku.getOrderedCliques()){
			Clique clique = sudoku.getClique(label);		//get most colored clique
			//start coloring the uncolored cells
			for(Cell cell : clique.getCells()){

//		for(Clique row : sudoku.getCliques()[0]){
//			for(Cell cell : row.getCells()){
				//System.out.println("Last cell valid colors: " + row.getCells().get(8).vColorsToString());
				if(cell.getColor() != '.') continue;		//skip over any cells already colored
				
				System.out.println(sudoku.toString() + "\n");
				if(cell.getValidColors().size() == 0) return null;	//if there are no valid colors for a cell, we've hit an invalid arrangement
				
				//for each legal color
				ArrayList<Character> colors = new ArrayList<Character>();
				colors.addAll(cell.getValidColors());
				//System.out.println("Valid colors: " + cell.vColorsToString() + "\n");
				for(int i = colors.size()-1; i >= 0; i--){
					//color vertex
					cell.color(colors.get(i));
					//System.out.println("Colored:\n" + sudoku.toString());
					Puzzle solution = colorPuzzle(sudoku);
					//if solved, return solution
					if(solution != null && solution.solved()) return solution;
					//uncolor vertex
					cell.uncolor();
					//System.out.println("Uncolored");
					//System.out.println("Uncolored:\n" + sudoku.toString());
					//System.out.println("Valid colors: " + cell.vColorsToString() + "\n");
				}
				
			}
		}
		
		return null;
	}

}
