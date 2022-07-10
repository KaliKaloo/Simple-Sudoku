package sudoku.userInterface.logic;

import java.io.IOException;

import sudoku.computationalLogic.GameLogic;
import sudoku.constants.GameState;
import sudoku.constants.Messages;
import sudoku.problemDomain.IStorage;
import sudoku.problemDomain.SudokuGame;
import sudoku.userInterface.IUserInterfaceContract;

//manages the interaction between the user and the user interface and the backend of the application(computation logic)
public class ControlLogic implements IUserInterfaceContract.EventListener {
	
	//when dealing with a controller/presenter/view model, communicate with the backend and or view (frontend) through interfaces
	
	private IStorage storage;
	private IUserInterfaceContract.View view;
	
	public ControlLogic(IStorage storage, IUserInterfaceContract.View view) {
		this.storage = storage;
		this.view = view;		
		
	}
	
	
	//When user inputs or deletes a number, we're going to write that to the game storage
	@Override
	public void onSudokuInput(int x, int y, int input) {
		try {
			//try to write user input
			SudokuGame gameData = storage.getGameData();
			int[][] newGridState = gameData.getCopyGridState();
			newGridState[x][y] = input;
			
			//if the write is successful, we create a new game object cause its a immutable object
			//create a new one from the old one
			gameData = new SudokuGame(
					GameLogic.checkForCompletion(newGridState),
					newGridState
				);
			
			storage.updateGameData(gameData);
			view.updateSquare(x, y, input);
			
			//if game is completed, show dialog to user
			if(gameData.getGameState() == GameState.COMPLETE) {
				view.showDialog(Messages.GAME_COMPLETE);
			}
			
		}catch(IOException e) {
			// but if something goes wrong, show the error
			e.printStackTrace();
			view.showError(Messages.ERROR);
		}
		
	}

	@Override
	public void onDialogClick() {
		// gets called when the user clicks on the OK dialog
		// makes a new game for the user
		try {
			storage.updateGameData(
					GameLogic.getNewGame()
				);
			
			view.updateBoard(storage.getGameData());
		}catch(IOException e){
			view.showError(Messages.ERROR);
		}
	}

	
	
}
