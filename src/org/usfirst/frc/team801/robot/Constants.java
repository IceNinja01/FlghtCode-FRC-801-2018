package org.usfirst.frc.team801.robot;

public class Constants {
	/*Work on using preferences tables
	public static double FrontRightBias = Robot.prefs.getDouble("FrontRightBias", 0.0);
	public static double FrontLeftBias = Robot.prefs.getDouble("FrontLeftBias", 0.0);
	public static double BackLeftBias = Robot.prefs.getDouble("BackLeftBias", 0.0);
	public static double BackRightBias = Robot.prefs.getDouble("BackRightBias", 0.0);
	*/
	public static final double[] AngleBias = {141.76, 345.7, 134.01, 91.5};
	
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

	//For chassis PID z axis
	public static double ultrakP = 0.02;
	public static double ultrakI = 0.0000001;
	public static double ultrakD = 1.0;
	public static double ultrakF = 0.001;
	
	public static double chassisAcceleration = 100;		//acceleration slope inches/sec
	public static double chassisVelocity = 100;			//Max Velocity inches/sec
	public static double wheelRotPerInch = 0.6;	//Sensor Shaft revolutions per wheel turn in inch
	
	//AutoSelector Constants
		public static final int LEFT = 0, CENTER = 1, RIGHT = 2;
		public static final int LOCATION = LEFT;
	//Elevator SetPoints
		public static double elevatorMotorBottomPos = 0.0; //inches
		public static double elevatorMotorLowPos = 24; //inches
		public static double elevatorMotorMidPos = 56; //inches
		public static double elevatorMotorTopPos = 72; //inches
		public static double elevatorMotorHookPos = 64; //inches
	//Lift
		public static double liftMotorBottomLimit = 0.0;//inches
		public static double liftMotorTopLimit = 2;//inches

		public static boolean LOGGING_ENABLE = false;
	

	
}
