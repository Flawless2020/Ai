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
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    //TODO make sure that timer is set
    public void mazeRunner() {
        
        int currentLocation = start;
        ArrayList<Integer> availableMoves = new ArrayList<>();
        ArrayList<Integer> visitedLocation = new ArrayList<>();
        ArrayList<Integer> currentPath = new ArrayList<>();
        //start the timer here
        /*while loop will run until currentLocation is not same as finish*/
        while (currentLocation != finish) {
            /*for loop which will check every block of array list for currentLocation*/
            for (Maze x : maze) {
                if (x.getLeft() == currentLocation) {
                    visitedLocation.add(currentLocation);//adds currentLocation to visitedLocation array
                    currentPath.add(currentLocation);//updateds current path
                    availableMoves.clear();//clears available moves to ensure that only current location's moves are available
                    availableMoves.addAll(checkLocation(currentLocation));//adds current location's available moves
                    
                    if(availableMoves.contains(finish)){//checks if finish was reached
                        currentPath.add(finish);
                    }else if(availableMoves.isEmpty()){//checks if there are no more moves available, and moves back one room backwards
                        currentPath.remove(currentPath.size());
                        currentLocation=currentPath.get(currentPath.size());
                    }else if(!availableMoves.isEmpty()){//checks if there are available moves, and checks if these moves have been used previously 
                        for(int y : availableMoves){
                            if(!visitedLocation.contains(y)){
                                currentLocation=y;
                            }
                        }
                    }
                }
            }
        }
        //stop the timer here
        for(int i = 0;i<currentPath.size();i++){
            System.out.println("Move Number "+i+" is "+currentPath.get(i));
        }
    }
    public int randomPick(int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max - 0)) + 0;

    return randomNum;
    }
    /*
    Used for purpose of cheacking available moves for current locations
    */
    public ArrayList<Integer> checkLocation(int location){
        ArrayList<Integer> locations=new ArrayList<>();
        for (Maze x : maze) {
            if(x.getLeft()==location){
                locations.add(x.getRight());
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
