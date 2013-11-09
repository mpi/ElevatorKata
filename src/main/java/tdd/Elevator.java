package tdd;


public class Elevator {

    private DoorsDriverSpy doorsDriver;

    public Elevator(DoorsDriverSpy doorsDriver) {
        this.doorsDriver = doorsDriver;
    }

    public enum State {
        AWAITING
    }

    public int currentFloor() {
        return 0;
    }

    public State state() {
        return State.AWAITING;
    }

    public void pushButton(int requestedFloor) {
        if (requestedFloor == currentFloor()) {
            doorsDriver.openDoors();
        } else{
            doorsDriver.closeDoors();
        }
    }

}
