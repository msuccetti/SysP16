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

		public static final float WHEEL_CIRCUMFERENCE = (float) (54.0 * 3.1415926);

		// Settings for all motors
		public static final MotorSettings motorSettings[] =
		{
				// VL
				new MotorSettings(INVERTED, TPU_A, 0),
				// VR
				// new MotorSettings(INVERTED, TPU_A, 4),
				// rewired due to damaged I/O
				new MotorSettings(INVERTED, TPU_B, 12),
				//SM
				new MotorSettings(DIRECT, TPU_A, 8),
				//HL
				new MotorSettings(DIRECT, TPU_B, 0),
				//HR
				new MotorSettings(DIRECT, TPU_B, 4),
				//HM
				new MotorSettings(INVERTED, TPU_B, 8)
		};

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

	// encoder
	public static class Encoder
	{
		// period of encoder tasks [ms]
		public static final int PERIOD = 5;
		// pulses / revolution
		public static final float TICKS_PER_ROTATION = 16 * 4 * DcMotors.GEAR_RATIO;
		// Settings for all encoders
		public static final EncoderSettings encoderSettings[] =
		{
				/*
				 * new EncoderSettings(DIRECT, TPU_A, 6, TICKS_PER_ROTATION,//VL
				 * DcMotors.WHEEL_CIRCUMFERENCE),
				 */
				// VL rewired due to damaged I/O
				new EncoderSettings(DIRECT, TPU_B, 14, TICKS_PER_ROTATION,
						DcMotors.WHEEL_CIRCUMFERENCE),
				// VR
				new EncoderSettings(INVERTED, TPU_A, 2, TICKS_PER_ROTATION,
						DcMotors.WHEEL_CIRCUMFERENCE),
				// SM
				new EncoderSettings(INVERTED, TPU_B, 10, TICKS_PER_ROTATION,
						(float) 1.25),
				// HL
				new EncoderSettings(DIRECT, TPU_B, 6, TICKS_PER_ROTATION,
						DcMotors.WHEEL_CIRCUMFERENCE),
				// HR
				new EncoderSettings(INVERTED, TPU_B, 2, TICKS_PER_ROTATION,
						DcMotors.WHEEL_CIRCUMFERENCE),
				// HM
				new EncoderSettings(INVERTED, TPU_A, 10, TICKS_PER_ROTATION,
						(float) 1.25) };
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
	public static final short SERVO_RETRACTED_POS = 750000 / TPU_PWM.tpuTimeBase;
	public static final short SERVO_EXTENDED_POS = 2550000 / TPU_PWM.tpuTimeBase;

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
	}
}
