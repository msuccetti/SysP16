package robot;

import common.Consts;

import robot.cases.DriveStempelCase;
import robot.drivers.StartButton;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;


public class MainCase extends Task
{
	States state;

	public MainCase()
	{
		// Konstruktor der Klasse
		state = States.WLAN_CONNECTING;

	}

	//role of the robot (Where did I start)
	public enum Roles
	{
		//the robot is positioned at the left border, if looked at the bomb from behind
		LEFT_BORDER,
		//the robot is positioned at the right border, if looked at the bomb from behind
		RIGHT_BORDER
	};

	public enum States
	{
		INIT,
		WLAN_CONNECTING,
		WAIT_FOR_START,
		START_POSITION,
		DRIVE_TO_STAMP,
		CATCH_STAMP,
		DRIVE_TO_MIRROR,
		STAMPING,
		DRIVE_TO_BOMB,
		DISARMING_BOMP,
		FINISHED
	}

	public void action()
	{
		System.out.print(".");
		switch (state)
		{

			case WLAN_CONNECTING:
			{

//				if (Wlan.connected())
				{
					state = States.WAIT_FOR_START;
					System.out
							.println("State Change: wlan_connected -> wait_for_start");
				}
				break;
			}

			case WAIT_FOR_START:
			{
				if (StartButton.isPressed())
				{
					System.out.print("State Change: ");
					System.out.print(States.WAIT_FOR_START.toString());
					System.out.print(" -> ");
					System.out.print(States.DRIVE_TO_STAMP.toString());
					state = States.DRIVE_TO_STAMP;
				}
				break;
			}

			case DRIVE_TO_STAMP:
			{
				// System.out.println("MainCase.action(), case zuStempelFahren");
				// DriveStempelCase.run();
				//
				// if (endcoder_finish==true)
				// {
				// main_state = States_main.stempel_greifen;
				// }
				break;
			}

			case CATCH_STAMP:
			{
				//
				// System.out.println("MainCase.action(), case stempelGreifen");
				// if (stempel_gegriffen==true)
				// {
				// main_state = States_main.zu_spiegel_fahren;
				// }
				//
				// break;
			}

			case DRIVE_TO_MIRROR:
			{
				// System.out.println("MainCase.action(), case zuSpiegelFahren");
				// if (spiegel_reached==true)
				// {
				// main_state = States_main.stempeln;
				// }
				break;
			}
			case STAMPING:
			{
				// System.out.println("MainCase.action(), case stempeln");
				// if (stempeln_finish==true)
				// {
				// main_state = States_main.zu_bombe_fahren;
				// }
				break;
			}
			case DRIVE_TO_BOMB:
			{
				// System.out.println("MainCase.action(), case zuBombeFahren");
				// if (bombe_reached==true)
				// {
				// main_state = States_main.bombe_entschaerfen;
				// }
				break;
			}
			case DISARMING_BOMP:
			{
				// System.out.println("MainCase.action(), case bombeEntschaerfen");
				// if (bombe_disarmed==true)
				// {
				// main_state = States_main.fertig;
				// }
				break;
			}
			case FINISHED:
			{
				// nicht tun
			}
			case START_POSITION:
				break;
			default:
				break;
		}
	}

	static
	{

		Task t = new MainCase();
		t.period = Consts.StateMachine.PERIOD;
		Task.install(t);
	}
}