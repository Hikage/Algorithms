package sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Puzzle {

	private StringBuilder[] puzzle;
	private int dimension;
	
	public Puzzle(int d){
		dimension = d;
		int size = (int)Math.pow(dimension, 2);
		puzzle = new StringBuilder[size];
		
		StringBuilder init = new StringBuilder("");
		for(int c = 0; c < size; c++){
			init.append('.');
		}
		for(int r = 0; r < size; r++)				
			puzzle[r] = init;
	}
	
	public Puzzle(String filename) throws IOException{
		FileReader fr;
        BufferedReader buff;
        
        try{
        	fr = new FileReader(filename);
            buff = new BufferedReader(fr);
            String line = buff.readLine();
            
            int size = line.length();
            dimension = (int)Math.sqrt(size);
            puzzle = new StringBuilder[size];
            
            for(int r = 0; r < size; r++){
            	if(line.isEmpty())
            		System.err.println("Invalid input file. Error line " + r);
            	else
            		puzzle[r] = new StringBuilder(line);
            	line = buff.readLine();
            }
        }
        catch(IOException ex){
            System.err.println("Oh no! An error occurred!\n Error: " + ex);
        }
	}
	
	public void setColor(int r, int c, char clr){
		puzzle[r].setCharAt(c, clr);
	}
	
	public char getColor(int r, int c){
		return puzzle[r].charAt(c);
	}
	
	public int getBox(int r, int c){
		return c/dimension + (r/dimension * dimension);
	}
	
	public String toString(){
		String puzz = "";
		
		int size = (int)Math.pow(dimension, 2);
		for(int r = 0; r < size; r++)			
			puzz += puzzle[r] + "\n";
		
		return puzz;
	}
}
