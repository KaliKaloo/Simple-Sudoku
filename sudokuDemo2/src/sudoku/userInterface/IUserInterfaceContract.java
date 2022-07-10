package sudoku.userInterface;

import sudoku.problemDomain.SudokuGame;

public interface IUserInterfaceContract {
	//using parent interfaces as a namespace or a way of differentiating idfferent interfaces
	//if have multiple different user interface features, this is how to differentiate them
	// most would have eventlistner and a view
	
	
	//event listener like a controller or presenter 
	interface EventListener {
		void onSudokuInput(int x, int y, int input);

		void onDialogClick();
	}
	
	//name for part of application that binds to the user interface
	interface View{
		void setListener(IUserInterfaceContract.EventListener listener);
		void updateSquare(int x, int y, int input);
		void updateBoard(SudokuGame game);
		void showDialog(String message);
		void showError(String message);
	}
}
