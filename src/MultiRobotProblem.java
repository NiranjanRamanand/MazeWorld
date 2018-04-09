package mazeworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MultiRobotProblem extends SearchProblem{
	int [][] startCoords;
	int [][] maze;
	int maxX;
	int maxY;
	
	int [][] goals;
	
	int currentTurn = 0;
	
	public MultiRobotProblem(int[][] startCoords, int [][] maze, int [][] goals){
		startNode = new MultiRobotNode(arrayCopy(startCoords), 0);
		this.startCoords = startCoords;
		this.maze = maze;
		this.goals = goals;
		
		maxY = maze.length - 1;
		maxX = maze[0].length - 1;
	}
	
	private int [] [] arrayCopy(int [][] arr){
		int [][] copy = new int[arr.length][];
		
		for(int i = 0; i < arr.length; i++){
			copy[i] = Arrays.copyOf(arr[i], arr[i].length);
		}
		return copy;
	}
	
	protected class MultiRobotNode implements SearchNode{
		int [][] currCoords;
		int currTurn;
		int distance;
		
		private MultiRobotNode(int [][] currCoords, int currTurn){
			this.currCoords = currCoords;
			this.currTurn = currTurn;
		}

		@Override
		public ArrayList<SearchNode> getSuccessors() {

			ArrayList<SearchNode> successors = new ArrayList<>();
			int nextTurn = (currTurn + 1) % currCoords.length;
			
			int x = currCoords[currTurn][0];
			int y = currCoords[currTurn][1];
			int [][] arr = null;
			
			if(x < maxX && maze[y][x + 1] != 1){ //right node
				if(!hasRobot(x + 1, y)){
					arr = arrayCopy(currCoords);
					
					arr[currTurn][0] = x + 1;
					arr[currTurn][1] = y;
					successors.add(new MultiRobotNode(arr, nextTurn));
				}
			}
			
			if(x > 0 && maze[y][x - 1] != 1){ //left node
				if(!hasRobot(x - 1, y)){
					
					arr = arrayCopy(currCoords);
					arr[currTurn][0] = x - 1;
					arr[currTurn][1] = y;
					successors.add(new MultiRobotNode(arr, nextTurn));
				}
			}
			if(y < maxY && maze[y + 1][x] != 1){ //top node
				if(!hasRobot(x, y + 1)){
					arr = arrayCopy(currCoords);
					arr[currTurn][0] = x;
					arr[currTurn][1] = y + 1;
					successors.add(new MultiRobotNode(arr, nextTurn));
				}
			}
			if(y > 0 && maze[y - 1][x] != 1){ //bottom node
				if(!hasRobot(x, y - 1)){
					arr = arrayCopy(currCoords);
					arr[currTurn][0] = x;
					arr[currTurn][1] = y - 1;
					successors.add(new MultiRobotNode(arr, nextTurn));
				}
			}
			
			successors.add(new MultiRobotNode(arrayCopy(currCoords), nextTurn)); //make no move
			
			return successors;
		}

		private boolean hasRobot(int x, int y){
			for(int i = 0; i < currCoords.length; i++){
				if(currCoords[i][0] == x && currCoords[i][1] == y){
					return true;
				}
			}
			
			return false;
		}
		

		@Override
		public boolean goalTest() {
			return Arrays.deepEquals(currCoords, goals);
		}

		public int [][] getCoords(){
			return currCoords;
		}

		@Override
		public int heuristic() {
			int sum = 0;
			
			for(int i = 0; i < currCoords.length; i++){
				sum += (Math.abs(currCoords[i][0] - goals[i][0]) + Math.abs(currCoords[i][1] - goals[i][1]));
			}
			
			return sum;
		}
		
		public void updateDistance(int d) {
			distance = d;
		}
		
		public int distance(){
			return distance;
		}
		@Override
		public boolean equals(Object other) {
			//System.out.println(currCoords.hashCode());
			//System.out.println( ((MultiRobotNode) other).currCoords.toString().hashCode());
			
			int [] [] arr = ((MultiRobotNode) other).currCoords;
			
			if(arr.length != currCoords.length)
				return false;
			
			if(((MultiRobotNode) other).currTurn != currTurn)
				return false;
			
			for(int i = 0; i < currCoords.length; i++){
				if(arr[i][0] != currCoords[i][0] || arr[i][1] != currCoords[i][1])
					return false;
			}
			
			return true;
		//return Arrays.deepEquals(currCoords, ((MultiRobotNode) other).currCoords) && currTurn == ((MultiRobotNode) other).currTurn;
		}
		
		@Override
		public int hashCode(){
			int hash = 0;
			
			for(int i = 0; i < currCoords.length; i++){
				hash *= (currCoords[i][0] + currCoords[i][1] + 10*currCoords[i][0] * currCoords[i][1] + 100*currTurn + 1);
				hash = hash % 1000000;
			}
			return hash;
		}
		
		@Override
		public String toString() {
			String s = "[(" + currCoords[0][0]+ ", " + currCoords[0][1]+ ")";
			for(int i = 1; i < currCoords.length; i++){
				s += ", (" + currCoords[i][0]+ ", " + currCoords[i][1]+ ")";
			}
			s += ", Current Turn = " + currTurn + "]";
			
			
			return s;
		}
	}
}
