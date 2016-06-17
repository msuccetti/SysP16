package tests;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

/**
 * class to test the wlan adapter
 *
 */
public class DriveCtrlTester extends Task
{
	// period of action() method [ms]
	private static final float actionPeriod = 500;

	// time to wait between tests [s]
	private static final float waitTimeBetweenTests = 5;

	// cycles of action() between tests
	private static final int cyclesBetweenTests = (int) (waitTimeBetweenTests / (actionPeriod / 1000.0));

	// counter to wait in action() method
	private static int actionCount = cyclesBetweenTests;

	// Fixed test setting
	private static DriveCtrlTester tester; // This is me (singleton
											// instance)

	public DriveCtrlTester()
	{

		// configure console output
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("Testing class DriveCtrl");
	}

	public void action()
	{
		// wait for 1waitTimeBetweenTests s
		if (cyclesBetweenTests > actionCount++)
		{
			return;
		}
		// reset wait counter
		actionCount = 0;

		for (int direction = 0; 360 > direction; direction += 45)
		{
			AllObjects.driveCtrl.setMovement(direction, 10, 0);
		}
	}

	/**
	 * initialises the tester
	 */
	static
	{
		try
		{
			// create new tester
			tester = new DriveCtrlTester();
			tester.period = 500;
			Task.install(tester);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
