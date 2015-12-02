package ai;

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

public class PathBetweenNodes {

    Scanner input = new Scanner(System.in);
    PathBetweenNodes graph;
    private static String START;
    private static String END;
    private static boolean flag;
    private Map<String, LinkedHashSet<String>> map = new HashMap();

    public void addEdge(String node1, String node2) {

        LinkedHashSet<String> adjacent = map.get(node1);

        if (adjacent == null) {
            adjacent = new LinkedHashSet();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);
    }

    public void addTwoWayVertex(String node1, String node2) {
        addEdge(node1, node2);
        addEdge(node2, node1);
    }

    public boolean isConnected(String node1, String node2) {

        Set adjacent = map.get(node1);
        if (adjacent == null) {
            return false;
        }
        return adjacent.contains(node2);
    }

    public LinkedList<String> adjacentNodes(String last) {

        LinkedHashSet<String> adjacent = map.get(last);
        if (adjacent == null) {
            return new LinkedList();
        }
        return new LinkedList<String>(adjacent);

    }

    public void readMaze() throws FileNotFoundException, IOException {
        String line = null;
        System.out.println("Provide name of the file");
        String fileName = input.next();
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader
                = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            String[] locations = line.split(" ");
            graph.addTwoWayVertex(locations[0], locations[1]);
        }
        bufferedReader.close();
    }

    private void breadthFirst(PathBetweenNodes graph,
            LinkedList<String> visited) {

        LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());
        for (String node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.equals(END)) {
                visited.add(node);
                printPath(visited);
                flag = true;
                visited.removeLast();
                break;
            }
        }

        for (String node : nodes) {
            if (visited.contains(node) || node.equals(END)) {
                continue;
            }
            visited.addLast(node);
            breadthFirst(graph, visited);
            visited.removeLast();
        }
        if (flag == false) {
            System.out.println("No path Exists between " + START + " and " + END);
            flag = true;
        }
    }

    private void printPath(LinkedList<String> visited) {

        if (flag == false) {
            System.out.println("Yes there exists a path between " + START + " and " + END);
        }
        for (String node : visited) {
            System.out.print(node);
            System.out.print(" ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        PathBetweenNodes x = new PathBetweenNodes();

        x.graph = new PathBetweenNodes();
        x.readMaze();
        LinkedList<String> visited = new LinkedList();
        System.out.println("Enter the source node:");
        Scanner sc = new Scanner(System.in);
        START = sc.next();
        System.out.println("Enter the destination node:");
        END = sc.next();
        visited.add(START);
        new PathBetweenNodes().breadthFirst(x.graph, visited);
        sc.close();
    }
}
