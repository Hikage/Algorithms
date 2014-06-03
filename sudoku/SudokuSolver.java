package sudoku;

public class SudokuSolver {


	private String colors = ".123456789";
	
	public static void main(String[] args) {
		Puzzle sudoku = new Puzzle(3);
		System.out.println(sudoku.toString());
	}

}
