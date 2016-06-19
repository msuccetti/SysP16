package robot.cases;

import robot.cases.StateMachineDriveToBomb.States;

public class StateMachineDisarmBomb
{
	public enum States
	{
		NONE,
		DRIVE_TO_REAR,
		DRIVE_TO_FRONT,
		PUSH_CUTTER,
		SET_BACK_FROM_BOMB,
		SET_FORWARD_TO_BOMB,
		CUT_REAR_WIRE,
		CUT_FRONT_WIRE,
		FINISHED
	}

	public States state = States.NONE;

	void stateMachine()
	{
		switch (state)
		{
			case NONE:
				//TODO: Depending on role and code we need to decide
				//where to drive to, start driving and set state
				break;
			case CUT_FRONT_WIRE:
				break;
			case CUT_REAR_WIRE:
				break;
			case DRIVE_TO_FRONT:
				break;
			case DRIVE_TO_REAR:
				break;
			case PUSH_CUTTER:
				break;
			case SET_BACK_FROM_BOMB:
				break;
			case SET_FORWARD_TO_BOMB:
				break;
			case FINISHED:
				//We are completely done
				break;
		}
	}
}
