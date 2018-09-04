
package lib.commandbased;

import java.util.*;

/**
 * @author Finn Frankis
 * @version Jul 8, 2018
 * 
 *          The scheduler is extremely error-safe. Any individual command that
 *          throws an exception or invalid method call should not stop the other
 *          components from running. Therefore errors are frequently just
 *          printed rather than thrown. To force better practices upon the
 *          students this style could be reversed.
 */
public class Scheduler {
	private static Scheduler sch;
	private Set<Command> runningCommands;
	private List<Subsystem> subsystems;
	private Stack<Command> toRemove;
	
	/**
	 * Creates a new scheduler, with the internal collection as an identity set
	 * wrapped around an IdentityHashMap. This allows for multiple similar
	 * commands to run and count as different.
	 */
	private Scheduler() {
		runningCommands = Collections.newSetFromMap(new IdentityHashMap<>());
		subsystems = new ArrayList<Subsystem>();
		toRemove = new Stack<Command>();
	}
	
	/**
	 * @return the singleton instance of the scheduler
	 */
	public static Scheduler getInstance() {
		if(sch == null)
			sch = new Scheduler();
		return sch;
	}
	
	/**
	 * Starts a new command running, stopping any commands that it would
	 * conflict with
	 * 
	 * @param c
	 *            the command to run
	 */
	public void add(Command c) {
		if(c == null)
			new IllegalArgumentException("Cannot start a null command").printStackTrace();
		else if(!runningCommands.add(c))
			new IllegalArgumentException("Command " + c.getClass().getSimpleName() + " is already running")
					.printStackTrace();
		else {
			for(Command o : runningCommands)
				if(c != o && sharedSubsystem(c, o))
					toRemove.push(o);
			while(!toRemove.isEmpty())
				interrupt(toRemove.pop(), c);
		}
	}
	
	/**
	 * Stops a command from running (no interrupt), restarting all default
	 * commands from subsystems used
	 * 
	 * @param c
	 *            the command to stop
	 */
	public void remove(Command c) {
		if(c == null)
			new IllegalArgumentException("Cannot stop a null command").printStackTrace();
		else if(!runningCommands.remove(c))
			new IllegalArgumentException("Command " + c.getClass().getSimpleName() + " is not running")
					.printStackTrace();
		else {
			try {
				c.end();
			} catch(Exception e) {
				System.err.println("Error ending command " + c.getClass().getSimpleName());
				e.printStackTrace();
			}
			for(Subsystem s : c.getRequired())
				if(s.getDefaultCommand() != null)
					add(s.getDefaultCommand());
		}
	}
	
	/**
	 * Stops a command from running (interrupt), restarting all default commands
	 * from subsystems used that don't conflict with the interruption
	 * 
	 * @param c
	 *            the command to stop
	 */
	public void interrupt(Command c, Command cause) {
		if(c == null)
			new IllegalArgumentException("Cannot stop a null command").printStackTrace();
		else if(!runningCommands.remove(c))
			new IllegalArgumentException("Command " + c.getClass().getSimpleName() + " is not running")
					.printStackTrace();
		else {
			try {
				c.interrupted();
			} catch(Exception e) {
				System.err.println("Error ending command " + c.getClass().getSimpleName());
				e.printStackTrace();
			}
			for(Subsystem s : c.getRequired())
				if(s.getDefaultCommand() != null)
					if(cause == null || !sharedSubsystem(cause, s.getDefaultCommand()))
						add(s.getDefaultCommand());
		}
	}
	
	/**
	 * Checks if a command is running
	 * 
	 * @param c
	 *            the command to check
	 * @return whether that command is contained in the set of running commands
	 */
	public boolean isRunning(Command c) {
		return runningCommands.contains(c);
	}
	
	/**
	 * Executes each command currently running once, then removes them if they
	 * have finished.
	 * 
	 * A single subsystem will never be executed more than once in a call of run
	 */
	public void run() {
		for(Command c : runningCommands) {
			try {
			    if (!c.isInitialized())
			    {
			        try { c.initialize(); c.setInitialized(true);}
			        catch(Exception e) {
		                System.err.println("Error initializing command " + c.getClass().getSimpleName());
		                e.printStackTrace();
		            }
			    }
				c.execute();
				if(c.isFinished())
					toRemove.push(c);
			} catch(Exception e) {
				System.err.println("Error running command " + c.getClass().getSimpleName());
				e.printStackTrace();
			}
		}
		
		for (Subsystem s : subsystems) { 
		    if (s.getDefaultCommand() == null)
		        s.initDefaultCommand();
		}
		while(!toRemove.isEmpty())
			remove(toRemove.pop());
	}
	
	/**
	 * Checks if two commands both use any one of the same subsystem and
	 * therefore would conflict
	 * 
	 * @param c
	 *            the first command
	 * @param o
	 *            the second command
	 * @return whether they have any shared subsystems
	 */
	public static boolean sharedSubsystem(Command c, Command o) {
		for(Subsystem s : c.getRequired())
			if(o.doesRequire(s))
				return true;
		return false;
	}
	
	protected void addSubsystem (Subsystem s) {
	    
	}
}
