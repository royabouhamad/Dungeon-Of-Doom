import java.lang.Math;

public class BotPlayer extends HumanPlayer {

  /* How many go's the bot has had. */
  private int botTurn = 0;

  /**
  * Choose whether bot looks or moves.
  *
  * @param : Players coordinates
  * @return : Command to be processed
  *
  */
  protected String botMove(int playerX, int playerY) {
    if ((botTurn % 4) == 0) {
      botTurn++;
      System.out.println("LOOK");
      return "look";
    } else {
      String command = chasePlayer(playerX, playerY);
      botTurn++;
      return command;
    }
  }

  /**
  * Chase player if they are in view otherwise move in a random direction.
  *
  * @param : Player coordinates.
  * @return : Move command.
  *
  */
  protected String chasePlayer(int playerX, int playerY) {
    int botDirection;
    char move;
    String command;
    char[] vertical = {'n', 's'};
    char[] directions = {'n', 'e', 's', 'w'};

    // Check whether player is in view of bot and move accordingly. 
    if ((position[0] - 2 >= playerX && position[0] + 2 <= playerX) || (position[1] -2 >= playerY && position[1] + 2 <= playerY)) {
      if ((botTurn % 2) == 0) {
        if (position[0] - playerX > 0){
          move = 'w';
        } else {
          move = 'e';
        }
      } else {
        if (position[1] - playerY > 0) {
          move = 'n';
        } else {
          move = 's';
        }
      }
    } else {
      botDirection = (int)(Math.random()*3);
      move = directions[botDirection];
    }

    command = "move " + Character.toString(move);
    System.out.println("MOVE " + Character.toString(move));
    return command;
  }
}
