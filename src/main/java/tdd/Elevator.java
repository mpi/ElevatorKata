package tdd;

import java.util.ArrayList;
import java.util.List;

public class Elevator {

    private final DoorsDriver doorsDriver;
    private final Engine engine;

    private State state = State.AWAITING;
    private List<Integer> requestedFloors = new ArrayList<Integer>();
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

    public void onDoorsOpened() {
        doorsDriver.closeDoors();
    }

    public void onDoorsClosed() {

        if(hasMoreFloorsToVisit()){
            await();
            return;
        }
        
        int destination = nextFloorToVisit();
        
        if (destination > currentFloor()) {
            goingUp();
        } else if (destination < currentFloor()) {
            goingDown();
        }

    }

    private boolean hasMoreFloorsToVisit() {
        return requestedFloors.isEmpty();
    }

    private Integer nextFloorToVisit() {
        
        return requestedFloors.iterator().next();
    }

    private void await() {
        state = State.AWAITING;
        return;
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
        
        if(shouldStopOnCurrentFloor()){
            stopOnFloor();
        }
    }

    private boolean shouldStopOnCurrentFloor() {
        return requestedFloors.contains(currentFloor);
    }

    private void stopOnFloor() {
        engine.stop();
        requestedFloors.remove((Object)currentFloor);
        doorsDriver.openDoors();
    }


}
