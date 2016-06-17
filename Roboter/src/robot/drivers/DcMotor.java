package robot.drivers;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import common.Consts;

/*
 * Control a motor using sign magnitude. Each PWM channel represents a direction. There is always one PWM channel set to zero
 */
public class DcMotor
{
	// defines directions
	public static final boolean FORWARD = true;
	public static final boolean REVERSE = false;

	private TPU_PWM mPwm1;
	private TPU_PWM mPwm2;

	private boolean mBraking = false;
	private boolean mDirection; // FORWARD or REVERSE
	private short mSpeed; // this variable is positive all the time

	/**
	 * @param aTpuA
	 *            boolean stating if using TPUA or TPUB
	 * @param aChannel1
	 *            Channel for first PWM
	 * @param aChannel2
	 *            Channel for second PWM
	 * @param aEncoder
	 *            reference to the motor's encoder. Can be set to null.
	 */
	public DcMotor(boolean aPolarity, boolean aTpuA, int aChannel1,
			int aChannel2)
	{
		setMotor(aPolarity, aTpuA, aChannel1, aChannel2);
	}

	public DcMotor(Consts.MotorSettings inSettings)
	{
		setMotor(inSettings.polarity, inSettings.tpuA, inSettings.channel1,
				inSettings.channel1 + 1);
	}

	/**
	 * @param aTpuA
	 *            boolean stating if using TPUA or TPUB
	 * @param aChannel1
	 *            Channel for first PWM
	 * @param aChannel2
	 *            Channel for second PWM
	 * @param aEncoder
	 *            reference to the motor's encoder. Can be set to null.
	 */
	public void setMotor(boolean aPolarity, boolean aTpuA, int aChannel1,
			int aChannel2)
	{
		if (aPolarity)
		{// true -> take direction of motor as normal
			mPwm1 = new TPU_PWM(aTpuA, aChannel1, Consts.TEST_MOTOR_PWM_PERIOD,
					0);
			mPwm2 = new TPU_PWM(aTpuA, aChannel2, Consts.TEST_MOTOR_PWM_PERIOD,
					0);
		}
		else
		{// false -> take direction of motor as inverted
			mPwm2 = new TPU_PWM(aTpuA, aChannel1, Consts.TEST_MOTOR_PWM_PERIOD,
					0);
			mPwm1 = new TPU_PWM(aTpuA, aChannel2, Consts.TEST_MOTOR_PWM_PERIOD,
					0);
		}

		mDirection = FORWARD;
		mSpeed = 0;
	}

	/**
	 * @param aSpeed
	 *            positive value means forward, negative value reverse. Full
	 *            speed at Short.MAX_VALUE
	 */
	public void setSpeed(short aSpeed)
	{
		mDirection = aSpeed >= 0 ? FORWARD : REVERSE;
		mSpeed = aSpeed >= 0 ? aSpeed : (short) -aSpeed;
		update();
	}

	public short getSpeed()
	{
		if (FORWARD==mDirection)
		{
			return mSpeed;
		}else
		{
			return (short)-mSpeed;
		}
	}

	/**
	 * @param aDirection
	 *            FORWARD or REVERSE. Change direction without changing speed.
	 */
	public void setDirection(boolean aDirection)
	{
		mDirection = aDirection;
		update();
	}

	/**
	 * signalling full high time on both PWMs leads to brake. This is OK
	 * according to the motor controller's data sheet but I guess the motors may
	 * get a little bit warm...
	 */
	public void brake()
	{
		mPwm1.update(Short.MAX_VALUE);
		mPwm2.update(Short.MAX_VALUE);
		mBraking = true;
	}

	/**
	 * @return returns the current direction
	 */
	public boolean getDirection()
	{
		return mDirection;
	}

	/**
	 * switch the direction from FORWARD to REVERSE and vice versa.
	 */
	public void toggleDirection()
	{
		mDirection = !mDirection;
		update();
	}

	/**
	 * applies the defined speed and direction to the PWM output according to
	 * the sign magnitude procedure. This function releases the brakes!
	 */
	private void update()
	{
		mBraking = false;
		
		if (0==mSpeed)
		{
			mPwm1.update(0);
			mPwm2.update(0);
		}
		mPwm1.update(mDirection == FORWARD ? mSpeed / TPU_PWM.tpuTimeBase : 0);
		mPwm2.update(mDirection == REVERSE ? mSpeed / TPU_PWM.tpuTimeBase : 0);

	}

	/**
	 * @return returns the current PWM uptime
	 */
	public short getPwmUptime()
	{
		return mSpeed;
	}

	/**
	 * @return determine if the motor is running
	 */
	public boolean isRunning()
	{
		return mSpeed != 0;
	}

	/**
	 * @return determine whether the brakes are enabled
	 */
	public boolean isBraking()
	{
		return mBraking;
	}
	
	public void printStatus()
	{
		System.out.print("MOT:");
		if (FORWARD==mDirection)
		{
			System.out.print(" +");
		} else
		{
			System.out.print(" -");
		}
		System.out.println(mSpeed);
	}

}
