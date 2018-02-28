package SwerveClass;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Utilities.RollingAverage;
import org.usfirst.frc.team801.robot.Utilities.Utils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("unused")
public class SwerveDrive implements MotorSafety {
	//Variables to be used are set to private
	
	protected MotorSafetyHelper m_safetyHelper;
	protected PIDOutput[] pidOutput = new PIDOutput[4]; 
	private PIDController[] pidTurnController  = new PIDController[4];
	private PIDSource[] pidTurnSource  = new PIDSource[4];
	public static final double kDefaultExpirationTime = 0.1;
	public static final double kDefaultMaxOutput = 1.0;

	protected double temp, STR, FWD, RCW;
	protected double A,B,C,D, max;
	protected double L = 33.5;
	protected double W = 28.25;
	protected double R = Math.sqrt(L*L+W*W);
	protected double kP = 0.005;
	protected double kI = 0.00;
	protected double kD = 0.0;
	protected double timeUs;
	private String motorName[] = {"FrontRight","FrontLeft","BackLeft","BackRight"};

	private double[] oldAngle = {0,0,0,0};
	private double maxDriveVoltage = 0.75;
	private double maxTurnVoltage = 1.0;
	private int deadBand = 2; //
	private Team801TalonSRX[] driveMotors  = new Team801TalonSRX[4];
	private Team801TalonSRX[] turnMotors  = new Team801TalonSRX[4];
    private double[] wheelAngles = new double[4];
	private double[] wheelSpeeds = new double[4];
	private double[] angleJoyStickDiff = new double[4];
	private double[] angleError = new double[4];

