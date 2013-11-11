ElevatorKata
============

Implement Elevator class. Requirements:

The Elevator:
 1. starts on the ground floor,
 2. can be in one of following states: "going up", "going down", "waiting", "need maintenance",
 3. closes doors after pushing button, after closing doors elevator starts the engine,
 4. after reaching requested floor the elevator stops and opens the doors,
 5. visits floors in proper order (if 4, 2, 5 were pushed, it should visit 2, 4, 5),
 6. maintain one direction (up/down) as long as possible,
 7. stops and goes into "need maintenance" state, in case of the engine failure,
 8. does not respond to any commands, in "need maintenance" state,
 9. goes into "need maintenace" state if it detects the floors sensor failure (e.g. the floors are visited out of order: 1, 3, 2).

Sample Solution can be found here:
 * https://github.com/mpi/ElevatorKata/tree/sample-solution
 * http://www.youtube.com/watch?v=FUjKYiTCK88
