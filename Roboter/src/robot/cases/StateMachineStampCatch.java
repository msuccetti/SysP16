package robot.cases;

import robot.cases.StateMachineDriveToStamp.States;

public class StateMachineStampCatch
{
	public enum States
	{
		NONE, FINISHED,
	}

	public States state = States.NONE;

	void stateMachine()
	{
		switch (state)
		{
			case NONE:
				break;
			case FINISHED:
				break;
		}
	}
}
