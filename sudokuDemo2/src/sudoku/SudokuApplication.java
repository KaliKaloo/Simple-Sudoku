package sudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import sudoku.buildLogic.SudokuBuildLogic;
import sudoku.userInterface.IUserInterfaceContract;
import sudoku.userInterface.UserInterfaceImpl;

import java.io.IOException;

//extends means class inherits from another class.
public class SudokuApplication extends Application {
	//is user interface
	private IUserInterfaceContract.View uiImpl;
	
	
	//the stage object is a window that javafx gives us that we can modify.
	//SudokuBuildLogic contains build logic. code required to wire things together
	@Override
	public void start(Stage primaryStage) throws Exception {
		uiImpl = new UserInterfaceImpl(primaryStage);
		try {
			SudokuBuildLogic.build(uiImpl);
		} catch(IOException e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

