package pt314.blocks.game;

import static org.junit.Assert.*;

import org.junit.Test;

import pt314.blocks.gui.SimpleGUI;

public class GameBoardTest {

	@Test(expected= IllegalArgumentException.class) 
	public void testGameBoard() {
		GameBoard gameBoard = new GameBoard(0, 0);
	}
	
	@Test
	public void testMoveBlockInvalidDirection() {
		
		GameBoard gameBoard = new GameBoard(5, 5);
		Block horizontalBlock1 = new HorizontalBlock();
		Block verticalBlock1 = new VerticalBlock();
		Block targetBlock = new TargetBlock();
		
		gameBoard.placeBlockAt(horizontalBlock1, 0, 0);
		assertFalse(gameBoard.moveBlock(0,0,Direction.UP,1)); //check for the wrong direction of horizontal block
		assertFalse(gameBoard.moveBlock(0,0,Direction.DOWN,1)); //check for the wrong direction of horizontal block
		assertTrue(gameBoard.moveBlock(0,0,Direction.RIGHT,1));
		assertTrue(gameBoard.moveBlock(0,1,Direction.RIGHT,2));
		assertTrue(gameBoard.moveBlock(0,3,Direction.LEFT,2)); //check the move to the left 
		gameBoard.placeBlockAt(verticalBlock1, 0, 3);         
		assertFalse(gameBoard.moveBlock(0,3,Direction.LEFT,1)); //check for the wrong direction of vertical block
		assertFalse(gameBoard.moveBlock(0,3,Direction.RIGHT,1)); //check for the wrong direction of vertical block
		assertTrue(gameBoard.moveBlock(0,3,Direction.DOWN,1)); //check for the correct direction of vertical block
		assertTrue(gameBoard.moveBlock(1,3,Direction.DOWN,2)); // check for the move with distance higher than 1
		assertTrue(gameBoard.moveBlock(3,3,Direction.UP,1));  //check for the correct direction of vertical block
		gameBoard.placeBlockAt(targetBlock, 2, 0);
		assertFalse(gameBoard.moveBlock(2,0,Direction.UP,1)); //check for target block out of bounds condition
		assertFalse(gameBoard.moveBlock(2,0,Direction.DOWN,3));
		assertTrue(gameBoard.moveBlock(2,0,Direction.RIGHT,1));
	}
	
	@Test
	public void testMoveBlockOutOfBounds() {
		
		GameBoard gameBoard = new GameBoard(5, 5);
		Block horizontalBlock1 = new HorizontalBlock();
		Block verticalBlock1 = new VerticalBlock();
		Block targetBlock = new TargetBlock();
		
		gameBoard.placeBlockAt(horizontalBlock1, 0, 0);
		assertFalse(gameBoard.moveBlock(0,0,Direction.LEFT,1)); //check for out of bounds
		assertTrue(gameBoard.moveBlock(0,0,Direction.RIGHT,1));
		assertTrue(gameBoard.moveBlock(0,1,Direction.RIGHT,2));
		assertFalse(gameBoard.moveBlock(0,3,Direction.RIGHT,2));//check for out of bounds
		assertTrue(gameBoard.moveBlock(0,3,Direction.LEFT,2)); //check the move to the left 
		
		gameBoard.placeBlockAt(verticalBlock1, 0, 3);  
		
		assertFalse(gameBoard.moveBlock(0,3,Direction.UP,1));
		assertTrue(gameBoard.moveBlock(0,3,Direction.DOWN,1));   //check for the correct direction of vertical block
		assertTrue(gameBoard.moveBlock(1,3,Direction.DOWN,2));  // check for the move with distance higher than 1
		assertFalse(gameBoard.moveBlock(3,3,Direction.DOWN,2)); // check for the out of bounds of vertical block
		assertTrue(gameBoard.moveBlock(3,3,Direction.UP,2));  //check for the correct direction of vertical block
		
		gameBoard.placeBlockAt(targetBlock, 2, 0);
		assertFalse(gameBoard.moveBlock(2,0,Direction.LEFT,1)); //check for target block out of bounds condition
		assertTrue(gameBoard.moveBlock(2,0,Direction.RIGHT,3));
		assertFalse(gameBoard.moveBlock(2,3,Direction.RIGHT,3));
	}
	
	@Test
	public void testMoveBlockAnotherBlockOnWay() {
		
		GameBoard gameBoard = new GameBoard(5, 5);
		Block horizontalBlock1 = new HorizontalBlock();
		Block verticalBlock1 = new VerticalBlock();
		Block targetBlock = new TargetBlock();
		
		gameBoard.placeBlockAt(horizontalBlock1, 0, 0);
		assertTrue(gameBoard.moveBlock(0,0,Direction.RIGHT,1));
		assertTrue(gameBoard.moveBlock(0,1,Direction.RIGHT,1));
		gameBoard.placeBlockAt(verticalBlock1, 0, 3); 
		assertFalse(gameBoard.moveBlock(0,2,Direction.RIGHT,1)); //check the move of a horizontal block for a vertical block on the way
		gameBoard.placeBlockAt(horizontalBlock1, 0, 1);
		assertFalse(gameBoard.moveBlock(0,2,Direction.LEFT,1));//check the move of a horizontal block for another horizontal block on the way
		gameBoard.placeBlockAt(verticalBlock1, 2, 3); 
		assertFalse(gameBoard.moveBlock(0,3,Direction.DOWN,2)); //check the move of a vertical block for a vertical block on the way
		assertFalse(gameBoard.moveBlock(0,3,Direction.DOWN,3));
		gameBoard.placeBlockAt(horizontalBlock1, 1, 3);
		assertFalse(gameBoard.moveBlock(0,3,Direction.DOWN,1));//check the move of a vertical block for a horizontal block on the way
		assertFalse(gameBoard.moveBlock(2,3,Direction.UP,1));
		gameBoard.placeBlockAt(targetBlock, 2, 0);
		assertTrue(gameBoard.moveBlock(2,0,Direction.RIGHT,1));
		assertFalse(gameBoard.moveBlock(2,1,Direction.RIGHT,2));//check the move of a target block with vertical block on its way
		gameBoard.placeBlockAt(horizontalBlock1, 2, 0);
		assertFalse(gameBoard.moveBlock(2,1,Direction.LEFT,1));//check the move of the target block with horizontal block on the way
	}

}
