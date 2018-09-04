package lib.commandbased;

/**
 * http://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/command/ConditionalCommand.html
 * 
 * A {@link ConditionalCommand} is a {@link Command} that starts one of two commands.
 *
 * <p>
 * A {@link ConditionalCommand} uses m_condition to determine whether it should run onTrue or
 * onFalse.
 * </p>
 *
 * <p>
 * A {@link ConditionalCommand} adds the proper {@link Command} to the {@link Scheduler} during
 * {@link ConditionalCommand#initialize()} and then {@link ConditionalCommand#isFinished()} will
 * return true once that {@link Command} has finished executing.
 * </p>
 *
 * <p>
 * If no {@link Command} is specified for onFalse, the occurrence of that condition will be a
 * no-op.
 * </p>
 *
 * @see Command
 * @see Scheduler
 */
public abstract class ConditionalCommand extends Command {
	
	private Command onTrue, onFalse;
	private Command running;
	
	/**
	 * Creates a new ConditionalCommand with a given onTrue Command.
	 *
	 * <p>
	 * Users of this constructor should also override condition().
	 *
	 * @param onTrue
	 *            The Command to execute if {@link ConditionalCommand#condition()} returns true
	 */
	public ConditionalCommand(Command onTrue) {
		this(onTrue, null);
	}
	
	/**
	 * Creates a new ConditionalCommand with given onTrue and onFalse Commands.
	 *
	 * <p>
	 * Users of this constructor should also override condition().
	 *
	 * @param onTrue
	 *            The Command to execute if {@link ConditionalCommand#condition()} returns true
	 * @param onFalse
	 *            The Command to execute if {@link ConditionalCommand#condition()} returns false
	 */
	public ConditionalCommand(Command onTrue, Command onFalse) {
		this.onTrue = onTrue;
		this.onFalse = onFalse;
	}
	
  /**
   * The Condition to test to determine which Command to run.
   *
   * @return true if onTrue should be run or false if onFalse should be run.
   */
	public abstract boolean condition();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.Command#isFinished()
	 */
	@Override
	protected boolean isFinished() {
		return running == null || !running.isRunning();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.Command#initialize()
	 */
	@Override
	protected void initialize() {
		running = condition() ? onTrue : onFalse;
		if(running != null)
			running.start();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.Command#execute()
	 */
	@Override
	protected void execute() {
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.Command#end()
	 */
	@Override
	protected void end() {
		if(running != null && running.isRunning())
			running.cancel();
	}
}
