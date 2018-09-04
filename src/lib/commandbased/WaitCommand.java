package lib.commandbased;

/**
 * A {@link WaitCommand} will wait for a certain amount of time before
 * finishing. It is useful if you want a {@link CommandGroup} to pause for a
 * moment.
 *
 * @see CommandGroup
 */
public class WaitCommand extends TimedCommand {
	/**
	 * Instantiates a {@link WaitCommand} with the given timeout.
	 *
	 * @param timeout
	 *            the time the command takes to run (seconds)
	 */
	public WaitCommand(double timeout) {
		super(timeout);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.TimedCommand#postInitialize()
	 */
	@Override
	protected void postInitialize() {}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.Command#execute()
	 */
	@Override
	protected void execute() {}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.Command#end()
	 */
	@Override
	protected void end() {}
}