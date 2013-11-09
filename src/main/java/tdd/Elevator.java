package tdd;


public class Elevator {

    private boolean doorsOpeningHasBeenRequested;
    private boolean doorsClosingHasBeenRequested;

    public enum State {
        AWAITING
    }

    public int currentFloor() {
        return 0;
    }

    public State state() {
        return State.AWAITING;
    }

    public boolean doorsOpeningHasBeenRequested() {
        return doorsOpeningHasBeenRequested;
    }

    public void pushButton(int requestedFloor) {
        if (requestedFloor == currentFloor()) {
            doorsOpeningHasBeenRequested = true;
        } else{
            doorsClosingHasBeenRequested = true;
        }
    }

    public boolean doorsClosingHasBeenRequested() {
        return doorsClosingHasBeenRequested;
    }

}
