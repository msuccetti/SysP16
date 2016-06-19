package tests;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

import common.Consts;
import robot.middleware.AllObjects;

/**
 * class to test the wlan adapter
 *
 */
public class PIDSpeedTester extends Task
{
	//Fixed test setting
	private static PIDSpeedTester tester; // This is me (singleton instance)
	
	private static final int MOT_INDEX=Consts.DcMotorSpeedPID.PID_SM;
	

	public PIDSpeedTester()
	{

		// configure console output
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("Testing class DcMotorSpeedPID");
		AllObjects.motors[Consts.DcMotors.MOTOR_HM].setSpeed((short)16000);
//		AllObjects.pidControllers[MOT_INDEX].setTargetSpeed((float)10);
//		AllObjects.pidControllers[MOT_INDEX].enable(true);
	}

	public void action()
	{
/*		 AllObjects.pidControllers[MOT_INDEX].printStatus();
		 AllObjects.encoders[MOT_INDEX].printStatus();
		 AllObjects.motors[MOT_INDEX].printStatus();*/
	}

	/**
	 * initialises the tester
	 */
	static
	{
		try
		{
			// create new tester
			tester = new PIDSpeedTester();
			tester.period = 500;
			Task.install(tester);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
