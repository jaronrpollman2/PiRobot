import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;


/**
 * 
 * Class for a limit switch
 * The state to start off is always 0, but the button will give you a 1 on push in
 * and a 2 on pull out.
 * 
 * @author Jaron Pollman
 * @author Chad Brinkman
 *
 */
public class InputSwitch {
	
    // create gpio controller
    final GpioController gpio = GpioFactory.getInstance();
    

    // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
    final public GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_DOWN);
    //final GpioPinDigitalInput onButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_DOWN);
    
    public int ballDetect = 0;
    public int onSwitch = 0;
	
	InputSwitch() throws InterruptedException{
		input();
		System.out.println("<--Pi4J--> GPIO Listen Example ... started.");
	}

    
    public void input() throws InterruptedException {

  
        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                
                ballDetect++;
              
                System.out.println("GPIO pin state : " + ballDetect);
                if(ballDetect == 2){
                	ballDetect = 0;
                }
                
            }
            
            
            
        });
        

        System.out.println(" ... complete the GPIO #07 (04) circuit and see the listener feedback here in the console.");
        
        // keep program running until user aborts (CTRL-C)

        
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller        
    }


	public int getBallDetect() {
		return ballDetect;
	}


	public void setBallDetect(int ballDetect) {
		this.ballDetect = ballDetect;
	}


	}

