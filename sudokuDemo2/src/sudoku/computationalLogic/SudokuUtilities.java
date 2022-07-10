package sudoku.computationalLogic;

import sudoku.problemDomain.SudokuGame;

public class SudokuUtilities {
	//copies one sudoku array into another sudoku array
	public static void copySudokuArrayValue(int[][] oldArray, int[][] newArray) {
		for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++) {
			for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++) {
			newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
			}
		}
	}
	
	//take in old array and return a new array with the same values
	
	public static int[][] copyToNewArray(int[][] oldArray) {
		int[][] newArray = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];
		for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++) {
			for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++) {
			newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
			}
		}
	return newArray;
	}
	
}
	
