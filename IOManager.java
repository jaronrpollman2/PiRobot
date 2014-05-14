import java.io.IOException;
/**
 * 
 * @author Chad Brinkman
 * @author Jaron Pollman
 * 
 * Class to manage the outputs for our robot as well as methods that tell the robot to drive or turn.
 *
 */

public class IOManager {
	
	//Variables
	
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
	
	
	public Servo m1;
	public Servo m2;
	public Servo s1;
	public Solenoid c1;
	public PingSensor p1;
	
	private long exitTime;
	
	IOManager() throws IOException{
		
		initialize();
	
	}
	/**
	 * Sets the Motors and Servos to their appropriate pins.
	 * @throws IOException
	 */
	public void initialize() throws IOException{
		//2 motors, 1 servo, 1 coil
		
//		//Speed, rotation CW, pin #
		m1 = new Servo(0, true, 17);
		m2 = new Servo(0, true, 18);
		s1 = new Servo(0, true, 21);
		//Power level, pin #, pulse length (mS)
		c1 = new Solenoid(85, 25, 500);
		
		//Ultrasonic Sensors
		//p1 = new PingSensor(5);
		
	}
	/**
	 * Displays the state of the motors and servo.
	 */
	public void status(){
		
		System.out.println("Motor 1: " + m1.getState());
		System.out.println("Motor 2: " + m2.getState());
		System.out.println("Servo 1: " + s1.getState());
		
	}
	/**
	 * Method to drive forward at a speed of (position) and for a set time (delay)
	 * @param position
	 * @param delay
	 * @return true
	 * @throws IOException
	 */
	public boolean drive(int speed, int delay) throws IOException{
		m1.moveTo(-speed);
		m2.moveTo(speed);
		
		delay(delay);
		m1.off();
		m2.off();
		return true;
	}
	public void controlDrive() throws IOException{
		m1.moveTo(-35);
		m2.moveTo(35);
		
		delay(30);
		m1.off();
		m2.off();
		
	}
	public void controlDriveReverse() throws IOException{
		m1.moveTo(35);
		m2.moveTo(-35);
		
		delay(30);
		
		m1.off();
		m2.off();
		
		
	}
	/**
	 * Method used for rotating.
	 * Positive value for right, negative value for left.
	 * @param speed
	 * @param delay
	 * @return
	 * @throws IOException
	 */
	public boolean turn(int speed,int delay) throws IOException{
		m1.moveTo(speed);
		m2.moveTo(speed);
		
		delay(delay);
		m1.off();
		m2.off();
		return true;
	}
	public void turnLeft() throws IOException{
		m1.moveTo(35);
		m2.moveTo(35);
		
		delay(30);
		m1.off();
		m2.off();
	}
	public void turnRight() throws IOException{
		m1.moveTo(-35);
		m2.moveTo(-35);
		
		delay(30);
		m1.off();
		m2.off();
	}
	/**
	 * Method to pause the motors for a set amount of time.
	 * Used in between each drive or turn method to prevent from stressing the motors.
	 * @param delay
	 * @return
	 * @throws IOException
	 */
	public boolean pause(int delay) throws IOException{
		m1.moveTo(0);
		m2.moveTo(0);
		
		delay(delay);
		m1.off();
		m2.off();
		return true;
	}
	/**
	 * Method to create a delay in milliseconds
	 * @param delay
	 * @return true
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
	
	public void enableInput(){
		
	}
	
	public void manageInputs(){
		
	}
/**
 * Sets all motors and servos to on.
 * @throws IOException
 */
	public void enableOutputs() throws IOException{
		m1.on();
		m2.on();
		s1.on();
	}
	/**
	 * Sets all motors and servos to off.
	 * @throws IOException
	 */
	public void disableOutputs() throws IOException{
		m1.off();
		m2.off();
		s1.off();
	}
	
	
	

}
