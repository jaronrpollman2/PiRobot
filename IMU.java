import java.io.IOException;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
/**
 * 
 * @author Chad Brinkman
 * @author Jaron Pollman
 * class for the imu we put on the raspberry pi currently doesn't work properly
 *
 */
public class IMU {

	I2CBus bus;
	
	I2CDevice i2c;
	
	//Addresses had to be shifted one bit to the right to account for the R/W bit.
	int compAdd = 0x1e;
	int accAdd = 0x53;
	int gyroAdd = 0x68;
	
	long compRaw [] = new long [3];
	long accRaw [] = new long [3];
	int gyroRaw [] = new int [3];
	
	double compOffset [] = new double [3];
	double accOffset [] = new double [3];
	double gyroOffset [] = new double [3];
	
	byte [] compData = new byte [6];
	byte [] accData = new byte [6];
	byte [] gyroData = new byte [6];
	
	double compVector [] = new double [3];
	double accVector [] = new double [3];
	double gyroRate [] = new double [3];
	
	int accAdjust = 256;
	int gyroAdjust = 823627; //Might not be needed, default 823627
	
	long measureTime;
	long temp = 0;

	

	IMU() throws IOException{
		initialize();
		measureTime = System.currentTimeMillis();
	}
	
	public void initialize() throws IOException{
		
		bus = I2CFactory.getInstance(I2CBus.BUS_1);
		
		//Compass 
		i2c = bus.getDevice(compAdd); 
        i2c.write(0x00, (byte) 0x18);
        i2c.write(0x01, (byte) 0x00);		
		
		//Accelerometer
		i2c = bus.getDevice(accAdd);		
		i2c.write(0x31,(byte) 0x0F); //0x0b
		i2c.write(0x2d, (byte) 0x08);
		
		//Gyro
		i2c = bus.getDevice(gyroAdd);		
		i2c.write(0x15,(byte) 0x07);
		//Sets up filter and FL_SEL
		i2c.write(0x16, (byte) 0x1a);		
	}
	
	public void readSensors() throws IOException{
		
		measureTime = System.currentTimeMillis();
		
		i2c = bus.getDevice(compAdd);
		i2c.write(0x02, (byte) 0x01);	
		i2c.read(0x03, compData, 0, compData.length);
		
		i2c = bus.getDevice(accAdd);
		i2c.read(0x32, accData, 0, accData.length);

		i2c = bus.getDevice(gyroAdd);
		i2c.read(0x1d, gyroData, 0, gyroData.length);
		
		
		//big-endian, Y and Z axis are flipped
		compRaw[0] = (compData[0] * 0x100) + compData[1];
		compRaw[1] = (compData[4] * 0x100) + compData[5];
		compRaw[2] = (compData[2] * 0x100) + compData[3];
		
		
		accRaw[0] = (accData[0] * 0x100) + accData[1];
		accRaw[1] = (accData[2] * 0x100) + accData[3];
		accRaw[2] = (accData[4] * 0x100) + accData[5];
		
		//big-endian
		gyroRaw[0] = ((gyroData[1] * 0x100) + gyroData[0]);
		gyroRaw[1] = ((gyroData[3] * 0x100) + gyroData[2]);
		gyroRaw[2] = ((gyroData[5] * 0x100) + gyroData[4]);
		

	}
	
	public void normalize(){
		double hyp;
		long temp;
		
		//Compass
		temp = ((compRaw[0] * compRaw[0]) + (compRaw[1] * compRaw[1]) + (compRaw[2] * compRaw[2]));
		hyp = Math.sqrt(temp);
		compVector[0] = compRaw[0] / hyp;
		compVector[1] = compRaw[1] / hyp;
		compVector[2] = compRaw[2] / hyp;	
		
		//Accelerometer
		accRaw[0] = (accRaw[0] / accAdjust);
		accRaw[1] = (accRaw[1] / accAdjust);
		accRaw[2] = (accRaw[2] / accAdjust);
		
		temp = ((accRaw[0] * accRaw[0]) + (accRaw[1] * accRaw[1]) + (accRaw[2] * accRaw[2]));
		hyp = Math.sqrt(temp);
		accVector[0] = accRaw[0] / hyp;
		accVector[1] = accRaw[1] / hyp;
		accVector[2] = accRaw[2] / hyp;	
		
		//Gyro
		gyroRate[0] = (gyroRaw[0] / gyroAdjust); 
		gyroRate[1] = (gyroRaw[1] / gyroAdjust);
		gyroRate[2] = (gyroRaw[2] / gyroAdjust);
	}
	
	public void offset(){
		
		compVector[0] = compVector[0] + compOffset[0];
		compVector[1] = compVector[1] + compOffset[1];
		compVector[2] = compVector[2] + compOffset[2];
		
		accVector[0] = accVector[0] + accOffset[0];
		accVector[1] = accVector[1] + accOffset[1];
		accVector[2] = accVector[2] + accOffset[2];
		
		gyroRate[0] = (int) (gyroRate[0] + gyroOffset[0]);
		gyroRate[1] = (int) (gyroRate[1] + gyroOffset[1]);
		gyroRate[2] = (int) (gyroRate[2] + gyroOffset[2]);
		
	}
	
