package tdd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tdd.Elevator.State;

@RunWith(MockitoJUnitRunner.class)
public class ElevatorTest {

    @Mock
    private DoorsDriver doorsDriver;
    @Mock
    private Engine engine;

    private Elevator elevator;


    @Before
    public void setUp() {
        elevator = new Elevator(doorsDriver, engine);
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

    @Test
    public void shouldGoUpIfUpperFloorHasBeenRequestedAndDoorsHasBeenClosed() throws Exception {

        // given:
        elevator.pushButton(1);
        // when:
        elevator.onDoorsClosed();
        // then:
        assertThat(elevator.state()).isEqualTo(State.GOING_UP);
    }
    
    @Test
    public void shouldGoDownIfLowerFloorHasBeenRequestedAndDoorsHasBeenClosed() throws Exception {
        
        // given:
        elevator.pushButton(-1);
        // when:
        elevator.onDoorsClosed();
        // then:
        assertThat(elevator.state()).isEqualTo(State.GOING_DOWN);
    }
    
    @Test
    public void shouldRemainInAwaitingStateIfCurrentFloorHasBeenRequestedAndDoorsHasBeenClosed() throws Exception {
        
        // given:
        elevator.pushButton(0);
        // when:
        elevator.onDoorsClosed();
        // then:
        assertThat(elevator.state()).isEqualTo(State.AWAITING);
    }
    
    @Test
    public void shouldRemainInAwaitingStateUnlessDoorsHasBeenClosed() throws Exception {
        
        // given:
        // when:
        elevator.pushButton(1);
        // then:
        assertThat(elevator.state()).isEqualTo(State.AWAITING);
    }

    @Test
    public void shouldTurnEngineUpIfGoingUp() throws Exception {

        // given:
        elevator.pushButton(1);
        // when:
        elevator.onDoorsClosed();
        // then:
        verify(engine).up();
    }
    
    @Test
    public void shouldTurnEngineDownIfGoingDown() throws Exception {
        
        // given:
        elevator.pushButton(-1);
        // when:
        elevator.onDoorsClosed();
        // then:
        verify(engine).down();
    }
    
    @Test
    public void shouldNotTurnEngineIfCurrentFloorRequested() throws Exception {
        
        // given:
        elevator.pushButton(0);
        // when:
        elevator.onDoorsClosed();
        // then:
        verifyNoMoreInteractions(engine);
    }

    @Test
    public void shouldOpenDoorsAfterReachingDestinationFloor() throws Exception {
        
        // given:
        requestedFloorsAre(1);
        // when:
        elevator.onFloorReached(1);
        // then:
        verifyThatOpeningDoorsHasBeenRequested();
    }

    @Test
    public void shouldCloseDoorsAfterDoorsHaveBeenOpened() throws Exception {

        // given:
        // when:
        elevator.onDoorsOpened();
        // then:
        verify(doorsDriver).closeDoors();
    }
    
    @Test
    public void shouldStopEngineAfterReachingDestinationFloor() throws Exception {
        
        // given:
        requestedFloorsAre(1);
        // when:
        elevator.onFloorReached(1);
        // then:
        verify(engine).stop();
    }

    @Test
    public void shouldNotStopEngineAfterReachingIntermedieteFloor() throws Exception {
        
        // given:
        requestedFloorsAre(2);
        // when:
        elevator.onFloorReached(1);
        // then:
        verifyNoMoreInteractions(engine);
    }
    
    @Test
    public void shouldUpdateCurrentFloorIfNewFloorReached() throws Exception {

        // given:
        // when:
        elevator.onFloorReached(2);
        // then:
        assertThat(elevator.currentFloor()).isEqualTo(2);
    }
    
    @Test
    public void shouldStopOnFirstFloorIfMultipleFloorsRequested() throws Exception {

        // given:
        requestedFloorsAre(1, 2);
        
        // when:
        elevator.onFloorReached(1);
        
        // then:
        verify(engine).stop();
    }
    
    @Test
    public void shouldContinueToNextFloorIfFirstVisited() throws Exception {
        
        // given:
        requestedFloorsAre(1, 2);
        
        // when:
        floorHasBeenVisited(1);
        
        // then:
        verify(engine).up();
    }

    @Test
    public void shouldEnterAwaitingStateAfterVisitingLastRequestedFloor() throws Exception {

        // given:
        requestedFloorsAre(1, 2);
        
        // when:
        floorHasBeenVisited(1);
        floorHasBeenVisited(2);
        
        // then:
        assertThat(elevator.state()).isEqualTo(State.AWAITING);
    }
    
    @Test
    public void shouldPickInitialDirectionBasedOnFirstPushedButton() throws Exception {

        // given:
        requestedFloorsAre(-1, 1);
        
        // when:
        elevator.onDoorsClosed();
        
        // then:
        verify(engine).down();
    }
    
    @Test
    public void shouldMaintainOneDirectionAsLongAsPossible() throws Exception {

        SimulationEngine simulation = new SimulationEngine();
        elevator = new Elevator(new ImmediateDoorsDriver(), simulation);

        // given:
        requestedFloorsAre(1, -1, 3, -2, 2);
        // when:
        simulation.simulate();
        // then:
        assertThat(simulation.visited()).containsExactly(1, 2, 3, -1, -2);
    }
    
    @Test
    public void shouldIgnoreDuplicatedFloorREquests() throws Exception {
        
        SimulationEngine simulation = new SimulationEngine();
        elevator = new Elevator(new ImmediateDoorsDriver(), simulation);
        
        // given:
        requestedFloorsAre(1, 2, 1, 2);
        // when:
        simulation.simulate();
        // then:
        assertThat(simulation.visited()).containsExactly(1, 2);
    }
    
    // --
    
    private void floorHasBeenVisited(int visitedFloor) {
        elevator.onFloorReached(visitedFloor);
        reset(engine);
        elevator.onDoorsClosed();
    }
    
    private void requestedFloorsAre(int... requestedFloors) {

        for (int floor : requestedFloors) {
            elevator.pushButton(floor);
        }
        elevator.onDoorsClosed();
        reset(engine);
    }
    
    private void verifyThatOpeningDoorsHasBeenRequested() {
        verify(doorsDriver).openDoors();
    }

    private void verifyThatOpeningDoorsHasNotBeenRequested() {
        verify(doorsDriver, never()).openDoors();
    }

    private void verifyThatClosingDoorsHasBeenRequested() {
        verify(doorsDriver).closeDoors();
    }
    
    private void verifyThatClosingDoorsHasNotBeenRequested() {
        verify(doorsDriver, never()).closeDoors();
    }
    
    private final class ImmediateDoorsDriver implements DoorsDriver {
        @Override
        public void closeDoors() {
            elevator.onDoorsClosed();
        }
        
        @Override
        public void openDoors() {
            elevator.onDoorsOpened();
        }
    }
    
    public class SimulationEngine implements Engine {

        private int direction = 0;
        private int floor = 0;
        private List<Integer> visited = new ArrayList<Integer>();
        
        @Override
        public void up() {
            direction = 1;
        }

        @Override
        public void down() {
            direction = -1;
        }

        @Override
        public void stop() {
            direction = 0;
            visited.add(floor);
        }

        public void simulate(){
            for(int i=0; i<100; i++){
                floor += direction;
                elevator.onFloorReached(floor);
            }
        }

        public List<Integer> visited() {
            return visited;
        }
        
    }


}



