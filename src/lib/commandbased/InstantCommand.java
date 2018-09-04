package lib.commandbased;

/**
 * @author joel
 *
 */
public abstract class InstantCommand extends Command {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lib.Command#isFinished()
	 */
	@Override
	protected boolean isFinished() {
		return true;
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
	}
	
}
