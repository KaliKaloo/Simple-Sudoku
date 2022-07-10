package sudoku.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sudoku.problemDomain.IStorage;
import sudoku.problemDomain.SudokuGame;

public class LocalStorageImpl implements IStorage {

	private static File GAME_DATA = new File(
			System.getProperty("user.home"),
			"gamedata.txt"
			//use this file to storing and retrieving the data
	);
			
	
	// method will write to local file system using output streams
	@Override
	public void updateGameData(SudokuGame game) throws IOException {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
			
			//create an object output stream 
			// we can do this casue the sudokuGame class extends serilizable
			// this allows us to serilize that class (turn it into a data format that is easy to read anad write e.g. text file)
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(game);
			objectOutputStream.close();
			
		} catch (IOException e){
			throw new IOException("Unable to access Game Data");
			
		}
		
	}

	// method to read the data from the local file system using input streams
	@SuppressWarnings("resource")
	@Override
	public SudokuGame getGameData() throws IOException {
		FileInputStream  fileInputStream = new FileInputStream(GAME_DATA);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		
		try {
			SudokuGame gameState = (SudokuGame) objectInputStream.readObject();
			objectInputStream.close();
			return gameState;
		} catch (ClassNotFoundException e) {
			throw new IOException("File not found");
		}
	}
	
}
