package tdd;

public class Elevator {

    private DoorsDriver doorsDriver;
    private State state = State.AWAITING;
    private int requestedFloor;

    public Elevator(DoorsDriver doorsDriver) {
        this.doorsDriver = doorsDriver;
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

        this.requestedFloor = requestedFloor;
        
        if (requestedFloor == currentFloor()) {
            doorsDriver.openDoors();
        } else {
            doorsDriver.closeDoors();
        }
        
        
    }

    public void onDoorsClosed() {
        
        if(requestedFloor > currentFloor()){
            state = State.GOING_UP;
        } else if(requestedFloor < currentFloor()){
            state = State.GOING_DOWN;
        }
        
    }

}
