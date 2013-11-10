package tdd;

import java.util.HashSet;
import java.util.Set;

public class Elevator {

    private final DoorsDriver doorsDriver;
    private final Engine engine;

    private State state = State.AWAITING;
    private Set<Integer> requestedFloors = new HashSet<Integer>();
    private int currentFloor = 0;

    public Elevator(DoorsDriver doorsDriver, Engine engine) {
        this.doorsDriver = doorsDriver;
        this.engine = engine;
    }

    public enum State {
        AWAITING, GOING_UP, GOING_DOWN
    }

    public int currentFloor() {
        return currentFloor;
    }

    public State state() {
        return state;
    }

    public void pushButton(int requestedFloor) {

        requestedFloors.add(requestedFloor);

        if (requestedFloor == currentFloor()) {
            doorsDriver.openDoors();
        } else {
            doorsDriver.closeDoors();
        }

    }

    public void onDoorsClosed() {

        int destination = requestedFloors.iterator().next();
        
        if (destination > currentFloor()) {
            goingUp();
        } else if (destination < currentFloor()) {
            goingDown();
        }

    }

    private void goingDown() {
        state = State.GOING_DOWN;
        engine.down();
    }

    private void goingUp() {
        state = State.GOING_UP;
        engine.up();
    }

    public void onFloorReached(int reachedFloor) {

        currentFloor = reachedFloor;
        
        if(requestedFloors.contains(reachedFloor)){
            engine.stop();
            doorsDriver.openDoors();
            requestedFloors.remove(reachedFloor);
        }
        
    }

}
