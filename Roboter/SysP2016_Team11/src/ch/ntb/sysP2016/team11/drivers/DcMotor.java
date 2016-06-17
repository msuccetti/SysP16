package ch.ntb.sysP2016.team11.drivers;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import ch.ntb.sysP2016.team11.definitions.Consts;


/*
 * Control a motor using sign magnitude. Each PWM channel represents a direction. There is always one PWM channel set to zero
 */
public class DcMotor {
	//defines directions
	public static final boolean FORWARD = true;
	public static final boolean REVERSE = false;
	
	private TPU_PWM mPwm1;
	private TPU_PWM mPwm2;
	
	private QuadratureEncoder mEncoder;
	
	private boolean mBraking = false;	
	private boolean mDirection; //FORWARD or REVERSE
	private short mSpeed; // this variable is positive all the time

	
	/**
	 * @param aTpuA		boolean stating if using TPUA or TPUB
	 * @param aChannel1	Channel for first PWM
	 * @param aChannel2	Channel for second PWM
	 * @param aEncoder	reference to the motor's encoder. Can be set to null.
	 */
	public DcMotor(boolean aTpuA, int aChannel1, int aChannel2, QuadratureEncoder aEncoder) {
		mPwm1= new TPU_PWM(aTpuA, aChannel1, Consts.TEST_MOTOR_PWM_PERIOD, 0);
		mPwm2= new TPU_PWM(aTpuA, aChannel2, Consts.TEST_MOTOR_PWM_PERIOD, 0);
		mDirection = FORWARD;
		mSpeed=0;
		mEncoder = aEncoder;
	}
	
	/**
	 * @param aSpeed	positive value means forward, negative value reverse. Full speed at Short.MAX_VALUE
	 */
	public void setSpeed(short aSpeed){
		// prevent too large values because abs(MIN_VALUE) > abs(MAX_VALUE)
		if(aSpeed == Short.MIN_VALUE){
			aSpeed++;
		}
		
		mDirection= aSpeed>=0 ? FORWARD : REVERSE;
		mSpeed = aSpeed>=0 ? aSpeed : (short)-aSpeed;
		update();
	}
	
	/**
	 * @param aDirection	FORWARD or REVERSE. Change direction without changing speed.
	 */
	public void setDirection(boolean aDirection){
		mDirection = aDirection;
		update();
	}
	
	/**
	 * signalling full high time on both PWMs leads to brake.
	 * This is OK according to the motor controller's data sheet but I guess
	 * the motors may get a little bit warm...
	 */
	public void brake(){
		mPwm1.update(Short.MAX_VALUE);
		mPwm1.update(Short.MAX_VALUE);
		mBraking=true;
	}
	
	/**
	 * @return returns the current direction
	 */
	public boolean getDirection(){
		return mDirection;
	}
	
	/**
	 * switch the direction from FORWARD to REVERSE and vice versa.
	 */
	public void toggleDirection(){
		mDirection = !mDirection;
		update();
	}
	
	
	/**
	 * applies the defined speed and direction to the PWM output according to the 
	 * sign magnitude procedure.
	 * This function releases the brakes!
	 */
	private void update(){
		mPwm1.update(mDirection == FORWARD ? mSpeed/TPU_PWM.tpuTimeBase : 0);
		mPwm2.update(mDirection == REVERSE ? mSpeed/TPU_PWM.tpuTimeBase : 0);

		mBraking=false;
		
	}
	
	/**
	 * @return	returns the current PWM uptime
	 */
	public short getPwmUptime(){
		return mSpeed;
	}
	
	/**
	 * @return	determine if the motor is running
	 */
	public boolean isRunning(){
		return mSpeed!=0;
	}
	
	/**
	 * @return determine whether the brakes are enabled
	 */
	public boolean isBraking(){
		return mBraking;
	}
	
	/**
	 * @param aEncoder define a quadrature encoder for this motor
	 */
	public void setItsEncoder(QuadratureEncoder aEncoder){
		mEncoder = aEncoder;
	}
	/**
	 * @return		reference of the quadrature encoder attached to the motor
	 */
	public QuadratureEncoder getEncoder(){
		return mEncoder;
	}
}
