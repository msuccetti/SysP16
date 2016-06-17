package robot.drivers;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;

public class StartButton {
	public static final byte start_knopf_pin = 89; //the I/O pin to connect the start button to

	static MPIOSM_DIO start_knopf; //represents a button which needs to be pressed to start the robot 

	public static boolean isPressed() {
		return start_knopf.get() == true; 
	}
	
	static {
		start_knopf = new MPIOSM_DIO(start_knopf_pin, false);
	}
}
