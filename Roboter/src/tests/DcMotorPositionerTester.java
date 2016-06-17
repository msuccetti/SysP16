package tests;

import java.io.PrintStream;

import common.Consts;
import robot.middleware.DcMotorPositioner;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

/**
 * class to test the positioning of a single dc motor
 *
 */
public class DcMotorPositionerTester extends Task
{
	final static float PID_DT = (float) 0.01;

	DcMotorPositioner dcMotorPositioner;
	private static DcMotorPositionerTester tester; // This is me (singleton
													// instance)

	public DcMotorPositionerTester()
	{

		// configure console output
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("Testing class DcMotorPositioner");
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_VL].setTarget((float) 1, (float) 1);
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_VR].setTarget((float) 1, (float) 1);
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_SM].setTarget((float) 1, (float) 1);
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_HL].setTarget((float) 1, (float) 1);
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_HR].setTarget((float) 1, (float) 1);
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_HM].setTarget((float) 1, (float) 1);
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_VL].enable(true);
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_VR].enable(true);
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_SM].enable(true);
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_HL].enable(true);
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_HR].enable(true);
		AllObjects.dcMotorPositioners[Consts.DcMotorPositioner.POSITIONER_HM].enable(true);
	}

	public void action()
	{
		//VL
		int positioner=Consts.DcMotorPositioner.POSITIONER_VL;
		if (AllObjects.dcMotorPositioners[positioner].isReached())
		{
			if (0 == AllObjects.dcMotorPositioners[positioner].getTarget())
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 500, (float) 100);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
			else
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 0, (float) 100);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
		}

		//VR
		positioner=Consts.DcMotorPositioner.POSITIONER_VR;
		if (AllObjects.dcMotorPositioners[positioner].isReached())
		{
			if (0 == AllObjects.dcMotorPositioners[positioner].getTarget())
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 100, (float) 10);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
			else
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 0, (float) 10);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
		}

		//SM
		positioner=Consts.DcMotorPositioner.POSITIONER_SM;
		if (AllObjects.dcMotorPositioners[positioner].isReached())
		{
			if (0 == AllObjects.dcMotorPositioners[positioner].getTarget())
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 4, (float) 3);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
			else
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 0, (float) 0.5);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
		}
		
		//HL
		positioner=Consts.DcMotorPositioner.POSITIONER_HL;
		if (AllObjects.dcMotorPositioners[positioner].isReached())
		{
			float localTarget = AllObjects.dcMotorPositioners[positioner].getTarget();


			if (0 == localTarget)
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 3, (float) 1);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
			else
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 0, (float) 100);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
		}

		//HM
		positioner=Consts.DcMotorPositioner.POSITIONER_HM;
		if (AllObjects.dcMotorPositioners[positioner].isReached())
		{
			if (0 == AllObjects.dcMotorPositioners[positioner].getTarget())
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 2.5, (float) 1);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
			else
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 0, (float) 1);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
		}

		//HR
		positioner=Consts.DcMotorPositioner.POSITIONER_HR;
		if (AllObjects.dcMotorPositioners[positioner].isReached())
		{
			if (0 == AllObjects.dcMotorPositioners[positioner].getTarget())
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 1000, (float) 250);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
			else
			{
				AllObjects.dcMotorPositioners[positioner].setTarget((float) 0, (float) 50);
				AllObjects.dcMotorPositioners[positioner].enable(true);
			}
		}

/*		dcMotorPositioner.printStatus();
		AllObjects.pidControllers[AllObjects.PID_HM].printStatus();
		AllObjects.encoders[AllObjects.PID_HM].printStatus();*/
	}

	/**
	 * initialises the tester
	 */
	static
	{
		try
		{
			// create new tester
			tester = new DcMotorPositionerTester();
			tester.period = 500;
			Task.install(tester);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
