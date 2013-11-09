package tdd;

public class DoorsDriverSpy {

    public boolean doorsOpeningHasBeenRequested;
    public boolean doorsClosingHasBeenRequested;

    public void closeDoors() {
        doorsClosingHasBeenRequested = true;
    }

    public void openDoors() {
        doorsOpeningHasBeenRequested = true;
    }

    public boolean doorsClosingHasBeenRequested() {
        return doorsClosingHasBeenRequested;
    }

    public boolean doorsOpeningHasBeenRequested() {
        return doorsOpeningHasBeenRequested;
    }
}