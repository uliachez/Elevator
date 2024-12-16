import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Elevator implements Runnable {
    private int currentFloor = 0;
    private final PriorityQueue<Integer> floorRequests = new PriorityQueue<>();
    private int targetFloor = 0;
    private boolean movingUp = true;

    public synchronized void moveToFloor(int floor) {
        floorRequests.add(floor); // Добавляем новый запрос в очередь
        notify();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (floorRequests.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            processNextRequest();
        }
    }

    private void processNextRequest() {
        Integer targetFloor;
        synchronized (this) {
            targetFloor = floorRequests.peek();
        }

        if (targetFloor != null) {
            while (true) {
                synchronized (this) {
                    targetFloor = floorRequests.peek();
                    this.targetFloor = targetFloor;
                    if (currentFloor == targetFloor) {
                        System.out.println("Elevator stopping at floor: " + floorRequests.poll());
                        break;
                    }
                }
                if (currentFloor < targetFloor) {
                    currentFloor++;
                    movingUp = true;
                } else {
                    currentFloor--;
                    movingUp = false;
                }

                // Simulate travel time
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public boolean isMoving() {
        return currentFloor < targetFloor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }
}
