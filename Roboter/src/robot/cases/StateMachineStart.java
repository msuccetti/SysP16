package robot.cases;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import common.Consts;

import java.io.PrintStream;

import robot.cases.StateMachineDriveToBomb.States;
import robot.drivers.RobotState;
import robot.drivers.RobotState.StartPosition;
import robot.middleware.AllObjects;

/**
 * 
 * @author msuccetti
 *
 *         the class start represents the time when the system starts to run.
 */
public class StateMachineStart
{
	public enum States
	{
		NONE, GET_ROLE, WAIT_FOR_START_BUTTON, FINISHED
	}

	public States state = States.NONE;

	// counter to test a button for stability
	static int waitCount = 0;

	// number of cycles to be counted to test a button for stability
	static int waitCycles = 0;

	public StateMachineStart()
	{
		waitCycles = Consts.StateMachine.PERIOD
				/ Consts.Buttons.WAIT_TIME_STABLE;
		if (1 > waitCycles)
		{// we need at least one wait cycle
			waitCycles = 1;
		}
	}

	void stateMachine()
	{
		switch (state)
		{
			case NONE:
				state = States.GET_ROLE; // next state
				stateMachine();// carry on immediately
				break;
			case GET_ROLE:
				// get IR sensor values
				int brightnessRight = AllObjects.IrSensorChassisRight
						.readValue();
				int brightnessLeft = AllObjects.IrSensorChassisRight
						.readValue();
				// test, which IR sensor is brighter
				if (brightnessRight < brightnessLeft)
				{// right is bright -> we stand at the left border
					AllObjects.mainStateMachine.role = StateMachineMain.Roles.LEFT_BORDER;
				}
				else
				{// left is bright -> we stand at the rightborder
					AllObjects.mainStateMachine.role = StateMachineMain.Roles.RIGHT_BORDER;
				}
				state = States.WAIT_FOR_START_BUTTON;
				break;
			case WAIT_FOR_START_BUTTON:
				if (AllObjects.button_start.isPressed())
				{//button is presed
					if (waitCycles <= waitCount)
					{//button is stable
						state = States.FINISHED;
						break;
					}
					waitCount++;
					break;
				}
				//not pressed -> reset stability tester
				waitCount=0;
				break;
			case FINISHED:
				break;
		}
	}
}
