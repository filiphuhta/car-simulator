import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import model.Car;
import model.Room;

public class Simulator {

/**
 * Get the user input about the room size and the starting
 * destionation of the car.
 * 
 */
    public void startSimulator() throws IOException {
        var room = setRoomSize();
        var car =  setStartDest();
        runSimulation(car, room);
    }

    /**
     * Ask user for the room size which should be writed in the format (4 6).
     * 
     * If it was wrong format it will ask the user again until the user have writed
     * the correct input.
     */
    private Room setRoomSize() throws IOException {
        BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
        String wrongInput = "The input must be written with two integers separated with a space. Example: 4 10";
        boolean isCorrect = false;
        while (!isCorrect) {
            System.out.print("Enter room size:");
            String romeSize = standardInput.readLine();
            Room room = new Room();
            if (romeSize.contains(" ")) {
                var numbers = romeSize.split(" ");
                if (isNumeric(numbers[0]) && isNumeric(numbers[1])) {
                    room.setX(Integer.parseInt(numbers[0]));
                    room.setY(Integer.parseInt(numbers[1]));
                    isCorrect = true;
                } else {
                    System.out.println(wrongInput);
                }

                return room;
            } else {
                System.out.println(wrongInput);
            }
        }

        return null;

    }

    /**
     * Ask user for the starting destionation of the car which should be writed in
     * the format (1 1 N).
     * 
     * If it was wrong format it will ask the user again until the user have writed
     * the correct input.
     */
    private Car setStartDest() throws IOException {
        BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
        String wrongInput = "This input must be two integers and one letter separated with spaces. The letter can be N, S, W or E.";
        boolean isCorrect = false;
        while (!isCorrect) {
            System.out.print("Enter car starting point:");
            String startDestString = standardInput.readLine();
            Car car = new Car();
            if (startDestString.contains(" ")) {
                String[] startDest = startDestString.split(" ");
                if (isNumeric(startDest[0]) && isNumeric(startDest[1]) && isDestination(startDest[2])) {
                    car.setX(Integer.parseInt(startDest[0]));
                    car.setY(Integer.parseInt(startDest[1]));
                    car.setDestination(startDest[2].toUpperCase());
                    isCorrect = true;
                } else {
                    System.out.println(wrongInput);
                }

                return car;
            } else {
                System.out.println(wrongInput);
            }
        }

        return null;

    }

    /**
     * Ask user to write commands and the start the simulation.
     * 
     * When simulation is done it checks if the car has crashed in to the wall or
     * not.
     */
    private void runSimulation(Car car, Room room) throws IOException {
        BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Input car simulation commands in series. Available commands are F, B, L or R.");
        String simulationInput = standardInput.readLine();
        char arr[] = simulationInput.toUpperCase().toCharArray(); // convert the String object to array of char
        Car carAfterSimulation = simulationAlgorithm(car, arr);
        checkIfCrash(carAfterSimulation, room);

    }

    /**
     * Takes in the cars starting destionation and the commands in a char array and
     * runs it through the simulation algorithm.
     * 
     * After the method has gone through all commands from the user, the method
     * stops and returns a Car with the end destination
     */
    private Car simulationAlgorithm(Car car, char[] arr) {
        for (char c : arr) {
            String turn = String.valueOf(c);
            if (turn.equals("L") || turn.equals("R")) {
                car = switchDirection(turn, car);
            }
            if (turn.equals("F") || turn.equals("B")) {
                car = move(turn, car);
            }

        }
        return car;
    }

    /**
     * Checks if the car is inside of the room or if it has crashed in to the wall.
     */
    private void checkIfCrash(Car car, Room room) {
        if (car.getX() < 0 || car.getY() < 0 || car.getX() > room.getX() || car.getY() > room.getY()) {
            System.out.println("Failure!");
            System.out.println("The car did hit the wall in the simulation!");
        } else {
            System.out.println("Success!");
            System.out.println("Your car stopped at:" + "(" + car.getX() + "," + car.getY() + ") With the direction: "
                    + car.getDestination() + " which is inside of the room with size(" + room.getX() + "," + room.getY()
                    + ")");
        }
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("[0-9.]+");
    }

    private boolean isDestination(String str) {
        String[] dest = { "N", "S", "W", "E" };
        return str != null && Arrays.stream(dest).anyMatch(str.toUpperCase()::equals);
    }

    /**
     * 
     * @String input parameter turn user turn which can be F(Forward) & B(Back)
     * @Car input parameter car The current car object with direction and X,Y cordinates.
     * 
     * The function returns Car object with new X,Y cordinates based on which turn 
     * the user have chosen.
     */
    private Car move(String turn, Car car) {
        String direction = car.getDestination();
        switch (direction) {
            case "N":
                if (turn.equals("F")) {
                    car.setY(car.getY() + 1);
                } else {
                    car.setY(car.getY() - 1);
                }
                break;
            case "S":
                if (turn.equals("F")) {
                    car.setY(car.getY() - 1);
                } else {
                    car.setY(car.getY() + 1);
                }
                break;
            case "W":
                if (turn.equals("F")) {
                    car.setX(car.getX() - 1);
                } else {
                    car.setX(car.getX() + 1);
                }
                break;
            case "E":
                if (turn.equals("F")) {
                    car.setX(car.getX() + 1);
                } else {
                    car.setX(car.getX() - 1);
                }
                break;
        }
        car.setDestination(direction);
        return car;

    }

     /**
     * 
     * @String input parameter turn which can be F(Forward) & B(Back)
     * @String input parameter car The current car object with direction and X,Y cordinates.
     * 
     * The function returns Car object with new direction based on the turn the user
     * has chosen.
     * 
     */

    private Car switchDirection(String turn, Car car) {
        String direction = car.getDestination();
        switch (direction) {
            case "N":
                if (turn.equals("L"))
                    direction = "W";
                else
                    direction = "E";
                break;
            case "S":
                if (turn.equals("L"))
                    direction = "E";
                else
                    direction = "W";
                break;
            case "W":
                if (turn.equals("L"))
                    direction = "S";
                else
                    direction = "N";
                break;
            case "E":
                if (turn.equals("L"))
                    direction = "N";
                else
                    direction = "S";
                break;

        }
        car.setDestination(direction);
        return car;
    }
}
