
import java.util.Random;

public class RequestGenerator implements Runnable {
    private final ControlSystem controlSystem;
    private final Random random = new Random();
    private final int numberOfFloors;

    public RequestGenerator(ControlSystem system, int floors) {
        this.controlSystem = system;
        this.numberOfFloors = floors;
    }

    @Override
    public void run() {
        //while (true) {
        //    int floor = random.nextInt(numberOfFloors);
        //    controlSystem.requestElevator(floor);
            //try {
            //    Thread.sleep(10000); // Generate requests every 10 seconds
            //} catch (InterruptedException e) {
            //    Thread.currentThread().interrupt();
            //    break;
            //}
        //}
    }
}
