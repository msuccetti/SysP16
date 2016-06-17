package tests;

import java.io.PrintStream;

import robot.middleware.DriveInDirections;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

/**
 * class to test the wlan adapter
 *
 */
public class DriveInDirectionsTester extends Task
{

	// period of action() method [ms]
	private static final float actionPeriod = 500;

	// time to wait between tests [s]
	private static final float waitTimeBetweenTests = 5;

	// cycles of action() between tests
	private static final int cyclesBetweenTests = (int) (waitTimeBetweenTests / (actionPeriod / 1000.0));

	// counter to wait in action() method
	private static int actionCount = cyclesBetweenTests;

	// actual test
	private static DriveInDirections.States tests = DriveInDirections.States.STOP;

	// Fixed test setting
	private static DriveInDirectionsTester tester; // This is me (singleton
													// instance)

	public DriveInDirectionsTester()
	{

		// configure console output
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("Testing class DcMotorSpeedPID");
	}

	public void action()
	{
		if (DriveInDirections.States.STOP == tests)
		{// switch to first test
			tests = DriveInDirections.States.BACKWARD_LEFT;
		}
		// wait for 1waitTimeBetweenTests s
		if (cyclesBetweenTests > actionCount++)
		{
			return;
		}
		// reset wait counter
		actionCount = 0;

		switch (tests)
		{
			case BACKWARD_LEFT:
				System.out.println("Drive backward with left component");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.BACKWARD_LEFT, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.BACKWARD_RIGHT;
				break;
			case BACKWARD_RIGHT:
				System.out.println("Drive backward with right component");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.BACKWARD_RIGHT, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.FORWARD_LEFT;
				break;
			case FORWARD_LEFT:
				System.out.println("Drive forwards with left component");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.FORWARD_LEFT, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.FORWARD_RIGHT;
				break;
			case FORWARD_RIGHT:
				System.out.println("Drive forwards with right component");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.FORWARD_RIGHT, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.LEFT;
				break;
			case LEFT:
				System.out.println("Drive left");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.LEFT, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.LEFT_FORWARD;
				break;
			case LEFT_FORWARD:
				System.out.println("Drive left with forward component");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.LEFT_FORWARD, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.LEFT_BACKWARD;
				break;
			case LEFT_BACKWARD:
				System.out.println("Drive left with backward component");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.LEFT_BACKWARD, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.RIGHT;
				break;
			case RIGHT:
				System.out.println("Drive right");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.RIGHT, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.RIGHT_FORWARD;
				break;
			case RIGHT_FORWARD:
				System.out.println("Drive right with forward component");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.RIGHT_FORWARD, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.RIGHT_BACKWARD;
				break;
			case RIGHT_BACKWARD:
				System.out.println("Drive right with backward component");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.RIGHT_BACKWARD, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.TURN_LEFT;
				break;
			case STOP:
				System.out.println("Stop");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.STOP, 10);
				AllObjects.driveInDirections.stopDrive();
				break;
			case TURN_LEFT:
				System.out.println("Turn left");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.TURN_LEFT, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.TURN_RIGHT;
				break;
			case TURN_RIGHT:
				System.out.println("Turn right");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.TURN_RIGHT, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.BACKWARD;
				break;
			case BACKWARD:
				System.out.println("Drive backward");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.BACKWARD,
						10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.FORWARD;
				break;
			case FORWARD:
				System.out.println("Drive forward");
				AllObjects.driveInDirections
						.setDrive(DriveInDirections.States.FORWARD, 10);
				AllObjects.driveInDirections.enable(true);
				tests = DriveInDirections.States.STOP;
				break;
			default:
				System.out.println("Stop");
				AllObjects.driveInDirections.setDrive(DriveInDirections.States.STOP, 10);
				AllObjects.driveInDirections.stopDrive();
				break;
		}
		/*
		 * AllObjects.pidControllers[MOT_INDEX].printStatus();
		 * AllObjects.encoders[MOT_INDEX].printStatus();
		 * AllObjects.motors[MOT_INDEX].printStatus();
		 */
	}

	/**
	 * initialises the tester
	 */
	static
	{
		try
		{
			// create new tester
			tester = new DriveInDirectionsTester();
			tester.period = 500;
			Task.install(tester);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
