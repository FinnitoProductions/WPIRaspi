package lib.commandbased;

/**
 * A {@link StartCommand} will call the {@link Command#start() start()} method
 * of another command when it is initialized and will finish immediately.
 */
public class StartCommand extends InstantCommand {
	/**
	 * The command to fork.
	 */
	private final Command commandToFork;
	
	/**
	 * Instantiates a {@link StartCommand} which will start the given command
	 * whenever its {@link Command#initialize() initialize()} is called.
	 *
	 * @param commandToStart
	 *            the {@link Command} to start
	 */
	public StartCommand(Command commandToStart) {
		commandToFork = commandToStart;
	}
	
	@Override
	protected void initialize() {
		commandToFork.start();
	}
}
