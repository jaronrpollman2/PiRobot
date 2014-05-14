//import com.pi4j.io.i2c.I2CBus;
//import com.pi4j.io.i2c.I2CDevice;
//import com.pi4j.io.i2c.I2CFactory;
//import com.pi4j.jni.I2C;
//
//import java.io.IOException;


public class PingSensor {
	
//	
//	
////	Serial pc = new Serial(USBTX, USBRX);
////	char buf[] = {0,0,0,0,0,0,0,0,0,0};
////	char cmd[] = { 0x42 };
//	
//	I2CBus bus;
//	I2CDevice i2c;
//	int pingAdd = 0x02;
//	
//	
//	public void ping() throws InterruptedException{
//		
//		bus = I2CFactory.getInstance(I2CBus.BUS_1);
//		i2c = bus.getDevice(pingAdd);
//		
//		while(true){
//			Thread.sleep(100);
//			{
//				I2CDevice i2c = new I2C(p9, p10);
//				i2c.frequency(9600);
//				i2c.write(0x02, cmd, 1, false);
//			}
//			{
//				DigitalInOut scl = new DigitalInOut(p10);
//				scl = 0;
//				scl.input();
//				scl.mode(PullUp);
//				Thread.sleep(1);
//				scl.output();
//				Thread.sleep(1);
//				scl.input();
//				scl.mode(PullUp);
//				Thread.sleep(1);
//			}
//			{
//				I2C i2c = new I2C(p9, p10);
//				i2c.frequency(9600);
//				i2c.read(0x02, buf, 5, false);
//				System.out.printf("distance: %d cm", buf[0]);
//			}
//			Thread.sleep(500);
//		}
//	}
//	
//	
//	
	
}
