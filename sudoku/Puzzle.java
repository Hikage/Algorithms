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
				Cell cell = new Cell(cliques[ROWS][r], cliques[COLS][c], cliques[BXS][this.getBox(r, c)]);				
				puzzle[r][c] = cell;
				
				cliques[ROWS][r].addCell(cell);
				cliques[COLS][c].addCell(cell);
				cliques[BXS][this.getBox(r, c)].addCell(cell);
			}
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
	
	public int getBox(int r, int c){
		if(r >= size || c >= size || r < 0 || c < 0){
			System.err.println("Invalid dimentions (" + r + "," + c + ") for size " + size);
			System.exit(0);
		}
		
		return c/dimension + (r/dimension * dimension);
	}
	
	public void populatePuzzle(String filename) throws IOException{
		FileReader fr;
        BufferedReader buff;
        
        try{
        	fr = new FileReader(filename);
            buff = new BufferedReader(fr);
            String line = buff.readLine();
            
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
	
	public char getColor(int r, int c){
		if(r >= size || c >= size || r < 0 || c < 0){
			System.err.println("Invalid dimentions (" + r + "," + c + ") for size " + size);
			System.exit(0);
		}
		
		return puzzle[r][c].getColor();
	}
	
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
	
	public Clique getClique(String label){
		int type = Character.getNumericValue(label.charAt(0));
		int num = Character.getNumericValue(label.charAt(1));
		return cliques[type][num];
	}
	
	public Clique[][] getCliques(){
		return cliques;
	}
	
	public ArrayList<String> getOrderedCliques(){
		return orderedCliques;
	}
	
	public boolean solved(){
		for(Cell[] rows : puzzle)
			for(Cell cell : rows)
				if(cell.getColor() == '.') return false;
		return true;
	}
	
	public String orderedCliquesToString(){
		String oclc = "";
		for(String c : orderedCliques){
			oclc += c + "(" + getClique(c).getNumUncolored() + "), ";
		}
		return oclc;
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
