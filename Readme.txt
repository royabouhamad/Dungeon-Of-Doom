Hello and welcome to the Dungeon of Doom Game!!

--------------
Installation
--------------

To install the game, enter the 'Game' directory in terminal and run 'javac GameLogic.java', 
followed by 'java GameLogic' to run. New maps can be created as '.txt' files. Map file structure can be seen below.

--------------
Maps
--------------

Maps must be layed out in a specific format. An example can be seen below:

name Small Labyrinth of Dooom
win 3
##########################
#........................#
#.....###........###.....#
#......G..........G......#
#........................#
#...........E............#
#......G.........G.......#
#........G.....G.........#
#..........###...........#
#........................#
##########################

Key:
# - Wall
G - Gold
P - Player
E - Exit
B - Bot

-------------
How to play
-------------

The aim of the game is to escape the Dungeon of Doom with enough gold to exit. The player
can roam around the game board and pickup gold, however there is a bot in the Dungeon as well.
If the bot is on the same space as the player the player instantly loses. A list of commands can be
seen below:

HELLO - Tells player how much gold they must collect.
LOOK - Shows the player a 5x5 grid of the board around them.
GOLD - Tells player how much golf they own.
QUIT - Exits game.
PICKUP - Picks up gold.
