package tdd;

public class Elevator {

    private final DoorsDriver doorsDriver;
    private final Engine engine;

    private State state = State.AWAITING;
    private int destinationFloor;

    public Elevator(DoorsDriver doorsDriver, Engine engine) {
        this.doorsDriver = doorsDriver;
        this.engine = engine;
    }

    public enum State {
        AWAITING, GOING_UP, GOING_DOWN
    }

    public int currentFloor() {
        return 0;
    }

    public State state() {
        return state;
    }

    public void pushButton(int requestedFloor) {

        destinationFloor = requestedFloor;

        if (requestedFloor == currentFloor()) {
            doorsDriver.openDoors();
        } else {
            doorsDriver.closeDoors();
        }

    }

    public void onDoorsClosed() {

        if (destinationFloor > currentFloor()) {
            goingUp();
        } else if (destinationFloor < currentFloor()) {
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
        
        if(reachedFloor == destinationFloor){
            engine.stop();
            doorsDriver.openDoors();
        }
        
    }

}
