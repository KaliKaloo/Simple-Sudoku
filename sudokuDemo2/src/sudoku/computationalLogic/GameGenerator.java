package sudoku.computationalLogic;
import static sudoku.problemDomain.SudokuGame.GRID_BOUNDARY;
import sudoku.problemDomain.Coordinates;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameGenerator {
	public static int[][] getNewGameGrid() {
		return unsolvedGame(getSolvedGame());
	
	}
	
	// algorithm to create an unsolved sudoku game that is solveable
	private static int[][] unsolvedGame(int[][] solvedGame) {
		Random random = new Random(System.currentTimeMillis());
		boolean solvable = false;
		int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];
		
		while(solvable == false) {
			
			SudokuUtilities.copySudokuArrayValue(solvedGame, solvableArray);
			
			int index =0;
			
			//remove 40 numbers from the grid by randomly picking them
			while(index < 40) {
				int xCoordiante = random.nextInt(GRID_BOUNDARY);
				int yCoordiante = random.nextInt(GRID_BOUNDARY);
				
				//check to see if we already reset the value. if not set it to 0
				if(solvableArray[xCoordiante][yCoordiante] != 0) {
					solvableArray[xCoordiante][yCoordiante] = 0;
					index++;
				}
				
			}
		
			//now attempt to solve it
			int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
			SudokuUtilities.copySudokuArrayValue(solvableArray, toBeSolved);
			
			//use SudokuSolver class to check if it is solvable.
			solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
		
			
			
		}
		
		return solvableArray;
		
	}


	// method will generate a solved sudoku game
	private static int[][] getSolvedGame() {
		
		//first seed a random number
		Random random = new Random(System.currentTimeMillis());
		int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];
		
		for(int value = 1; value <= GRID_BOUNDARY; value++) {
			// for the values 1 to 9, we will allocate them 9 times. (nine 1's, nine 2's etc
			int allocations = 0;
			// interrupt: if an allocation is attempted too many times, we will increment the interrupt
			int interrupt = 0;
			
			List<Coordinates> allocTracker = new ArrayList<>();
		
			int attempts = 0;
			
			//while the number of allocations for a given number is less than 9, keep attempting to allocate it
			while(allocations < GRID_BOUNDARY) {
				//if the number of interrupts exceeds 200, go through the past numbers we've allocated and resent them to 0
				// basically backtracking when we get stuck (interrupt >200)
				if(interrupt >200) {
					allocTracker.forEach(coord -> {
						newGrid[coord.getX()][coord.getY()] = 0;
					});
					
					interrupt = 0;
					allocations = 0;
					allocTracker.clear();
					attempts++;
					
					//but this may not be enough
					//if attempts is bigger than 500, then we are definetly stuck
					//clear the whole array (grid)
					if(attempts > 500) {
						clearArray(newGrid);
						attempts = 0;
						value = 1;
						
					}
				}
				
				//RANDOMLY select a sqaure
				int xCoordinate = random.nextInt(GRID_BOUNDARY);
				int yCoordinate = random.nextInt(GRID_BOUNDARY);
				
				//if that sqaure is empty, assgin it the value
				if(newGrid[xCoordinate][yCoordinate] == 0) {
					newGrid[xCoordinate][yCoordinate] = value;
					
					//immediately after allcoating value, check if it is a valid or invalid sudoku game
					//if invalid, reset sqaure to 0
					//if valid, add coordiantes to aloctracker and increment allocations
					if(GameLogic.sudokuIsInvalid(newGrid)) {
						newGrid[xCoordinate][yCoordinate] = 0;
						interrupt++;
					}else {
						allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
						allocations++;
					}
				}
				
			}
		
		}
				
		return newGrid;
	}


	private static void clearArray(int[][] newGrid) {
		for(int xIndex = 0; xIndex <GRID_BOUNDARY ; xIndex++) {
			for(int yIndex = 0; yIndex <GRID_BOUNDARY ; yIndex++) {
			newGrid[xIndex][yIndex] = 0;
			}
		}
	}
}
