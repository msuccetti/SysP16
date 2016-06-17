package robot.drivers;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;

public class Switch {
	private final MPIOSM_DIO mDio;
	
	/**
	 * @param aChannel the MPIOB channel number of the swithch
	 */
	public Switch(int aChannel){
		mDio=new MPIOSM_DIO(aChannel,false);
	}
	
	/**
	 * @return get the switch's current position
	 */
	public boolean get(){
		return mDio.get();
	}

}
