import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.*;
import org.mockito.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CoffeeMakerQuestTest {

	CoffeeMakerQuest cmq;
	Player player;
	Room room1;	// Small room
	Room room2;	// Funny room
	Room room3;	// Refinanced room
	Room room4;	// Dumb room
	Room room5;	// Bloodthirsty room
	Room room6;	// Rough room

	@Before
	public void setup() {
		// 0. Turn on bug injection for Player and Room.
		Config.setBuggyPlayer(true);
		Config.setBuggyRoom(true);
		
		// 1. Create the Coffee Maker Quest object and assign to cmq.
		cmq = CoffeeMakerQuest.createInstance();

		// 2. Create a mock Player and assign to player and call cmq.setPlayer(player). 
		// Player should not have any items (no coffee, no cream, no sugar)
		player = Mockito.mock(Player.class);
		cmq.setPlayer(player);
		Mockito.when(player.checkCoffee()).thenReturn(false);
		Mockito.when(player.checkCream()).thenReturn(false);
		Mockito.when(player.checkSugar()).thenReturn(false);

		// 3. Create mock Rooms and assign to room1, room2, ..., room6.
		// Mimic the furnishings / adjectives / items of the rooms in the original Coffee Maker Quest.
		room1 = mock(Room.class);
		Mockito.when(room1.getAdjective()).thenReturn("Small");
		Mockito.when(room1.getFurnishing()).thenReturn("Quaint sofa");
		Mockito.when(room1.getDescription()).thenReturn("You see a Small room.\nIt has a Quaint sofa.\nA Magenta door leads North.");
		Mockito.when(room1.getItem()).thenReturn(Item.CREAM);
		
		room2 = Mockito.mock(Room.class);
		Mockito.when(room2.getAdjective()).thenReturn("Funny");
		Mockito.when(room2.getFurnishing()).thenReturn("Sad record player");
		Mockito.when(room2.getDescription()).thenReturn("You see a Funny room.\nIt has a Sad record player.\nA Beige door leads North. \nA Massive door leads south");
		Mockito.when(room2.getItem()).thenReturn(Item.NONE);
		
		
		room3 = Mockito.mock(Room.class);
		Mockito.when(room3.getAdjective()).thenReturn("Refinanced");
		Mockito.when(room3.getFurnishing()).thenReturn("Tight pizza");
		Mockito.when(room3.getDescription()).thenReturn("You see a Refinanced room.\nIt has a Tight pizza.\nA Dead door leads North. \nA Smart door leads south");
		Mockito.when(room3.getItem()).thenReturn(Item.COFFEE);
		
		
		room4 = Mockito.mock(Room.class);
		Mockito.when(room4.getAdjective()).thenReturn("Dumb");
		Mockito.when(room4.getFurnishing()).thenReturn("Flat energy drink");
		Mockito.when(room4.getDescription()).thenReturn("You see a Dumb room.\nIt has a Flat energy drink.\nA Vivacious door leads North. \nA Slim door leads south");
		Mockito.when(room4.getItem()).thenReturn(Item.NONE);
		
		
		room5 = Mockito.mock(Room.class);
		Mockito.when(room5.getAdjective()).thenReturn("Bloodthirsty");
		Mockito.when(room5.getFurnishing()).thenReturn("Beautiful bag of money");
		Mockito.when(room5.getDescription()).thenReturn("You see a Bloodthirsty room.\nIt has a Beautiful bag of money.\nA Purple door leads North. \nA Sandy door leads south");
		Mockito.when(room5.getItem()).thenReturn(Item.NONE);
		
		
		room6 = Mockito.mock(Room.class);
		Mockito.when(room6.getAdjective()).thenReturn("Rough");
		Mockito.when(room6.getFurnishing()).thenReturn("Perfect air hockey table");
		Mockito.when(room6.getDescription()).thenReturn("You see a Rough room.\nIt has a Perfect air hockey table.\nA Minimalist door leads south");
		Mockito.when(room6.getItem()).thenReturn(Item.SUGAR);
		
		
		// 4. Add the rooms created above to mimic the layout of the original Coffee Maker Quest.
		cmq.addFirstRoom(room1);
		cmq.addRoomAtNorth(room2, "Beige", "Massive");
		cmq.addRoomAtNorth(room3, "Dead", "Smart");
		cmq.addRoomAtNorth(room4, "Vivacious", "Slim");
		cmq.addRoomAtNorth(room5, "Purple", "Sandy");
		cmq.addRoomAtNorth(room6, "", "Minimalist");
		
	}

	@After
	public void tearDown() {
	}
	
	/**
	 * Test case for String getInstructionsString().
	 * Preconditions: None.
	 * Execution steps: Call cmq.getInstructionsString().
	 * Postconditions: Return value is " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 */
	@Test
	public void testGetInstructionsString() {
		Assert.assertEquals(cmq.getInstructionsString(), " INSTRUCTIONS (N,S,L,I,D,H) > ");
	}
	
	/**
	 * Test case for boolean addFirstRoom(Room room).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock room and assign to myRoom.
	 * Execution steps: Call cmq.addFirstRoom(myRoom).
	 * Postconditions: Return value is false.
	 */
	@Test
	public void testAddFirstRoom() {
		//preconditions
		Room myRoom = Mockito.mock(Room.class);
		
		//postconditions
		Assert.assertFalse(cmq.addFirstRoom(myRoom));
	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Fake bed" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is true.
	 *                 room6.setNorthDoor("North") is called.
	 *                 myRoom.setSouthDoor("South") is called.
	 */
	@Test
	public void testAddRoomAtNorthUnique() {
		//precondition
		Room myRoom = Mockito.mock(Room.class);
		Mockito.when(myRoom.getFurnishing()).thenReturn("Fake bed");
		Mockito.when(myRoom.getItem()).thenReturn(null);
		
		//execution
		boolean added = cmq.addRoomAtNorth(myRoom, "North", "South");
		
		//postconditions
		Assert.assertTrue(added);
		Mockito.verify(room6, Mockito.times(1)).setNorthDoor("North");
		Mockito.verify(myRoom, Mockito.times(1)).setSouthDoor("South");
	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Flat energy drink" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is false.
	 *                 room6.setNorthDoor("North") is not called.
	 *                 myRoom.setSouthDoor("South") is not called.
	 */
	@Test
	public void testAddRoomAtNorthDuplicate() {
		//preconditions
		Room myRoom = Mockito.mock(Room.class);
		Mockito.when(myRoom.getFurnishing()).thenReturn("Flat energy drink");
		
		//execution 
		boolean added = cmq.addRoomAtNorth(myRoom, "North", "South");
		
		//postconditions
		Assert.assertFalse(added);
		Mockito.verify(room6, Mockito.times(0)).setNorthDoor("North");
		Mockito.verify(myRoom, Mockito.times(0)).setSouthDoor("South");
	}
	
	/**
	 * Test case for Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room) has not yet been called.
	 * Execution steps: Call cmq.getCurrentRoom().
	 * Postconditions: Return value is null.
	 */
	@Test
	public void testGetCurrentRoom() {
		//preconditions - all square
		
		//execution - all square
		
		//postconditions
		Assert.assertEquals(cmq.getCurrentRoom(), null);
	}
	
	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room room) has not yet been called.
	 * Execution steps: Call cmq.setCurrentRoom(room3).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(room3) is true. 
	 *                 Return value of cmq.getCurrentRoom() is room3.
	 */
	@Test
	public void testSetCurrentRoom() {
		//preconditions - all square
		
		//execution
		boolean setCurRoom = cmq.setCurrentRoom(room3);
		
		//postconditions
		Assert.assertTrue(setCurRoom);
		Assert.assertEquals(cmq.getCurrentRoom(), room3);
	}
	
	/**
	 * Test case for String processCommand("I").
	 * Preconditions: Player does not have any items.
	 * Execution steps: Call cmq.processCommand("I").
	 * Postconditions: Return value is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n".
	 */
	@Test
	public void testProcessCommandI() {
		//preconditions - all square
		
		//execution - all square
		
		//postconditions
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
		Assert.assertEquals(cmq.processCommand("I"), "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
		
	}
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "There might be something here...\nYou found some creamy cream!\n".
	 *                 player.addItem(Item.CREAM) is called.
	 */
	@Test
	public void testProcessCommandLCream() {
		//preconditions
		boolean setCurRoom = cmq.setCurrentRoom(room1);
		
		//execution
		String found = cmq.processCommand("l");
		
		//postconditions
		Assert.assertEquals(found, "There might be something here...\nYou found some creamy cream!\n");
		Mockito.verify(player, Mockito.times(1)).addItem(Item.CREAM);
	}
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room3) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "There might be something here...\nYou found some caffeinated coffee!\n".
	 *                 player.addItem(Item.COFFEE) is called.
	 */
	@Test
	public void testProcessCommandLCoffee() {
		//preconditions
		boolean setCurRoom = cmq.setCurrentRoom(room3);
		
		//execution
		String found = cmq.processCommand("l");
		
		//postconditions
		Assert.assertEquals(found, "There might be something here...\nYou found some caffeinated coffee!\n");
		Mockito.verify(player, Mockito.times(1)).addItem(Item.COFFEE);
	}
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room6) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "There might be something here...\nYou found some sweet sugar!\n".
	 *                 player.addItem(Item.SUGAR) is called.
	 */
	@Test
	public void testProcessCommandLSugar() {
		//preconditions
		boolean setCurRoom = cmq.setCurrentRoom(room6);
		
		//execution
		String found = cmq.processCommand("l");
		
		//postconditions
		Assert.assertEquals(found, "There might be something here...\nYou found some sweet sugar!\n");
		Mockito.verify(player, Mockito.times(1)).addItem(Item.SUGAR);
	}
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room2) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "You don't see anything out of the ordinary.\n".
	 *                 room2.getItem() is called.
	 */
	@Test
	public void testProcessCommandLNothing() {
		//preconditions
		boolean setCurRoom = cmq.setCurrentRoom(room2);
		
		//execution
		String found = cmq.processCommand("l");
		
		//postconditions
		Assert.assertEquals(found, "You don't see anything out of the ordinary.\n");
		Mockito.when(room2.getItem()).thenReturn(Item.NONE);
	}
	
	/**
	 * Test case for String processCommand("n").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room4) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is room5.
	 */
	@Test
	public void testProcessCommandN() {
		//preconditions
		boolean setCurRoom = cmq.setCurrentRoom(room4);
		
		//execution
		String cmd = cmq.processCommand("n");
		Room r = cmq.getCurrentRoom();
		
		//postconditions
		Assert.assertEquals(cmd, "");
		Assert.assertEquals(r, room5);
		
	}
	
	/**
	 * Test case for String processCommand("n").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room6) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 * Postconditions: Return value of cmq.processCommand("n") is "You cannot go North\n".
	 */
	@Test
	public void testProcessCommandNoMoreN() {
		//preconditions
		boolean setCurRoom = cmq.setCurrentRoom(room6);
		
		//execution
		String cmd = cmq.processCommand("n");
		
		//postconditions
		Assert.assertEquals(cmd, "You cannot go North\n");
		
	}
	
	/**
	 * Test case for String processCommand("s").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("s") is "A door in that direction does not exist.\n".
	 *                 Return value of cmq.getCurrentRoom() is room1.
	 */
	@Test
	public void testProcessCommandS() {
		//preconditions
		boolean setCurRoom = cmq.setCurrentRoom(room1);
		
		//execution
		String cmd = cmq.processCommand("s");
		Room r = cmq.getCurrentRoom();
		
		//postconditions
		Assert.assertEquals(cmd, "A door in that direction does not exist.\n");
		Assert.assertEquals(r, room1);
	}
	
	/**
	 * Test case for String processCommand("s").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room2) has been called.
	 * Execution steps: Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("s") is "".
	 *                 Return value of cmq.getCurrentRoom() is room1.
	 */
	@Test
	public void testProcessCommandCanGoS() {
		//preconditions
		boolean setCurRoom = cmq.setCurrentRoom(room2);
		
		//execution
		String cmd = cmq.processCommand("s");
		Room r = cmq.getCurrentRoom();
		
		//postconditions
		Assert.assertEquals(cmd, "");
		Assert.assertEquals(r, room1);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has no items.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDLose() {
		//preconditions
		Mockito.when(player.checkCoffee()).thenReturn(false);
		Mockito.when(player.checkCream()).thenReturn(false);
		Mockito.when(player.checkSugar()).thenReturn(false);
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
		
		//execution
		String cmd = cmq.processCommand("D");
		boolean over = cmq.isGameOver();
		
		//postconditions
		Mockito.verify(player, Mockito.times(1)).getInventoryString();
		Assert.assertEquals(cmd, player.getInventoryString() + "\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n");
		
		Assert.assertTrue(over);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has all 3 items (coffee, cream, sugar).
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDWin() {
		//preconditions
		Mockito.when(player.checkCoffee()).thenReturn(true);
		Mockito.when(player.checkCream()).thenReturn(true);
		Mockito.when(player.checkSugar()).thenReturn(true);
		Mockito.when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n");
		
		//execution
		String cmd = cmq.processCommand("D");
		boolean over = cmq.isGameOver();
		
		//postconditions
		Assert.assertEquals(cmd, player.getInventoryString() + "\nYou drink the beverage and are ready to study!\nYou win!\n");
		Assert.assertTrue(over);
	}
	
	/**
	 * Test case for setCurrentRoom() = false .
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock room and assign to myRoom.
	 * Execution steps: Call cmq.setCurrentRoom(myRoom).
	 * Postconditions: Return value is false.
	 */
	@Test
	public void testSetCurrentRoomFail() {
		//preconditions
		Room myRoom = Mockito.mock(Room.class);
		
		//execution
		boolean setCurRoom = cmq.setCurrentRoom(myRoom);
		
		//postconditions
		Assert.assertFalse(setCurRoom);
	}
	
	/**
	 * Test case for String processCommand("<bad input>").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("q").
	 * Postconditions: Return value of cmq.processCommand("q") is "What?\n".
	 */
	@Test
	public void testProcessBadCommand() {
		//preconditions
		cmq.setCurrentRoom(room1);
		
		//execution
		String cmd = cmq.processCommand("q");
		
		//postconditions
		Assert.assertEquals(cmd, "What?\n");
	}
	
	/**
	 * Test case for String processCommand("H").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("H").
	 * Postconditions: Return value of cmq.processCommand("H") is "N - Go north\nS - Go south\nL - Look and collect any items in the room\nI - Show inventory of items collected\nD - Drink coffee made from items in inventory\n".
	 */
	@Test
	public void testProcessCommandH() {
		//preconditions
		cmq.setCurrentRoom(room1);
		
		//execution
		String cmd = cmq.processCommand("H");
		
		//postconditions
		Assert.assertEquals(cmd, "N - Go north\nS - Go south\nL - Look and collect any items in the room\nI - Show inventory of items collected\nD - Drink coffee made from items in inventory\n");
	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, null, null).
	 * Postconditions: Return value is false.
	 *                 room6.setNorthDoor("North") is not called.
	 *                 myRoom.setSouthDoor("South") is not called.
	 */
	@Test
	public void testAddNullRoomAtNorth() {
		//preconditions
		Room myRoom = Mockito.mock(Room.class);
		
		//execution 
		boolean added = cmq.addRoomAtNorth(myRoom, null, null);
		
		//postconditions
		Assert.assertFalse(added);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has just sugar.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYou have some tasty sugar.\n\nYou eat the sugar, but without caffeine, you cannot study.\nYou lose!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDSugarLose() {
		//preconditions
		Mockito.when(player.checkCoffee()).thenReturn(false);
		Mockito.when(player.checkCream()).thenReturn(false);
		Mockito.when(player.checkSugar()).thenReturn(true);
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYou have some tasty sugar.\n");
		
		//execution
		String cmd = cmq.processCommand("D");
		boolean over = cmq.isGameOver();
		
		//postconditions
		Mockito.verify(player, Mockito.times(1)).getInventoryString();
		Assert.assertEquals(cmd, player.getInventoryString() + "\nYou eat the sugar, but without caffeine, you cannot study.\nYou lose!\n");
		
		Assert.assertTrue(over);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has just cream.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "YOU HAVE NO COFFEE!\nYou have some fresh cream.\nYOU HAVE NO SUGAR!\n\nYou drink the cream, but without caffeine, you cannot study.\nYou lose!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDCreamLose() {
		//preconditions
		Mockito.when(player.checkCoffee()).thenReturn(false);
		Mockito.when(player.checkCream()).thenReturn(true);
		Mockito.when(player.checkSugar()).thenReturn(false);
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYou have some fresh cream.\nYOU HAVE NO SUGAR!\n");
		
		//execution
		String cmd = cmq.processCommand("D");
		boolean over = cmq.isGameOver();
		
		//postconditions
		Mockito.verify(player, Mockito.times(1)).getInventoryString();
		Assert.assertEquals(cmd, player.getInventoryString() + "\nYou drink the cream, but without caffeine, you cannot study.\nYou lose!\n");
		
		Assert.assertTrue(over);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has only coffee.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "You have a cup of delicious coffee.\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDCoffeeLose() {
		//preconditions
		Mockito.when(player.checkCoffee()).thenReturn(true);
		Mockito.when(player.checkCream()).thenReturn(false);
		Mockito.when(player.checkSugar()).thenReturn(false);
		Mockito.when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee.\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
		
		//execution
		String cmd = cmq.processCommand("D");
		boolean over = cmq.isGameOver();
		
		//postconditions
		Mockito.verify(player, Mockito.times(1)).getInventoryString();
		Assert.assertEquals(cmd, player.getInventoryString() + "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n");
		Assert.assertTrue(over);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has coffee and cream.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "You have a cup of delicious coffee.\nYou have some fresh cream.\nYOU HAVE NO SUGAR!\n\nWithout sugar, the coffee is too bitter. You cannot study.\nYou lose!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDCoffeeAndCreamLose() {
		//preconditions
		Mockito.when(player.checkCoffee()).thenReturn(true);
		Mockito.when(player.checkCream()).thenReturn(true);
		Mockito.when(player.checkSugar()).thenReturn(false);
		Mockito.when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee.\nYou have some fresh cream.\nYOU HAVE NO SUGAR!\n");
		
		//execution
		String cmd = cmq.processCommand("D");
		boolean over = cmq.isGameOver();
		
		//postconditions
		Mockito.verify(player, Mockito.times(1)).getInventoryString();
		Assert.assertEquals(cmd, player.getInventoryString() + "\nWithout sugar, the coffee is too bitter. You cannot study.\nYou lose!\n");
		Assert.assertTrue(over);
	}
	
	
	/**
	 * Test case for String addItem(Item i)
	 * Preconditions: Reflect addItem() to allow for public access
	 * Execution steps: Call cmq.addItem(Item.NONE)
	 * Postconditions: Return value of cmq.addItem(Item.NONE) is "You don't see anything out of the ordinary.\n"
	 */
	@Test
	public void testAddItemNone() {
		try {
			//preconditions
			Method privateAddItem = CoffeeMakerQuestImpl.class.getDeclaredMethod("addItem", Item.class);
			privateAddItem.setAccessible(true);
			
			//execution
			String str = (String) privateAddItem.invoke(cmq, Item.NONE);
			
			//postcondition
			assertEquals(str, "You don't see anything out of the ordinary.\n");
			
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}
}
