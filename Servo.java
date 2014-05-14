import java.io.IOException;

/**
 * @author Chad Brinkman
 * @author Jaron Pollman
 * 
 * Class for the Servos and Motors on our robot.
 *
 */

//	Accessible GPIO pins
//
//	GPIO number   Pin in P1 header
//	4              P1-7
//	17             P1-11
//	18             P1-12
//	21             P1-13
//	22             P1-15
//	23             P1-16
//	24             P1-18
//	25             P1-22


//	Runtime.getRuntime().exec("/bin/bash -c Your Command");


public class Servo {

	//Variables
	private boolean direction = true; //CW = true, CCW = false
	private int position = 0;
	private int pin;
	private boolean on = false;
	
	private double pulseout;
	private String pulseStr;
	
	private long exitTime;



	
	
	
    // create servo controller		
	Servo(int position, boolean direction, int pin) throws IOException{
		//Set variables
		setPosition(position);
		this.direction = direction;
		setPin(pin);
		
		//Gets pin, initially off
		Runtime.getRuntime().exec("sudo sh /home/pi/Desktop/Robot/runservo.sh " + pin + " " + 0);
		//System.out.println("/bin/bash -c echo \"" + pin + "=" + 0 + "\"" + " > /dev/pi-blaster");
		
	}
	

	
	/**
	 * Turns the servo on.
	 * @throws IOException
	 */
	public void on() throws IOException{
		on = true;
		calcPulse();
		output();
	}
	/**
	 * Turns the servo off
	 * @throws IOException
	 */
	public void off() throws IOException{
		on = false;
		calcPulse();
		output();
	}
	/**
	 * Basic move method. Is replaced by moveOn(int,int,int)
	 * (OutDated)
	 * @param position
	 * @throws IOException
	 */
	public void moveTo(int p) throws IOException{
		on = true;
		position = p;
		calcPulse();
		output();
	}
	/**
	 * 
	 * @param position/speed
	 * @param delay/ How long it drives for
	 * @return Boolean
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public boolean moveOn(int p, int d) throws IOException, InterruptedException{
		on = true;
		position = p;
		calcPulse();
		output();
		delay(d);
		return true;
	}
	
	/**
	 * Method to Calculate The pulse rate of the motors.
	 */
	public void calcPulse(){
		//Scales output to percent of PWM: y = 0.0005x + .15
		pulseout = (.0005 * position) + .15;
		//Gets rid of the zero in front of the double
		pulseStr = Double.toString(pulseout);
		pulseStr = pulseStr.substring(1);
	}
	

	/**
	 * Method that tells the pi where to send the output.
	 * @throws IOException
	 */
	public void output() throws IOException{
		//Quotes in quotes: \"		Gets you one double quote "
		//Outputs pulse value to batch file to send command
		if(on == true){
			Runtime.getRuntime().exec("sudo sh /home/pi/Desktop/Robot/runservo.sh " + pin + " " + pulseStr);
			//System.out.println("echo \"" + pin + "=" + pulseStr + "\"" + " > /dev/pi-blaster" );
		}else{
			Runtime.getRuntime().exec("sudo sh /home/pi/Desktop/Robot/runservo.sh " + pin + " " + 0);
			//System.out.println("echo \"" + pin + "=" + 0 + "\"" + " > /dev/pi-blaster" );
		}
		
	}
	
	/**
	 * Method much like Thread.sleep() only takes the system clock and adds a delay in milliseconds. 
	 * Used for telling how long to drive.
	 * @param delay
	 * @return
	 */
	public boolean delay(int delay){
		exitTime = System.currentTimeMillis() + (long) delay;
		boolean exitLoop = false;
		
		while(exitLoop == false){
			if(System.currentTimeMillis() > exitTime){
				exitLoop = true;
			}
		}	
		return true;
	}
	
	
	/**
	 * Sets the pin that the Servo is registered for.
	 * @param pin
	 */
	public void setPin(int pin){
		this.pin = pin;
		
	}
	/**
	 * Gets the pin that the Servo is registered for
	 * @return
	 */
	public int getPin() {
		return pin;
	}

	

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int pos) {
		//Limits
		if(pos > 200)
			pos = 200;
		if(pos < -200)
			pos = -200;
			
		//Flips the direction of rotation
		if(direction = false)
			position = pos * -1;
	
		this.position = pos;
	}



/**
 * 
 * @return true if clockwise false if counterclockwise.
 */
	public boolean isDirection() {
		return direction;
	}



/**
 * 
 * @param direction negative if counterclockwise positive if clockwise.
 */
	public void setDirection(boolean direction) {
		this.direction = direction;
	}
	
	/**
	 * 
	 * @return false
	 */
	public boolean getState(){
		return on;
	}
	
	
}