	private RollingAverage xavg;
	private RollingAverage yavg;
	private RollingAverage zavg;
	private int velocity = (int) ((Constants.chassisVelocity*Constants.wheelRotPerInch*4096)/10);
	private int accel = (int) ((Constants.chassisAcceleration*Constants.wheelRotPerInch*4096)/10);
	private int distance;
	private int targetPosition;
	private double[] error = new double[4];
	
	
	public  SwerveDrive(final Team801TalonSRX FrontRightDriveMotor,final Team801TalonSRX FrontLeftDriveMotor,final Team801TalonSRX BackLeftDriveMotor,final Team801TalonSRX BackRightDriveMotor,
			final Team801TalonSRX FrontRightTurnMotor,final Team801TalonSRX FrontLeftTurnMotor,final Team801TalonSRX rearLeftTurnMotor,final Team801TalonSRX rearRightTurnMotor,
			int avgSize) {
		
		driveMotors[0] = FrontRightDriveMotor;
		driveMotors[1]  = FrontLeftDriveMotor;
		driveMotors[2]   = BackLeftDriveMotor;
		driveMotors[3]  = BackRightDriveMotor;
		/*Set the Current Limit of the Drive Motors	 */
//		setDriveCurrentLimit(10, 200, 20);

		
		turnMotors[0] = FrontRightTurnMotor;
		turnMotors[1] = FrontLeftTurnMotor;
		turnMotors[2] = rearLeftTurnMotor;
		turnMotors[3] = rearRightTurnMotor;
		
		for(int i=0;i<4;i++){
			turnMotors[i].configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, Constants.kTimeoutMs);
			/* set the peak and nominal outputs, 12V means full */
			turnMotors[i].configNominalOutputForward(0, Constants.kTimeoutMs);
			turnMotors[i].configNominalOutputReverse(0, Constants.kTimeoutMs);
			turnMotors[i].configPeakOutputForward(11.0, Constants.kTimeoutMs);
			turnMotors[i].configPeakOutputReverse(-11.0, Constants.kTimeoutMs);
			/* 0.001 represents 0.1% - default value is 0.04 or 4% */
			turnMotors[i].configNeutralDeadband(0.001, Constants.kTimeoutMs);
			
//			/* Set the motors PIDF constants**/
//			turnMotors[i].config_kF(0, .001, Constants.kTimeoutMs);
//			turnMotors[i].config_kP(0, .001, Constants.kTimeoutMs);
//			turnMotors[i].config_kI(0, 0.0, Constants.kTimeoutMs);
//			turnMotors[i].config_kD(0, 0.0, Constants.kTimeoutMs);
//			turnMotors[i].configAllowableClosedloopError(0, deadBand, Constants.kTimeoutMs);//1 degree error=4096/360, expressed in native units
//	
			//set coast mode
			turnMotors[i].setNeutralMode(NeutralMode.Coast);
//			//set Voltage for turn motors
			turnMotors[i].set(ControlMode.PercentOutput, 0.0);
		
		/*the sensor and motor must be
		in-phase. This means that the sensor position must move in a positive direction as the motor
		controller drives positive motor output. To test this, first drive the motor manually (using
		gamepad axis for example). Watch the sensor position either in the roboRIO Web-based
		Configuration Self-Test, or by calling GetSelectedSensorPosition() and printing it to console.
		If the Sensor Position moves in a negative direction while Talon SRX motor output is positive
		(blinking green), then use the setSensorPhase() routine/VI to multiply the sensor position by (-
		1). Then retest to confirm Sensor Position moves in a positive direction with positive motor
		drive.**/
		turnMotors[i].setSensorPhase(false); 

		driveMotors[i].configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);
		
//		driveMotors[i].setSensorPhase(true);
        /* set the peak and nominal outputs, 12V means full */
		driveMotors[i].configNominalOutputForward(0, Constants.kTimeoutMs);
		driveMotors[i].configNominalOutputReverse(0, Constants.kTimeoutMs);
		driveMotors[i].configPeakOutputForward(11.0, Constants.kTimeoutMs);
		driveMotors[i].configPeakOutputReverse(-11.0, Constants.kTimeoutMs);
		/* 0.001 represents 0.1% - default value is 0.04 or 4% */
		driveMotors[i].configNeutralDeadband(0.01, Constants.kTimeoutMs);
		driveMotors[i].configAllowableClosedloopError(0, 0, Constants.kTimeoutMs);		
		/* Set the motors PIDF constants**/
		//index 0
		driveMotors[i].config_kF(0, .026, Constants.kTimeoutMs);
		driveMotors[i].config_kP(0, .051, Constants.kTimeoutMs);
		driveMotors[i].config_kI(0, 0.0, Constants.kTimeoutMs);
		driveMotors[i].config_kD(0, 1.0, Constants.kTimeoutMs);
		driveMotors[i].setSelectedSensorPosition(0, 0, Constants.kTimeoutMs);
		
//		//index 1
//		driveMotors[i].config_kF(1, 0.2, Constants.kTimeoutMs);
//		driveMotors[i].config_kP(1, 0.2, Constants.kTimeoutMs);
//		driveMotors[i].config_kI(1, 0.0, Constants.kTimeoutMs);
//		driveMotors[i].config_kD(1, 0.6, Constants.kTimeoutMs);
//		driveMotors[i].setSelectedSensorPosition(0, 1, Constants.kTimeoutMs);

