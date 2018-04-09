package mazeworld;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;


public abstract class SearchProblem extends UUSearchProblem {
	
	protected SearchNode startNode;
	int distance;
	
	protected interface SearchNode {
		public int heuristic();
		public int distance();
		public boolean goalTest();
		public ArrayList<SearchNode> getSuccessors();
		public boolean equals(Object other);
		public int hashCode();
		public void updateDistance(int d);
		
		
	}
	private class SearchNodeComparator implements Comparator<SearchNode>{

		@Override
		public int compare(SearchNode o1, SearchNode o2) {
			
			return o1.heuristic() - o2.heuristic() + o1.distance() - o2.distance();
		}
		
	}
	
	
	
	public List<SearchNode> aStarSearch(){
		PriorityQueue<SearchNode> pq = new PriorityQueue<>(20, new SearchNodeComparator());
		
		//backchain to get the path 
		HashMap<SearchNode, SearchNode> backchainMap = new HashMap<>();
		//allows shortest distances to be constantly updated
		HashMap<SearchNode, Integer> distanceFromStart = new HashMap<>(); //Also functions as visited
		
		pq.add(startNode);
		distanceFromStart.put(startNode, 0);
		
		SearchNode goal = null;
		
		frontierLoop:
		while(!pq.isEmpty()){
			SearchNode current = pq.poll();
			if(current.goalTest()){
				goal = current;
				break frontierLoop;
			} else {

				for(SearchNode successor : current.getSuccessors()){
					int newDist = distanceFromStart.get(current) + 1; //successor is one away from current
					
					if(backchainMap.get(successor) == null || 
							distanceFromStart.get(successor) > newDist){
						
						distanceFromStart.put(successor, newDist);
						successor.updateDistance(newDist);
						backchainMap.put(successor, current);
						pq.add((SearchNode)successor);
					}
				}
				
			}
			
		}
		return goal == null ? null : backchain(goal, backchainMap);
	}


	

	private  List<SearchNode> backchain(SearchNode goal,HashMap<SearchNode, SearchNode> backchainMap) {
		if(goal == null) return null;
		//System.out.println("exi");
		ArrayList<SearchNode> list = new ArrayList<>();
		SearchNode curr = goal;
		
		list.add(curr);

		while(!curr.equals(startNode)){
			//System.out.println(list);
			curr = backchainMap.get(curr);
			list.add(0, curr);
		}
		
		return list;
	}
		
}
