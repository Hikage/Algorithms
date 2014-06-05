package sudoku;

/**
 * "Sudoku" Sudoku Solver
 *
 * SudokuSolver.java
 * Driver for solving Sudoku puzzles
 */

import java.io.IOException;
import java.util.ArrayList;

public class SudokuSolver {	
	
	public static void main(String[] args) {
        if(args.length < 1){
            System.err.println("Usage: java SudokuSolver intput-file.txt");
            System.exit(0);
        }

        Puzzle sudoku = new Puzzle(3);

		try {
			sudoku.populatePuzzle(args[0]);
			System.out.println(sudoku.toString());
			
		    Puzzle solution = colorPuzzle(sudoku);
		    if(solution == null)
			    System.out.println("Sorry! No solution exists :(");
		    else
			    System.out.println("Solved!\n" + solution.toString());
		}
        catch (IOException e){
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

		//make a copy of the puzzle's sorted cliques
		ArrayList<Clique> orderedCliques = new ArrayList<Clique>();
		orderedCliques.addAll(sudoku.getOrderedCliques(sudoku.getCliques()));
		
		//start with the most colored clique, resorting after each is completed		
		while(!orderedCliques.isEmpty()){
			Clique clique = orderedCliques.remove(0);
			for(Cell cell : clique.getUncoloredCells()){
				//if there are no valid colors for a cell, we've hit an invalid arrangement
                if(cell.getValidColors().size() == 0) return null;
				    
                //pull out the colors, as Java doesn't like changing data
				ArrayList<Character> colors = cell.getValidColors();
				for(int i = 0; i < colors.size(); i++){
					cell.color(colors.get(i));
		            //System.out.println("\n" + sudoku.toString());
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
