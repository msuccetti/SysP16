package robot.drivers;

import ch.ntb.inf.deep.runtime.mpc555.driver.HLC1395Pulsed;

/**
 * 
 * @author msuccetti
 *
 *this class represents an IR sensor of type HLC1395
 */
public class IrSensor
{
	private static final int ADR3PIN = -1;//pin to select IR channel (bit 3, not used)
	private static final int ADR2PIN = 7;//pin to select IR channel (bit 2)
	private static final int ADR1PIN = 8;//pin to select IR channel (bit 1)
	private static final int ADR0PIN = 6;//pin to select IR channel (bit 0)
	private static final int TRGPIN = 9;//pin to trigger IR value acquiring
	private static final int ANPIN = 0;//pin to read IR value from

	// the sensor driver for all HLC1395 sensors
	static private boolean hLC1395PulsedInitialised=false;
	
	//the channel of the IR sensor
	private int sensorChannel;
	
	//constructor
	public IrSensor(int inChannel)
	{
		if (false == hLC1395PulsedInitialised)
		{//driver not initialised yet
			//initialise driver
			HLC1395Pulsed.init(ADR3PIN, ADR2PIN, ADR1PIN, ADR0PIN, TRGPIN, ANPIN);
			//start reading the sensors
			HLC1395Pulsed.start();
			//signal initialisation finished
			hLC1395PulsedInitialised=true;
		}
		
		//associate the sensor with a channel
		sensorChannel = inChannel;
	}
	
	public int readValue()
	{
		//read sensor value
		return HLC1395Pulsed.read(sensorChannel);
	}
}

