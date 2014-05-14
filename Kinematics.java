
public class Kinematics {

	//Variables
	double [] vector = new double[3];
	double [] vectorDCM = new double[3];
	double [] vectorAvg = new double[9];
	double [] vectorOffset = new double[6];
	
	double [] dcm = new double [9];
	//double [] dcmNew = new double [9];
	double [] dcmPast = new double [9];
	
	double [] dcmOrigin = new double[9];
	
	double [] dc = new double [3];
	double [] da = new double [3];
	double [] gyro = new double [3];
	
	double [] compOffset = new double [3];
	double [] accelOffset = new double[3];
	double [] gyroOffset = new double [3];
	
	long measureTime;
	long lastTime = 0;
	long dt;
	
	double compFactor = .33;
	double accelFactor = .33;
	double gyroFactor = .34;
	
	int cAvg = 0;
	int numAvg = 5;
	double [][] compAvg = new double[3][numAvg];	
	double [][] accelAvg = new double[3][numAvg];	


	double pi = 3.141592653;
		
	Kinematics(){
		for(int i=0; i<9; i++){
			dcmPast[0] = 0;
		}
		
		for(int i=0; i<2; i++){
			dcm[0] = 0;
		}
	}
	

	public void setValues(double [] c, double [] a, double [] g, long t){
		measureTime = t;
		dt = measureTime - lastTime;
		
		double xAvg = 0;
		double yAvg = 0;
		double zAvg = 0;
		
		
		compAvg[0][cAvg] = c[0];
		compAvg[1][cAvg] = c[1];
		compAvg[2][cAvg] = c[2];
		
		for(int i=0; i<(numAvg); i++){
			xAvg = xAvg + compAvg[0][i];
			yAvg = yAvg + compAvg[1][i];
			zAvg = zAvg + compAvg[2][i];
		}
		
		vectorAvg[0] = (xAvg/(numAvg));
		vectorAvg[1] = (yAvg/(numAvg));
		vectorAvg[2] = (zAvg/(numAvg));
				
		getThetaComp(xAvg, yAvg, zAvg);
		
		dcm[0] = Math.acos(xAvg/(numAvg)) * (180/pi);
		dcm[1] = Math.acos(yAvg/(numAvg)) * (180/pi);
		dcm[2] = Math.acos(zAvg/(numAvg)) * (180/pi);
		
		accelAvg[0][cAvg] = a[0];
		accelAvg[1][cAvg] = a[1];
		accelAvg[2][cAvg] = a[2];

		xAvg = 0;
		yAvg = 0;
		zAvg = 0;
		
		for(int i=0; i<(numAvg); i++){
			xAvg = xAvg + accelAvg[0][i];
			yAvg = yAvg + accelAvg[1][i];
			zAvg = zAvg + accelAvg[2][i];
		}
		
		vectorAvg[3] = (xAvg/(numAvg));
		vectorAvg[4] = (yAvg/(numAvg));
		vectorAvg[5] = (zAvg/(numAvg));
		
		getThetaAcc(xAvg, yAvg, zAvg);
		
		dcm[3] = Math.acos(xAvg/(numAvg)) * (180/pi);
		dcm[4] = Math.acos(yAvg/(numAvg)) * (180/pi);
		dcm[5] = Math.acos(zAvg/(numAvg)) * (180/pi);
		
		dcm[6] = g[0];
		dcm[7] = g[1];
		dcm[8] = g[2];
		
		
		if(cAvg < (numAvg - 1)){
			cAvg++;
		}else{
			cAvg = 0;
		}
		
		
		
//		System.out.println("C: " + dcm[0] + ", " + dcm[1] + ", " + dcm[2]);
//		System.out.println("A: " + dcm[3] + ", " + dcm[4] + ", " + dcm[5]);
//		System.out.println("G: " + dcm[6] + ", " + dcm[7] + ", " + dcm[8]);
	}

	public void setOffsetAngle(double [] c, double [] a, double [] g){
		compOffset = c;
		accelOffset = a;
		gyroOffset = g;
		
		
		vectorOffset[0] = c[0];
		vectorOffset[1] = c[1];
		vectorOffset[2] = c[2];
		
		vectorOffset[3] = a[0];
		vectorOffset[4] = a[1];
		vectorOffset[5] = a[2];
				
		
		
		dcmOrigin[0] = Math.acos(c[0]) * (180/pi);
		dcmOrigin[1] = Math.acos(c[1]) * (180/pi);
		dcmOrigin[2] = Math.acos(c[2]) * (180/pi);
		
		dcmOrigin[3] = Math.acos(a[0]) * (180/pi);
		dcmOrigin[4] = Math.acos(a[1]) * (180/pi);
		dcmOrigin[5] = Math.acos(a[2]) * (180/pi);
		
		dcmOrigin[6] = g[0];
		dcmOrigin[7] = g[1];
		dcmOrigin[8] = g[2];
		
	}
	
	public void offsetAngles(){
		for(int i=0; i<6; i++){
			dcm[i] = (dcm[i] + dcmOrigin[i]) - 180; 
		}
		dcm[6] = (dcm[6] + dcmOrigin[6]);
		dcm[7] = (dcm[7] + dcmOrigin[7]);
		dcm[8] = (dcm[8] + dcmOrigin[8]);
		
	}
	
