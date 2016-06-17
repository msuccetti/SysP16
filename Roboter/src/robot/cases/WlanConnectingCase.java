package robot.cases;

import ch.ntb.sysp16.test.WlanComplete;

public class WlanConnectingCase
{

	States state;

	public WlanConnectingCase()
	{
		// Konstruktor der Klasse
		state = States.RESET;

	}

	public enum States
	{
		NOT_CONNECTED,
		WAITING_FOR_COMMAND,
		SENDING_COMMAND,
	}
	
	/**
	 * 
	 * @param wantedState
	 * @return true->state was reached, false otherwise
	 */
	boolean stateMachineSingleRun(States wantedState)
	{
		switch (state)
		{
			case RESET:
				wlan.reset();
				return true;
			case CONNECTED:
				break;
			case INITIALISING:
				break;
			case SENDING_COMMAND:
				break;
			case WAITING_FOR_COMMAND:
				break;
			case WAITING_FOR_CONNECTION:
				break;
			default:
				break;
		}
		return false;
	}

}
