/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

 class MazeSolver<Vertex> {
	
	static String mazeFile;	
	static int verticesPoints;
	static long startTime;
	static long endTime;
	static BufferedReader  bufferedFileReader = new BufferedReader(new InputStreamReader(System.in));
	
		
	 static void dfs(boolean[][] adjacentMatrix, int vertex, int destination, int mode) {	
		    boolean[] visited = new boolean[verticesPoints]; // creating boolean array for visited vertices
		    ArrayList<Integer> path = new ArrayList<Integer>();	// create arrayList for paths		  
		    dfsRecursive(adjacentMatrix, visited, vertex, destination, path, mode);		    
		}			

	
	// implementing depth first search in recursive mode
	static int pathNumber;
	static void dfsRecursive(boolean[][] adjacentMatrix, boolean[] visited, int vertex, 
			int destination, ArrayList<Integer> path, int mode) {

		// keep visiting connected vertices
		visited[vertex] = true;
	    path.add(vertex);	   
	    if (vertex == destination && mode == 1){
	    	System.out.print("Path -->>  " + path + " "); 	
	    	
	    }
	    else if (vertex == destination && mode == 2) {
	    	printPath(path);
	    }
	    else {
	    	//continue seraching for more connected vertices
			for(int neighbour = 0; neighbour < verticesPoints; neighbour++){			
				// continue visiting more connected nodes
				if(adjacentMatrix[vertex][neighbour] && !visited[neighbour]) {									
					//recursive function to look at connected vertices
					dfsRecursive(adjacentMatrix, visited, neighbour, destination, path, mode);						
				}
			}
	    }	       
	    path.remove(path.size() - 1);	
	    
	    /** If Mode 1 was chosen only the 1st possible path is to be printed	**\
				 		/** If Mode 2 was selected, then print all the possible paths  **/	
	    if(mode == 1){    
	    	visited[vertex] = true;
	    }else if(mode == 2){
	    	visited[vertex] = false;
	    }
	   
	}
				
	static String chooseAFile() throws IOException{
		String mazeFile = null;		
		System.out.println("Please Input Name of the Maze File:");						
		mazeFile = bufferedFileReader.readLine() + ".txt";
		
		return mazeFile;
	}
	
	static int chooseMode() throws NumberFormatException, IOException {
		int userOption = 0;
		System.out.print("\n[1] = Mode 1");
		System.out.print("\n[2] = Mode 2");	
		System.out.print("\nCHOOSE AN OPTION : ");						
		userOption = Integer.parseInt(bufferedFileReader.readLine());			
							
		return userOption;
	}
	
	static int getVerticesSize() throws NumberFormatException, IOException{
		int countBeforeSpace, firstReadNumber, secondReadNumber;
		int maxValue1 = Integer.MIN_VALUE;
        int maxValue2 = Integer.MIN_VALUE;       
        String input = "";			        
	
		
			bufferedFileReader = new BufferedReader(new FileReader(mazeFile));		
			while((input = bufferedFileReader.readLine()) != null){
				// index before space
				countBeforeSpace = input.indexOf(" "); 
				// number(s) before space
				firstReadNumber = Integer.parseInt(input.substring(0, countBeforeSpace)); 
				// number(s) after the space
				secondReadNumber = Integer.parseInt(input.substring(countBeforeSpace + 1)); 
				
				if (maxValue1 < firstReadNumber) {
					maxValue1 = firstReadNumber;
			    }		            
			    if (maxValue2 < secondReadNumber) {
			        maxValue2 = secondReadNumber;
			    }		            
			    verticesPoints = Math.max(maxValue1, maxValue2);
			 }
							
		verticesPoints = (verticesPoints + 1);	// vertices points = (1st number + 1)
		bufferedFileReader.close();		
		return verticesPoints;
	}
		
	static boolean[][] createGraph() throws NumberFormatException, IOException{		
	boolean[][] graph = new boolean[verticesPoints][verticesPoints];// Initialising  2D array size (matrix)
	String input = "";	
	int edgeStart;
	int edgeEnd;		

		bufferedFileReader = new BufferedReader(new FileReader(mazeFile));		
		while((input = bufferedFileReader.readLine()) != null){
			int countBeforeSpace = input.indexOf(" "); // index before space
			edgeStart = Integer.parseInt(input.substring(0, countBeforeSpace)); // number before space
			edgeEnd = Integer.parseInt(input.substring(countBeforeSpace + 1)); // number after the space (rest of it)
			graph[edgeStart][edgeEnd] = graph[edgeEnd][edgeStart] = true;	//Connecting edges										   											 							 
		}			
	return graph;
}
		
	//
	static void printPath(ArrayList<Integer> path){
		pathNumber++;
    	System.out.print("Path [" + pathNumber + "] = ");
        for (int pathPointer = 0; pathPointer < path.size(); pathPointer++) {
            System.out.print(path.get(pathPointer) + ", ");
        }
        System.out.println("");
	}
	
	
	// constructing the Main method 
	public static void main(String[] args) throws NumberFormatException, IOException{
				mazeFile = chooseAFile();			//user will input name of the Text File		
		int mode = chooseMode();			//user chooses a mode		
		startTime = System.nanoTime();		//Starting timer	 point
		verticesPoints = getVerticesSize();	//Vertices size
		int startPoint = 0;
		int endPoint = 1;	
		
		boolean[][] adjacentMatrix = createGraph();		
		dfs(adjacentMatrix, startPoint, endPoint, mode);		
		endTime = System.nanoTime();   			
    	long timeElapsed = (endTime - startTime);
		double nanoSeconds = (double)timeElapsed/1000000000.0;
		System.out.print("\nFinished in  "+ timeElapsed +" Nano Seconds: " );
		System.out.print("\nFinished in  "+ nanoSeconds +" Seconds: " );
		
		System.out.println();
		System.out.println("\nFile Name: "+ mazeFile);
		System.out.println("Nodes: " + verticesPoints);
		
		if(mode == 1){			
			System.out.println("You have selected Mode1 and there is a path between " + startPoint + " and " + endPoint);		
		}if (mode ==2){
			System.out.println("You have selected Mode2 and there are paths between " + startPoint + " and " + endPoint);	
		}
	}	
}