		//set motion magic config
		driveMotors[i].configMotionCruiseVelocity(velocity, Constants.kTimeoutMs);
		driveMotors[i].configMotionAcceleration(accel, Constants.kTimeoutMs);
//		//set coast mode
//		driveMotors[i].setNeutralMode(NeutralMode.Coast);
		//set Velocity Mode for drive motors
//		driveMotors[i].selectProfileSlot(0, 0);
//		driveMotors[i].set(ControlMode.Velocity, 0.0);
		driveMotors[i].setSensorPhase(false); 
		}
		
		
		for(int i=0;i<4;i++){
			int j =i;
			pidTurnSource[i] = new PIDSource() {				
				@Override
				public void setPIDSourceType(PIDSourceType pidSource) {				
				}
				@Override
				public double pidGet() {
					return currentAngle(turnMotors[j],j);
				}				
				@Override
				public PIDSourceType getPIDSourceType() {
					return PIDSourceType.kDisplacement;
				}
			};
			
			pidTurnController[i] = new PIDController(kP, kI, kD, pidTurnSource[i], turnMotors[i]);
			pidTurnController[i].setAbsoluteTolerance(deadBand);
			pidTurnController[i].setInputRange(0, 360);
			pidTurnController[i].setContinuous(true);
			pidTurnController[i].setOutputRange(-maxTurnVoltage, maxTurnVoltage);
			pidTurnController[i].enable();
		}

		
		// Initializes the _avg variables to size avgSize
		xavg = new RollingAverage(avgSize);
		yavg = new RollingAverage(avgSize);
		zavg = new RollingAverage(avgSize);
		
	}
	 /**
	   * Drive method for Swerve wheeled robots.
	   *	  
	   *
	   * <p>This is designed to be directly driven by joystick axes.
	   *
	   * @param AxisX         	The speed that the robot should drive in the X direction. [-1.0..1.0]
	   * @param AxisY         	The speed that the robot should drive in the Y direction. This input is
	   *                  		inverted to match the forward == -1.0 that joysticks produce. [-1.0..1.0]
	   * @param rotation  		The rate of rotation for the robot that is completely independent of the
	   *                  		translation. [-1.0..1.0]
	   * @param gyroAngle 		The current angle reading from the gyro. Use this to implement field-oriented
	   *                  		controls.
	   */
	public void drive(double AxisX, double AxisY, double rotation, double gyroAngle){
		xavg.add(AxisX);
		yavg.add(AxisY);
		zavg.add(rotation);
		//Calculate Angles and Magnitudes for each motor
		FWD = -yavg.getAverage();
		STR = xavg.getAverage();
		RCW = zavg.getAverage();
		double radians = gyroAngle *Math.PI/180.00;
		temp = FWD*Math.cos(radians) + STR*Math.sin(radians);
		STR = -FWD*Math.sin(radians) + STR*Math.cos(radians);
		FWD = temp;
		//Perform the following calculations for each new set of FWD, STR, and RCW commands:
		A = STR - RCW*(L/R);
		B = STR + RCW*(L/R);
		C = FWD - RCW*(W/R);
		D = FWD + RCW*(W/R);
		
	    wheelSpeeds[0] = Math.sqrt(B*B + C*C);
	    wheelSpeeds[1] = Math.sqrt(B*B + D*D);
	    wheelSpeeds[2] = Math.sqrt(A*A + D*D);
	    wheelSpeeds[3] = Math.sqrt(A*A + C*C);
	    
	    //TODO threshold the arctan2 function inputs
	    wheelAngles[0] = Utils.wrapAngle0To360Deg(Math.atan2(B,C)*180/Math.PI);
	    wheelAngles[1] = Utils.wrapAngle0To360Deg(Math.atan2(B,D)*180/Math.PI);
	    wheelAngles[2] = Utils.wrapAngle0To360Deg(Math.atan2(A,D)*180/Math.PI);
	    wheelAngles[3] = Utils.wrapAngle0To360Deg(Math.atan2(A,C)*180/Math.PI);
		
	    //Normalize wheelSpeeds
	    //determine max motor speed
	    max=wheelSpeeds[0]; 
	    if(wheelSpeeds[1]>max){
	    	max=wheelSpeeds[1]; 
	    }
	    if(wheelSpeeds[2]>max){
	    	max=wheelSpeeds[2]; 
	    }
	    if(wheelSpeeds[3]>max){
	    	max=wheelSpeeds[3];
	    }
	    //Divide by max motor speeds
	    if(max>1){
	    	wheelSpeeds[0]/=max; 
	    	wheelSpeeds[1]/=max; 
	    	wheelSpeeds[2]/=max; 
	    	wheelSpeeds[3]/=max;
	    }

	    	double[] degs = new double[4];
		    for(int i=0;i<4;i++){
		    	degs[i] = currentAngle(turnMotors[i],i);
		    	
		    	angleJoyStickDiff[i]= wheelAngles[i]- oldAngle[i];
		    	angleError[i] = wheelAngles[i] - degs[i];

			    if(Math.abs(angleJoyStickDiff[i]) > 90){ //new angle is greater than a 90degree turn, so find shortest path
			    	//reverse translational motors 
					driveMotors[i].selectProfileSlot(0, 0);
			    	driveMotors[i].set(ControlMode.Velocity, maxDriveVoltage*wheelSpeeds[i]*4800*4096/600);
			    	
			    	//find new angle
			    	wheelAngles[i] -= 180.0; //subtract 180 degrees
			    	if(wheelAngles[i] < 0){ //wrap to new angle between 0-360
			    		wheelAngles[i] += 360.0;
			    	}
			    	//now the angle is set to move to the shortest path, which is just 180 degrees 
			    	//from the current heading
			    	
			    }    
			    
			    else
			    {
					driveMotors[i].selectProfileSlot(0, 0);
			    	driveMotors[i].set(ControlMode.Velocity, -maxDriveVoltage*wheelSpeeds[i]*4800*4096/600);
			    }
				//Turn Motors
			    if(wheelSpeeds[i]>0.1){
			    	pidTurnController[i].setSetpoint(wheelAngles[i]);
			    	oldAngle[i] = wheelAngles[i];
			    }
		    
		    currentSpeed(driveMotors[i], i);
		    
	    }

	    	SmartDashboard.putNumber("Angle", angleJoyStickDiff[0]);

		if (m_safetyHelper != null) {
			m_safetyHelper.feed();
      	}

	}
	
	public void CMDdrive(double AxisX, double AxisY, double rotation, double gyroAngle){
		
		//Calculate Angles and Magnitudes for each motor
		FWD = -AxisY;
		STR = AxisX;
		RCW = rotation;
		double radians = gyroAngle *Math.PI/180.00;
		temp = FWD*Math.cos(radians) + STR*Math.sin(radians);
		STR = -FWD*Math.sin(radians) + STR*Math.cos(radians);
		FWD = temp;
		//Perform the following calculations for each new set of FWD, STR, and RCW commands:
		A = STR - RCW*(L/R);
		B = STR + RCW*(L/R);
		C = FWD - RCW*(W/R);
		D = FWD + RCW*(W/R);
		
	    wheelSpeeds[0] = Math.sqrt(B*B + C*C);
	    wheelSpeeds[1] = Math.sqrt(B*B + D*D);
	    wheelSpeeds[2] = Math.sqrt(A*A + D*D);
	    wheelSpeeds[3] = Math.sqrt(A*A + C*C);
	    
	    //TODO threshold the arctan2 function inputs
	    wheelAngles[0] = Utils.wrapAngle0To360Deg(Math.atan2(B,C)*180/Math.PI);
	    wheelAngles[1] = Utils.wrapAngle0To360Deg(Math.atan2(B,D)*180/Math.PI);
	    wheelAngles[2] = Utils.wrapAngle0To360Deg(Math.atan2(A,D)*180/Math.PI);
	    wheelAngles[3] = Utils.wrapAngle0To360Deg(Math.atan2(A,C)*180/Math.PI);
		
	    //Normalize wheelSpeeds
	    //determine max motor speed
	    max=wheelSpeeds[0]; 
	    if(wheelSpeeds[1]>max){
	    	max=wheelSpeeds[1]; 
	    }
	    if(wheelSpeeds[2]>max){
	    	max=wheelSpeeds[2]; 
	    }
	    if(wheelSpeeds[3]>max){
	    	max=wheelSpeeds[3];
	    }
	    //Divide by max motor speeds
	    if(max>1){
	    	wheelSpeeds[0]/=max; 
	    	wheelSpeeds[1]/=max; 
	    	wheelSpeeds[2]/=max; 
	    	wheelSpeeds[3]/=max;
	    }

	    	double[] degs = new double[4];
		    for(int i=0;i<4;i++){
		    	degs[i] = currentAngle(turnMotors[i],i);
		    	
		    	angleJoyStickDiff[i]= wheelAngles[i]- oldAngle[i];
		    	angleError[i] = wheelAngles[i] - degs[i];

			    if(Math.abs(angleJoyStickDiff[i]) > 90){ //new angle is greater than a 90degree turn, so find shortest path
			    	//reverse translational motors 
					driveMotors[i].selectProfileSlot(0, 0);
			    	driveMotors[i].set(ControlMode.Velocity, maxDriveVoltage*wheelSpeeds[i]*4800*4096/600);
			    	
			    	//find new angle
			    	wheelAngles[i] -= 180.0; //subtract 180 degrees
			    	if(wheelAngles[i] < 0){ //wrap to new angle between 0-360
			    		wheelAngles[i] += 360.0;
			    	}
			    	//now the angle is set to move to the shortest path, which is just 180 degrees 
			    	//from the current heading
			    	
			    }    
			    
			    else
			    {
					driveMotors[i].selectProfileSlot(0, 0);
			    	driveMotors[i].set(ControlMode.Velocity, -maxDriveVoltage*wheelSpeeds[i]*4800*4096/600);
			    }
				//Turn Motors
			    if(wheelSpeeds[i]>0.1){
			    	pidTurnController[i].setSetpoint(wheelAngles[i]);
			    	oldAngle[i] = wheelAngles[i];
			    }
		    
		    currentSpeed(driveMotors[i], i);
		    
	    }

	    	SmartDashboard.putNumber("Angle", angleJoyStickDiff[0]);

		if (m_safetyHelper != null) {
			m_safetyHelper.feed();
      	}

	}
	
	public void turnMotors(double angle_CMD){
	    for(int i=0;i<4;i++){
	    	pidTurnController[i].setSetpoint(angle_CMD);
	    }
	}
	
	public void turnMotorsDrive(double angle_CMD , double speed){
	    for(int i=0;i<4;i++){
	    	pidTurnController[i].setSetpoint(angle_CMD);
	    	driveMotors[i].set(ControlMode.Velocity, maxDriveVoltage*speed*4800*4096/600);
	    }
		
	}
	public void turnMotorsDriveAndRotateAndGyro(double angle_CMD , double AxisY, double rotation, double gyroAngle){
	//Calculate Angles and Magnitudes for each motor
			FWD = -AxisY;
			STR = 0.0;
			RCW = rotation;
			temp = FWD*Math.cos(gyroAngle) + STR*Math.sin(gyroAngle);
			STR = -FWD*Math.sin(gyroAngle) + STR*Math.cos(gyroAngle);
			FWD = temp;
			//Perform the following calculations for each new set of FWD, STR, and RCW commands:
			A = STR - RCW*(L/R);
			B = STR + RCW*(L/R);
			C = FWD - RCW*(W/R);
			D = FWD + RCW*(W/R);
			
		    wheelSpeeds[0] = Math.sqrt(B*B + C*C);
		    wheelSpeeds[1] = Math.sqrt(B*B + D*D);
		    wheelSpeeds[2] = Math.sqrt(A*A + D*D);
		    wheelSpeeds[3] = Math.sqrt(A*A + C*C);
		    //Normalize wheelSpeeds
		    //determine max motor speed
		    max=wheelSpeeds[0]; 
		    if(wheelSpeeds[1]>max){
		    	max=wheelSpeeds[1]; 
		    }
		    if(wheelSpeeds[2]>max){
		    	max=wheelSpeeds[2]; 
		    }
		    if(wheelSpeeds[3]>max){
		    	max=wheelSpeeds[3];
		    }
		    //Divide by max motor speeds
		    if(max>1){
		    	wheelSpeeds[0]/=max; 
		    	wheelSpeeds[1]/=max; 
		    	wheelSpeeds[2]/=max; 
		    	wheelSpeeds[3]/=max;
		    }
		    
		    for(int i=0;i<4;i++){
		    	driveMotors[i].set(ControlMode.Velocity, wheelSpeeds[i] * 4800 * 4096/600);
		    }
	}
	@Override
	public void setExpiration(double timeout) {
		// TODO Auto-generated method stub
		m_safetyHelper.setExpiration(timeout);
	}

	@Override
	public double getExpiration() {
		return m_safetyHelper.getExpiration();
	}

	@Override
	public boolean isAlive() {
		return m_safetyHelper.isAlive();
	}

	@Override
	public void stopMotor() {
		for(int i=0;i>4;i++){
		    if (driveMotors[i] != null) {
		      driveMotors[i].set(ControlMode.Velocity, 0);
		    }
		    if (turnMotors[i] != null) {
		      turnMotors[i].set(ControlMode.Position, 0);
		    }
		}
	    if (m_safetyHelper != null) {
	      m_safetyHelper.feed();
	    }
	}
	public void brakeOn() {
		for(int i=0;i>4;i++){
		    if (driveMotors[i] != null) {
			  driveMotors[i].setNeutralMode(NeutralMode.Brake);
		      driveMotors[i].neutralOutput();
		    }
		    if (turnMotors[i] != null) {
		      turnMotors[i].setNeutralMode(NeutralMode.Brake);
		      turnMotors[i].neutralOutput();
		    }
		}
	    if (m_safetyHelper != null) {
	      m_safetyHelper.feed();
	    }
	}
	public void brakeOff() {
		for(int i=0;i>4;i++){
		    if (driveMotors[i] != null) {
		    	  driveMotors[i].setNeutralMode(NeutralMode.Coast);
			      driveMotors[i].set(ControlMode.Velocity, 0);
			    }
			    if (turnMotors[i] != null) {
			      turnMotors[i].setNeutralMode(NeutralMode.Coast);
			      turnMotors[i].set(ControlMode.Position, 0);
		    }
		}
	    if (m_safetyHelper != null) {
	      m_safetyHelper.feed();
	    }
	}

	@Override
	public void setSafetyEnabled(boolean enabled) {
		m_safetyHelper.setSafetyEnabled(enabled);
	}

	@Override
	public boolean isSafetyEnabled() {
		return m_safetyHelper.isSafetyEnabled();
	}

	@Override
	public String getDescription() {
		return "Swerve Drive";
	}
	
  @SuppressWarnings("unused")
