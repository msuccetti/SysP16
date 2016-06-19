package robot.cases;

import common.Consts;
import robot.cases.StateMachineDriveToStamp.States;
import robot.cases.StateMachineMain.Roles;
import robot.drivers.StartButton;
import robot.middleware.AllObjects;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;

public class StateMachineMain extends Task
{
	public static States state = States.INIT;
	public Roles role = Roles.NONE;
	StateMachineStart start = new StateMachineStart();
	StateMachineDriveToStamp driveToStamp = new StateMachineDriveToStamp();
	StateMachineStampCatch stampCatch = new StateMachineStampCatch();
	StateMachineDriveToMirror driveToMirror = new StateMachineDriveToMirror();
	StateMachineStamp stamp = new StateMachineStamp();
	StateMachineDriveToBomb driveToBomb = new StateMachineDriveToBomb();
	StateMachineDisarmBomb disarmBomb = new StateMachineDisarmBomb();

	public StateMachineMain()
	{
		period = Consts.StateMachine.PERIOD;
		Task.install(this);
	}

	// role of the robot (Where did I start)
	public enum Roles
	{
		// not defined yet
		NONE,
		// the robot is positioned at the left border, if looked at the bomb
		// from behind
		LEFT_BORDER,
		// the robot is positioned at the right border, if looked at the bomb
		// from behind
		RIGHT_BORDER
	};

	public enum States
	{
		INIT,
		FIND_ROLE,
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
		stateMachine();
	}

	public void stateMachine()
	{
		System.out.print(".");
		switch (state)
		{
			case INIT:
				state=States.FIND_ROLE;
				action();
				break;

			case FIND_ROLE:
			// TODO: Read IR sensors and choose the one which is darker as
			// the border sensor
			if ()
			{
				role = Roles.RIGHT_BORDER;
			}
			else
			{
				role = Roles.LEFT_BORDER;
			}
			state=States.WLAN_CONNECTING;
			stateMachine();
			break;
			
			case WLAN_CONNECTING:
			{// test if already connected
				if (AllObjects.wlan.connected())
				{
					state = States.WAIT_FOR_START;
					System.out.println(
							"State Change: wlan_connected -> wait_for_start");
					// TODO: LED combination to signal wlan connection
					// (signal OK for state)
				}
				break;
			}

			case WAIT_FOR_START:
			{
				if (AllObjects.button_start.isPressed())
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
				if (StateMachineDriveToStamp.States.FINISHED != driveToStamp.state)
				{
					driveToStamp.stateMachine();
				}
				else
				{
					state=States.CATCH_STAMP;
				}
				break;
			}

			case CATCH_STAMP:
			{
				if (StateMachineStampCatch.States.FINISHED != stampCatch.state)
				{
					stampCatch.stateMachine();
				}
				else
				{
					state=States.DRIVE_TO_MIRROR;
				}
				break;
			}

			case DRIVE_TO_MIRROR:
			{
				if (StateMachineDriveToMirror.States.FINISHED != driveToMirror.state)
				{
					stampCatch.stateMachine();
				}
				else
				{
					state=States.STAMPING;
				}
				break;
			}
			case STAMPING:
			{
				if (StateMachineStamp.States.FINISHED != stamp.state)
				{
					stampCatch.stateMachine();
				}
				else
				{
					state=States.DRIVE_TO_BOMB;
				}
				break;
			}
			case DRIVE_TO_BOMB:
			{
				if (StateMachineDriveToBomb.States.FINISHED != driveToBomb.state)
				{
					driveToBomb.stateMachine();
				}
				else
				{
					state=States.DISARMING_BOMP;
				}
				break;
			}
			case DISARMING_BOMP:
			{
				if (StateMachineDisarmBomb.States.FINISHED != disarmBomb.state)
				{
					disarmBomb.stateMachine();
				}
				else
				{
					state=States.FINISHED;
				}
				break;
			}
			case FINISHED:
			{
				// we are ready
			}
			case START_POSITION:
				break;
			default:
				break;
		}
	}
}