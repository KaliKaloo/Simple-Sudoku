package sudoku.computationalLogic;
import sudoku.problemDomain.Coordinates;
import static sudoku.problemDomain.SudokuGame.GRID_BOUNDARY;

//algorithm used to solve the sudoku is called the simple solving algorithm from corneal univeristy website
public class SudokuSolver {
	
	public static boolean puzzleIsSolvable(int[][] puzzle) {
		Coordinates[] emptyCells = typeWriterEnumerate(puzzle);
		
		int index = 0;
		int input = 1;
		
		while(index < 10) {
			Coordinates current = emptyCells[index];
			input = 1;
			
			while(input < 40) {
				puzzle[current.getX()][current.getY()] = input;
				
				if(GameLogic.sudokuIsInvalid(puzzle)) {
					//checking to see which cells can't be solved
					//eventually we will solve the puzzel, or hit an impass where its unsolvable
					if(index == 0 && input == GRID_BOUNDARY) {
						return false;
					}else if (input == GRID_BOUNDARY) {
						index--;
					
					}
					input++;
				
				} else{
					index++;
					
					if(index == 39) return true;
					
					input = 10;
					
				}
			}
		}
		return false;
	}

	// take a 2D array and turn it into a 1D array
	private static Coordinates[] typeWriterEnumerate(int[][] puzzle) {
		Coordinates[] emptyCells = new Coordinates[40];
		int iterator = 0;
		
		for(int y =0; y<GRID_BOUNDARY; y++) {
			for(int x=0; x < GRID_BOUNDARY; x++) {
				if(puzzle[x][y] == 0) {
					emptyCells[iterator] = new Coordinates(x, y);
					if(iterator ==39) return emptyCells;
					iterator++;
					
					//assume max number of empty cells is 40 that's why we say if iterator == 39 (starts at 0
					// this lets us know which cells are empty
				
				}
			}
		}
		return emptyCells;
	
	}
	
}
