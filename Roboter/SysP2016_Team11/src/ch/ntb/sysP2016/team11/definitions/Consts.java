package ch.ntb.sysP2016.team11.definitions;

import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;
import ch.ntb.sysP2016.team11.utilities.Logger;

/*
 * Hardware specific constants are stored in this file
 * 
 */
public class Consts {
	
	//DC motors
	public static final int 	MOTOR_PWM_PERIOD = Short.MAX_VALUE/TPU_PWM.tpuTimeBase;
	public static final int 	MOTOR_GEAR_RATIO = 112;

	//encoder
	public static final float 	ENCODER_TICKS_PER_ROTATION = 16*4*MOTOR_GEAR_RATIO; //TODO check if this is correct

	// Servo
	public static final int 	SERVO_PWM_PERIOD = 20000000/TPU_PWM.tpuTimeBase;
	public static final short 	SERVO_RETRACTED_POS = 750000 /TPU_PWM.tpuTimeBase;
	public static final short 	SERVO_EXTENDED_POS = 2550000 /TPU_PWM.tpuTimeBase;
	
	//IR sensors
	public static final short[] IR_SENSOR_THRESHOLD = {500, 500, 500};
	//laser sensors
	//WiFi
	//wheel
	public static final float 	WHEEL_CIRCUMFERENCE = 0; // TODO: insert proper value in millimeter
	
	//Software
	public static final int LOG_LEVEL = Logger.STATE_INFO_DETAILED;
	
	/************ test board *******************************/

	public static final int 	TEST_MOTOR_PWM_PERIOD = Short.MAX_VALUE/TPU_PWM.tpuTimeBase;
	public static final float 	TEST_ENCODER_TICKS_PER_ROTATION = 4243.592F;

	private Consts() {}
}
