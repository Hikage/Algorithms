package sudoku;

/**
 * "Sudoku" Sudoku Solver
 * Copyright © 2014 Brianna Shade
 * bshade@pdx.edu
 *
 * Cell.java
 * Houses cell data and functions
 */

import java.util.ArrayList;

public class Cell {
	private char color;
	private ArrayList<Character> validColors;
	private Clique[] cliques;
	private static final int ROW = 0, COL = 1, BOX = 2;
	
	/**
	 * Constructor class
	 * @param row: Clique representing the Cell's row
	 * @param col: Clique representing the Cell's column
	 * @param box: Clique representing the Cell's box
	 */
	public Cell(Clique row, Clique col, Clique box){
		color = '.';
		initCliques(row, col, box);
		initValidColors();
	}
	
	/**
	 * Initializes the three Cliques for each cell
	 * @param row: Clique representing the Cell's row
	 * @param col: Clique representing the Cell's column
	 * @param box: Clique representing the Cell's box
	 */
	private void initCliques(Clique row, Clique col, Clique box){
		cliques = new Clique[3];
		cliques[ROW] = row;
		cliques[COL] = col;
		cliques[BOX] = box;
	}
	
	/**
	 * Initializes the valid colors available to each Cell
	 */
	private void initValidColors(){
		String colors = "123456789";
		validColors = new ArrayList<Character>();
		//initialize with all colors available
		for(int i = 0; i < colors.length(); i++)
			validColors.add(colors.charAt(i));

		//check to make sure no other Cell in the Clique already has this color
		for(Clique clique : cliques)
			for(Cell cell : clique.getCells())
				validColors.remove(Character.valueOf(cell.getColor()));		//no-op if not present
	}
	
	/**
	 * Colors the Cell
	 * @param c: color to assign to the Cell
	 */
	public void color(char c){
		color = c;
		validColors.clear();			//no colors are valid when one has already been assigned
		
		//Clique housekeeping
		for(Clique clique : cliques){
			clique.removeColor(c);
			clique.decrementUncolored();
		}
	}
	
	/**
	 * Clears the color from the Cell
	 */
	public void uncolor(){
		char oldColor = color;
		color = '.';
		for(Clique clique : cliques){
			clique.addColor(oldColor);
			clique.incrementUncolored();
		}
		initValidColors();				//reset valid available colors
	}
	
	/**
	 * Removes a specified color from the list of valid colors
	 * @param c: color to be removed
	 */
	public void removeValidColor(char c){
		if(!validColors.isEmpty())
			validColors.remove(Character.valueOf(c));
	}
	
	/**
	 * Adds a color to the validColors list.. but only if it's legal
	 * @param c: color to be added
	 */
	public void addValidColor(char c){
		if(validColors.contains(c)) return;			//don't want to add it again
		for(Clique clique : cliques)
			for(Cell cell : clique.getCells())
				if(cell.getColor() == c) return;	//illegal if another Cell in a related Clique already has it assigned

		//can just add it if the list is empty
		if(validColors.isEmpty()){
			validColors.add(c);
			return;
		}
		//keep the colors in order for proper iteration
		for(int i = validColors.size()-1; i >= 0; i--){
			if(c < validColors.get(i)){
				validColors.add(i, c);
				return;
			}
		}	
		
	}
	
	/**
	 * Retrieves the Cell's color
	 * @return: the Cell's color
	 */
	public char getColor(){
		return color;
	}
	
	/**
	 * Retrieves the list of valid colors
	 * @return: the list of valid colors
	 */
	public ArrayList<Character> getValidColors(){
		return validColors;
	}
	
	/**
	 * Converts the list of available colors into a string format, for convenience and testing
	 * @return: list of valid colors, in string format
	 */
	public String vColorsToString(){
		String vColors = "";
		for(char c : validColors)
			vColors += c;
		return vColors;
	}
}
