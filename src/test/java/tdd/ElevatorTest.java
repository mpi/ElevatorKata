package tdd;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import tdd.Elevator.State;

public class ElevatorTest {

    private DoorsDriverSpy doorsDriver;

    private Elevator elevator;

    @Before
    public void setUp() {
        doorsDriver = new DoorsDriverSpy();
        elevator = new Elevator(doorsDriver);
    }
    
    @Test
    public void shouldStartOnZeroFloor() throws Exception {

        // given:
        // when:
        int floor = elevator.currentFloor();
        // then:
        assertThat(floor).isZero();
    }

    @Test
    public void shouldStartInAwaitingState() throws Exception {

        // given:
        // when:
        State state = elevator.state();
        // then:
        assertThat(state).isEqualTo(State.AWAITING);
    }

    @Test
    public void shouldOpenDoorsIfCurrentFloorHasBeenRequested() throws Exception {

        // given:
        // when:
        elevator.pushButton(0);
        // then:
        verifyThatOpeningDoorsHasBeenRequested();
    }

    @Test
    public void shouldNotOpenDoorsIfDifferentFloorHasBeenRequested() throws Exception {

        // given:
        // when:
        elevator.pushButton(1);
        // then:
        verifyThatOpeningDoorsHasNotBeenRequested();
    }

    @Test
    public void shouldCloseDoorsIfDifferentFloorHasBeenRequested() throws Exception {
        
        // given:
        // when:
        elevator.pushButton(1);
        // then:
        verifyThatClosingDoorsHasBeenRequested();
    }

    @Test
    public void shouldNotCloseDoorsIfCurrentFloorHasBeenRequested() throws Exception {
        
        // given:
        // when:
        elevator.pushButton(0);
        // then:
        verifyThatClosingDoorsHasNotBeenRequested();
    }

    // --
    
    private void verifyThatOpeningDoorsHasBeenRequested() {
        assertThat(doorsDriver.doorsOpeningHasBeenRequested()).isTrue();
    }

    private void verifyThatOpeningDoorsHasNotBeenRequested() {
        assertThat(doorsDriver.doorsOpeningHasBeenRequested()).isFalse();
    }

    private void verifyThatClosingDoorsHasBeenRequested() {
        assertThat(doorsDriver.doorsClosingHasBeenRequested()).isTrue();
    }
    
    private void verifyThatClosingDoorsHasNotBeenRequested() {
        assertThat(doorsDriver.doorsClosingHasBeenRequested()).isFalse();
    }
    
}


