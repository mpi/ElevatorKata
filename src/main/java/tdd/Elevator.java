package tdd;

import java.util.ArrayList;
import java.util.List;

import tdd.Engine.MalfunctionException;

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
        AWAITING, GOING_UP, GOING_DOWN, NEED_MAINTENENCE
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

        if (!requestedFloors.contains(floor)) {
            requestedFloors.add(floor);
        }
    }

    public void onDoorsOpened() {
        doorsDriver.closeDoors();
    }

    public void onDoorsClosed() {

        if (isInMaintenenceMode()) {
            return;
        }

        if (hasMoreFloorsToVisit()) {
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

        if (isGoingUp()) {
            if (hasMoreRequestedFloorsAbove()) {
                return currentFloor + 1;
            }
        }

        if (isGoingDown()) {
            if (hasMoreRequestedFloorsBelow()) {
                return currentFloor - 1;
            }
        }

        return requestedFloors.get(0);
    }

    private boolean isGoingDown() {
        return State.GOING_DOWN.equals(state);
    }

    private boolean isGoingUp() {
        return State.GOING_UP.equals(state);
    }

    private boolean isInMaintenenceMode() {
        return State.NEED_MAINTENENCE.equals(state);
    }

    private boolean hasMoreRequestedFloorsAbove() {

        for (Integer floor : requestedFloors) {
            if (floor > currentFloor) {
                return true;
            }
        }
        return false;
    }

    private boolean hasMoreRequestedFloorsBelow() {

        for (Integer floor : requestedFloors) {
            if (floor < currentFloor) {
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

        try {
            engine.down();
        } catch (MalfunctionException e) {
            needMaintenence();
        }
    }

    private void goingUp() {
        state = State.GOING_UP;
        try {
            engine.up();
        } catch (MalfunctionException e) {
            needMaintenence();
        }
    }

    private void needMaintenence() {
        state = State.NEED_MAINTENENCE;
    }

    public void onFloorReached(int reachedFloor) {

        currentFloor = reachedFloor;

        if (shouldStopOnCurrentFloor()) {
            stopOnFloor();
        }
    }

    private boolean shouldStopOnCurrentFloor() {
        return requestedFloors.contains(currentFloor);
    }

    private void stopOnFloor() {
        try {
            engine.stop();
        } catch (MalfunctionException e) {
            needMaintenence();
        }
        requestedFloors.remove((Object) currentFloor);
        doorsDriver.openDoors();
    }

}
