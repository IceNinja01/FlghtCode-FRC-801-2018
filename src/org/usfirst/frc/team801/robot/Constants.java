package org.usfirst.frc.team801.robot;

public class Constants {
	/*Work on using preferences tables
	public static double FrontRightBias = Robot.prefs.getDouble("FrontRightBias", 0.0);
	public static double FrontLeftBias = Robot.prefs.getDouble("FrontLeftBias", 0.0);
	public static double BackLeftBias = Robot.prefs.getDouble("BackLeftBias", 0.0);
	public static double BackRightBias = Robot.prefs.getDouble("BackRightBias", 0.0);
	*/
	public static final double[] AngleBias = {177,190.0,183,297};
	
	/** which Talon on CANBus*/
	public static final int kTalonID = 0;

	/**
	 * How many sensor units per rotation.
 	 * Using CTRE Magnetic Encoder.
	 * @link https://github.com/CrossTheRoadElec/Phoenix-Documentation#what-are-the-units-of-my-sensor
	 */
	public static final double kSensorUnitsPerRotation = 4096;

	/**
	 * Which PID slot to pull gains from.  Starting 2018, you can choose 
	 * from 0,1,2 or 3.  Only the first two (0,1) are visible in web-based configuration.
	 */
	public static final int kSlotIdx = 0;
	
	/** 
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops.  
	 * For now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;
	/**
	 * set to zero to skip waiting for confirmation, set to nonzero to wait
	 * and report to DS if action fails.
	 */
	public static final int kTimeoutMs = 10;
	
	/**
	 * Base trajectory period to add to each individual 
	 * trajectory point's unique duration.  This can be set
	 * to any value within [0,255]ms.
	 */
	public static final int kBaseTrajPeriodMs = 0;
	
	/**
	 * Motor deadband, set to 1%.
	 */
	public static final double kNeutralDeadband  = 0.01;

	public static double ultrakP = 0.02;
	public static double ultrakI = 0.0000001;
	public static double ultrakD = 1.0;

	public static double chassisAcceleration = 2.5;		//acceleration slope inches/sec
	public static double chassisVelocity = 5.0;			//Max Velocity inches/sec
	public static double wheelRotPerInch = 7.5/12.5;	//Sensor Shaft revolutions per wheel turn in inch

	
	
}
