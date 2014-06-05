package sudoku;

/**
 * "Sudoku" Sudoku Solver
 * Copyright © 2014 Brianna Shade
 * bshade@pdx.edu
 *
 * Puzzle.java
 * Object class for puzzle-level functions and structure
 */

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
	
	/**
	 * Constructor class
	 * @param d: the dimension for the puzzle; typically 3, but could also be 2
	 */
	public Puzzle(int d){
		dimension = d;
		size = (int)Math.pow(dimension, 2);
		puzzle = new Cell[size][size];
		
		initializeCliques();
		
		for(int r = 0; r < size; r++){			
			for(int c = 0; c < size; c++){				
				Cell cell = new Cell(cliques[ROWS][r], cliques[COLS][c], cliques[BXS][this.getBox(r, c)]);				
				puzzle[r][c] = cell;
				
				//add cells to their Cliques
				cliques[ROWS][r].addCell(cell);
				cliques[COLS][c].addCell(cell);
				cliques[BXS][this.getBox(r, c)].addCell(cell);
			}
		}
	}
	
	/**
	 * Initializes all of the Puzzle's Cliques
	 */
	private void initializeCliques(){
		cliques = new Clique[3][size];
		
		for(int i = 0; i < size; i++){
			cliques[ROWS][i] = new Clique();
			cliques[COLS][i] = new Clique();
			cliques[BXS][i] = new Clique();
		}
	}
	
	/**
	 * Calculates a given Cell's box
	 * @param r: Cell's row
	 * @param c: Cell's column
	 * @return: the box number within the puzzle grid
	 */
	public int getBox(int r, int c){
		if(r >= size || c >= size || r < 0 || c < 0){
			System.err.println("Invalid dimentions (" + r + "," + c + ") for size " + size);
			System.exit(0);
		}
		
		return c/dimension + (r/dimension * dimension);
	}
	
	/**
	 * Populates a puzzle from an input data file
	 * @param filename: file to be used to build puzzle
	 * @throws IOException
	 */
	public void populatePuzzle(String filename) throws IOException{
		FileReader fr;
        BufferedReader buff;
        
        try{
        	fr = new FileReader(filename);
            buff = new BufferedReader(fr);
            String line = buff.readLine();
            
            //one line in the file per row in the puzzle
            for(int r = 0; r < size; r++){
            	if(line.isEmpty())
            		System.err.println("Invalid input file. Error line " + r);
            	else
            		for(int c = 0; c < size; c++)
            			setColor(r, c, line.charAt(c));
            	line = buff.readLine();
            }
        }
        catch(IOException ex){
            System.err.println("Oh no! An error occurred!\n Error: " + ex);
        }
	}
	
	/**
	 * Sets the color of a Cell at a designated location
	 * @param r: Cell's row
	 * @param c: Cell's column
	 * @param clr: color to be assigned
	 */
	public void setColor(int r, int c, char clr){
		if(r >= size || c >= size || r < 0 || c < 0){
			System.err.println("Invalid dimentions (" + r + "," + c + ") for size " + size);
			System.exit(0);
		}
		
		if(clr != '.')
			puzzle[r][c].color(clr);
		else
			puzzle[r][c].uncolor();
	}
	
	/**
	 * Retrieves the color of a Cell at a specified location
	 * @param r: Cell's row
	 * @param c: Cell's column
	 * @return: the color of the Cell at the specified location
	 */
	public char getColor(int r, int c){
		if(r >= size || c >= size || r < 0 || c < 0){
			System.err.println("Invalid dimentions (" + r + "," + c + ") for size " + size);
			System.exit(0);
		}
		
		return puzzle[r][c].getColor();
	}
	
	/**
	 * Orders the Cliques based on how many of their cells are uncolored (most colored first)
	 */
	public void orderCliques(){
		orderedCliques = new ArrayList<String>();
		for(int i = 0; i < cliques.length; i++){
			for(int j = 0; j < cliques[0].length; j++){
				if(cliques[i][j].getNumUncolored() == 0) continue;
				boolean found = false;
				for(int k = 0; k < orderedCliques.size(); k++){
					if(cliques[i][j].getNumUncolored() < this.getClique(orderedCliques.get(k)).getNumUncolored()){
						orderedCliques.add(k, Integer.toString(i) + j);
						found = true;
						break;
					}
				}

				String label = Integer.toString(i) + j;
				if(!found) orderedCliques.add(label);
			}
		}
	}
	
	/**
	 * Translates a Clique label into a corresponding Clique (labels are stored in orderedCliques for simplicity)
	 * @param label: string representation of the sought-for Clique
	 * @return: the specified Clique
	 */
	public Clique getClique(String label){
		int type = Character.getNumericValue(label.charAt(0));
		int num = Character.getNumericValue(label.charAt(1));
		return cliques[type][num];
	}
	
	/**
	 * Retrieves the set of Puzzle Cliques
	 * @return: the set of Puzzle Cliques
	 */
	public Clique[][] getCliques(){
		return cliques;
	}
	
	/**
	 * Retrieves the set of sorted Puzzle Cliques (represented by labels)
	 * @return: the set of sorted Puzzle Cliques
	 */
	public ArrayList<String> getOrderedCliques(){
		return orderedCliques;
	}
	
	/**
	 * Determines if the Puzzle has been completed
	 * @return: if the Puzzle is solved
	 */
	public boolean solved(){
		for(Cell[] rows : puzzle)
			for(Cell cell : rows)
				if(cell.getColor() == '.') return false;
		return true;
	}
	
	/**
	 * Converts orderedCliques to a string representation, for convenience and testing
	 * @return: the string representation of orderedCliques
	 */
	public String orderedCliquesToString(){
		String oclc = "";
		for(String c : orderedCliques){
			oclc += c + "(" + getClique(c).getNumUncolored() + "), ";
		}
		return oclc;
	}
	
	/**
	 * Translates the Puzzle into an ASCII picture, for convenience
	 * @return: the Puzzle's string representation
	 */
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
