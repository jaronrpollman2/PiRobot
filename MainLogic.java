import java.io.IOException;
import java.util.Timer;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;




public class MainLogic {
		


	public static void main(String[] args) throws IOException, InterruptedException {
		
		//-----Startup Routine---------------------------------
		IOManager io = new IOManager();
//		IMU imu = new IMU();
//		Kinematics k = new Kinematics();	
		final InputSwitch input = new InputSwitch();
		int ball = 0;
		final Timer time = new Timer();	
		final Servo s = new Servo(45,true,17);
		
		io.drive(25, 2000);
		io.pause(500);
		io.turn(25, 1000);
		io.pause(500);
		io.drive(25, 2000);
		 
		/*
		while(input.getBallDetect() <= 1){
		time.schedule(s, 1, 2000);
		if(input.getBallDetect() == 2){
		time.cancel();
		}
		}*/
		
		
			
		//Thread.sleep(500);
//		//Forward
//		io.m1.moveTo(-45);
//		io.m2.moveTo(45);
//		
//		io.m1.moveOn(-45, 50);
//		io.m2.moveOn(45, 50);
//		io.s1.moveTo(100);
//		Thread.sleep(800);
//		
//		io.c1.pulseOut(87, 900);;
//		io.s1.off();
//		Thread.sleep(500);
//		io.m1.off();
//		io.m2.off();
//		
//		Thread.sleep(5000);
//		
//
//			io.m1.moveTo(-45);
//			io.m2.moveTo(40);
//			Thread.sleep(800);
//			
//			io.m1.moveOn(-45, 300);
//			io.m2.moveOn(45, 300);
//			Thread.sleep(800);
//			
//			io.m1.off();
//			io.m2.off();
//			
//			Thread.sleep(2000);
//			
//			io.m1.moveOn(35, 500);
//			io.m2.moveOn(-45, 500);
//			Thread.sleep(800);
//			
//			io.m1.moveOn(45, 500);
//			io.m2.moveOn(-35, 500);
//			Thread.sleep(800);
//		
//		
//		Thread.sleep(100);
//		
//		io.c1.pulseOut(81, 500);;
//		
//		io.s1.moveOn(-100, 500);
//		
//		
		
		
		
			
		
		
		
		
		//----------------------------------------------------
		      
//	       imu.initialize();
//	       imu.readSensors();
//	       imu.normalize();
//	       //Runtime.getRuntime().exec("/bin/bash/ -c clear");
//	       imu.calibrateFixed();     
//	       k.setOffsetAngle(imu.getCompOffset(), imu.getAccOffset(), imu.getGyroOffset());
//
//	       
//	       for(int i=0; i<10000; i++){
//	       	imu.readSensors();
//	       	imu.normalize();
//	       	imu.printData();
//	       	imu.printVectors();
//	       	
//	       	k.setValues(imu.getCompVector(), imu.getAccVector(), imu.getGyroRate(), imu.getMeasureTime());
//	       	k.heading();
//	       	//k.offsetAngles();
//	       	//k.updateDCM();
//	       	//k.printDCM();
//	       	//k.calcVector();
//	       	//k.printVector();
//	       	//k.printDCM();
//	       	//k.printTime();
//	       	
//	       	try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	       	
//	       	for(int j=0; j<30; j++){
//	       		System.out.print("\u001b[2J");
//	       		System.out.flush();
//	       	}
//	       	
//	       	
//	       }
//		
//	      	imu.calibrateFixed();
//		
//	
	}

	
}
