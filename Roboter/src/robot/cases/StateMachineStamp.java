package robot.cases;

import robot.cases.StateMachineDriveToStamp.States;

public class StateMachineStamp {
	public enum States
	{
		NONE, FINISHED
	}

	public States state = States.NONE;

	void stateMachine()
	{
		switch (state)
		{
			case NONE:
				break;
			case FINISHED:
				//we are finished with that state machine
				break;
		}
	}
}
