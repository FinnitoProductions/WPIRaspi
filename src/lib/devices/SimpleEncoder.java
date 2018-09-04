package lib.devices;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinEdge;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Represents a magnetic encoder.
 * @author Finn Frankis
 * @version Jul 20, 2018
 */
public class SimpleEncoder extends FeedbackSensor {
    private GpioPinDigitalInput orangeInput, brownInput;
    private TalonSRX controller; 
    
    /**
     * Constructs a new Encoder.
     * @param orangePort the pin into which the orange encoder wire is plugged in
     * @param brownPort the pin into which the brown encoder wire is plugged in
     */
    public SimpleEncoder (int orangePort, int brownPort) {
        GpioController currentController = GpioFactory.getInstance();

        orangeInput = currentController.provisionDigitalInputPin(RaspiPin.getPinByAddress(orangePort),
                PinPullResistance.PULL_DOWN);
        brownInput = currentController.provisionDigitalInputPin(RaspiPin.getPinByAddress(brownPort),
                PinPullResistance.PULL_DOWN);

        orangeInput.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        brownInput.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);

        orangeInput.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent (GpioPinDigitalStateChangeEvent changeEvent) {
                if (changeEvent.getEdge().equals(PinEdge.RISING) || changeEvent.getEdge().equals(PinEdge.FALLING)) 
                    addToPosition((controller != null) ? (controller.getOutputDirection()) : 0);

            }
        });
    }
    
    protected FeedbackSensor setController (TalonSRX controller)
    {
        this.controller = controller;
        return this;
    }
}