private void setupMotorSafety() {
	    m_safetyHelper = new MotorSafetyHelper(this);
	    m_safetyHelper.setExpiration(kDefaultExpirationTime);
	    m_safetyHelper.setSafetyEnabled(true);
	  }
  
	public double currentAngle(Team801TalonSRX motor,int num) {
		   int motorNumber = motor.getDeviceID();
		   // Convert timeUs Pulse to angle
		   /* get the period in us, rise-to-rise in microseconds */
		   double timeUs = motor.getSensorCollection().getPulseWidthRiseToRiseUs();
		   // Convert timeUs Pulse to angle	   
		   double degrees = motor.getSensorCollection().getPulseWidthRiseToFallUs()*(360.0/timeUs);
		   SmartDashboard.putNumber("RawAngle_"+motorName[num], degrees);
		   degrees = Utils.wrapAngle0To360Deg(degrees) - Constants.AngleBias[num];
		   degrees = Utils.wrapAngle0To360Deg(degrees);
		   SmartDashboard.putNumber(motorName[num], degrees);
		   return degrees;
	}
	
	public double currentSpeed(Team801TalonSRX motor, int num){
		double speed = motor.getSelectedSensorVelocity(0);
		SmartDashboard.putNumber("Speed"+motorName[num], speed);
		return speed;
	}

	
	public void setMaxDriveVoltage(double setVoltage){
		this.maxDriveVoltage = setVoltage;
	}
	
	public void setDriveCurrentLimit(int peakAmps, int durationMs, int continousAmps) {
	/* Peak Current and Duration must be exceeded before current limit is activated.
	When activated, current will be limited to Continuous Current.
	Set Peak Current params to 0 if desired behavior is to immediately current-limit. */
		for(int i=0;i>4;i++){
			driveMotors[i].configPeakCurrentLimit(peakAmps, Constants.kTimeoutMs); /* 35 A */
			driveMotors[i].configPeakCurrentDuration(durationMs, Constants.kTimeoutMs); /* 200ms */
			driveMotors[i].configContinuousCurrentLimit(continousAmps, Constants.kTimeoutMs); /* 30A */
			driveMotors[i].enableCurrentLimit(true); /* turn it on */
		}
	}
	
	public void driveInit(){
		/*This is called one time during to setup motion magic on the drive motors.
		 */
		for(int i=0;i>4;i++){
//			driveMotors[i].selectProfileSlot(1, 0);
			driveMotors[i].clearMotionProfileHasUnderrun(Constants.kTimeoutMs);
			driveMotors[i].clearMotionProfileTrajectories();
			/* Set the motors PIDF constants**/
			//index 0
			driveMotors[i].config_kF(0, .026, Constants.kTimeoutMs);
			driveMotors[i].config_kP(0, .051, Constants.kTimeoutMs);
			driveMotors[i].config_kI(0, 0.0, Constants.kTimeoutMs);
			driveMotors[i].config_kD(0, 1.0, Constants.kTimeoutMs);
			driveMotors[i].setSelectedSensorPosition(0, 0, Constants.kTimeoutMs);
		}
	}
	
	public void motionMagicInit(){
		/*This is called one time during to setup motion magic on the drive motors.
		 */
		for(int i=0;i>4;i++){
//			driveMotors[i].selectProfileSlot(1, 0);
			driveMotors[i].clearMotionProfileHasUnderrun(Constants.kTimeoutMs);
			driveMotors[i].clearMotionProfileTrajectories();
			/* Set the motors PIDF constants**/
			//index 0
			driveMotors[i].config_kF(0, .2, Constants.kTimeoutMs);
			driveMotors[i].config_kP(0, .2 , Constants.kTimeoutMs);
			driveMotors[i].config_kI(0, 0.0, Constants.kTimeoutMs);
			driveMotors[i].config_kD(0, 0.6, Constants.kTimeoutMs);
			driveMotors[i].setSelectedSensorPosition(0, 0, Constants.kTimeoutMs);
		}
	}
	
	public void motionMagicDrive(double distance, double angle) {
		/*
		 * This is used after the motionMagicInit is called
		 */
		//convert distance to shaft rotations, drive inches to shaft rotations is 7.5 shaftRotations / 1 wheel rotation ~ 12.5inches
		targetPosition = (int) (distance*Constants.wheelRotPerInch*4096);
		double[] degs = new double[4];
		double[] angleDiff = new double[4];
		double[] oldAngle = new double[4];		
		//
		wheelAngles[0] = angle;
	    wheelAngles[1] = angle;
	    wheelAngles[2] = angle;
	    wheelAngles[3] = angle;
	    
		SmartDashboard.putNumber("Target", targetPosition);
		for(int i=0; i<4 ;i++){
//			driveMotors[i].selectProfileSlot(1, 0);
			driveMotors[i].set(ControlMode.MotionMagic, targetPosition);
			
			degs[i] = currentAngle(turnMotors[i],i);
	    		    	
			angleDiff[i]= wheelAngles[i]- oldAngle[i];
	    	angleError[i] = wheelAngles[i] - degs[i];

		    if(Math.abs(angleDiff[i]) > 90){ //new angle is greater than a 90degree turn, so find shortest path
		    	//reverse translational motors 
		    	
		    	//find new angle
		    	wheelAngles[i] -= 180.0; //subtract 180 degrees
		    	
		    	if(wheelAngles[i] < 0){ //wrap to new angle between 0-360
		    		wheelAngles[i] += 360.0;
		    	}
		    }
		    pidTurnController[i].setSetpoint(wheelAngles[i]);
	    	oldAngle[i] = wheelAngles[i];
	    	//now the angle is set to move to the shortest path, which is just 180 degrees 
	    	//from the current heading
		    	
		 }    
		
	}
	
	public double getTraveledDistance() {
		//Used during Motion Magic Profile to find the robots distance traveled

		for(int i=0;i<4;i++){
			distance += driveMotors[i].getSelectedSensorPosition(0);
		}
		distance /=driveMotors.length;
		distance *= 12.5/(7.5*4096); //convert to inches
		SmartDashboard.putNumber("TraveledDistance", distance);
		System.out.println(distance);
		return distance;

	}
	public int getPositionErrorDrive() {
		//Used during Motion Magic Profile to find the robots distance traveled
		
		System.out.print("\ttarget" + targetPosition + " ");
		int err =0;
		for(int i=0;i<4;i++){
			error[i] += Math.abs(driveMotors[i].getSelectedSensorPosition(0));
//			error[i] *= 12.5/(7.5*4096); //convert to inches
			SmartDashboard.putNumber("Drive Motor Position Error", error[i]);
//			System.out.print("error " + i + " " + error[i] + "\t");
			err +=error[i];
		}
		err /= 4;
		
		System.out.print(err);
		

		return err;

	}
	
}