	public void updateDCM(){		
		dc[0] = dcm[0] - dcmPast[0];
		dc[1] = dcm[1] - dcmPast[1];
		dc[2] = dcm[2] - dcmPast[2];
		//System.out.println("dC: " + dc[0]+ ", " + dc[1] + ", " + dc[2]);
		
		da[0] = dcm[3] - dcmPast[3];
		da[1] = dcm[4] - dcmPast[4];
		da[2] = dcm[5] - dcmPast[5];
		//System.out.println("dA: " + da[0]+ ", " + da[1] + ", " + da[2]);
		
		
		//Current Angle = (Past Angle + gyro * dt) + (accel angle change) + (comp angle change)
		vectorDCM[0] = vectorDCM[0] + (gyroFactor * gyro[0] * dt) + (accelFactor * da[0]) + (compFactor * dc[0]);
		vectorDCM[1] = vectorDCM[1] + (gyroFactor * gyro[1] * dt) + (accelFactor * da[1]) + (compFactor * dc[1]);
		vectorDCM[2] = vectorDCM[2] + (gyroFactor * gyro[2] * dt) + (accelFactor * da[2]) + (compFactor * dc[2]);
		
		lastTime = measureTime;
		dcmPast = dcm;
	}
	
	public void calcVector(){
		vector[0] = Math.cos(vectorDCM[0] * (pi/180));
		vector[1] = Math.cos(vectorDCM[1] * (pi/180));
		vector[2] = Math.cos(vectorDCM[2] * (pi/180));
	}
	
	public double[] getVector(){
		return vector;
	}
	
	public void printDCM(){
//		System.out.println("DCM: " + dcm[0] + ", " + dcm[1] + ", " + dcm[2] + ", Squared Value: " + 
//				(Math.pow(Math.cos(dcm[0]), 2) + Math.pow(Math.cos(dcm[1]), 2) + Math.pow(Math.cos(dcm[2]), 2)) );
		
		System.out.println("C: " + dcm[0] + ", " + dcm[1] + ", " + dcm[2]);
		System.out.println("A: " + dcm[3] + ", " + dcm[4] + ", " + dcm[5]);
		System.out.println("G: " + dcm[6] + ", " + dcm[7] + ", " + dcm[8]);
	}
	
	public void printVector(){
		System.out.println("Vector: " + vector[0] + ", " + vector[1] + ", " + vector[2]);
	}
	
	public void printTime(){
		System.out.println("Measured: " + measureTime + ", Old: " + lastTime + ", dt:" + dt);
	}
	
	public void quadrant(){
//		int c;
//		int a;
//		int g;
		
		//if()
		
		System.out.println("Compass Quadrant: ");
		System.out.println("Accelerometer Quadrant: ");
		System.out.println("Gyro Quadrant: ");
	}
	
	
	public void getThetaComp(double x, double y, double z){
		double compTheta;
		double tempX = ((x/numAvg) * vectorOffset[0]);
		double tempY = ((y/numAvg) * vectorOffset[1]);
		double tempZ = ((z/numAvg) * vectorOffset[2]);
		//double accelTheta;9
		
		System.out.println("Inputs: " + (x/5) + ", " + (y/5) + ", " + (z/5));
		
		compTheta = Math.acos(tempX + tempY + tempZ) * (180/pi);
		System.out.println("Compass Angle: " + compTheta);
		System.out.println("Offset: " + vectorOffset[0] + ", " + vectorOffset[1] + ", " + vectorOffset[2]);
	}
	
	public void getThetaAcc(double x, double y, double z){
		double compTheta;
		double tempX = ((x/numAvg) * vectorOffset[3]);
		double tempY = ((y/numAvg) * vectorOffset[4]);
		double tempZ = ((z/numAvg) * vectorOffset[5]);
		//double accelTheta;9
		
		System.out.println("Inputs: " + (x/5) + ", " + (y/5) + ", " + (z/5));
		
		compTheta = Math.acos(tempX + tempY + tempZ) * (180/pi);
		System.out.println("Accelerometer Angle: " + compTheta);
		System.out.println("Offset: " + vectorOffset[0] + ", " + vectorOffset[1] + ", " + vectorOffset[2]);
	}
	
	
	public void setOffsetVector(double [] c, double [] a, double [] g){
		compOffset = c;
		accelOffset = a;
		gyroOffset = g;
			
		vectorOffset[0] = c[0];
		vectorOffset[1] = c[1];
		vectorOffset[2] = c[2];
		
		vectorOffset[3] = a[0];
		vectorOffset[4] = a[1];
		vectorOffset[5] = a[2];
	}
	
	
	public void heading(){
		double [] heading = new double[6];
		
		heading[0] = (Math.acos(vectorAvg[0]) - Math.acos(vectorOffset[0]))*(180/pi);
		heading[1] = (Math.acos(vectorAvg[1]) - Math.acos(vectorOffset[1]))*(180/pi);
		heading[2] = (Math.acos(vectorAvg[2]) - Math.acos(vectorOffset[2]))*(180/pi);
		
		System.out.println("Compass Heading: " + heading[0] + ", " + heading[1] + ", " + heading[2]);
		
		heading[3] = (Math.acos(vectorAvg[3]) - Math.acos(vectorOffset[3]))*(180/pi);
		heading[4] = (Math.acos(vectorAvg[4]) - Math.acos(vectorOffset[4]))*(180/pi);
		heading[5] = (Math.acos(vectorAvg[5]) - Math.acos(vectorOffset[5]))*(180/pi);
		
		System.out.println("Accelerometer Heading: " + heading[3] + ", " + heading[4] + ", " + heading[5]);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
