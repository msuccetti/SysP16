package robot.cases;

public class StateMachineDriveToStamp
{
	public enum States
	{
		NONE, DRIVE_START, DRIVING, FINISHED
	}

	public States state = States.NONE;

	void stateMachine()
	{
		switch (state)
		{
			case NONE:
				state = States.DRIVE_START;
				stateMachine(); // carry on immediately to save time
				break;
			case DRIVE_START:
				//TODO: drive, depending on role
				break;
			case DRIVING:
				//TODO: ask for reached, stop driving and set new state
				break;
			case FINISHED:
				//we are finished with that state machine
				break;
			default:
				break;
		}
	}
}
