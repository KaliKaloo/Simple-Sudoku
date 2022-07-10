package sudoku.problemDomain;

import java.io.IOException;

//interface can be used to design parts of the application upfront ahead of time.
//anticipate IOExceptions
public interface IStorage {
	void updateGameData(SudokuGame game) throws IOException;
	SudokuGame getGameData() throws IOException;
}
