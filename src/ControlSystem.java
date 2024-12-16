
import java.util.List;
import java.util.ArrayList;

public class ControlSystem {
    private final List<Elevator> elevators = new ArrayList<>();

    public ControlSystem(int numElevators) {
        for (int i = 0; i < numElevators; i++) {
            Elevator elevator = new Elevator();
            elevators.add(elevator);
            new Thread(elevator).start();
        }
    }

    public void requestElevator(int floor) {
        Elevator bestElevator = findBestElevator(floor);
        bestElevator.moveToFloor(floor);
        System.out.println(floor);
    }

    private Elevator findBestElevator(int requestedFloor) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        int id = 0, selectedId = -1;
        for (Elevator elevator : elevators) {
            int currentFloor = elevator.getCurrentFloor();
            int distance = Math.abs(currentFloor - requestedFloor);

            // Consider elevators that are not in motion or moving towards the requested floor
            if (distance < minDistance || bestElevator == null) {
                if (currentFloor == elevator.getTargetFloor() || // elevator is idle
                        (elevator.isMovingUp() && requestedFloor >= currentFloor) ||
                                (!elevator.isMovingUp() && requestedFloor <= currentFloor)
                ) {
                    bestElevator = elevator;
                    minDistance = distance;
                    selectedId = id;
                }
            }
            id++;
        }

        System.out.println("Selected elevator is " + selectedId + " to move to " + requestedFloor + " floor");

        return bestElevator;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }
}
