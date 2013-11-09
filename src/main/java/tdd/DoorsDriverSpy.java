package tdd;

public class DoorsDriverSpy implements DoorsDriver {

    public boolean doorsOpeningHasBeenRequested;
    public boolean doorsClosingHasBeenRequested;

    @Override
    public void closeDoors() {
        doorsClosingHasBeenRequested = true;
    }

    @Override
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