package sudoku;

import java.util.ArrayList;

public class Cell {
	private char color;
	private ArrayList<Character> validColors;
	private Clique[] cliques;
	private static final int ROW = 0, COL = 1, BOX = 2;
	
	public Cell(Clique row, Clique col, Clique box){
		color = '.';
		initCliques(row, col, box);
		initValidColors();
	}
	
	private void initCliques(Clique row, Clique col, Clique box){
		cliques = new Clique[3];
		cliques[ROW] = row;
		cliques[COL] = col;
		cliques[BOX] = box;
	}
	
	private void initValidColors(){
		String colors = "123456789";
		validColors = new ArrayList<Character>();
		colorLoop:
		for(int i = 0; i < colors.length(); i++){
			for(Clique clique : cliques)
				for(Cell cell : clique.getCells())
					if(cell.getColor() == colors.charAt(i)) continue colorLoop;
			validColors.add(colors.charAt(i));
		}
	}
	
	public void color(char c){
		color = c;
		validColors.clear();
		
		for(Clique clique : cliques){
			clique.decrementUncolored();
			clique.removeColor(c);
		}
	}
	
	public void uncolor(){
		char oldColor = color;
		color = '.';
		for(Clique clique : cliques){
			clique.addColor(oldColor);
			clique.incrementUncolored();
		}
		initValidColors();
	}
	
	public void removeValidColor(char c){
		if(!validColors.isEmpty())
			validColors.remove(Character.valueOf(c));
	}
	
	public void addValidColor(char c){
		if(validColors.contains(c)) return;
		for(Clique clique : cliques)
			for(Cell cell : clique.getCells())
				if(cell.getColor() == c) return;

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
	
	public char getColor(){
		return color;
	}
	
	public ArrayList<Character> getValidColors(){
		return validColors;
	}
	
	public String vColorsToString(){
		String vColors = "";
		for(char c : validColors)
			vColors += c;
		return vColors;
	}
}
