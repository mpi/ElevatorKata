package tdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tdd.Elevator.Status;

public class ElevatorTest {

    private Elevator elevator;

    @Before
    public void setUp() {
        elevator = new Elevator();
    }
    
    @Test
    public void shouldStartOnZeroFloor() throws Exception {

        // given:
        // when:
        int floor = elevator.currentFloor();
        
        // then:
        Assert.assertEquals(0, floor);
    }

    @Test
    public void shouldStartInAwaitingState() throws Exception {
        
        // given:
        // when:
        Status status = elevator.status();
        
        // then:
        Assert.assertEquals(Elevator.Status.AWAITING, status);
    }
    
    
}
