package sudoku;

public class Cell {
	private char color;
	
	public Cell(){
		color = '.';
	}
	
	public Cell(char c){
		setColor(c);
	}
	
	public void setColor(char c){
		color = c;
	}
	
	public char getColor(){
		return color;
	}
}
