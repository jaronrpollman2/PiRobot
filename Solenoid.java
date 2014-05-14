import java.io.IOException;


public class Solenoid{
	
	int power = 0;
	double powerOut;
	double pulseLength = 0;
	int pin;

	
	
	Solenoid(int power, int pin, int pulse) throws IOException{
		setPower(power);
		this.pin = pin;
		this.pulseLength = pulse;
		
		//Gets pin, initially off
		Runtime.getRuntime().exec("sudo sh /home/pi/Desktop/Robot/runservo.sh " + pin + " " + 0);
	}

	public void fire() throws IOException, InterruptedException{
		calcPulse();
		output();
	}
	
	public void pulseOut(int power, double pulseLength) throws IOException, InterruptedException{
		this.power = power;
		this.pulseLength = pulseLength;
		fire();
	}
		
	public void calcPulse(){
		//Scales output to percent of PWM, pulse length in millisecond
		powerOut = (.01 * power);

	}
		
	public void output() throws IOException, InterruptedException{
		//Quotes in quotes: \"		Gets you one double quote "
		//Outputs pulse value to batch file to send command

		Runtime.getRuntime().exec("sudo sh /home/pi/Desktop/Robot/runservo.sh " + pin + " " + powerOut);
		System.out.println("echo \"" + pin + "=" + powerOut + "\"" + " > /dev/pi-blaster" );
		Thread.sleep((long) pulseLength);
		Runtime.getRuntime().exec("sudo sh /home/pi/Desktop/Robot/runservo.sh " + pin + " " + 0);
		System.out.println("echo \"" + pin + "=" + powerOut + "\"" + " > /dev/pi-blaster" );

	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		if(power > 100){
			this.power = 100;
		}else{
			this.power = power;
		}
		
	}
	

}
