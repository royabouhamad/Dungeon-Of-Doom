/**
 * Runs the game with a human player and contains code needed to read inputs.
 *
 */
 import java.io.*;
 import java.lang.Math;

public class HumanPlayer {

		/* Gold collected by player */
		private int goldCollected = 0;

		/* player position coordinates */
		protected int[] position = new int[2];

		/**
		 * Reads player's input from the console.
		 * <p>
		 * return : A string containing the input the player entered.
		 */
		protected String getInputFromConsole() {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				String command = reader.readLine();
				return command;
			} catch (IOException e) {
				System.err.println(e.getMessage());
				return null;
			}
		}

		/**
		 * Moves player.
		 *
		 * @param command specifies direction player is to move in.
		 */
		protected void getNextAction(char command) {
			if (command == 'n') {
				position[1]--;
			} else if (command == 'e') {
				position[0]++;
			} else if (command == 's') {
				position[1]++;
			} else if (command == 'w') {
				position[0]--;
			}
		}

		/**
		* Accessor method for goldCollected.
		*
		*/
		protected int getGoldCollected() {
			return goldCollected;
		}

		/**
		* Accessor method for position.
		*
		*/
		protected int[] getPosition() {
			return position;
		}

		/**
		* Place the player in random position on the game board.
		*
		* @param : Dimensions of map.
		* @return : Position coordinates of player.
		*
		*/
		protected int[] placePlayer(int xLength, int yLength) {
			position[0] = (int)(Math.random() * xLength);
			position[1] = (int) (Math.random() * yLength);
			return position;
		}

		/**
		* Increment goldCollected.
		*
		*/
		protected void collectGold() {
			goldCollected++;
		}

}
