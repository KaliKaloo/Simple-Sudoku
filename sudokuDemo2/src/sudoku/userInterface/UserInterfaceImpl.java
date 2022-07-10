package sudoku.userInterface;

import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sudoku.constants.GameState;
import sudoku.problemDomain.Coordinates;
import sudoku.problemDomain.SudokuGame;

public class UserInterfaceImpl implements IUserInterfaceContract.View, EventHandler<KeyEvent>{

	//stage = window bg for the application,  Group = like a <div> in html
	private final Stage stage;
	private final Group root;
	
	//how to keep track of 81 different text fields
	private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;
	
	private IUserInterfaceContract.EventListener listener;
	
	//style stuff that could be put into a different file if it were more complex
	private static final double WINDOW_Y = 732;
	private static final double WINDOW_X = 668;
	private static final double BOARD_PADDING = 50;
	private static final double BOARD_X_AND_Y = 576;
	
	private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 150, 136);
	private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
	private static final String SUDOKU = "Sudoku";
	
	
	//constructor jumps into helper method
	public UserInterfaceImpl(Stage stage) {
		super();
		this.stage = stage;
		this.root = new Group();
		this.textFieldCoordinates = new HashMap<>();
		initializeUserInterface();
	}
	
	//helper method calls other helper method for each user interface feature and then shows it to user
	private void initializeUserInterface() {
		// TODO Auto-generated method stub
		drawBackground(root);
		drawTitle(root);
		drawSudokuBoard(root);
		drawTextFields(root);
		drawGridLines(root);
		stage.show();
	}
	 
	//outer darker turqoise colour
	private void drawBackground(Group root2) {
		// TODO Auto-generated method stub
		Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
		scene.setFill(WINDOW_BACKGROUND_COLOR);
		stage.setScene(scene);
	}

	private void drawTitle(Group root2) {
		// TODO Auto-generated method stub
		Text title = new Text(235, 690, SUDOKU);
		title.setFill(Color.WHITE);
		Font titleFont = new Font(43);
		title.setFont(titleFont);
		root.getChildren().addAll(title);
	}
	
	//lighter turqoise color
	private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);
        boardBackground.setFill(BOARD_BACKGROUND_COLOR);
        root.getChildren().add(boardBackground);
    }

	private void drawTextFields(Group root2) {
		// TODO Auto-generated method stub
		final int xOrigin = 50;
		final int yOrigin = 50;
		
		//change in x and y values
		final int xAndYDelta = 64;
		
		//O(n^2) runtime complexity
		for(int xIndex = 0; xIndex < 9; xIndex++) {
			for(int yIndex = 0; yIndex < 9; yIndex++) {
				int x = xOrigin + xIndex * xAndYDelta;
				int y = yOrigin + yIndex * xAndYDelta;
				
				SudokuTextField tile = new SudokuTextField(xIndex, yIndex);
				
				styleSudokutile(tile, x, y);
				
				tile.setOnKeyPressed(this);
				//how to listen to user input
				
				//everytime we draw a text feild, add it to hashmap. use coordinates object as the key. 
				//when use put function, cooridnates is key, text field reference is the value.
				textFieldCoordinates.put(new Coordinates(xIndex, yIndex), tile);
				
				root.getChildren().add(tile);
			}
		}
		
	}

	//method responsible for styling a Sudoku tiles/board
	private void styleSudokutile(SudokuTextField tile, double x, double  y) {
		// TODO Auto-generated method stub
		Font numberFont = new Font(32);
		tile.setFont(numberFont);
		tile.setAlignment(Pos.CENTER);
		
		tile.setLayoutX(x);
		tile.setLayoutY(y);
		tile.setPrefHeight(64);
		tile.setPrefWidth(64);
		
		tile.setBackground(Background.EMPTY); //TRANSPARENT
		
	}

	private void drawGridLines(Group root2) {
		// draw the grid lines
		//some of the lines are thicker than others to separate the 9 3x3 squares
		int xAndY = 114;
		int index = 0;
		while(index < 8) {
			int thickness;
			if(index == 2 || index == 5) {
				thickness = 3;
			}else {
				thickness = 2;
			}
			
			//use rectangle from javafx to draw the lines
			Rectangle verticleLine = getLine(
					xAndY + 64 * index,
					BOARD_PADDING,
					BOARD_X_AND_Y,
					thickness
			);
			
			Rectangle horizontalLine = getLine(
					BOARD_PADDING,
					xAndY + 64 * index,
					thickness,
					BOARD_X_AND_Y
			);
			
			//how to add the ui elements to the group (div)
			root.getChildren().addAll(
					verticleLine,
					horizontalLine
				);
			index++; 
		}
		
	}
	
	//function to draw lines (rectangle)
	private Rectangle getLine(double boardPadding, 
							double y, 
							double height, 
							double boardXAndY) {
		Rectangle line = new Rectangle();
		line.setX(boardPadding);
		line.setY(y);
		line.setHeight(height);
		line.setWidth(boardXAndY);
		
		line.setFill(Color.BLACK);
		return line;
	}

	@Override
	public void setListener(IUserInterfaceContract.EventListener listener) {
		// TODO Auto-generated method stub
		//build logic will assign control logic class to this view through the interface
		this.listener = listener;
		
	}

	@Override
	public void updateSquare(int x, int y, int input) {
		// method called when updating a single square after the user has inputed a number
		//only update the ui elements that need to be updated rather than the whole board all at once
		
		//get coordinates from the hashmap
		SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x,y));
		
		//turn user input into a string
		String value = Integer.toString(
				input
			);
		
		if(value.equals("0")) value = "";
		
		//set user text input number from 0-9
		tile.textProperty().setValue(value);
	}

	@Override
	public void updateBoard(SudokuGame game) {
		// update the entire board
		// occurs when the user finishes the game, starts a new game
		for(int xIndex = 0;xIndex < 9; xIndex++) {
			for(int yIndex = 0; yIndex <9; yIndex++) {
				// for each text field, grab it from the hashmap by making a new coordinates object using the x and y values
				//remember the keys for the hashmap are the coordinates
				TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));
				
				//get immtutable copy of the game state
				String value = Integer.toString(
							game.getCopyGridState()[xIndex][yIndex]
						);
				
				//if value equals 0, set text field to be empty. 
				if(value.equals("0")) value ="";
				
				tile.setText(value);
				
				//when new game is created
				if(game.getGameState()== GameState.NEW) {
					//if we have an empty square, enable the text field and set it to a slightly less opacity font
					// in quotes is a bit of css
					if(value.equals("")) {
						tile.setStyle("-fx-opacity: 1;");
						tile.setDisable(false);
					} else {
						//if number is already in a tile, set disable to true so users cannot change it
						// also set the opacity to be higher
						tile.setStyle("-fx-opacity: 0.8");
						tile.setDisable(true);
					}
				}
				
			
			}
		}
	}

	@Override
	public void showDialog(String message) {
		// this will get called when the logic center of the game indicates that the game is completed properly.
		// then show this alert dialog which will ask the user if they wanna play again.
		Alert dialog  = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
		//wait for user input
		dialog.showAndWait();
		
		// once user has clicked on dialog, we tell the listener. 
		if(dialog.getResult() == ButtonType.OK) listener.onDialogClick();
	}
	

	@Override
	public void showError(String message) {
		// if any kind of error occurs, show it to the user
		Alert dialog  = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
		//wait for user input
		dialog.showAndWait();
		
	}
	
	//comes from the event handler which is the javafx interface
	// if user enters a number in text box, that event will pop up here.
	//check event type to see if it was a key that was pressed
	//don't care about mouse clicks
	@Override
	public void handle(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getEventType() == KeyEvent.KEY_PRESSED) {
			//better to use regex than to have a huge stack of OR's
			if(event.getText().matches("[0-9]")){
				//value is whatever the user has entered
				int value = Integer.parseInt(event.getText());
				
				//source is the variable/uiElement/object that was clicked on. will get passed as a Java object 
				handleInput(value, event.getSource());
				
			}else if(event.getCode() == KeyCode.BACK_SPACE){
				//check for backspace
				//treat it like a 0
				handleInput(0, event.getSource());
				
			} else {
				// if the user types anything else, just set the square to empty.
				((TextField) event.getSource()).setText("");
			}
		}
		//once event is consumed it wont propagate through the rest of the application
		event.consume();
	}

	private void handleInput(int value, Object source) {
		// once the input occurs, pass the input to the control logic class which is the listner
		// says that at this x and y coordiates, this value was inputted
		listener.onSudokuInput(
				((SudokuTextField) source).getX(),
				((SudokuTextField) source).getY(),
				value
			);
		
	}
	

}
