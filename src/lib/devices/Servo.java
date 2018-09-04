package lib.devices;

import com.diozero.api.OutputDeviceInterface;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;

/**
 * Represents a Servo motor.
 * @author Finn Frankis
 * @version Jul 22, 2018
 */
public class Servo implements OutputDeviceInterface
{
    private GpioPinPwmOutput servoPort;
    
    /**
     * Constructs a new Servo.
     * @param pin the PWM pin number, based on WiringPi convention
     */
    public Servo (int pin)
    {
        GpioController gpio = GpioFactory.getInstance();
        
        //servoPort = gpio.provisionSoftPwmOutputPin(RaspiPin.getPinByAddress(pin));
        
        servoPort = gpio.provisionPwmOutputPin(RaspiPin.getPinByAddress(pin));
        
        Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
        Gpio.pwmSetRange(1024);
        Gpio.pwmSetClock(192);

        servoPort.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
    }

    /**
    * Sets the servo to a given value.
    * @param value the servo value
     */
    @Override
    public void setValue(float value)
    {
        servoPort.setPwm((int) (value));
    }
}
