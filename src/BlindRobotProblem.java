package mazeworld;

import java.util.ArrayList;
import java.util.Arrays;

public class BlindRobotProblem extends SearchProblem {
	int [][] maze;
	int [] goal;
	
	public BlindRobotProblem(int [][] maze, int [] goal){
		this.maze = maze;
		this.goal = goal;
		
		int [] [] startConfig = new int [maze.length][maze[0].length];
		
		for(int i = 0; i < startConfig.length; i++){
			for(int j = 0; j < startConfig[0].length; j++){
				if(maze[i][j] == 0){
					startConfig[i][j] = 0;
				} else {
					startConfig[i][j] = 1;
				}
			}
		}
		
		startNode = new BlindRobotNode(startConfig, "Start");
		
	}
	
	protected class BlindRobotNode implements SearchNode{
		int [][] beliefState;
		String from;
		public BlindRobotNode(int [][] beliefState, String from){
			this.from = from;
			this.beliefState = beliefState;
		}
		
		@Override
		public int heuristic() { 
			int max = 0;
			int sum = 0;
			int numPossiblePositions = 0;
			
			FromTheBottom:
			for(int i = 0; i < beliefState.length; i++){
				sum = 0;//
				for(int j = 0; j < beliefState[0].length; j++){
					if(beliefState[i][j] == 0)
						sum++;
				}
				if(sum > max || sum > 0){ 
					if(sum > max) //going in this direction gets rid of more positions
						max = sum;
					break FromTheBottom; //found the bottom-most row with a possible position
										// and so no need to continue through other rows
				}
			}
				
			FromTheTop:
			for(int i = beliefState.length - 1; i > 0; i--){
				sum = 0;
				for(int j = 0; j < beliefState[0].length; j++){
					if(beliefState[i][j] == 0)
						sum++;
				}
				if(sum > max || sum > 0){
					if(sum > max)
						max = sum;
					break FromTheTop;
				}
			}
			
			FromTheLeft:
			for(int i = 0; i < beliefState[0].length; i++){
				sum = 0;
				for(int j = 0; j < beliefState.length; j++){
					if(beliefState[j][i] == 0)
						sum++;
				}
				if(sum > max || sum > 0){
					if(sum > max)
						max = sum;
					break FromTheLeft;
				}
			}
			
			FromTheRight:
				for(int i = beliefState[0].length - 1; i > 0; i--){
					sum = 0;
					for(int j = 0; j < beliefState.length; j++){
						if(beliefState[j][i] == 0)
							sum++;
					}
					if(sum > max || sum > 0){
						if(sum > max)
							max = sum;
						break FromTheRight;
					}
				}
			
			for(int i = 0; i < beliefState.length; i++){
				for(int j = 0; j < beliefState[0].length; j++){
					if(beliefState[i][j] == 0)
						numPossiblePositions++;
				}
			}
			
			return numPossiblePositions - sum;
		}

		@Override
		public int distance() {
			
			return 0;
		}

		@Override
		public boolean goalTest() {
			int sum = 0;
			
			for(int i = 0; i < beliefState.length; i++){
				for(int j = 0; j < beliefState[0].length; j++){
					if(beliefState[i][j] == 0){
						if(goal[0] != j || goal[1] != i)
							return false;
						sum++;
					}
					if(sum > 1)
						return false;
				}
			}

			return true;
		}

