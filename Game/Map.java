/**
 * Reads and contains in memory the map of the game.
 *
 */
 import java.io.*;
 import java.util.*;

public class Map {

	/* Representation of the map */
	private char[][] map;

	/* Map name */
	private String mapName;

	/* Gold required for the human player to win */
	private int goldRequired;

  /* Dimensions of map being used */
	private int[] dimensions = new int[2];

	/**
	 * Default constructor, creates the default map "Very small Labyrinth of doom".
	 */
	public Map() {
		mapName = "Very small Labyrinth of Doom";
		goldRequired = 2;
		map = new char[][]{
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
		};
	}

	/**
	 * Constructor that accepts a map to read in from.
	 *
	 * @param : The filename of the map file.
	 */
	public Map(String fileName) {
		readMap(fileName);
	}

		/**
		 * @return : Gold required to exit the current map.
		 */
		protected int getGoldRequired() {
				return goldRequired;
		}

		/**
		 * @return : The map as stored in memory.
		 */
		protected char[][] getMap() {
				return map;
		}

		/**
		 * @return : The name of the current map.
		 */
		protected String getMapName() {
				return mapName;
		}

		/**
		 * Reads the map from file.
		 *
		 * @param : Name of the map's file.
		 */
		protected void readMap(String fileName) {
			String filePath = "maps/" + fileName + ".txt";
			File newMap = new File(filePath);
			try {
				Scanner reader = new Scanner(newMap);
				String line;
				dimensions = getMapDimensions(newMap);
				map = new char[dimensions[0]][dimensions[1]];
				mapName = reader.nextLine().replaceFirst("name ", "");
				goldRequired = Integer.parseInt(reader.nextLine().replaceFirst("win ", ""));

				for (int i = 0; reader.hasNextLine(); i++) {
					line = reader.nextLine();
					map[i] = line.toCharArray();
				}

				reader.close();
			} catch (FileNotFoundException e) {
				System.out.println("Invalid file name!");
			}
		}

    /**
    * Change game space to a '.'
    *
    * @param : x and y coordinates of game space to change.
    *
    */
		protected void setToDot(int x, int y) {
			map[y][x] = '.';
		}

    /**
    *
    * Get the dimensions of the map.
    *
    * @param : Map to get dimensions of.
    * @return : array containing dimesions of map.
    *
    */
		protected int[] getMapDimensions(File newMap) {
			try {
				Scanner scan = new Scanner(newMap);
				int counter = 0;

				for (int i = 0; i < 2; i++) {
					scan.nextLine();
				}

				dimensions[1] = scan.nextLine().length();

				while (scan.hasNextLine()) {
					counter++;
					scan.nextLine();
				}

				dimensions[0] = counter + 1;
				scan.close();
				return dimensions;
			} catch(FileNotFoundException e) {
				System.out.println("Invalid file name!");
				return null;
			}
		}

}
