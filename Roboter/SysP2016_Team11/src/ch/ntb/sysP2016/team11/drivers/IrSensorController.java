package ch.ntb.sysP2016.team11.drivers;

import ch.ntb.inf.deep.runtime.mpc555.driver.HLC1395Pulsed;
import ch.ntb.sysP2016.team11.definitions.PinMap;

/**
 * This class provides an interface to the static methods of the
 * IR-Sensor driver class provided in the DEEP libraries. 
 * It is a singleton because the underlying class is static therefore
 * only one IR-sensor controller can be used.
 *
 */
public class IrSensorController {

	private static IrSensorController mInstance=null; //singleton pattern
	
	/**
	 * initialise the IR-sensor driver library class
	 */
	private IrSensorController() {
		HLC1395Pulsed.init(PinMap.IR_SENSOR_CTRL_ADR_3,
				PinMap.IR_SENSOR_CTRL_ADR_2,
				PinMap.IR_SENSOR_CTRL_ADR_1,
				PinMap.IR_SENSOR_CTRL_ADR_0, 
				PinMap.IR_SENSOR_CTRL_TRIGGER, 
				PinMap.IR_SENSOR_CTRL_OUT);
		HLC1395Pulsed.start();
	}
	
	/**
	 * @param aSensorNr	the sensors address
	 * @return			the current reading of the desired sensor
	 */
	public short getValue(int aSensorNr){
		return HLC1395Pulsed.read(aSensorNr);
	}
	
	/**
	 * Singleton pattern.
	 * @return	instance
	 */
	public static IrSensorController getInstance(){
		if (mInstance==null){
			mInstance = new IrSensorController();
		}
		return mInstance;
	}
}
