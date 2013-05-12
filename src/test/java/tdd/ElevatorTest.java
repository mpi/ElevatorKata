package tdd;

import org.junit.Assert;
import org.junit.Test;


public class ElevatorTest {

    @Test
    public void shouldStartOnZeroFloor() throws Exception {

        // given:
        Elevator elevator = new Elevator();
        
        // when:
        int floor = elevator.currentFloor();
        
        // then:
        Assert.assertEquals(0, floor);
    }
    
}
