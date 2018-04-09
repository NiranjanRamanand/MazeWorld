package mazeworld;

import java.util.ArrayList;
import java.util.Arrays;

public class BFSMazeProblem extends UUSearchProblem {

	int [] [] maze = null;
	
	int maxX;
	int maxY;
	
	int goalX;
	int goalY;
	
	
	
	public BFSMazeProblem(int [][] maze, int goalX, int goalY) {
		startNode = new BFSMazeNode(0, 0);
		this.maze = maze;
		maxX = maze[0].length - 1;
		maxY = maze.length - 1;
		
		this.goalX = goalX;
		this.goalY = goalY;
		
	}
	
	private class BFSMazeNode implements UUSearchNode {
		
		int x;
		int y;
		
		public BFSMazeNode(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		@Override
		public ArrayList<UUSearchNode> getSuccessors() {
			ArrayList<UUSearchNode> successors = new ArrayList<>();
			
			//Check which adj nodes aren't obstacles or maze boundaries
			if(x < maxX && maze[y][x + 1] != 1) //right node
				successors.add(new BFSMazeNode(x + 1, y));
			
			if(x > 0 && maze[y][x - 1] != 1) //left node
				successors.add(new BFSMazeNode(x - 1, y));
			
			if(y < maxY && maze[y + 1][x] != 1) //top node
				successors.add(new BFSMazeNode(x, y+1));
			
			if(y > 0 && maze[y - 1][x] != 1) //bottom node
				successors.add(new BFSMazeNode(x, y - 1));
			
			return successors;
		}
		
		
		@Override
		public boolean goalTest() {
			return x == goalX && y == goalY;
		}
		@Override
		public int hashCode() {
			return x * 100 + y;
		}
		
		@Override
		public int getDepth() {
			return 0;
		}
		
		@Override
		public boolean equals(Object other) {
			return x == ((BFSMazeNode) other).x && y == ((BFSMazeNode) other).y;
		}


		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}
}
