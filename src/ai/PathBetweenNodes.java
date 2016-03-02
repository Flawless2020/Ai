package ai;

/**
 * This is to import java library used in the class
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
/** this is the class created to solve the mazes **/
public class PathBetweenNodes {

    Scanner input = new Scanner(System.in); // a scanner object is created to get the input file from the user
    PathBetweenNodes graph;      //creating an instance of the class PathBetweenNodes 
    private static String START;   // declaring the start node as private variable
    private static String END;     // declaring the end node as private variable
    private static boolean flag;   //declaring private boolean variable flag 
    private Map<String, LinkedHashSet<String>> map = new HashMap<String, LinkedHashSet<String>>();  // creating a private instance of HashMap()
    public void addEdge(String node1, String node2) {  // declaring connection between two nodes

        LinkedHashSet<String> adjacent = map.get(node1);
// checking to see if adjacent node exist or otherwise is null 
        if (adjacent == null) {
            adjacent = new LinkedHashSet<String>();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);   // if exist we add the instance node to adjacency list
    }
/** this method will make pairwise connection between nodes **/
    public void addTwoWayVertex(String node1, String node2) {
        addEdge(node1, node2);  
        addEdge(node2, node1);
    }    // declares that node1 is connected to node 2 and vice versa

 /** creating a boolean method to see if the adjacent node exist or otherwise is null **/
    public boolean isConnected(String node1, String node2) {
    	
        Set<String> adjacent = map.get(node1);
        if (adjacent == null) {
            return false; // boolean method should return a value; if adjacent node is null then the method return false
        }
        return adjacent.contains(node2); // if there exist adjacent node then returns adjacency list contains node2 
    }
  
 /** creating an instance of adjacent node called: Last **/
    public LinkedList<String> adjacentNodes(String last) {
    	
        LinkedHashSet<String> adjacent = map.get(last);
        if (adjacent == null) {
            return new LinkedList<String>();
        }
        return new LinkedList<String>(adjacent);// add adjacent to the list if it's not null

    }

    /** Implementing the breadth first search **/
    private void breadthFirst(PathBetweenNodes graph, LinkedList<String> visited) {

        LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());
        for (String node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.equals(END)) {// if statement to see if node is equal to the destination node
                visited.add(node);// add node to visited array
                printPath(visited);// Calling printPath method to print the array of visited
                flag = true;
                visited.removeLast();
                break;
            }
        }

        for (String node : nodes) { // implementing a for loop to call each node in the array nodes
            if (visited.contains(node) || node.equals(END)) { // if statement to see if node is already visited or it is the end node
                continue;
            }
            visited.addLast(node); //adding the last node to visited array
            breadthFirst(graph, visited); // implementing the breath first search
            visited.removeLast(); // removing the last node from array visited
        }
        if (flag == false) {
            System.out.println("No path Exists between " + START + " and " + END);
            flag = true;
        }
    }
  /** if a path exist between start and end nodes then it will be printed by the following code **/
    private void printPath(LinkedList<String> visited) {

        if (flag == false) {
            System.out.println("Yes there exists a path between " + START + " and " + END);
        }
        for (String node : visited) { //creating for loop to print the nodes stored in visited array 
            System.out.print(node);
            System.out.print(" ");
        }
        System.out.println();
    }
  /** this is the maze reader that reads the maze from the file it's been stored to **/
    public void readMaze() throws FileNotFoundException, IOException {
    	
        String line = null; // initialising the value of the line to null
        System.out.println("Provide name of the file");// asking user to input the name of the maze file in question
        String fileName = input.next();
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            String[] locations = line.split(" ");
            graph.addTwoWayVertex(locations[0], locations[1]);// making pairwise connection between two vertices
        }
        bufferedReader.close(); // BufferedReader must be closed after reading the file is implemented.
    }
    /** The following is the main method **/
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	
    	//Creating an instance object of the class PathBetweenNodes
    	PathBetweenNodes x = new PathBetweenNodes();
        x.graph = new PathBetweenNodes();
        x.readMaze(); // reading the maze from the file
        LinkedList<String> visited = new LinkedList<String>(); // creating an array called visited to store visited nodes
        System.out.println("Enter the source node:");//asking user to enter the start node
        Scanner sc = new Scanner(System.in); // Scanner to input the start node into the system
        START = sc.next();
        System.out.println("Enter the destination node:");// asking user to input the destination node
        END = sc.next();  //scan the destination node into the system
        visited.add(START);// adding the start node to array visited
        new PathBetweenNodes().breadthFirst(x.graph, visited); // implementing the breath first search for graph x and array visited
        sc.close(); // scanner must be closed
    }
}
