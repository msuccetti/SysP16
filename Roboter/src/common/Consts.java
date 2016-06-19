package common;

import robot.drivers.DcMotor;
import robot.drivers.QuadratureEncoder;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_FQD;
import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_PWM;

/*
 * Hardware specific constants are stored in this file
 * 
 */
public class Consts
{
	// tpu A is true in settings
	private static final boolean TPU_A = true;
	// tpu B is true in settings
	private static final boolean TPU_B = false;
	// normal polarity is true
	private static final boolean DIRECT = true;
	// reverse polarity is true
	private static final boolean INVERTED = false;

	// Settings for a single motor
	public static class MotorSettings
	{
		public boolean polarity;
		public boolean tpuA;
		public int channel1;

		public MotorSettings(boolean inPolarity, boolean inTpuA, int inChannel1)
		{
			polarity = inPolarity;
			tpuA = inTpuA;
			channel1 = inChannel1;
		}
	}

	// DC motors
	public static class DcMotors
	{
		public static final int PWM_PERIOD = Short.MAX_VALUE
				/ TPU_PWM.tpuTimeBase;
		public static final int GEAR_RATIO = 112;

		public static final float WHEEL_CIRCUMFERENCE = (float) (54.0
				* 3.1415926);

		public static final int MOTOR_VL_PWM1 = 0;
		public static final int MOTOR_VR_PWM1 = 4;
		public static final int MOTOR_SM_PWM1 = 8;
		public static final int MOTOR_HL_PWM1 = 0;
		public static final int MOTOR_HR_PWM1 = 4;
		public static final int MOTOR_HM_PWM1 = 8;

		// Settings for all motors
		public static final MotorSettings motorSettings[] =
		{
				// VL
				new MotorSettings(INVERTED, TPU_A, MOTOR_VL_PWM1),
				// VR
				// new MotorSettings(INVERTED, TPU_A, MOTOR_VR_PWM1),
				// rewired due to damaged I/O
				new MotorSettings(INVERTED, TPU_B, 12),
				// SM
				new MotorSettings(DIRECT, TPU_A, MOTOR_HL_PWM1),
				// HL
				new MotorSettings(DIRECT, TPU_B, MOTOR_HL_PWM1),
				// HR
				new MotorSettings(DIRECT, TPU_B, MOTOR_HR_PWM1),
				// HM
				new MotorSettings(INVERTED, TPU_B, MOTOR_HM_PWM1) };

		// motor indices (see motorSettings[])
		public final static int MOTOR_VL = 0;
		public final static int MOTOR_VR = 1;
		public final static int MOTOR_HL = 3;
		public final static int MOTOR_HR = 4;
		public final static int MOTOR_SM = 2;
		public final static int MOTOR_HM = 5;
	}

	// Settings for a single encoder
	public static class EncoderSettings
	{
		public boolean mTpu;

		public final int mChannel;
		public final float mTicksPerRotation;
		public final float mDistancePerRotation;
		public boolean mDirection;
		public long mDistance;

		public EncoderSettings(boolean aPolarity, boolean aTpuA, int aChannel,
				float aTicksPerRotation, float aDistancePerRotation)
		{
			mDirection = aPolarity;
			mTpu = aTpuA;
			mTicksPerRotation = aTicksPerRotation;
			mDistancePerRotation = aDistancePerRotation;
			mChannel = aChannel;
		}
	}

	public static final int ENCODER_VL_PWM = 6;// 2. PWM on next pin
	public static final int ENCODER_VR_PWM = 2;// 2. PWM on next pin
	public static final int ENCODER_SM_PWM = 10;// 2. PWM on next pin
	public static final int ENCODER_HL_PWM = 6;// 2. PWM on next pin
	public static final int ENCODER_HR_PWM = 2;// 2. PWM on next pin
	public static final int ENCODER_HM_PWM = 10;// 2. PWM on next pin

	// encoder
	public static class Encoder
	{
		// period of encoder tasks [ms]
		public static final int PERIOD = 5;
		// pulses / revolution
		public static final float TICKS_PER_ROTATION = 16 * 4
				* DcMotors.GEAR_RATIO;
		// Settings for all encoders
		public static final EncoderSettings encoderSettings[] =
		{
				// VL
				new EncoderSettings(DIRECT, TPU_A, ENCODER_VL_PWM,
						TICKS_PER_ROTATION, DcMotors.WHEEL_CIRCUMFERENCE),

				// VR
				new EncoderSettings(INVERTED, TPU_A, ENCODER_VR_PWM,
						TICKS_PER_ROTATION, DcMotors.WHEEL_CIRCUMFERENCE),
				// SM
				new EncoderSettings(INVERTED, TPU_B, ENCODER_SM_PWM,
						TICKS_PER_ROTATION, (float) 1.25),
				// HL
				new EncoderSettings(DIRECT, TPU_B, ENCODER_HL_PWM,
						TICKS_PER_ROTATION, DcMotors.WHEEL_CIRCUMFERENCE),
				// HR
				new EncoderSettings(INVERTED, TPU_B, ENCODER_HR_PWM,
						TICKS_PER_ROTATION, DcMotors.WHEEL_CIRCUMFERENCE),
				// HM
				new EncoderSettings(INVERTED, TPU_A, ENCODER_HM_PWM,
						TICKS_PER_ROTATION, (float) 1.25) };

