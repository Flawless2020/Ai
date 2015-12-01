/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Matthew
 */
/*Main method*/
public class AI {

    private ArrayList<Maze> maze = new ArrayList<>();
    Scanner input = new Scanner(System.in);
    int start;
    int finish;
    Random random = new Random();
    long startTimer;
    long endTimer;

    //TODO make sure that timer is set
    public void mazeRunner() {
        startTimer= System.nanoTime();
        int currentLocation = start;
        int previousLocation = -1;
        ArrayList<Integer> availableMoves = new ArrayList<>();
        ArrayList<Integer> visitedLocation = new ArrayList<>();
        ArrayList<Integer> currentPath = new ArrayList<>();
        ArrayList<Integer> pathTaken = new ArrayList<>();
        System.out.println("Moves:");
        while (currentLocation != finish) {
            System.out.println(currentLocation);
            for (Maze x : maze) {
                if (x.getLeft() == currentLocation || x.getRight() == currentLocation) {
                    pathTaken.add(currentLocation);
                    visitedLocation.add(currentLocation);
                    currentPath.add(currentLocation);
                    availableMoves.clear();
                    availableMoves.addAll(checkLocation(currentLocation));

                    if (availableMoves.contains(finish)) {
                        for (int d : availableMoves) {
                            if (d == finish) {
                                currentPath.add(d);
                                currentLocation = d;
                                pathTaken.add(d);
                            }
                        }
                    } else { 
                        int randomNumber = random.nextInt((availableMoves.size() - 0) + 0);
                        int locationPicked = availableMoves.get(randomNumber);
                        while (locationPicked == currentLocation) {
                            randomNumber = random.nextInt((availableMoves.size() - 0) + 0);
                            locationPicked = availableMoves.get(randomNumber);
                        }
                        if (availableMoves.size() == 1 && previousLocation == locationPicked) {
                            previousLocation = currentLocation;
                            currentLocation = locationPicked;
                            currentPath.remove(currentPath.size() - 1);
                        } else if (!visitedLocation.contains(locationPicked)) {
                            previousLocation = currentLocation;
                            currentLocation = locationPicked;
                        } else if (locationPicked != previousLocation) {
                            previousLocation = currentLocation;
                            currentLocation = locationPicked;
                        }
                    }
                    break;
                }
            }
        }
        endTimer=System.nanoTime();
        System.out.println("Time taken to solve " + (endTimer-startTimer));
        for (int i = 0; i < pathTaken.size(); i++) {
            System.out.println("Path taken Move Number " + i + " is " + pathTaken.get(i));
        }
        for (int i = 0; i < currentPath.size(); i++) {
            System.out.println("Move Number " + i + " is " + currentPath.get(i));
        }
    }
    /*
     Used for purpose of cheacking available moves for current locations
     */

    public ArrayList<Integer> checkLocation(int location) {
        ArrayList<Integer> locations = new ArrayList<>();
        for (Maze x : maze) {
            if (x.getLeft() == location) {
                locations.add(x.getRight());
            }
            if (x.getRight() == location) {
                locations.add(x.getLeft());
            }
        }
        return locations;
    }
    /*
     Used for purpose of getting start and finish locations
     */

    public void startFinish() {
        System.out.println("Provide starting location");
        start = input.nextInt();
        System.out.println("Provide ending location");
        finish = input.nextInt();
    }

    /*
     Takes User input and read the maze file.
     */
    public void readMaze() throws FileNotFoundException, IOException {
        String line = null;
        System.out.println("Provide name of the file");
        String fileName = input.next();
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader
                = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            Maze mazeLocation = new Maze();
            String[] locations = line.split(" ");
            mazeLocation.setLeft(Integer.parseInt(locations[0]));
            mazeLocation.setRight(Integer.parseInt(locations[1]));
            maze.add(mazeLocation);
        }
        bufferedReader.close();
        for (Maze x : maze) {
            System.out.println(x.getLeft() + " " + x.getRight());
        }
    }

    public static void main(String[] args) throws IOException {
        AI ai = new AI();
        ai.readMaze();
        ai.startFinish();
        ai.mazeRunner();
    }
}
