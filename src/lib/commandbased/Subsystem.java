package lib.commandbased;

/**
 * @author Finn Frankis
 * @version Jul 8, 2018
 */
public abstract class Subsystem
{
    private Command defaultCommand;
    
    public abstract void initDefaultCommand();
    
    public Subsystem () {
        Scheduler.getInstance().addSubsystem(this);
    }
    
    public void setDefaultCommand (Command c)
    {
    		if(!c.doesRequire(this))
    		    c.requires(this);
//    			throw new IllegalArgumentException("A subsystem's default command must require it");
        defaultCommand = c;
    }
    
    public Command getDefaultCommand()
    {
        return defaultCommand;
    }
}
