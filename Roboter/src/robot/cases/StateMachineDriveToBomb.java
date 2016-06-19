package robot.cases;

public class StateMachineDriveToBomb
{

	public enum States
	{
		NONE,
		DRIVE_START,
		DRIVING_ALONG_LEFT_BORDER,
		DRIVING_ALONG_RIGHT_BORDER,
		DRIVING_RIGHT_ALONG_WALL,
		DRIVING_LEFT_ALONG_WALL,
		DETECTED_STRIPE,
		WAITING_P,
		DRIVING_TOWARDS_BOMB,
		FINISHED
	}

	public States state = States.NONE;

	void stateMachine()
	{
		switch (state)
		{
			case NONE:
				// TODO: start driving, set new state, all
				// depending on the role
				break;
			case DETECTED_STRIPE:
				break;
			case DRIVE_START:
				break;
			case DRIVING_ALONG_LEFT_BORDER:
				break;
			case DRIVING_ALONG_RIGHT_BORDER:
				break;
			case DRIVING_LEFT_ALONG_WALL:
				break;
			case DRIVING_RIGHT_ALONG_WALL:
				break;
			case DRIVING_TOWARDS_BOMB:
				break;
			case WAITING_P:
				// might not be necessary
				break;
			case FINISHED:
				break;
		}
	}
}