		// encoder indices (see encoderSettings[])
		public final static int ENCODER_VL = 0;
		public final static int ENCODER_VR = 1;
		public final static int ENCODER_SM = 2;
		public final static int ENCODER_HL = 3;
		public final static int ENCODER_HR = 4;
		public final static int ENCODER_HM = 5;
	}

	// Settings for a single motor controller
	public static class DcMotorSpeedPIDSettings
	{
		public int period; // period in [ms]
		public DcMotor motor; // the controlled motor
		public QuadratureEncoder encoder;// the encoder to be used for control
		public float P;// pid proportional
		public float I;// pid integral
		public float D;// pid differential

		public DcMotorSpeedPIDSettings(int inPeriod, DcMotor inMotor,
				QuadratureEncoder inQuadratureEncoder, float inP, float inI,
				float inD)
		{
			period = inPeriod;
			motor = inMotor;
			encoder = inQuadratureEncoder;
			P = inP;
			I = inI;
			D = inD;
		}
	}

	// motor controllers
	public static class DcMotorSpeedPID
	{
		// period of pid tasks [ms]
		public static final int PERIOD = 10;

		// motor indices
		public final static int PID_VL = 0;
		public final static int PID_VR = 1;
		public final static int PID_HL = 3;
		public final static int PID_HR = 4;
		public final static int PID_SM = 2;
		public final static int PID_HM = 5;
	}

	// positioners
	public static class DcMotorPositioner
	{
		public final static int POSITIONER_VL = 0;
		public final static int POSITIONER_VR = 1;
		public final static int POSITIONER_SM = 2;
		public final static int POSITIONER_HL = 3;
		public final static int POSITIONER_HR = 4;
		public final static int POSITIONER_HM = 5;

	}

	// Servo
	public static final int SERVO_PWM_PERIOD = 20000000 / TPU_PWM.tpuTimeBase;
	public static final short SERVO_RETRACTED_POS = 750000
			/ TPU_PWM.tpuTimeBase;
	public static final short SERVO_EXTENDED_POS = 2550000
			/ TPU_PWM.tpuTimeBase;

	// IR sensors
	public static class IrSensors
	{
		public static final short[] SENSOR_THRESHOLD =
		{ 500, 500, 500 };
	}

	// laser sensors
	public static class LaserSensors
	{

	}

	// WiFi
	public static class Wlan
	{
		/**
		 * true->wlan needs to provide a server (wlan hotspot)
		 */
		public final static boolean MASTER = true;

		/**
		 * ssid, if configured as a wlan hotspot
		 */
		public final static String SSID = "SysPNet_Team11";

		/**
		 * local ip if configured as a wlan hotspot
		 */
		public final static String IP_LOCAL = "169.254.11.2";

		/**
		 * remote ip (I guess it is never used)
		 */
		public final static String IP_REMOTE = "169.254.11.1";

		/**
		 * reset pin of wlan adapter
		 */
		public final static int WLAN_RESET_PIN = 5;

		/**
		 * Wlan task period [ms]
		 */
		public final static int PERIOD = 1000;

	}
	
	//sensors
	public static class Sensors
	{
		//channels of IR sensors
		public static final int IR_SENSOR_CHASSIS_RIGHT_CHANNEL=0;
		public static final int IR_SENSOR_CHASSIS_LEFT_CHANNEL=1;
	}
	
	//buttons
	public static class Buttons
	{
		 // Wait time for button cycles to be counted stable [ms]
		public static final int WAIT_TIME_STABLE=100;
		 // the I/O pin to connect the start button to
		public static final byte IO_BUTTON_START = 89;
	}

	
	// Software
	public static final int LOG_LEVEL = Logger.STATE_INFO_DETAILED;

	/************ test board *******************************/

	public static final int TEST_MOTOR_PWM_PERIOD = Short.MAX_VALUE
			/ TPU_PWM.tpuTimeBase;

	public class StateMachine
	{
		/**
		 * task period [ms]
		 */
		public final static int PERIOD = 1000;

		// robot was placed at the right border is true
		private static final boolean PLACED_AT_RIGHT_BORDER = true;
		// robot was placed at the right border is true
		private static final boolean PLACED_AT_LEFT_BORDER = false;

	}
}
