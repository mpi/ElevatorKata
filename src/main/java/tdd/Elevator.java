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

        queueRequestedFloor(requestedFloor);

        if (requestedFloor == currentFloor()) {
            doorsDriver.openDoors();
        } else {
            doorsDriver.closeDoors();
        }

    }

    private void queueRequestedFloor(int floor) {
        
        if(!requestedFloors.contains(floor)){
            requestedFloors.add(floor);
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
        
        int nextFloor = nextFloorToVisit();
        
        if (nextFloor > currentFloor()) {
            goingUp();
        } else if (nextFloor < currentFloor()) {
            goingDown();
        }

    }

    private boolean hasMoreFloorsToVisit() {
        return requestedFloors.isEmpty();
    }

    private Integer nextFloorToVisit() {
        
        if(State.GOING_UP.equals(state)){
            if(hasMoreRequestedFloorsAbove()){
               return currentFloor + 1; 
            }
        }
        
        if(State.GOING_DOWN.equals(state)){
            if(hasMoreRequestedFloorsBelow()){
                return currentFloor - 1; 
            }
        }
        
        return requestedFloors.get(0);
    }

    private boolean hasMoreRequestedFloorsAbove() {

        for (Integer floor : requestedFloors) {
            if(floor > currentFloor){
                return true;
            }
        }
        return false;
    }
    
    private boolean hasMoreRequestedFloorsBelow() {
        
        for (Integer floor : requestedFloors) {
            if(floor < currentFloor){
                return true;
            }
        }
        return false;
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
