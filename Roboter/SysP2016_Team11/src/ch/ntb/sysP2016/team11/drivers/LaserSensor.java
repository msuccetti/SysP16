package ch.ntb.sysP2016.team11.drivers;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;

public class LaserSensor {
	private final MPIOSM_DIO mDio;
	
	public LaserSensor(int aChannel) {
		mDio=new MPIOSM_DIO(aChannel,false);
	}
	
	public boolean objectVisible(){
		//TODO: check if true==object visible!!!
		return mDio.get();
	}

}
