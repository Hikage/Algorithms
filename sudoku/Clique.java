package sudoku;

import java.util.ArrayList;

public class Clique {

	private ArrayList<Cell> cells;
	private int numUncolored;
	
	public Clique(){
		cells = new ArrayList<Cell>();
		numUncolored = 0;
	}	
	
	public void addCell(Cell c){
		cells.add(c);
		incrementUncolored();
	}
	
	public void calcNumUncolored(){
		numUncolored = 0;
		for (Cell c : cells)
			if(c.getColor() == '.'){
				incrementUncolored();
			}
	}
	
	public void decrementUncolored(){
		numUncolored--;
	}
	
	public void incrementUncolored(){
		numUncolored++;
	}
	
	public void addColor(char c){
		for(Cell cell : cells)
			cell.addValidColor(c);
	}
	
	public void removeColor(char c){
		for(Cell cell : cells)
			cell.removeValidColor(c);
	}
	
	public int getNumUncolored(){
		return numUncolored;
	}
	
	public ArrayList<Cell> getCells(){
		return cells;
	}
}
