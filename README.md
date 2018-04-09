# MazeWorld
There are 3 driver classes, all both similar and different

Class Specific:
The drivers house all the main methods.

In BFSMazeDriver, the search will always start from the bottom left and attempt to go to the goalX and goalY specified, unless it cannot (which happens often in randomly spawned mazes)

In MultiRobotDriver, one must define the start/goal/and maze 2d arrays. There are quite a few suggestions for these from previous tests included within the class.

In BlindRobotDriver, only the goal array and maze array need be specified. Some suggestions are included. One strict constraint is that the maze cannot have more than 1 connected component.


General:
One can generate n-by-k mazes with randomMaze(n,k). Alternatively, one can enter a maze in a .txt file in a similar fashion to those already included.

Also, access to the files may be computer specific, but just make sure that you're in the proper working directory to access the files and make your own files.

Hope you have fun!
