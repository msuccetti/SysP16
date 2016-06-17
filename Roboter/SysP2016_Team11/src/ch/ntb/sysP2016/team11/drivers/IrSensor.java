package ch.ntb.sysP2016.team11.drivers;

import ch.ntb.sysP2016.team11.definitions.Consts;

public class IrSensor {

	private final int mSensorNr;
	private final IrSensorController mController; // The IR-Sensors are all attached to a controller
	
	/**
	 * @param aController	reference to the controller's interface
	 * @param aSensorNr		define at which controller address the sensor is attached 
	 */
	public IrSensor(IrSensorController aController, int aSensorNr) {
		mSensorNr = aSensorNr;
		mController = aController;
	}

	/**
	 * @return	determine if the sensor triggers (something is in its proximity)
	 */
	public boolean isTriggered(){
		short  signal = mController.getValue(mSensorNr);
		return signal > Consts.IR_SENSOR_THRESHOLD[mSensorNr];
	}
}
