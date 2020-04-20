/**
 * Contains the main logic part of the game, as it processes.
 *
 */
 import java.io.*;

public class GameLogic {

	private Map map;
	private HumanPlayer player;
	private BotPlayer bot;
	private char turn = 'p';

	/**
	 * Default constructor
	 */
	public GameLogic() {
		player = new HumanPlayer();
		bot = new BotPlayer();
		String[] allMaps = getMapNames();
		System.out.println("Please type the number of the map you would like to use (or d for the default map): ");

		for (int i = 0; i < allMaps.length; i++) {
			System.out.println((i+1) + ". " + allMaps[i]);
		}

		while (true) {
			String mapChoice = player.getInputFromConsole();
			System.out.println();
			if (mapChoice.equals("d")) {
				map = new Map();
				break;
			} else {
					try {
						int mapNumber = Integer.parseInt(mapChoice);
						System.out.println(allMaps[mapNumber - 1]);
						map = new Map(allMaps[mapNumber - 1]);
						break;
					} catch (NumberFormatException e) {
						System.out.println("Please enter a number.");
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Invalid number, no map found.");
					}
				}
			}
		}

		/**
	 * Checks if player is in same game place as bot.
	 *
		 * @return if the game is running.
		 */
		private boolean gameRunning() {
			if (player.getPosition()[0] == bot.getPosition()[0] && player.getPosition()[1] == bot.getPosition()[1]){
				return false;
			} else {
				return true;
			}
		}

		/**
		* Read and format map names from directory.
		*
		* @return : List of map names.
		*
		*/
		private String[] getMapNames() {
			File directory = new File("maps/");
			String[] maps = directory.list();
			for (int i = 0; i < maps.length; i++) {
				maps[i] = maps[i].replace(".txt", "");
			}
			return maps;
		}

		/**
	 * Returns the gold required to win.
	 *
		 * @return : Gold required to win.
		 */
		private String hello() {
			String goldToWin = "Gold to win: " + map.getGoldRequired();
				return goldToWin;
		}

	/**
	 * Returns the gold currently owned by the player.
	 *
		 * @return : Gold currently owned.
		 */
		private String gold() {
			String goldOwned = "Gold owned: " + player.getGoldCollected();
			return goldOwned;
		}

		/**
		 * Checks if movement is legal and updates player's location on the map.
		 *
		 * @param direction : The direction of the movement.
		 * @return : Protocol if success or not.
		 */
		private String move(char direction) {
			int posX = 0;
			int posY = 0;
			direction = Character.toLowerCase(direction);

			if (turn == 'p') {
				posX = player.getPosition()[0];
				posY = player.getPosition()[1];
			} else if (turn == 'b') {
				posX = bot.getPosition()[0];
				posY = bot.getPosition()[1];
			}

			if (direction == 'n') {
				if (checkForHash(posX, posY - 1)) {
					return "FAIL";
				} else {
					processMove(direction);
					return "SUCCESS";
				}
			}
			else if (direction == 'e') {
				if (checkForHash(posX + 1, posY)) {
					return "FAIL";
				} else {
					processMove(direction);
					return "SUCCESS";
				}
			}
			else if (direction == 's') {
				if (checkForHash(posX, posY + 1)) {
					return "FAIL";
				} else {
					processMove(direction);
					return "SUCCESS";
				}
			}
			else if (direction == 'w') {
				if (checkForHash(posX - 1, posY)) {
					return "FAIL";
				} else {
					processMove(direction);
					return "SUCCESS";
				}
			}
			else {
				return "INVALID";
			}
		}

