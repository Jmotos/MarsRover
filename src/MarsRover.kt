enum class DIRECTION {
    NORTH {
        override fun left(): DIRECTION = WEST
        override fun right(): DIRECTION = EAST
    },
    WEST {
        override fun left(): DIRECTION = SOUTH
        override fun right(): DIRECTION = NORTH
    },
    SOUTH {
        override fun right(): DIRECTION = WEST
        override fun left(): DIRECTION = EAST
    },
    EAST {
        override fun left(): DIRECTION = NORTH
        override fun right(): DIRECTION = SOUTH
    };

    abstract fun left(): DIRECTION
    abstract fun right(): DIRECTION
}

enum class COMMAND(val letter: String) {
    LEFT("L") {
        override fun action(direction: DIRECTION) = direction.left()
    }, RIGHT("R") {
        override fun action(direction: DIRECTION) = direction.right()
    };

    abstract fun action(direction: DIRECTION): DIRECTION
}

data class MarsRover(
    private val direction: DIRECTION = DIRECTION.NORTH,
    private val commandTranslator: CommandTranslator = StringToCommand()
) {
    fun accept(commands: String): MarsRover {
        return applyCommands(commandTranslator.translate(commands))
    }

    private fun applyCommands(commands: List<COMMAND>): MarsRover {
        var rover = this
        for (command in commands) {
            rover = rover.copy(direction = turn(rover, command))
        }
        return rover
    }

    private fun turn(rover: MarsRover, command: COMMAND): DIRECTION {
        return command.action(rover.direction)
    }
}

interface CommandTranslator {
    fun translate(input: Any): List<COMMAND>
}

private class StringToCommand: CommandTranslator {
    override fun equals(other: Any?) = true
    override fun hashCode() = 3

    override fun translate(input: Any): List<COMMAND> {
        val commandList = input.toString().split(",")
        val result = mutableListOf<COMMAND>()
        for (inputCommand in commandList) {
            result.add(getCommand(inputCommand))
        }
        return result.toList()
    }

    private fun getCommand(inputCommand: String): COMMAND {
        return COMMAND.values().first { it.letter == inputCommand }
    }
}