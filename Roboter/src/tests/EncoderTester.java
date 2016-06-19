package tests;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import robot.middleware.AllObjects;

/**
 * class to test the wlan adapter
 *
 */
public class EncoderTester extends Task
{
	//Fixed test setting
	
	// number of steps used by the speed ramp from zero to full speed
	final private static int SPEED_STEP = 4;

	// states of the speed ramp tester
	private enum TestForwardBackward
	{
		TEST_FIRST_UP, TEST_DOWN, TEST_SECOND_UP, TEST_FINISHED
	};

	// actual speed setting
	private static Short speed = 0;
	// actually tested encoder
	private static int encoderCount = 0;
	// actual state of the speed ramp
	private static TestForwardBackward testForwardBackward = TestForwardBackward.TEST_FIRST_UP;

	private static EncoderTester tester; // This is me (singleton instance)

	public EncoderTester()
	{

		// configure console output
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("Testing class QuadratureEncoder");
	}

	public void action()
	{
		if (6 > encoderCount)
		{

			speedRampMotors(encoderCount);
			if (TestForwardBackward.TEST_FINISHED == testForwardBackward)
			{
				encoderCount++;
				testForwardBackward = TestForwardBackward.TEST_FIRST_UP;
			}
		}
		else
		{
			System.out.println("Resetting Encoders");
			encoderCount = 0;// reset counter
			for (int encoderResetCount = 0; 6 > encoderResetCount; encoderResetCount++)
			{
				AllObjects.encoders[encoderResetCount].resetPosition();
			}
		}
		System.out.print("Encoder ");
		for (int encCount = 0; 6 > encCount; encCount++)
		{
			System.out.print(" ");
			System.out.print(encCount);
			System.out.print("value:");
			System.out.print(AllObjects.encoders[encCount].getPosition());
		}
		System.out.println();
	}

	// test driving motors in combination
	public void speedRampMotors(int motNum)
	{
		switch (testForwardBackward)
		{
			case TEST_FIRST_UP:
				if (Short.MAX_VALUE - (Short.MAX_VALUE / SPEED_STEP + 1) > speed)
				{
					AllObjects.motors[motNum].setSpeed(speed);
					speed = (short) (speed + (Short.MAX_VALUE / SPEED_STEP));
				}
				else
				{
					testForwardBackward = TestForwardBackward.TEST_DOWN;
				}
				break;
			case TEST_DOWN:
				if (Short.MIN_VALUE + (Short.MAX_VALUE / SPEED_STEP + 1) < speed)
				{
					AllObjects.motors[motNum].setSpeed(speed);
					speed = (short) (speed - (Short.MAX_VALUE / SPEED_STEP));
				}
				else
				{
					testForwardBackward = TestForwardBackward.TEST_SECOND_UP;
				}
				break;
			case TEST_SECOND_UP:
				if (0 > speed)
				{
					AllObjects.motors[motNum].setSpeed(speed);
					speed = (short) (speed + (Short.MAX_VALUE / SPEED_STEP));
				}
				else
				{
					testForwardBackward = TestForwardBackward.TEST_FINISHED;
				}
				break;
			case TEST_FINISHED:
				speed = 0;
				AllObjects.motors[motNum].setSpeed(speed);
				break;
		}
		// System.out.print("Speed: ");
		// System.out.println(speed);
	}

	/**
	 * initialises the tester
	 */
	static
	{
		try
		{
			// create new tester
			tester = new EncoderTester();
			tester.period = 500;
			Task.install(tester);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