		/**
		 * Converts the map from a 2D char array to a single string.
		 *
		 * @return : A String representation of the game map.
		 */
		private String look() {
			char[][] fullMap = map.getMap();
			String playerSurrounding = "";

			if (turn == 'p') {
				for (int i = player.getPosition()[1] - 2; i < player.getPosition()[1] + 3; i++) {
					if (i < 0) {
						i = 0;
					} else if (i >= fullMap.length) {
						break;
					}
					for (int j = player.getPosition()[0] - 2; j < player.getPosition()[0] + 3; j++) {
						if (j < 0) {
							j = 0;
						} else if (j >= fullMap[0].length) {
							continue;
						}
						if (i == player.getPosition()[1] && j == player.getPosition()[0]) {
							playerSurrounding += "P";
						} else if (i == bot.getPosition()[1] && j == bot.getPosition()[0]) {
							playerSurrounding += "B";
						} else {
								playerSurrounding += Character.toString(fullMap[i][j]);
						}
					}
					playerSurrounding += "\n";
				}
			} else {
				for (int i = bot.getPosition()[1] - 2; i < bot.getPosition()[1] + 3; i++) {
					if (i < 0) {
						i = 0;
					} else if (i >= fullMap.length) {
						break;
					}
					for (int j = bot.getPosition()[0] - 2; j < bot.getPosition()[0] + 3; j++) {
						if (j < 0) {
							j = 0;
						} else if (j >= fullMap[0].length) {
							continue;
						}
						if (i == player.getPosition()[1] && j == player.getPosition()[0]) {
							playerSurrounding += "P";
						} else if (i == bot.getPosition()[1] && j == bot.getPosition()[0]) {
							playerSurrounding += "B";
						} else {
								playerSurrounding += Character.toString(fullMap[i][j]);
						}
					}
					playerSurrounding += "\n";
				}
			}
			return playerSurrounding;
		}

		/**
		 * Processes the player's pickup command, updating the map and the player's gold amount.
		 *
		 * @return If the player successfully picked-up gold or not.
		 */
		private String pickup() {
			int posX = player.getPosition()[0];
			int posY = player.getPosition()[1];

			if (map.getMap()[posY][posX] == 'G') {
				player.collectGold();
				map.setToDot(posX, posY);
				return "SUCCESS. " + gold();
			} else {
				return "FAIL. " + gold();
			}
		}

		/**
		 * Quits the game, shutting down the application.
		 */
		private void quitGame() {
			int posX = player.getPosition()[0];
			int posY = player.getPosition()[1];

			if (map.getMap()[posY][posX] == 'E' && player.getGoldCollected() == map.getGoldRequired()) {
				System.out.println("WIN! You have escaped the Dungeon of Doom!");
			} else {
				System.out.println("LOSE!");
			}
			System.exit(1);
		}

		/**
		* Check whether there is a wall being moved into.
		*
		* @param : Coordinates of player/bot.
		* @return : Whether there is a wall.
		*
		*/
		private boolean checkForHash(int x, int y) {
			if (map.getMap()[y][x] == '#') {
				return true;
			} else {
				return false;
			}
		}

		/**
		* Process move based on whether player or bots turn.
		*
		* @param : Direction of move.
		*
		*/
		private void processMove(char direction) {
			switch (turn) {
				case 'p': player.getNextAction(direction);
				case 'b': bot.getNextAction(direction);
			}
		}

		/**
		* Process which command was received.
		*
		* @param : Game command.
		*
		*/
		private void processCommand(String[] command) {
			if (command[0].equals("hello")){
				System.out.println(hello());
			} else if (command[0].equals("gold")){
				System.out.println(gold());
			} else if (command[0].equals("move")) {
				try {
					System.out.println(move(command[1].charAt(0)));
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("INVALID");
				}
			} else if (command[0].equals("look")) {
				System.out.printf(look());
			} else if (command[0].equals("pickup")) {
				System.out.println(pickup());
			} else if (command[0].equals("quit")) {
				quitGame();
			} else {
				System.out.println("INVALID");
			}
		}

		/**
		* Run the game.
		*
		*/
		private void playGame() {
			// Place bot and player on map ensuring they are not on a wall.
			while (true) {
				player.placePlayer(map.getMap()[0].length, map.getMap().length);
				bot.placePlayer(map.getMap()[0].length, map.getMap().length);
				if (!checkForHash(player.getPosition()[0], player.getPosition()[1]) && !checkForHash(bot.getPosition()[0], bot.getPosition()[1])) {
					break;
				}
			}

			// Process player and bots moves one after another.
			while (gameRunning()) {
				turn = 'p';
				System.out.println("Player move: ");
				String input = player.getInputFromConsole();
				String[] command = input.toLowerCase().split("\\s+");
				processCommand(command);
				turn = 'b';
				System.out.println("Bot move: ");
				String[] botTurn = bot.botMove(player.getPosition()[0], player.getPosition()[1]).split("\\s+");
				processCommand(botTurn);
				System.out.println();
			}
			System.out.println("LOSE!");
		}

	public static void main(String[] args) {
		GameLogic logic = new GameLogic();
		logic.playGame();
		}
}
