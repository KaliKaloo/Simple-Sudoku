package sudoku.userInterface;

import javafx.scene.control.TextField;

//making a custom text field 
//maintains an x and y coordinates/value
public class SudokuTextField extends TextField{
	private final int x;
	private final int y;
	
	public SudokuTextField(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	//override cause if you don't get strange behaviour when user enters number
	// will get repeats etc
	// makes it so that only 1 number is entered
	@Override
	public void replaceText(int i, int i1, String s) {
		if(!s.matches("[0-9]")) {
			super.replaceText(i, i1, s);
		}
	}
	
	@Override
	public void replaceSelection(String s) {
		if(!s.matches("[0-9]")) {
			super.replaceSelection(s);
		}
	}
}
