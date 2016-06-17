package tests;

import java.io.PrintStream;

import common.Consts;
import robot.drivers.DcMotor;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

/**
 * class to test the wlan adapter
 *
 */
public class DcMotorTester extends Task
{
	// states of the main test machine
	private enum TestMainStates
	{
		TEST_ALLSEQ, TEST_2, TEST_3, TEST_4, TEST_FINISHED
	};

	// states of the speed ramp tester
	private enum TestForwardBackward
	{
		TEST_FIRST_UP, TEST_DOWN, TEST_SECOND_UP, TEST_FINISHED
	};

	private static DcMotorTester tester; // This is me (singleton instance)
	final private static int SPEED_STEP = 16; // number of steps used by the
												// speed ramp from zero to full
												// speed
	private static DcMotor motors[]; // array to hold instances of motors
	private int testVectorCount = 0; // counter for test cases
	// actual state of the test state machine
	private static TestMainStates testMainState = TestMainStates.TEST_FINISHED;
	// actual state of the speed ramp
	private static TestForwardBackward testForwardBackward = TestForwardBackward.TEST_FIRST_UP;
	private static Short pwmValue = 0;// actual speed (all motors are the same)

	// motor combination for driving 2 driver motors
	private final int test2Tests[][] =
	{
	{ 0, 1 },
	{ 0, 3 },
	{ 0, 4 },
	{ 1, 3 },
	{ 1, 4 },
	{ 3, 4 } };

	// motor combination for driving 3 driver motors
	private final int test3Tests[][] =
	{
	{ 0, 1, 3 },
	{ 0, 1, 4 },
	{ 0, 3, 4 },
	{ 1, 3, 4 } };

	// HW connections of the driver motors
	final Consts.MotorSettings motorSettings[] =
	{ Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_VL],// VL
			Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_VR],// VR
			Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_SM],// SM
			Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_HL],// HL
			Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_HR],// HR
			Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_HM],// HM
	};

	public DcMotorTester()
	{
		// configure console output
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("Testing class DcMotor");
	}

	public void action()
	{
		switch (testMainState)
		{
		// test all motors one after another
			case TEST_ALLSEQ:
				if (6 > testVectorCount)
				{
					speedRampMotors(new int[]
					{ testVectorCount });
					if (TestForwardBackward.TEST_FINISHED == testForwardBackward)
					{// ramp is finished -> delete motors and increase test
						// vector
						motors = null;
						testVectorCount++;
						testForwardBackward = TestForwardBackward.TEST_FIRST_UP;
					}
				}
				else
				{
					testVectorCount = 0;// reset counter
					testMainState = TestMainStates.TEST_2;
				}
				break;
			// test 2 motors simultaneously
			case TEST_2:
				if (test2Tests.length > testVectorCount)
				{
					speedRampMotors(test2Tests[testVectorCount]);
					if (TestForwardBackward.TEST_FINISHED == testForwardBackward)
					{// ramp is finished -> delete motors and increase test
						// vector
						motors = null;
						testVectorCount++;
						testForwardBackward = TestForwardBackward.TEST_FIRST_UP;
					}
				}
				else
				{
					testVectorCount = 0;// reset counter
					testMainState = TestMainStates.TEST_3;
				}
				break;
			// test 3 motors simultaneously
			case TEST_3:
				if (test3Tests.length > testVectorCount)
				{
					speedRampMotors(test3Tests[testVectorCount]);
					if (TestForwardBackward.TEST_FINISHED == testForwardBackward)
					{// ramp is finished -> delete motors and increase test
						// vector
						motors = null;
						testVectorCount++;
						testForwardBackward = TestForwardBackward.TEST_FIRST_UP;
					}
				}
				else
				{
					testVectorCount = 0;// reset counter
					testMainState = TestMainStates.TEST_4;
				}
				break;
			// test 4 motors simultaneously
			case TEST_4:
				speedRampMotors(new int[]
				{ 0, 1, 3, 4 });
				if (TestForwardBackward.TEST_FINISHED == testForwardBackward)
				{// ramp is finished -> delete motors and increase test vector
					motors = null;
					testForwardBackward = TestForwardBackward.TEST_FIRST_UP;
					System.out.println("Testing main finished");
					testMainState = TestMainStates.TEST_FINISHED;
				}
				break;
			case TEST_FINISHED:
				testMainState = TestMainStates.TEST_ALLSEQ;
				break;
		}
		// Task.remove(tester);
	}

	// test driving motors in combination
	public void speedRampMotors(int[] motNums)
	{
		if (null == motors)
		{
			motors = new DcMotor[motNums.length];// create array for motor
													// instances
			System.out.print("testing motor: ");
			for (int motCount = 0; motNums.length > motCount; motCount++)
			{// create motor instances
				System.out.print(motNums[motCount]);
				System.out.print(" ");
				motors[motCount] = AllObjects.motors[motNums[motCount]];
			}
			System.out.println();
		}
		switch (testForwardBackward)
		{
		// forward speed loop from 0 to full speed
			case TEST_FIRST_UP:
				if (Short.MAX_VALUE - (Short.MAX_VALUE / SPEED_STEP + 1) > pwmValue)
				{
					setMotorsSpeed(motors);
					pwmValue = (short) (pwmValue + (Short.MAX_VALUE / SPEED_STEP));
				}
				else
				{
					testForwardBackward = TestForwardBackward.TEST_DOWN;
				}
				break;
			// speed loop from full speed forward to full speed back
			case TEST_DOWN:
				if (Short.MIN_VALUE + (Short.MAX_VALUE / SPEED_STEP + 1) < pwmValue)
				{
					setMotorsSpeed(motors);
					pwmValue = (short) (pwmValue - (Short.MAX_VALUE / SPEED_STEP));
				}
				else
				{
					testForwardBackward = TestForwardBackward.TEST_SECOND_UP;
				}
				break;
			// speed loop from full speed speed back to 0
			case TEST_SECOND_UP:
				if (0 > pwmValue)
				{
					setMotorsSpeed(motors);
					pwmValue = (short) (pwmValue + (Short.MAX_VALUE / SPEED_STEP));
				}
				else
				{
					testForwardBackward = TestForwardBackward.TEST_FINISHED;
				}
				break;
			case TEST_FINISHED:
				pwmValue = 0;
				setMotorsSpeed(motors);
				break;
		}
		// System.out.print("Speed: ");
		// System.out.println(speed);
	}

	// set speed for all motors given in array
	public void setMotorsSpeed(DcMotor[] inMotors)
	{
		for (int motNum = 0; inMotors.length > motNum; motNum++)
		{
			inMotors[motNum].setSpeed(pwmValue);
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
			tester = new DcMotorTester();
			tester.period = 100;
			Task.install(tester);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
