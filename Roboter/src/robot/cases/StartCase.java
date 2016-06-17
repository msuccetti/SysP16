package robot.cases;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.ppc32.Task;

import java.io.PrintStream;

import robot.MainCase;
import robot.drivers.RobotState;
import robot.drivers.RobotState.StartPosition;

/**
 * 
 * @author msuccetti
 *
 * the class start represents the time when the system starts to run.
 */
public class StartCase {
	
	MPIOSM_DIO start_knopf; //represents a button which needs to be pressed to start the robot 
	public static final byte start_knopf_pin = 89; //the I/O pin to connect the start button to
	
	int ir_links;
	int ir_rechts;
	
	public StartCase()
	{
		start_knopf = new MPIOSM_DIO(start_knopf_pin, false);
	}
	
	public void position_erfassen()
	{
		//get IR sensor values
		int brightnessRight=RobotState.ChassisRight.readValue();
		int brightnessLeft=RobotState.ChassisLeft.readValue();
		if(50>Math.abs(brightnessRight-brightnessLeft))
		{
			//test, which IR sensor is brighter
			if(brightnessRight<brightnessLeft)
			{//right is bright -> we stand at the left border
				RobotState.setStartPosition(RobotState.StartPosition.START_POS_RIGHT);
			}
			else
			{//left is bright -> we stand at the rightborder
				RobotState.setStartPosition(RobotState.StartPosition.START_POS_LEFT);
			}
		}
	}
	
	public void action()
	{
		
	//if(start_knopf.get() == true && main_state == wait_for_start && position_erfasst == true)
	//{
	//	startbedingungen = true;
	//}
	
	
	}
	
	static 
	{

		Task t = new MainCase();
		t.period = 100;
		Task.install(t);
	}
}
