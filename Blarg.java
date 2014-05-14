import java.io.IOException;
import java.io.InputStreamReader;

public class Blarg{
	Servo motorL;
	Servo motorR;
	Servo servoArm;
	
	public void initalize() throws IOException{
		motorL = new Servo(0, true, 17);
		motorR = new Servo(0, false, 18);
		servoArm = new Servo(-50, true, 21);
	}
	
        public void nativeKeyPressed(char e) {
    		switch(e){
			case 'w': //move forward call
				try {
					motorL.moveTo(-50);
					motorR.moveTo(50);
					motorL.on();
					motorR.on();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("up");				break;
			
			case 's': //move back call
				try {
					motorL.moveTo(50);
					motorR.moveTo(-50);
					motorL.on();
					motorR.on();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("down");				break;
			
			case 'd': // turn right call 
				try {
					motorL.moveTo(50);
					motorR.moveTo(50);
					motorL.on();
					motorR.on();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("right");				break;
			
			case 'a': // turn left call
				try {
					motorL.moveTo(-50);
					motorR.moveTo(-50);
					motorL.on();
					motorR.on();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("left");				break;
			
			case '.': // make arm go out
				try {
					servoArm.moveTo(50);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(">");				break;
			
			case ',': // make arm go in
				try {
					servoArm.moveTo(-50);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("<");			break;
			case 'l': //fire ball HADOUKEN
				System.out.println("Fire!");
				break;
		}        
    }

        public static void main(String[] args) throws IOException {
        	char cmd = 'd';
        	Blarg i = new Blarg();
        	i.initalize();
	        while(cmd != '0'){
	        	try {
						cmd = (char) new InputStreamReader(System.in).read ();
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	i.nativeKeyPressed(cmd);
	        }		
      }
}
