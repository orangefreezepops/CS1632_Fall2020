import java.util.*;

enum Item {
	NONE,
	COFFEE,
	CREAM,
	SUGAR
}

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {

	private Player player;
	private ArrayList<Room> rooms;
	private int currentRoom;
	private boolean gameOver;
	
	CoffeeMakerQuestImpl() {
		rooms  = new ArrayList<Room>();
		currentRoom = -1;
		gameOver = false;
	}

	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Set the player to p.
	 * 
	 * @param p the player
	 */
	public void setPlayer(Player p) {
		player = p;
	}
	
	/**
	 * Add the first room in the game. If room is null or if this not the first room
	 * (there are pre-exiting rooms), the room is not added and false is returned.
	 * 
	 * @param room the room to add
	 * @return true if successful, false otherwise
	 */
	public boolean addFirstRoom(Room room) {
		if(room == null || rooms.size() != 0) {
			return false;
		}
		rooms.add(room);
		return true;
	}

	/**
	 * Attach room to the northern-most room. If either room, northDoor, or
	 * southDoor are null, the room is not added. If there are no pre-exiting rooms,
	 * the room is not added. If room is not a unique room (a pre-exiting room has
	 * the same adjective or furnishing), the room is not added. If all these tests
	 * pass, the room is added. Also, the north door of the northern-most room is
	 * labeled northDoor and the south door of the added room is labeled southDoor.
	 * Of course, the north door of the new room is still null because there is
	 * no room to the north of the new room.
	 * 
	 * @param room      the room to add
	 * @param northDoor string to label the north door of the current northern-most room
	 * @param southDoor string to label the south door of the newly added room
	 * @return true if successful, false otherwise
	 */
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor) {
		if(room == null || northDoor == null || southDoor == null || rooms.size() == 0) {
			return false;
		}
		for(Room r : rooms) {
			if(r.getAdjective() == room.getAdjective() || r.getFurnishing() == room.getFurnishing()) {
				return false;
			}
		}
		rooms.get(rooms.size() - 1).setNorthDoor(northDoor);
		room.setSouthDoor(southDoor);
		rooms.add(room);
		return true;
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */ 
	public Room getCurrentRoom() {
		if(currentRoom == -1) {
			return null;
		}
		return rooms.get(currentRoom);
	}
	
	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {
		for(int i = 0; i < rooms.size(); i++) {
			if(rooms.get(i).equals(room)) {
				currentRoom = i;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 */
	public String getInstructionsString() {
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}
	
	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game. Make
     * sure you use Player.getInventoryString() whenever you need to display
     * the inventory.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) {
		if(cmd.equalsIgnoreCase("N")) {
			if(currentRoom < rooms.size() - 1) {
				setCurrentRoom(rooms.get(++currentRoom));
				return "";
			}
			return "You cannot go North\n";
			
		} else if(cmd.equalsIgnoreCase("S")) {
			if(currentRoom > 0) {
				setCurrentRoom(rooms.get(--currentRoom));
				return "";
			}
			return "A door in that direction does not exist.\n";
		} else if(cmd.equalsIgnoreCase("L")) {
			return addItem(rooms.get(currentRoom).getItem());
		} else if(cmd.equalsIgnoreCase("I")) {
			return player.getInventoryString();
		} else if(cmd.equalsIgnoreCase("D")) {
			gameOver = true;
			if(player.checkCoffee() && player.checkCream() && player.checkSugar()) {
				return player.getInventoryString() + "\nYou drink the beverage and are ready to study!\nYou win!\n";
			}
			else if(player.checkCoffee() && player.checkCream()) {
				return player.getInventoryString() + "\nWithout sugar, the coffee is too bitter. You cannot study.\nYou lose!\n";
			}
			else if(player.checkCream() && player.checkSugar()) {
				return player.getInventoryString() + "\nYou drink the sweetened cream, but without caffeine you cannot study.\nYou lose!\n";
			}
			else if(player.checkCoffee() && player.checkSugar()) {
				return player.getInventoryString() + "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";
			}
			else if(player.checkCream()) {
				return player.getInventoryString() + "\nYou drink the cream, but without caffeine, you cannot study.\nYou lose!\n";
			}
			else if(player.checkCoffee()) {
				return player.getInventoryString() + "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";
			}
			else if(player.checkSugar()) {
				return player.getInventoryString() + "\nYou eat the sugar, but without caffeine, you cannot study.\nYou lose!\n";
			} else {
				return player.getInventoryString() + "\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n";
			}
		} else if(cmd.equalsIgnoreCase("H")) {
			return "N - Go north\nS - Go south\nL - Look and collect any items in the room\nI - Show inventory of items collected\nD - Drink coffee made from items in inventory\n";
		} else {
			return "What?\n";
		}
	}
	
	
	/**
	 * Depending on what item is given, it returns a string to tell the player what they found.
	 * @param i the Item that is being looked for
	 * @return a string that corresponds to the item
	 */
	private String addItem(Item i) {
		switch (i) {
		case NONE:
			return "You don't see anything out of the ordinary.\n";
		case COFFEE:
			player.addItem(Item.COFFEE);
			return "There might be something here...\nYou found some caffeinated coffee!\n";
		case CREAM:
			player.addItem(Item.CREAM);
			return "There might be something here...\nYou found some creamy cream!\n";
		case SUGAR:
			player.addItem(Item.SUGAR);
			return "There might be something here...\nYou found some sweet sugar!\n";
		default:
			return "";
		}
	}
	
}
