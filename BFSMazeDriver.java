package mazeworld;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BFSMazeDriver {
	
	public static void main(String [] args){
		int [][] maze = randomMaze(5,5);//loadFromFile("maze1.txt");
		printMaze(maze);
		
		int goalX = maze[0].length - 1;
		int goalY = 0;
		
		BFSMazeProblem problem = new BFSMazeProblem(maze, goalX, goalY);
		System.out.println("Path: " + problem.breadthFirstSearch());
		
		
		
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
}
