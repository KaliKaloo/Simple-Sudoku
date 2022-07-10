package sudoku.problemDomain;

import java.util.Objects;

public class Coordinates {
	private final int x;
	private final int y;
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	
	//this helps store coordiantes objects into a hashmap
	// mainly boilder plate code
	//overroode the equals and hashcode functions so that we can use it later
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false; 
		Coordinates that = (Coordinates) obj;
		
		return x ==that.x &&
				y == that.y;
	}
	
	// take  x and y value to generate a unique identifier for those specific coordinates
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	
}
