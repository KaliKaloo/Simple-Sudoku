package sudoku.problemDomain;

import java.io.Serializable;

import sudoku.constants.GameState;

public class SudokuGame implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final GameState gameState;
	//Store the sudoku grid in a 2D array
	private final int[][] gridState;
	
	public static final int GRID_BOUNDARY = 9;
	
	public SudokuGame(GameState gameState, int[][] gridState) {
		this.gameState = gameState;
		this.gridState = gridState;
	}

	public GameState getGameState() {
		return gameState;
	}

	// make the gridstate immutable so protects sudoku game objects
	public int[][] getCopyGridState() {
		return gridState;
	}
	
	
}
