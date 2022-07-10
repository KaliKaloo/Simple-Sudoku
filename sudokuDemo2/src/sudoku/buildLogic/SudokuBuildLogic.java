package sudoku.buildLogic;

import java.io.IOException;

import sudoku.computationalLogic.GameLogic;
import sudoku.persistence.LocalStorageImpl;
import sudoku.problemDomain.IStorage;
import sudoku.problemDomain.SudokuGame;
import sudoku.userInterface.IUserInterfaceContract;
import sudoku.userInterface.logic.ControlLogic;

// taking the logic required to build up and wire all the different parts in the application together
// just before they launch for the user to use
// encapsulate it all here

public class SudokuBuildLogic {
	public static void build(IUserInterfaceContract.View userInterface) throws IOException{
		SudokuGame initialState;
		IStorage storage = new LocalStorageImpl();
		
		
		//attempt to get the game data from the storage if it exists
		// otherwise storage will throw an exception and we ask game logic for a new game
		try {
			initialState = storage.getGameData();
			
		}catch (IOException e) {
			initialState = GameLogic.getNewGame();
			storage.updateGameData(initialState);
		}
		
		// create logic class (controlLogic) Requires storage and reference to user interface
		IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface);
		
		// bind the controlLogic to the userinterface so that they can talk both ways
		userInterface.setListener(uiLogic);
		// call this method to update the board with its initial state
		userInterface.updateBoard(initialState);
	}
}
