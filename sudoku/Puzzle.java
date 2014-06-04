package sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Puzzle {
	private static final int ROWS = 0;
	private static final int COLS = 1;
	private static final int BXS = 2;

	private Cell[][] puzzle;
	private Clique[][] cliques;
	private ArrayList<String> orderedCliques;
	private int dimension;
	private int size;
	
	
	public Puzzle(int d){
		dimension = d;
		size = (int)Math.pow(dimension, 2);
		puzzle = new Cell[size][size];
		
		initializeCliques();
		
		for(int r = 0; r < size; r++){			
			for(int c = 0; c < size; c++){				
				Cell cell = new Cell();
				setCell(r, c, cell);
			}
		}
		
        calculateUncolored();
	}
	
	public Puzzle(String filename) throws IOException{
		FileReader fr;
        BufferedReader buff;
        
        try{
        	fr = new FileReader(filename);
            buff = new BufferedReader(fr);
            String line = buff.readLine();
            
            size = line.length();
            dimension = (int)Math.sqrt(size);
            puzzle = new Cell[size][size];
            
            initializeCliques();
            
            for(int r = 0; r < size; r++){
            	if(line.isEmpty())
            		System.err.println("Invalid input file. Error line " + r);
            	else
            		for(int c = 0; c < size; c++){
            			Cell cell = new Cell(line.charAt(c));
            			setCell(r, c, cell);
            		}
            	line = buff.readLine();
            }
            
            calculateUncolored();
        }
        catch(IOException ex){
            System.err.println("Oh no! An error occurred!\n Error: " + ex);
        }
	}
	
	private void initializeCliques(){
		cliques = new Clique[3][size];
		
		for(int i = 0; i < size; i++){
			cliques[ROWS][i] = new Clique();
			cliques[COLS][i] = new Clique();
			cliques[BXS][i] = new Clique();
		}
	}
	
	private void calculateUncolored(){

		for(int i = 0; i < size; i++){
			cliques[ROWS][i].calcNumUncolored();
			cliques[COLS][i].calcNumUncolored();
			cliques[BXS][i].calcNumUncolored();
		}
	}
	
	private void setCell(int r, int c, Cell cell){
		puzzle[r][c] = cell;
		
		cliques[ROWS][r].addCell(cell);
		cliques[COLS][c].addCell(cell);
		cliques[BXS][this.getBox(r, c)].addCell(cell);
	}
	
	public void setColor(int r, int c, char clr){
		puzzle[r][c].setColor(clr);
		if(clr != '.'){
			cliques[ROWS][r].decrementUncolored();
			cliques[COLS][c].decrementUncolored();
			cliques[BXS][this.getBox(r, c)].decrementUncolored();
		}
	}
	
	public char getColor(int r, int c){
		return puzzle[r][c].getColor();
	}
	
	public int getBox(int r, int c){
		return c/dimension + (r/dimension * dimension);
	}
	
	public void orderCliques(){
		orderedCliques = new ArrayList<String>();
		for(int i = 0; i < cliques.length; i++){
			for(int j = 0; j < cliques[0].length; j++){
				boolean found = false;
				for(int k = 0; k < orderedCliques.size(); k++){
					int type = Character.getNumericValue(orderedCliques.get(k).charAt(0));
					int num = Character.getNumericValue(orderedCliques.get(k).charAt(1));
					if(cliques[i][j].getNumUncolored() < cliques[type][num].getNumUncolored()){
						orderedCliques.add(k, Integer.toString(i) + j);
						found = true;
						break;
					}
				}

				String label = Integer.toString(i) + j;
				if(!found) orderedCliques.add(label);
			}
		}
		
		for(String c : orderedCliques){
			System.out.print(c + "(" + cliques[Character.getNumericValue(c.charAt(0))][Character.getNumericValue(c.charAt(1))].getNumUncolored() + "), ");
		}
	}
	
	public String toString(){
		String puzz = "";
		
		for(int r = 0; r < size; r++){
			for(int c = 0; c < size; c++)
				puzz += puzzle[r][c].getColor();
			puzz += "\n";
		}
		
		return puzz;
	}
}
