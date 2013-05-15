package tdd;

import static tdd.Elevator.Status.AWAITING;

public class Elevator {

    public enum Status{
        GOING_UP, GOING_DOWN, AWAITING, NEED_MAINTENENCE;
    }

    private Status status = AWAITING;
    
    public int currentFloor() {
        return 0;
    }
    
    public void pushButton(int floorNumber){
        
    }

    public Status status(){
        return status;
    }

}
