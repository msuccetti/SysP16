package ch.ntb.sysP2016.team11.procedure.milestone3;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.sysP2016.team11.drivers.IrSensor;
import ch.ntb.sysP2016.team11.drivers.Led;

/**
 * This demo task shows the IR sensor state by driving a LED accordingly
 * It uses the three IR sensors with address 0, 1 and 2.
 * 
 * @author joel
 */
public class DemoIrSensor extends Task{
	
	private final IrSensor mSens0;
	private final IrSensor mSens1;
	private final IrSensor mSens2;
	
	private final Led mLed0;
	private final Led mLed1;
	private final Led mLed2;
	

	public DemoIrSensor(IrSensor aSens0, IrSensor aSens1, IrSensor aSens2, Led aLed0, Led aLed1, Led aLed2) {
		mSens0 = aSens0;
		mSens1 = aSens1;
		mSens2 = aSens2;
		
		mLed0 = aLed0;
		mLed1 = aLed1;
		mLed2 = aLed2;
		period=50;
		Task.install(this);
	}

	
	public void action(){
		mLed0.set(mSens0.isTriggered());
		mLed1.set(mSens1.isTriggered());
		mLed2.set(mSens2.isTriggered());
	}
}
