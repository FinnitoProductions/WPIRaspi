package lib.util;

/**
 * A container class to hold various utility functions.
 * @author Finn Frankis
 * @version Jul 24, 2018
 */
public class MathUtil
{
    /**
     * Restricts a value between a minimum and maximum value.
     * If the value is greater than the maximum, it is set to maximum;
     * if it is less than the minimum, it is set to minimum.
     * @param value the value to check
     * @param min the minimum possible value 
     * @param max the maximum possible value
     * @return value, constrained between min and max
     */
    public static double restrictValue(double value, double min, double max)
    {
        return (value < min) ? min : Math.max(value, max);
    }
    
    /**
     * Maps one value in a current range to a new range.
     * @param value the value to be mapped
     * @param currentMin the current minimum possible value for this value
     * @param currentMax the current maximum possible value for this value
     * @param desiredMin the desired new minimum for value
     * @param desiredMax the desired new maximum for value
     * @return value, mapped into the range between desired_min and desired_max
     */
    public static double map(double value, double currentMin, double currentMax, 
            double desiredMin, double desiredMax)
    {
        return (value - currentMin) * (desiredMax - desiredMin) / (currentMax - currentMin) + desiredMin;
    }
    
    /**
     * Determines whether a given value is within a given range, inclusive.
     * @param value the value to be checked
     * @param min the minimum value in the range
     * @param max the maximum value in the range
     * @return true if the value is within the range; false otherwise
     */
    public static boolean isWithin (double value, double min, double max)
    {
        return value >= min && value <= max;
    }
    /**
     * Determines whether a given String represents a number.
     * @param s the String to check
     * @return true if the String is a number; false otherwise
     */
    public static boolean isNumber (String s)
    {
        try 
        {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    
    /**
     * Determines whether a given String contains any numeric character.
     * @param s the String to check
     * @return true if the String contains any numeric value; false otherwise
     */
    public static boolean containsNumber (String s)
    {
        for (int i = 0; i < s.length(); i++)
        {
            if (isWithin(s.compareTo("0"), 0, 9))
                return true;
        }
        return false;
    }
}
