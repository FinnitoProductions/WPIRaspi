package lib.commandbased;

/**
 * A {@link TimedCommand} will wait for a timeout before finishing.
 * {@link TimedCommand} is used to execute a command for a given amount of time.
 */
public abstract class TimedCommand extends Command {
	
	private long timeInitialized; // millis
	private double timeout; // seconds
	
	/**
	 * Instantiates a TimedCommand with the given timeout.
	 *
	 * @param timeout
	 *            the time the command takes to run (seconds)
	 */
	public TimedCommand(double timeout) {
		this.timeout = timeout;
	}
	
	@Override
	protected final void initialize() {
		timeInitialized = System.currentTimeMillis();
		postInitialize();
	}
	
	/**
	 * Called after initializing with the start time, substituted for initialize
	 * in timed commands
	 */
	protected abstract void postInitialize();
	
	/**
	 * Ends command when timed out.
	 */
	@Override
	protected boolean isFinished() {
		return timeSinceInitialized() >= timeout;
	}
	
	public double timeSinceInitialized() {
		return (double) (System.currentTimeMillis() - timeInitialized) / 1000.0;
	}
}
