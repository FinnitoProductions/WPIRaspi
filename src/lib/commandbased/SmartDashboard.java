package lib.commandbased;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

import lib.util.MathUtil;

/**
 * Simulates the SmartDashboard by putting data to the console to be received by the GUI.
 * @author Finn Frankis
 * @version Aug 6, 2018
 */
public class SmartDashboard {
    private static final String PREFIX = "SD: ";

    /**
     * Puts a given number to the console to be read.
     * @param key the key to which the value will be assigned (the title of the graph), containing no numeric characters
     * @param value the value to be graphed under the key
     */
    public static void putNumber (String key, double value) {
        if (MathUtil.containsNumber(key))
            throw new InvalidParameterException(key + " contains a numeric character.");
        System.out.println(PREFIX + key + " " + value);
    }
}
