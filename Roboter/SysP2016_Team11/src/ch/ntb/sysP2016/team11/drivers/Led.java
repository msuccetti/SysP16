package ch.ntb.sysP2016.team11.drivers;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;

public class Led {
	private final MPIOSM_DIO mDio;
	private boolean mCurrentState=false;
	
	/**
	 * @param aChannel	MPIOB channel number
	 */
	public Led(int aChannel){
		mDio=new MPIOSM_DIO(aChannel,true);
		off();
	}
	
	/**
	 * turn the lamp on
	 */
	public void on(){
		mCurrentState=true;
		mDio.set(mCurrentState);
		
	}
	
	/**
	 * @param aOn	define whether the lamp should be on or off
	 */
	public void set(boolean aOn){
		if(aOn){
			on();
		} 
		else {
			off();
		}
	}
	
	/**
	 * turn the lamp off
	 */
	public void off(){
		mCurrentState=false;
		mDio.set(mCurrentState);
	}
	
	/**
	 * if the lamp is on, turn it off and vice versa.
	 */
	public void toggle(){
	mCurrentState= !mCurrentState;
	mDio.set(mCurrentState);
	}
	
	/**
	 * @return	true if the led is on, false otherwise
	 */
	public boolean getCurrentState(){
		return mCurrentState;
	}

}
