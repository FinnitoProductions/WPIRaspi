package lib.commandbased;

import lib.util.ConsoleReader;
import lib.util.SocketReader;

/**
 * Represents a robot which is controlled by timed segments.
 * @author Finn Frankis
 * @version Jul 8, 2018
 */
public abstract class TimedRobot {
    private int autonTime;
    private int teleopTime;
    private boolean initOver;
    private static final String stopCharacter = "^C";

    /**
     * Constructs a new TimedRobot.
     * @param autonTimeMs the time (in ms) for which auton should last
     * @param teleopTimeMs the time (in ms) for which teleop should last
     */
    public TimedRobot (int autonTimeMs, int teleopTimeMs) {
        this.autonTime = autonTimeMs;
        this.teleopTime = teleopTimeMs;
    }

    /**
     * To be run when the robot is enabled.
     * @throws InterruptedException if the Thread.sleep() calls are interrupted
     */
    public void run () throws InterruptedException {
        SocketReader.makeConnection();
        Thread initThread, periodicThread;

        (periodicThread = new Thread() {
            public void run () {
                try {
                    System.out.println("Drivers behind the line.");
                    Thread.sleep(1000l);
                    System.out.println("Beginning in");
                    Thread.sleep(1000l);
                    System.out.println("3...");
                    Thread.sleep(1000l);
                    System.out.println("...2...");
                    Thread.sleep(1000l);
                    System.out.println("...1");
                    Thread.sleep(1000l);
                    System.out.println("POWER UP!");
                    while (!initOver);
                    System.out.println("Autonomous period beginning.");
                    long startTime = System.currentTimeMillis();
                    autonomousInit();
                    while (System.currentTimeMillis() - startTime < autonTime) {
                        System.out.println("entering again");
                        System.out.println("SOCK VAL:" + SocketReader.getValue());
                        autonomousPeriodic();
                        superPeriodic();
                        Thread.sleep(5l); // pause to allow time for other operations
                    }
                    System.out.println("Teleoperated period beginning.");
                    teleopInit();
                    startTime = System.currentTimeMillis();
                    while (System.currentTimeMillis() - startTime < teleopTime) {
                        teleopPeriodic();
                        superPeriodic();
                        Thread.sleep(5l); // pause to allow time for other operation
                    }
                    System.out.println("Match complete.");
                    TimedRobot.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        (initThread = new Thread() {
            public void run () {
                robotInit();
                initOver = true;
            }
        }).start();

    }

    /**
     * Called when autonomous begins.
     */
    public abstract void autonomousInit ();

    /**
     * Called periodically during the autonomous period.
     */
    public abstract void autonomousPeriodic ();

    /**
     * Called when teleop begins.
     */
    public abstract void teleopInit ();

    /**
     * Called periodically during the teleoperated period.
     */
    public abstract void teleopPeriodic ();

    /**
     * Called when the code is first deployed to the robot.
     */
    public abstract void robotInit ();

    /**
     * Called periodically while the robot is powered on with code deployed.
     */
    public abstract void robotPeriodic ();
    
    public void superPeriodic()
    {
        robotPeriodic();
        if (ConsoleReader.getValue().equals(stopCharacter))
            stop();
    }

    public static void stop () {
        System.out.println("STOPPING");
        System.exit(0);
    }
}
