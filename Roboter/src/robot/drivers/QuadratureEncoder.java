package robot.drivers;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_FQD;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import common.Consts;
import common.Observer;

public class QuadratureEncoder extends Task
{

	private TPU_FQD mTpu;

	private float mTicksPerRotation; // defines how many increments result
										// in a turn of the attached motor
	private float mDistancePerRotation; // defines what kind of distance
										// (mm) is done in one turn of
										// the motor
	private boolean mDirection; // FORWARD or REVERSE
	private long mPosition; // driven distance in encoder increments since last
							// reset,
							// not mm!

	/**
	 * @param aTpuA
	 *            define whether TPUA or TPUB is used
	 * @param aChannel
	 *            define the TPU's chanel number
	 * @param aTicksPerRotation
	 *            define how many increments result in one motor rotation
	 * @param aDistancePerRotation
	 *            define how much distance is done in one motor rotation
	 */
	public QuadratureEncoder(boolean aPolarity, boolean aTpuA, int aChannel,
			float aTicksPerRotation, float aDistancePerRotation)
	{
		setEncoder(aPolarity, aTpuA, aChannel, aTicksPerRotation,
				aDistancePerRotation);
	}

	public QuadratureEncoder(Consts.EncoderSettings inSettings)
	{
		setEncoder(inSettings.mDirection, inSettings.mTpu, inSettings.mChannel,
				inSettings.mTicksPerRotation, inSettings.mDistancePerRotation);
	}

	public void setEncoder(boolean aPolarity, boolean aTpuA, int aChannel,
			float aTicksPerRotation, float aDistancePerRotation)
	{
		mDirection = aPolarity;
		mTpu = new TPU_FQD(aTpuA, aChannel);
		mTicksPerRotation = aTicksPerRotation;
		mDistancePerRotation = aDistancePerRotation;
		period = 5;// 5ms, probably the quickest task in the system
		Task.install(this);
	}

	/**
	 * @return how many rotations have been made since the last reset
	 */
	private float getRotations()
	{
		return (float) mPosition / mTicksPerRotation;
	}

	/**
	 * @return position (distance since last position reset [mm])
	 */
	public float getPosition()
	{
		float pos = getRotations() * mDistancePerRotation;
		if (Float.isNaN(pos))
		{
			printStatus();
		}
		return pos;
	}

	/**
	 * set position to zero
	 */
	public void resetPosition()
	{
		System.out.println("Encoder position reset");
		mPosition = 0;
		mTpu.setPosition(0);
	}

	public void printStatus()
	{
		System.out.print("ENC pr:");
		System.out.print(mPosition);
		System.out.print("pmm:");
		System.out.print(getPosition());
		System.out.print("tpr:");
		System.out.print(mTicksPerRotation);
		System.out.print("dpr:");
		System.out.print(mDistancePerRotation);
		System.out.print("tpu:");
		System.out.println(mTpu.getPosition());
	}

	public void action()
	{
		if (mDirection)
		{
			mPosition += mTpu.getPosition();
		}
		else
		{
			mPosition -= mTpu.getPosition();
		}

		// System.out.print("Distance update: ");
		// System.out.print(mTpu.getPosition()); System.out.print(", ");
		// System.out.println(mPosition);
		mTpu.setPosition(0);
	}

}
