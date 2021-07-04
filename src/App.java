
public class App {
    public static void main(String[] args) throws Exception {
        Simulator simulator = new Simulator();
        var room = simulator.setRoomSize();
        var car = simulator.setStartDest();
        simulator.runSimulation(car, room);
    }

}