		@Override
		public ArrayList<SearchNode> getSuccessors() {
			ArrayList<SearchNode> successors = new ArrayList<>();
			
			int maxX = beliefState[0].length - 1;
			int maxY = beliefState.length - 1;
			
			
			int [] [] nextBeliefState = new int [beliefState.length][beliefState[0].length];
			//moving right
			for(int i = 0; i <= maxY; i++){
				for(int j = 0; j <= maxX; j++){
					if(beliefState[i][j] == 0 && (j == maxX ||  maze[i][j + 1] == 1)){
						nextBeliefState[i][j] = 0;
					} else {
						if(j > 0 && beliefState[i][j - 1] == 0 && maze[i][j] != 1){ 
							nextBeliefState[i][j] = 0;
						} else if(j == maxX && beliefState[i][j] == 0){
							nextBeliefState[i][j] = 0;
						} else {
							nextBeliefState[i][j] = 1;
						}
					}
				}
			}
			
			if(!Arrays.deepEquals(nextBeliefState, beliefState))
				successors.add(new BlindRobotNode(nextBeliefState, "Right"));
			
			
			nextBeliefState = new int [beliefState.length][beliefState[0].length];
			//moving left
			for(int i = 0; i <= maxY; i++){
				for(int j = 0; j <= maxX; j++){
					if(beliefState[i][j] == 0 && (j == 0 ||  maze[i][j - 1] == 1)){
						nextBeliefState[i][j] = 0;
					} else {
						if(j < maxX && beliefState[i][j + 1] == 0 && maze[i][j] != 1){ 
							nextBeliefState[i][j] = 0;
						} else if(j == 0 && beliefState[i][j] == 0){
							nextBeliefState[i][j] = 0;
						} else {
							nextBeliefState[i][j] = 1;
						}
					}
				}
			}
			
			if(!Arrays.deepEquals(nextBeliefState, beliefState))
				successors.add(new BlindRobotNode(nextBeliefState, "Left"));
			
			
			
			nextBeliefState = new int [beliefState.length][beliefState[0].length];
			//moving up
			for(int i = 0; i <= maxY; i++){
				for(int j = 0; j <= maxX; j++){
					if(beliefState[i][j] == 0 && (i == maxY ||  maze[i + 1][j] == 1)){
						nextBeliefState[i][j] = 0;
					} else {
						if(i > 0 && beliefState[i - 1][j] == 0 && maze[i][j] != 1){ 
							nextBeliefState[i][j] = 0;
						} else if(i == maxX && beliefState[i][j] == 0){
							nextBeliefState[i][j] = 0;
						} else {
							nextBeliefState[i][j] = 1;
						}
					}
				}
			}
			
			if(!Arrays.deepEquals(nextBeliefState, beliefState))
				successors.add(new BlindRobotNode(nextBeliefState, "Up"));
			
			
			nextBeliefState = new int [beliefState.length][beliefState[0].length];
			//moving down
			for(int i = 0; i <= maxY; i++){
				for(int j = 0; j <= maxX; j++){
					if(beliefState[i][j] == 0 && (i == 0 ||  maze[i - 1][j] == 1)){
						nextBeliefState[i][j] = 0;
					} else {
						if(i < maxY && beliefState[i + 1][j] == 0 && maze[i][j] != 1){ 
							nextBeliefState[i][j] = 0;
						} else if(i == 0 && beliefState[i][j] == 0){
							nextBeliefState[i][j] = 0;
						} else {
							nextBeliefState[i][j] = 1;
						}
					}
				}
			}
			
			if(!Arrays.deepEquals(nextBeliefState, beliefState))
				successors.add(new BlindRobotNode(nextBeliefState, "Down"));
			
			
			return successors;
		}

		@Override
		public void updateDistance(int d) {
			
		}
		
		@Override
		public String toString(){
			String s = from + " ";
			return s;
			
		}
		
		public int [] [] getBeliefState(){
			return beliefState;
		}
		
		@Override
		public boolean equals(Object other) {
			//System.out.println(currCoords.hashCode());
			//System.out.println( ((MultiRobotNode) other).currCoords.toString().hashCode());
			
			int [] [] arr = ((BlindRobotNode) other).beliefState;
			
			if(arr.length != beliefState.length)
				return false;
			
			if(((BlindRobotNode) other).from != from)
				return false;
			
			for(int i = 0; i < beliefState.length; i++){
				for(int j = 0; j < beliefState.length; j++){
					if(arr[i][j] != beliefState[i][j])
						return false;
				}
			}
			
			return true;
		//return Arrays.deepEquals(currCoords, ((MultiRobotNode) other).currCoords) && currTurn == ((MultiRobotNode) other).currTurn;
		}
		
		@Override
		public int hashCode(){
			int hash = 0;
			
			for(int i = 0; i < beliefState.length; i++){
				for(int j = 0; j < beliefState[0].length; j++){
					hash *= (hash + 1) * (i + j + 1) * (beliefState[i][j] + 1);
					hash = hash % 1000000;
				}
			}
			return hash;
		}
		
	}
}
