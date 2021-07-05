import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import model.Car;
import model.Room;

public class Simulator {

    private boolean isCrashed = false;
    private Car car = new Car();
    private Room room = new Room();

    /**
     * Get the user input about the room size and the starting destionation of the
     * car.
     * 
     */
    public void startSimulator() throws IOException {
        setRoomSize();
        setStartDest();
        runSimulation();
    }

    /**
     * Ask user for the room size which should be writed in the format (4 6).
     * 
     * If it was wrong format it will ask the user again until the user have writed
     * the correct input.
     */
    private void setRoomSize() throws IOException {
        BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
        String wrongInput = "The input must be written with two integers separated with a space. Example: 4 10";
        boolean isCorrect = false;
        while (!isCorrect) {
            System.out.print("Enter room size:");
            String romeSize = standardInput.readLine();
            if (romeSize.contains(" ")) {
                var numbers = romeSize.split(" ");
                if (isNumeric(numbers[0]) && isNumeric(numbers[1])) {
                    room.setX(Integer.parseInt(numbers[0]));
                    room.setY(Integer.parseInt(numbers[1]));
                    isCorrect = true;
                } else {
                    System.out.println(wrongInput);
                }
            } else {
                System.out.println(wrongInput);
            }
        }

    }

    /**
     * Ask user for the starting destionation of the car which should be writed in
     * the format (1 1 N).
     * 
     * If it was wrong format it will ask the user again until the user have writed
     * the correct input.
     */
    private void setStartDest() throws IOException {
        BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
        String wrongInput = "This input must be two integers and one letter separated with spaces. The letter can be N, S, W or E.";
        boolean isCorrect = false;
        while (!isCorrect) {
            System.out.print("Enter car starting point:");
            String startDestString = standardInput.readLine();
            if (startDestString.contains(" ")) {
                String[] startDest = startDestString.split(" ");
                if (startDest.length == 3 && isNumeric(startDest[0]) && isNumeric(startDest[1])
                        && isDestination(startDest[2])) {
                    car.setX(Integer.parseInt(startDest[0]));
                    car.setY(Integer.parseInt(startDest[1]));
                    car.setDirection(startDest[2].toUpperCase());
                    isCorrect = true;
                } else {
                    System.out.println(wrongInput);
                }

            } else {
                System.out.println(wrongInput);
            }
        }

    }

    /**
     * Ask user to write commands and the start the simulation.
     * 
     * When simulation is done it prints if the car has crashed in to the wall or
     * not.
     */
    private void runSimulation() throws IOException {
        BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Input car simulation commands in series. Available commands are F, B, L or R: ");
        String simulationInput = standardInput.readLine();
        char arr[] = simulationInput.toUpperCase().toCharArray();
        simulationAlgorithm(arr);
        printResult();

    }

    private void simulationAlgorithm(char[] arr) {
        for (char c : arr) {
            String turn = String.valueOf(c);
            if (!isCrashed) {
                if (turn.equals("L") || turn.equals("R")) {
                    switchDirection(turn);
                }
                if (turn.equals("F") || turn.equals("B")) {
                    move(turn);
                }
            }

        }
    }

    /**
     * Checks if the car is inside of the room or if it has crashed in to the wall.
     */
    private void printResult() {
        if (isCrashed) {
            System.out.println("Failure!");
            System.out.println("The car did hit the wall in the simulation at: " + "(" + car.getX() + "," + car.getY()
                    + ") With the direction: " + car.getDirection() + "!");
        } else {
            System.out.println("Success!");
            System.out.println("Your car stopped at:" + "(" + car.getX() + "," + car.getY() + ") With the direction: "
                    + car.getDirection() + " which is inside of the room with size(" + room.getX() + "," + room.getY()
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
     * Uses the current direction to chose which directon the car should mode.
     */
    private void move(String turn) {
        String direction = car.getDirection();
        switch (direction) {
            case "N":
                if (turn.equals("F") && car.getY() != room.getY()) {
                    car.setY(car.getY() + 1);
                } else if (turn.equals("B") && car.getY() != 0) {
                    car.setY(car.getY() - 1);
                } else {
                    isCrashed = true;
                }
                break;
            case "S":
                if (turn.equals("F") && car.getY() != 0) {

                    car.setY(car.getY() - 1);
                } else if (turn.equals("B") && car.getY() != room.getY()) {
                    car.setY(car.getY() + 1);
                } else {
                    isCrashed = true;
                }
                break;
            case "W":
                if (turn.equals("F") && car.getX() != 0) {
                    car.setX(car.getX() - 1);
                } else if (turn.equals("B") && car.getX() != 0) {
                    car.setX(car.getX() - 1);
                } else {
                    isCrashed = true;
                }
                break;
            case "E":
                if (car.getX() != room.getX()) {
                    car.setX(car.getX() + 1);
                    break;
                } else if (turn.equals("B") && car.getX() != 0) {
                    car.setX(car.getX() - 1);
                } else {
                    isCrashed = true;
                }
                break;
        }

    }

    /**
     * 
     * Uses the current direction to chose which directon the car should rotate to.
     */

    private void switchDirection(String turn) {
        String direction = car.getDirection();
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
        car.setDirection(direction);
    }
}
