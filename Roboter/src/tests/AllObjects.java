package tests;

import robot.drivers.DcMotor;
import robot.drivers.QuadratureEncoder;
import robot.drivers.Wlan;
import robot.middleware.DcMotorPositioner;
import robot.middleware.DcMotorSpeedPID;
import robot.middleware.DriveInDirections;
import robot.middleware.DriveCtrl;
import common.Consts;
import common.Consts.DcMotorSpeedPIDSettings;

public class AllObjects
{
	// public static final AllObjects instance;

	// all encoders in a single array
	public static QuadratureEncoder[] encoders = new QuadratureEncoder[]
	{
			new QuadratureEncoder(
					Consts.Encoder.encoderSettings[Consts.Encoder.ENCODER_VL]),
			new QuadratureEncoder(
					Consts.Encoder.encoderSettings[Consts.Encoder.ENCODER_VR]),
			new QuadratureEncoder(
					Consts.Encoder.encoderSettings[Consts.Encoder.ENCODER_SM]),
			new QuadratureEncoder(
					Consts.Encoder.encoderSettings[Consts.Encoder.ENCODER_HL]),
			new QuadratureEncoder(
					Consts.Encoder.encoderSettings[Consts.Encoder.ENCODER_HR]),
			new QuadratureEncoder(
					Consts.Encoder.encoderSettings[Consts.Encoder.ENCODER_HM]) };

	// all motors in a single array
	public static DcMotor[] motors = new DcMotor[]
	{
			new DcMotor(Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_VL]),
			new DcMotor(Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_VR]),
			new DcMotor(Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_SM]),
			new DcMotor(Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_HL]),
			new DcMotor(Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_HR]),
			new DcMotor(Consts.DcMotors.motorSettings[Consts.DcMotors.MOTOR_HM]), };

	// Settings for all controllers
	public static Consts.DcMotorSpeedPIDSettings DcMotorSpeedPIDSettings[] =
	{ new DcMotorSpeedPIDSettings// VL
			(Consts.DcMotorSpeedPID.PERIOD,// motor period
					motors[Consts.DcMotors.MOTOR_VL],// motor used
					encoders[Consts.Encoder.ENCODER_VL],// encoder used
					3 * Consts.DcMotorSpeedPID.PERIOD,// pid proportional
					0 * Consts.DcMotorSpeedPID.PERIOD,// pid integral
					(float) -0.5 * Consts.DcMotorSpeedPID.PERIOD),// pid
																	// differential

			new DcMotorSpeedPIDSettings// VR
			(Consts.DcMotorSpeedPID.PERIOD,// motor period
					motors[Consts.DcMotors.MOTOR_VR],// motor used
					encoders[Consts.Encoder.ENCODER_VR],// encoder used
					3 * Consts.DcMotorSpeedPID.PERIOD,// pid proportional
					0 * Consts.DcMotorSpeedPID.PERIOD,// pid integral
					(float) -0.5 * Consts.DcMotorSpeedPID.PERIOD),// pid
																	// differential

			new DcMotorSpeedPIDSettings// SM
			(Consts.DcMotorSpeedPID.PERIOD,// motor period
					motors[Consts.DcMotors.MOTOR_SM],// motor used
					encoders[Consts.Encoder.ENCODER_SM],// encoder used
					300 * Consts.DcMotorSpeedPID.PERIOD,// pid proportional
					0 * Consts.DcMotorSpeedPID.PERIOD,// pid integral
					(float) -0.5 * Consts.DcMotorSpeedPID.PERIOD),// pid
																	// differential

			new DcMotorSpeedPIDSettings// HL
			(Consts.DcMotorSpeedPID.PERIOD,// motor period
					motors[Consts.DcMotors.MOTOR_HL],// motor used
					encoders[Consts.Encoder.ENCODER_HL],// encoder used
					3 * Consts.DcMotorSpeedPID.PERIOD,// pid proportional
					0 * Consts.DcMotorSpeedPID.PERIOD,// pid integral
					(float) -0.5 * Consts.DcMotorSpeedPID.PERIOD),// pid
																	// differential

			new DcMotorSpeedPIDSettings// HR
			(Consts.DcMotorSpeedPID.PERIOD,// motor period
					motors[Consts.DcMotors.MOTOR_HR],// motor used
					encoders[Consts.Encoder.ENCODER_HR],// encoder used
					3 * Consts.DcMotorSpeedPID.PERIOD,// pid proportional
					0 * Consts.DcMotorSpeedPID.PERIOD,// pid integral
					(float) -0.5 * Consts.DcMotorSpeedPID.PERIOD),// pid
																	// differential

			new DcMotorSpeedPIDSettings(Consts.DcMotorSpeedPID.PERIOD,// motor
																		// period
					motors[Consts.DcMotors.MOTOR_HM],// motor used
					encoders[Consts.Encoder.ENCODER_HM],// encoder used
					300 * Consts.DcMotorSpeedPID.PERIOD,// pid proportional
					0 * Consts.DcMotorSpeedPID.PERIOD,// pid integral
					(float) -0.5 * Consts.DcMotorSpeedPID.PERIOD),// pid
																	// differential
	};

	// all pid's in a single array
	public static DcMotorSpeedPID[] pidControllers = new DcMotorSpeedPID[]
	{
			new DcMotorSpeedPID(
					DcMotorSpeedPIDSettings[Consts.DcMotorSpeedPID.PID_VL]),// VL
			new DcMotorSpeedPID(
					DcMotorSpeedPIDSettings[Consts.DcMotorSpeedPID.PID_VR]),// VR
			new DcMotorSpeedPID(
					DcMotorSpeedPIDSettings[Consts.DcMotorSpeedPID.PID_SM]),// SM
			new DcMotorSpeedPID(
					DcMotorSpeedPIDSettings[Consts.DcMotorSpeedPID.PID_HL]),// HL
			new DcMotorSpeedPID(
					DcMotorSpeedPIDSettings[Consts.DcMotorSpeedPID.PID_HR]),// HR
			new DcMotorSpeedPID(
					DcMotorSpeedPIDSettings[Consts.DcMotorSpeedPID.PID_HM]) // HM
	};

	// all positioners in a single array
	public static DcMotorPositioner[] dcMotorPositioners = new DcMotorPositioner[]
	{
			new DcMotorPositioner((float) 0.1,
					pidControllers[Consts.DcMotorSpeedPID.PID_VL], (float) 50),// VL
			new DcMotorPositioner((float) 0.1,
					pidControllers[Consts.DcMotorSpeedPID.PID_VR], (float) 50),// VR
			new DcMotorPositioner((float) 0.1,
					pidControllers[Consts.DcMotorSpeedPID.PID_SM], (float) 3),// SM
			new DcMotorPositioner((float) 0.1,
					pidControllers[Consts.DcMotorSpeedPID.PID_HL], (float) 50),// HL
			new DcMotorPositioner((float) 0.1,
					pidControllers[Consts.DcMotorSpeedPID.PID_HR], (float) 50),// HR
			new DcMotorPositioner((float) 0.1,
					pidControllers[Consts.DcMotorSpeedPID.PID_HM], (float) 3),// HM
	};

	
//testing only !!!!!!!
	public static DriveInDirections driveInDirections=new DriveInDirections();
	
	public static DriveCtrl driveCtrl=new DriveCtrl();
	
	//initialise wlan
	public static Wlan wlan = new Wlan();

	
}
