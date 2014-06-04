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
		if(c.getColor() != '.') decrementUncolored();
	}
	
	public void decrementUncolored(){
		numUncolored--;
	}
	
	public void calcNumUncolored(){
		numUncolored = 0;
		for (Cell c : cells)
			if(c.getColor() == '.') numUncolored++;
	}
	
	public int getNumUncolored(){
		return numUncolored;
	}
}
