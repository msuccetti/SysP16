package ch.ntb.sysP2016.team11;

import ch.ntb.sysP2016.team11.procedure.milestone3.DemoIrSensor;
import ch.ntb.sysP2016.team11.procedure.milestone3.DemoStateChart;

public class Start {
	private Sys mSystem = Sys.getInstance();

	private Start() {
		new DemoStateChart(mSystem, 50);
		new DemoIrSensor(mSystem.IR_SENSOR_LEFT, mSystem.IR_SENSOR_RIGHT, mSystem.IR_SENSOR_BOTTOM, mSystem.TEST_LED_1, mSystem.TEST_LED_2, mSystem.TEST_LED_3);
		
	}

	
	
	static{
		new Start();
		}
}
