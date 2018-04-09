package mazeworld;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mazeworld.MultiRobotProblem.MultiRobotNode;
import mazeworld.SearchProblem.SearchNode;

public class MultiRobotDriver {
	/*
	 * Valid mazes to try:
	 * Maze1: [5x7], suggest {{0,0}, {1, 0},{2,0}} start and {{1, 4}, {2, 4}, {1,3}} goal
	 * Maze2: [7x10], this one has lots of corridors. 
	 * Maze3: [40x40], suggest going from {{0,0}} to {{39,39}}, this maze was randomly constructed, ~30% wall
	 * Maze4: [20x20], suggest going from {{0,0},{1,0}} to {{19,19},{18,18}}, this maze was randomly constructed, ~30% wall
	 * Maze5: [50,50], suggest going from {{0,0}} to {{49,49}}, this maze was randomly constructed, ~30% wall
	 * Or try randomMaze(int, int)
	 */
	
	
	public static void main(String [] args){
		int [][] start = {{0,0}};
		int [][] goal = {{39,39}};
		int [][] maze = loadFromFile("maze3.txt");
		
		boolean pathMayExist = true;
		
		
		
		check://A quick check against ...(A* with single robots is fast)
		for(int i = 0; i < start.length; i++){
			int [][] oneRobot = {start[i]};
			int [][] oneGoal = {goal[i]};
			
			if(new MultiRobotProblem(oneRobot, maze, oneGoal).aStarSearch() == null){
				pathMayExist = false;
				break check;
			}
		}
		
		if(pathMayExist){
			System.out.println("Path exists; Calculating...");
			printMaze(maze);
			MultiRobotProblem mrp = new MultiRobotProblem(start, maze, goal);
			List<SearchNode> path =  mrp.aStarSearch();
			
			animation(maze, path);
			System.out.println(path);
		} else {
			System.out.println("No path exists");
		}
		
	}
	
	public static int [][] loadFromFile(String file){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String currentLine = null;
			ArrayList<String> mazeRows = new ArrayList<String>();
			int mazeLength = -1;
			
			while((currentLine = reader.readLine()) != null){
				currentLine = currentLine.replace(" ", "").trim();
				if(mazeLength != -1 && currentLine.length() != mazeLength){
					System.err.println("Maze dimensions must be consistent");
					return null;
				}
				
				if (mazeLength == -1){
					if(currentLine.length() > 0) {
						mazeLength = currentLine.length();
					} else {
						System.err.println("Cannot have maze rows with no elements");
						return null;
					}
				}
				
				if(currentLine.replace("#", "").replaceAll(".", "").length() == 0){
					mazeRows.add(0, currentLine);
				} else {
					System.err.println("All maze rows must have only #'s or .'s");
					return null;
				}
				
			}
			
			if(!mazeRows.isEmpty())
				return toBinaryArray(mazeRows);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		return null;
	}
	
	private static int [][] toBinaryArray(ArrayList<String> rows) {
		int [][] arr = new int [rows.size()][rows.get(0).length()];
		for(int i = 0; i < rows.size(); i++){
			for(int j = 0; j < rows.get(0).length(); j++){
				//1 => Obstacle, 0 => Valid Spot
				arr[i][j] = (rows.get(i).charAt(j) == '#') ? 1 : 0;
			}
		}
		
		return arr;
	}

	private static int [][] randomMaze(int width, int height){
		int [][] arr = new int [width][height];
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				arr[i][j] = Math.random() < 0.3 ? 1 : 0;
			}
		}
		return arr;
	}

	private static void printMaze(int [][] mazeArray){
		System.out.println();
		for(int i = mazeArray.length - 1; i >= 0; i--){
			for(int j = 0; j < mazeArray[0].length; j++){
				if(mazeArray[i][j] == 0){
					System.out.print(". ");
				} else {
					System.out.print("# ");
				}
			}
			System.out.println();
		}
	}
	
	private static void printMaze(String [][] mazeArray){
		System.out.println();
		for(int i = mazeArray.length - 1; i >= 0; i--){
			for(int j = 0; j < mazeArray[0].length; j++){
				System.out.print(mazeArray[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static void animation(int [][] maze, List<SearchNode> path){
		String [] [] strMaze;
		int numRobots = ((MultiRobotNode)path.get(0)).getCoords().length;
		
		for(int step = 0; step < path.size(); step++){
			strMaze = new String [maze.length][maze[0].length];
			
			for(int i = 0; i < numRobots; i++){
				int x = ((MultiRobotNode)path.get(step)).getCoords()[i][0];
				int y = ((MultiRobotNode)path.get(step)).getCoords()[i][1];
				
				strMaze[y][x] = Integer.toString(i + 1);
			}
			
			for(int i = 0; i < maze.length; i++){
				for(int j = 0; j < maze[0].length; j++){
					if(strMaze[i][j] == null){
						if(maze[i][j] == 0){
							strMaze[i][j] = ".";
						} else {
							strMaze[i][j] = "#";
						}
					}
						
				}
			}
			
			printMaze(strMaze);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	 
}
