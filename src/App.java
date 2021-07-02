import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import models.Car;
import models.Room;

public class App {
    public static void main(String[] args) throws Exception {
        var room = setRoomSize();
        var car = setStartDest();
        runSimulation(car, room);
    }


    /**  Ask user for the room size which should be writed in the format (4 6). 
     * 
     * If it was wrong format it will ask the user again until the user 
     * have writed the correct input.
    */
    private static Room setRoomSize() throws IOException {
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

    /**  Ask user for the starting destionation of the car
     * which should be writed in the format (1 1 N). 
     * 
     * If it was wrong format it will ask the user again until the user 
     * have writed the correct input.
    */
    private static Car setStartDest() throws IOException {
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

    /**  Ask user to write commands and the start the simulation. 
     * 
     * When simulation is done it checks if the car has crashed in to the
     * wall or not.
    */
    private static void runSimulation(Car car, Room room) throws IOException {
        BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Input car simulation commands in series. Available commands are F, B, L or R.");
        String simulationInput = standardInput.readLine();
        char arr[] = simulationInput.toUpperCase().toCharArray(); // convert the String object to array of char
        Car carAfterSimulation = simulationAlgorithm(car, arr);
        checkIfCrash(carAfterSimulation, room);

    }

    /**  Takes in the cars starting destionation and the commands
     * in a char array and runs it through the simulation algorithm. 
     * 
     * After the method has gone through all commands 
     * from the user, the method stops and returns a 
     * Car with the end destination */
    private static Car simulationAlgorithm(Car car, char[] arr) {
        for (char c : arr) {
            String sInput = String.valueOf(c);
            if (car.getDestination().equals("N")) {
                if (sInput.equals("F")) {
                    car.setY(car.getY() + 1);
                }
                if (sInput.equals("B")) {
                    car.setY(car.getY() - 1);
                }
                if (sInput.equals("L")) {
                    car.setDestination("W");
                    continue;
                }
                if (sInput.equals("R")) {
                    car.setDestination("E");
                    continue;
                }
            }
            if (car.getDestination().equals("S")) {
                if (sInput.equals("F")) {
                    car.setY(car.getY() - 1);
                }
                if (sInput.equals("B")) {
                    car.setY(car.getY() + 1);
                }
                if (sInput.equals("L")) {
                    car.setDestination("E");
                    continue;
                }
                if (sInput.equals("R")) {
                    car.setDestination("W");
                    continue;
                }

            }
            if (car.getDestination().equals("E")) {
                if (sInput.equals("F")) {
                    car.setX(car.getX() + 1);
                }
                if (sInput.equals("B")) {
                    car.setX(car.getX() - 1);
                }
                if (sInput.equals("L")) {
                    car.setDestination("N");
                    continue;
                }
                if (sInput.equals("R")) {
                    car.setDestination("S");
                    continue;
                }

            }
            if (car.getDestination().equals("W")) {
                if (sInput.equals("F")) {
                    car.setX(car.getX() - 1);
                }
                if (sInput.equals("B")) {
                    car.setX(car.getX() + 1);
                }
                if (sInput.equals("L")) {
                    car.setDestination("S");
                    continue;
                }
                if (sInput.equals("R")) {
                    car.setDestination("N");
                    continue;
                }

            }

        }
        return car;
    }

    /**  Checks if the car is inside of the room or if it has
     * crashed in to the wall. 
    */
    private static void checkIfCrash(Car car, Room room) {
        if (car.getX() < 0 || car.getY() < 0 || car.getX() > room.getX() || car.getY() > room.getY()) {
            System.out.println("The car did hit the wall!!!");
        } else {
            System.out.println("Success!");
            System.out.println("Your car stopped at:" + "(" + car.getX() + "," + car.getY() + ") With the direction: "
                    + car.getDestination() + " which is inside of the room with size(" + room.getX() + "," + room.getY()
                    + ")");
        }
    }

    private static boolean isNumeric(String str) {
        return str != null && str.matches("[0-9.]+");
    }

    private static boolean isDestination(String str) {
        String[] dest = { "N", "S", "W", "E" };
        return str != null && Arrays.stream(dest).anyMatch(str.toUpperCase()::equals);
    }
}
