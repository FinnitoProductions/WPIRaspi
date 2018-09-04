/**
 * 
 */
package lib.devices;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;

/**
 * @author joel & Finn Frankis
 */
public class DigitalMotor implements com.diozero.api.OutputDeviceInterface {
	
	private GpioPinDigitalOutput forward, backward;
	private GpioPinPwmOutput enable;
	
	/**
	 * Constructs a new DigitalMotor.
	 * @param forward the forward digital pin (for forward direction), in WiringPi convention 
	 * @param backward the backward digital pin (for backward motion), in WiringPi convention 
	 * @param enable the enable analog pin, giving the magnitude of the speed
	 */
	public DigitalMotor(int forward, int backward, int enable) {
		GpioController gpio = GpioFactory.getInstance();
		this.forward = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(forward));
		this.backward = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(backward));
		this.enable = gpio.provisionSoftPwmOutputPin(RaspiPin.getPinByAddress(enable));
		
		Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
		Gpio.pwmSetRange(1024);
		Gpio.pwmSetClock(1024);
		
		this.forward.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
		this.backward.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
		this.enable.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	}
	

    /**
    * Sets the motor to operate at a given speed.
    * @param speed the speed at which the motors should be set
    */
    @Override
    public void setValue(float speed)
    {
        forward.setState(speed > 0);
        backward.setState(speed < 0);
        enable.setPwm((int) Math.round(Math.abs(1024 * speed)));
    }
}
