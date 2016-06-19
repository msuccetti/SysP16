package common;


public class PinMap {
	public static final boolean SERVO_TPUA=true;
	public static final int SERVO_PWM = 12;
	
	public static final int SWITCH_STOP = 13;
	public static final int SWITCH_RESET = 14;
	public static final int SWITCH_START = 15;
	public static final int SWITCH_TEST =9;
	
	public static final int IR_SENSOR_CTRL_ADR_3 = -1; // not used
	public static final int IR_SENSOR_CTRL_ADR_2 = -1; // not used?
	public static final int IR_SENSOR_CTRL_ADR_1 = 6;
	public static final int IR_SENSOR_CTRL_ADR_0 = 5;
	public static final int IR_SENSOR_CTRL_TRIGGER = 8;
	public static final int IR_SENSOR_CTRL_OUT = 0;
	
	public static final int IR_SENSOR_INDEX_LEFT = 0; //TODO: fake values
	public static final int IR_SENSOR_INDEX_RIGHT = 1; //TODO: fake values
	public static final int IR_SENSOR_INDEX_BOTTOM = 2; //TODO: fake values
	
	public static final int LASER_SENSOR_OUT_1 = 9;
	public static final int LASER_SENSOR_OUT_2 = 10;
	
	
	public static final int WLAN_RXD = 2;
	public static final int WLAN_TXD = 2;
	public static final int WLAN_RESET = 11;
	/************* test board ******************************/
	public static final int TEST_LED_1 = 10;
	public static final int TEST_LED_2 = 11;
	public static final int TEST_LED_3 = 12;
	
	
	private PinMap(){} // private constructor to prevent instantiation
}
