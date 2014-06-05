package sudoku;

/**
 * "Sudoku" Sudoku Solver
 *
 * Clique.java
 * Object class for clique-level functions and structure
 */

import java.util.ArrayList;

public class Clique {

	private ArrayList<Cell> cells;
	private int numUncolored;
	
	/**
	 * Constructor class
	 */
	public Clique(){
		cells = new ArrayList<Cell>();
		numUncolored = 0;
	}	
	
	/**
	 * Adds a Cell to the Clique
	 * @param c: cell to be added
	 */
	public void addCell(Cell c){
		cells.add(c);
		incrementUncolored();
	}
	
	/**
	 * Calculates the number of uncolored Cells in the Clique
	 */
	public void calcNumUncolored(){
		numUncolored = 0;
		for (Cell c : cells)
			if(c.getColor() == '.'){
				incrementUncolored();
			}
	}
	
	/**
	 * Decrements the uncolored count
	 */
	public void decrementUncolored(){
		numUncolored--;
	}
	
	/**
	 * Increments the uncolored count
	 */
	public void incrementUncolored(){
		numUncolored++;
	}
	
	/**
	 * Adds a color to all cells in the Clique (legality checked at the Cell level)
	 * @param c: color to be added
	 */
	public void addColor(char c){
		for(Cell cell : cells)
			cell.addValidColor(c);
	}
	
	/**
	 * Removes a color from all cells in the Clique
	 * @param c: color to be removed
	 */
	public void removeColor(char c){
		for(Cell cell : cells)
			cell.removeValidColor(c);
	}
	
	/**
	 * Retrieves the number of Cells uncolored
	 * @return: the number of uncolored Cells
	 */
	public int getNumUncolored(){
		return numUncolored;
	}
	
	/**
	 * Retrieves the set of Cells contained within the Clique
	 * @return: the set of Cells within the Clique
	 */
	public ArrayList<Cell> getCells(){
		return cells;
	}
	
	/**
	 * Retrieves the set of uncolored Cells within the Clique
	 * @return: the set of uncolored Cells within the Clique
	 */
	public ArrayList<Cell> getUncoloredCells(){
        ArrayList<Cell> uncoloredCells = new ArrayList<Cell>();
        for(Cell cell : cells)
            if(cell.getColor() == '.') uncoloredCells.add(cell);

        return uncoloredCells;
	}
}
