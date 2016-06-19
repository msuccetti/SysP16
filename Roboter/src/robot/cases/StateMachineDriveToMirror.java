package robot.cases;

import robot.middleware.AllObjects;

public class StateMachineDriveToMirror {
	public enum States
	{
		NONE,
		DRIVE_START,
		DRIVING_ALONG_LEFT_BORDER,
		DRIVING_ALONG_RIGHT_BORDER,
		DRIVING_RIGHT_ALONG_WALL,
		WAIING_P,
		DRIVING_LEFT_ALONG_WALL,
		DETECTED_WINDOW,
		FINISHED
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
				switch (AllObjects.mainStateMachine.role)
				{
					case NONE:
						break;
					case LEFT_BORDER:
						//TODO: start driving along the left border
						state=States.DRIVING_ALONG_LEFT_BORDER;
						break;
					case RIGHT_BORDER:
						//TODO: start driving along the right border
						state=States.DRIVING_ALONG_RIGHT_BORDER;
						break;
				}
				// TODO: drive, depending on role
				break;
			case DRIVING_ALONG_LEFT_BORDER:
				// TODO: ask for reached,
				// If reached, Start driving in new direction
				// and set new state (DRIVING_RIGHT_ALONG_WALL)
				break;
			case DRIVING_ALONG_RIGHT_BORDER:
				// TODO: ask for reached,
				// If reached, Start driving in new direction
				// and set new state (DRIVING_LEFT_ALONG_WALL)
				break;
			case DRIVING_RIGHT_ALONG_WALL:
				// TODO: ask for window to be detected,
				// If detected, carry on driving a certain distance and
				// stop, set state (FINISHED)
				break;
			case DRIVING_LEFT_ALONG_WALL:
				// TODO: ask for window to be detected,
				// If detected, carry on driving a certain distance and
				// stop, set state (FINISHED)
				break;
			case WAIING_P:
				// TODO: Waiting for partner to leave and signal,
				// then carry on (might need more states
				break;
			case FINISHED:
				// we are finished with that state machine
				break;
			default:
				break;
		}
	}
}
