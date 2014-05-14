import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
/**
 * 
 * @author Grayson Lorenz
 * uses command prompt to ask for a key and that key will make the robot react according to the letter
 * this class is no longer used
 *
 */
public class KeyInput{
	Servo motorL;
	Servo motorR;
	Servo servoArm;
	Solenoid launcher;
//	InputPin limit;
	int speed;
	IOManager io;
	
	public void initalize() throws IOException{
		io = new IOManager();
		motorL = new Servo(0, true, 17);
		motorR = new Servo(0, false, 18);
		servoArm = new Servo(-50, true, 22);
		launcher = new Solenoid(0, 22, 0);
//		limit = new InputPin();
		speed = 45;
	}
	
    public char nativeKeyPressed(char e) throws InterruptedException, IOException {
    
    	switch(e){
			case 'w': //move forward call
				System.out.println("up");
				return 'w';
		case 's': //move back call
				System.out.println("down");	
				return 's';
				
			case 'd': // turn right call 
				System.out.println("right");	
				return 'd';
					
			case 'a': // turn left call
				System.out.println("left");				
				return 'a';
					
			case '.': // make arm go out
				System.out.println(">");				
				return '.';
					
			case ',': // make arm go in
				System.out.println("<");			
				return ',';
					
			case 'l': //fire ball HADOUKEN
				System.out.println("Fire!");
				return 'l';
			case 'g':
				run();
				break;
			
			case 'x':
				stopAll();
				break;
    	}
		return e;        
    }
        
    public void moveFwd(int d) throws InterruptedException{
       	try {
			motorL.moveOn(-speed, d);
			motorR.moveOn(speed, d);
			motorL.off();
			motorR.off();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
        
    public void moveBack(int d) throws InterruptedException{
       	try {
			motorL.moveOn(speed, d);
			motorR.moveOn(-speed, d);
			motorL.off();
			motorR.off();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
        
    public void turnLeft(int d) throws InterruptedException{
       	try {
			motorL.moveOn(-speed, d);
			motorR.moveOn(-speed, d);
			motorL.off();
			motorR.off();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
        
    public void turnRight(int d) throws InterruptedException{
       	try {
			motorL.moveOn(speed, d);
			motorR.moveOn(speed, d);
			motorL.off();
			motorR.off();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
        
    public void armOut() throws InterruptedException{
       	try {
			servoArm.moveTo(100);
			Thread.sleep(100);
			servoArm.off();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
        
    public void armIn() throws InterruptedException{
       	try {
			servoArm.moveTo(-100);
			Thread.sleep(10);
			servoArm.off();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
    public void stopAll(){
		try {
			motorL.off();
			motorR.off();
			servoArm.off();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    public void fire() throws IOException, InterruptedException{
    	int power = 100;
    	int pulseLength = 500;
    	launcher.pulseOut(power, pulseLength);
    }
    
    public void firstShot() throws InterruptedException, IOException{
    	moveFwd(380); //12 in
    	Thread.sleep(380);
    	turnRight(800); //90 deg
    	//moveFwd(380); //? in
    	//turnLeft(500); //90 deg
    	fire();
    }
    
    public void secondShot() throws InterruptedException, IOException{
    	moveBack(190); //12 in
    	moveFwd(190); //12 in
    	fire();
    }
    
    public void thirdShot() throws InterruptedException, IOException{
    	turnRight(250); //90 deg
    	moveFwd(190); //? in
    	turnLeft(250); //90 deg
    	moveBack(190); //12 in
    	moveFwd(190); //12 in
    	turnLeft(250); //90 deg
    	moveFwd(190); //? in
    	turnRight(250); //90 deg
    	fire();
    }
    
    public void run() throws InterruptedException, IOException{
    	firstShot();
    	//secondShot();
    	//thirdShot();
    }
    
    public static void main(String[] args) throws IOException, InterruptedException{
    	
       	char cmd = (Character) null;
       	KeyInput i = new KeyInput();
       	i.initalize();
       	
	    while(cmd != '0'){
	       	try {
					cmd = (char) new InputStreamReader(System.in).read ();
				} catch (IOException e) {
					e.printStackTrace();
				}
	       	i.nativeKeyPressed(cmd);
	    }
//	    while(!limit.isPressed()){}
//	    i.run();
    }
}