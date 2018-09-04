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
public class Encoder extends FeedbackSensor
{
    private GpioPinDigitalInput orangeInput, brownInput;

    /**
     * Constructs a new Encoder.
     * @param orangePort the pin into which the orange encoder wire is plugged in
     * @param brownPort the pin into which the brown encoder wire is plugged in
     */
    public Encoder (int orangePort, int brownPort)
    {        
        GpioController currentController = GpioFactory.getInstance();

        orangeInput = currentController.provisionDigitalInputPin(RaspiPin.getPinByAddress(orangePort), PinPullResistance.PULL_DOWN);
        brownInput = currentController.provisionDigitalInputPin(RaspiPin.getPinByAddress(brownPort), PinPullResistance.PULL_DOWN);
        
        orangeInput.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        brownInput.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        
        orangeInput.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent changeEvent)
            {
                if (!changeEvent.getEdge().equals(PinEdge.BOTH) && !changeEvent.getEdge().equals(PinEdge.NONE))
                {
                    addToPosition((changeEvent.getEdge().equals(PinEdge.RISING) == brownInput.getState().equals(PinState.HIGH)) ? -1 : 1);
                }
                
            }
        });
        brownInput.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent changeEvent)
            {
//                System.out.println("B " + ((changeEvent.getEdge() != PinEdge.BOTH && changeEvent.getEdge() != PinEdge.NONE)
//                        ? (changeEvent.getEdge() == PinEdge.RISING ? 1 : -1) : 0) + " " + System.currentTimeMillis());
                if (!changeEvent.getEdge().equals(PinEdge.BOTH) && !changeEvent.getEdge().equals(PinEdge.NONE))
                {
                    addToPosition(changeEvent.getEdge().equals(PinEdge.RISING) == orangeInput.getState().equals(PinState.HIGH) ? 1 : -1);
                }
            }
        });
    }
}