	public void printData() throws IOException{
		
		
		System.out.println("Compass Raw: " + Integer.toBinaryString((int) compRaw[0]) + "," + 
				Integer.toBinaryString((int) compRaw[1]) + "," + Integer.toBinaryString((int) compRaw[2]));	
		
		System.out.println("Accelerometer Raw: " + Integer.toBinaryString((int) accRaw[0]) + "," + 
				Integer.toBinaryString((int) accRaw[1]) + "," + Integer.toBinaryString((int) accRaw[2]));	
		
		System.out.println("Gyro Raw: " + Integer.toBinaryString((int) gyroRaw[0]) + "," + 
				Integer.toBinaryString((int) gyroRaw[1]) + "," + Integer.toBinaryString((int) gyroRaw[2]));
	}
	
	public void printVectors(){
		
		System.out.println("Compass Vector: " + compVector[0] + "," + compVector[1] + "," + compVector[2]);
		System.out.println("Accelerometer Vector: " + accVector[0] + "," + accVector[1] + "," + accVector[2]);	
		System.out.println("Gyro Vector: " + gyroRate[0] + "," + gyroRate[1] + "," + gyroRate[2]);
	}
	
	public void calibrateFixed() throws IOException{
		
		compOffset[0] = 0;
		compOffset[1] = 0;
		compOffset[2] = 0;
		
		accOffset[0] = 0;
		accOffset[1] = 0;
		accOffset[2] = 0;
		
		gyroOffset[0] = 0;
		gyroOffset[1] = 0;
		gyroOffset[2] = 0;
		
		
		//get time here, and return the amount of time it took to calibrate after the loop
		double [][] compOff = new double[40][3];
		double [][] accOff = new double[40][3];
		
		for(int i=0; i<40; i++){
			
			
			
        	try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
			readSensors();
			normalize();
			
			compOff[i][0] = compVector[0];
			compOff[i][1] = compVector[1];
			compOff[i][2] = compVector[2];
			
			accOff[i][0] = accVector[0];
			accOff[i][1] = accVector[1];
			accOff[i][2] = accVector[2];

		}
		
		//Get Average
		for(int i=0; i<40; i++){
			compOffset[0] = compOffset[0] + compOff[i][0];
			compOffset[1] = compOffset[1] + compOff[i][1];
			compOffset[2] = compOffset[2] + compOff[i][2];	
			
			accOffset[0] = accOffset[0] + accOff[i][0];
			accOffset[1] = accOffset[1] + accOff[i][1];
			accOffset[2] = accOffset[2] + accOff[i][2];
		}
			
		compOffset[0] = compOffset[0] / 40;
		compOffset[1] = compOffset[1] / 40;
		compOffset[2] = compOffset[2] / 40;
		
		accOffset[0] = accOffset[0] / 40;
		accOffset[1] = accOffset[1] / 40;
		accOffset[2] = accOffset[2] / 40;
		
		System.out.println("Compass Offset: " + compOffset[0] + "," + compOffset[1] + "," + compOffset[2]);
		System.out.println("Accelerometer Offset: " + accOffset[0] + "," + accOffset[1] + "," + accOffset[2]);
		System.out.println("Gyro Offset: " + gyroOffset[0] + "," + gyroOffset[1] + "," + gyroOffset[2]);
		
	}
	

	
	public double[] getCompOffset() {
		return compOffset;
	}

	public void setCompOffset(double[] compOffset) {
		this.compOffset = compOffset;
	}

	public double[] getAccOffset() {
		return accOffset;
	}

	public void setAccOffset(double[] accOffset) {
		this.accOffset = accOffset;
	}

	public double[] getGyroOffset() {
		return gyroOffset;
	}

	public void setGyroOffset(double[] gyroOffset) {
		this.gyroOffset = gyroOffset;
	}

	/**
	 * @return the compVector
	 */
	public double[] getCompVector() {
		return compVector;
	}

	/**
	 * @param compVector the compVector to set
	 */
	public void setCompVector(double[] compVector) {
		this.compVector = compVector;
	}

	/**
	 * @return the accVector
	 */
	public double[] getAccVector() {
		return accVector;
	}

	/**
	 * @param accVector the accVector to set
	 */
	public void setAccVector(double[] accVector) {
		this.accVector = accVector;
	}

	/**
	 * @return the gyroRate
	 */
	public double[] getGyroRate() {
		return gyroRate;
	}

	/**
	 * @param gyroRate the gyroRate to set
	 */
	public void setGyroRate(double[] gyroRate) {
		this.gyroRate = gyroRate;
	}

	/**
	 * @return the measureTime
	 */
	public long getMeasureTime() {
		return measureTime;
	}

	/**
	 * @param measureTime the measureTime to set
	 */
	public void setMeasureTime(long measureTime) {
		this.measureTime = measureTime;
	}
	
	
	
	
	
	
	
	
	
}
