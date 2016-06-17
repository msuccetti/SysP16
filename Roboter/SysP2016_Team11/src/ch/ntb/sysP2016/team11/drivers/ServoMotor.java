package ch.ntb.sysP2016.team11.drivers;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

public class ServoMotor {
	
	private TPU_PWM mPwm;
	private short mUpTime=0;
	
	private final short mRetractedPos;
	private final short mExtendedPos;
	
	/**
	 * Initialise the servo motor. Its initial state is turned off.
	 * 
	 * @param aTpuA		TPUA = true, TPUB = false
	 * @param aChannel	TPU channel number
	 * @param aPeriod	PWM period
	 * @param aRetractedPos	PWM high time representing the retracted position
	 * @param aExtendedPos	PWM high time representing the extended position
	 */
	public ServoMotor(boolean aTpuA, int aChannel, int aPeriod, short aRetractedPos, short aExtendedPos) {
		mPwm=new TPU_PWM(aTpuA, aChannel, aPeriod, 0);
		mRetractedPos = aRetractedPos;
		mExtendedPos = aExtendedPos;
	}

	/**
	 * move to retracted position
	 */
	public void retract(){
		if (mUpTime != mRetractedPos){
			mUpTime = mRetractedPos;
			mPwm.update(mUpTime);
		}
	}
	
	/**
	 * move to extended position
	 */
	public void extend(){
		if(mUpTime != mExtendedPos){
			mUpTime = mExtendedPos;
			mPwm.update(mUpTime);
		}
	}
	
	/**
	 * @return	determine wheter it is extended
	 */
	public boolean isExtended(){
		return mUpTime == mExtendedPos;
	}

	/**
	 * @return determine whether it is retracted
	 */
	public boolean isRetracted(){
		return !isExtended();
	}
	
	/**
	 * turn the motor off (it holds no more torque but doesn't get hot)
	 */
	public void off(){
		mPwm.update(0);
	}

}
