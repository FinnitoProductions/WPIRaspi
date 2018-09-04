
package lib.commandbased;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * 
 * @author Finn Frankis
 * @version Jul 8, 2018
 */
public abstract class Command {
	private Set<Subsystem> required;
	private boolean isInitialized;
	
	/**
	 * Creates a new command.
	 */
	public Command() {
		required = Collections.newSetFromMap(new IdentityHashMap<>());
	}
	
	/**
	 * This method specifies that the given Subsystem is used by this command.
	 * This method is crucial to the functioning of the Command System in
	 * general.
	 * 
	 * Note that the recommended way to call this method is in the constructor.
	 * 
	 * @param s
	 *            the Subsystem required
	 */
	protected void requires(Subsystem s) {
		required.add(s);
	}
	
	/**
	 * Checks if the command requires the given Subsystem.
	 * 
	 * @param s
	 *            the system
	 * @return whether or not the subsystem is required, or false if given null
	 */
	public boolean doesRequire(Subsystem s) {
		return required.contains(s);
	}
	
	public Set<Subsystem> getRequired() {
		return required;
	}
	
	/**
	 * Returns whether this command is finished. If it is, then the command will
	 * be removed and end() will be called.
	 * 
	 * Returning false will result in the command never ending automatically. It
	 * may still be cancelled manually or interrupted by another command.
	 * Returning true will result in the command executing once and finishing
	 * immediately. We recommend using InstantCommand for this.
	 * 
	 * @return whether this command is finished.
	 */
	protected abstract boolean isFinished();
	
	/**
	 * The initialize method is called the first time this Command is run after
	 * being started.
	 */
	protected abstract void initialize();
	
	/**
	 * The execute method is called repeatedly until this Command either
	 * finishes or is canceled.
	 */
	protected abstract void execute();
	
	/**
	 * Called when the command ended peacefully. This is where you may want to
	 * wrap up loose ends, like shutting off a motor that was being used in the
	 * command.
	 */
	protected abstract void end();
	
	/**
	 * Called when the command ends because somebody called cancel() or another
	 * command shared the same requirements as this one, and booted it out. This
	 * is where you may want to wrap up loose ends, like shutting off a motor
	 * that was being used in the command.
	 * 
	 * Generally, it is useful to simply call the end() method within this
	 * method, as done here.
	 */
	protected void interrupted() {
		end();
	}
	
	/**
	 * This will cancel the current command. This will cancel the current
	 * command eventually. It can be called multiple times. And it can be called
	 * when the command is not running. If the command is running though, then
	 * the command will be marked as canceled and eventually removed.
	 * 
	 * A command can not be canceled if it is a part of a command group, you
	 * must cancel the command group instead.
	 */
	public void cancel() {
		Scheduler.getInstance().interrupt(this, null);
	}
	
	/**
	 * Starts up the command. Gets the command ready to start.
	 * 
	 * Note that the command will eventually start, however it will not
	 * necessarily do so immediately, and may in fact be canceled before
	 * initialize is even called.
	 */
	public void start() {
		Scheduler.getInstance().add(this);
		isInitialized = false;
	}
	
	/**
	 * Returns whether or not the command is running. This may return true even
	 * if the command has just been canceled, as it may not have yet called
	 * interrupted().
	 * 
	 * @return whether or not the command is running
	 */
	public boolean isRunning() {
		return Scheduler.getInstance().isRunning(this);
	}
	
	public String toString()
	{
	    return this.getClass().getName();
	}
	
	protected boolean isInitialized()
	{
	    return isInitialized;
	}
	
	protected void setInitialized(boolean newValue)
	{
	    isInitialized = newValue;
	}
}
