package test
import COMMAND
import DIRECTION
import MarsRover
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.hamcrest.core.Is
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class MarsRoverTest{

    @ParameterizedTest(name = "From \"{0}\", when turning left, ends facing {1}")
    @CsvSource("NORTH, WEST", "WEST, SOUTH", "SOUTH, EAST", "EAST, NORTH")
    fun `turning left in all directions`(initial: DIRECTION, expected: DIRECTION) {
        assertTurning(initial, faces(expected), COMMAND.LEFT)
    }

    @ParameterizedTest(name = "From \"{0}\", when turning right, ends facing {1}")
    @CsvSource("NORTH, EAST", "WEST, NORTH", "SOUTH, WEST", "EAST, SOUTH")
    fun `turning right in all directions`(initial: DIRECTION, expected: DIRECTION) {
        assertTurning(initial, faces(expected), COMMAND.RIGHT)
    }

    @ParameterizedTest(name = "From \"{0}\", when turning right, ends facing {1}")
    @CsvSource("NORTH, EAST", "WEST, NORTH", "SOUTH, WEST", "EAST, SOUTH")
    fun `turning two times in the same direction`(initial: DIRECTION, expected: DIRECTION) {
        assertTurning(initial, faces(expected), COMMAND.RIGHT)
    }

    @Test
    fun `turning right two times`() {
        assertSomeTurning(DIRECTION.NORTH, faces(DIRECTION.SOUTH), "R,R")
    }

    private fun assertTurning(initial: DIRECTION, expected: DIRECTION, command: COMMAND) {
        val rover = MarsRover(initial)
        val result = rover.accept(command.letter)
        assertThat(result, Is.`is`(MarsRover(expected)))
    }
    private fun assertSomeTurning(initial: DIRECTION, expected: DIRECTION, commands: String) {
        val rover = MarsRover(initial)
        val result = rover.accept(commands)
        assertThat(result, Is.`is`(MarsRover(expected)))
    }

    private fun faces(direction: DIRECTION): DIRECTION {
        return direction
    }

}