
public class LED {
	
	//Variables
	int pin;
	boolean state = false;
		
	LED(){
		
	}
	
	LED(int pin, boolean state){
		
	}
	
	public void high(){
		state = true;
	}
	
	public void low(){
		state = false;
	}
	
	public void changePin(int p){
		pin = p;
	}

}
