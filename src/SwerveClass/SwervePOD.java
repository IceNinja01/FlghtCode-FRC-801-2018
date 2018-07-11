package SwerveClass;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Utilities.Utils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwervePOD {

	
	private Team801TalonSRX driveMotor;
	private Team801TalonSRX turnMotor;
	private PIDSource pidTurnSource;
	private PIDController pidTurnController;
	private MotorName motorName;

	/**
	 * 
	 * @param Drive Motor Number for the Drive motor on SwervePOD
	 * @param Turn	Motor Number for the Turn motor on SwervePOD
	 * @param PODName EnumType for POD Name: RightFront(0), LeftFront(1), LeftBack(2), RightBack(3).
	 */
	
	public SwervePOD(int Drive, int Turn, int PODName) {
//		Initialize motors
		driveMotor  = new Team801TalonSRX(Drive);
		turnMotor  = new Team801TalonSRX(Turn);
		motorName.value= PODName;
	}
		
	public enum MotorName{
		RightFront(0),
		LeftFront(1),
		LeftBack(2),
		RightBack(3);
		public int value;
		MotorName(int initValue){
			
			this.value = initValue;
			
		}		
	}
	
	public void initialize() {
		turnMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, Constants.kTimeoutMs);
		/* set the peak and nominal outputs, 12V means full */
		turnMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
		turnMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
		turnMotor.configPeakOutputForward(11.0, Constants.kTimeoutMs);
		turnMotor.configPeakOutputReverse(-11.0, Constants.kTimeoutMs);
		turnMotor.enableVoltageCompensation(true); 
		/* 0.001 represents 0.1% - default value is 0.04 or 4% */
		turnMotor.configNeutralDeadband(0.001, Constants.kTimeoutMs);
		
//		/* Set the motors PIDF constants**/
//		turnMotors[i].config_kF(0, .001, Constants.kTimeoutMs);
//		turnMotors[i].config_kP(0, .001, Constants.kTimeoutMs);
//		turnMotors[i].config_kI(0, 0.0, Constants.kTimeoutMs);
//		turnMotors[i].config_kD(0, 0.0, Constants.kTimeoutMs);
//		turnMotors[i].configAllowableClosedloopError(0, deadBand, Constants.kTimeoutMs);//1 degree error=4096/360, expressed in native units
//
		//set coast mode
		turnMotor.setNeutralMode(NeutralMode.Coast);
//		//set Voltage for turn motors
		turnMotor.set(ControlMode.PercentOutput, 0.0);
	
	/*the sensor and motor must be
	in-phase. This means that the sensor position must move in a positive direction as the motor
	controller drives positive motor output. To test this, first drive the motor manually (using
	gamepad axis for example). Watch the sensor position either in the roboRIO Web-based
	Configuration Self-Test, or by calling GetSelectedSensorPosition() and printing it to console.
	If the Sensor Position moves in a negative direction while Talon SRX motor output is positive
	(blinking green), then use the setSensorPhase() routine/VI to multiply the sensor position by (-
	1). Then retest to confirm Sensor Position moves in a positive direction with positive motor
	drive.**/
		turnMotor.setSensorPhase(false); 


	driveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);
	/* set the peak and nominal outputs, 12V means full */
	driveMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
	driveMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
	driveMotor.configPeakOutputForward(11.0, Constants.kTimeoutMs);
	driveMotor.configPeakOutputReverse(-11.0, Constants.kTimeoutMs);
	driveMotor.enableVoltageCompensation(true); 
	driveMotor.configNeutralDeadband(0.01, Constants.kTimeoutMs);
	driveMotor.configAllowableClosedloopError(0, 0, Constants.kTimeoutMs);		
	/* Set the motors PIDF constants**/
	//index 0
	driveMotor.config_kF(0, .026, Constants.kTimeoutMs);
	driveMotor.config_kP(0, .051, Constants.kTimeoutMs);
	driveMotor.config_kI(0, 0.0, Constants.kTimeoutMs);
	driveMotor.config_kD(0, 0.5, Constants.kTimeoutMs);
	driveMotor.setSelectedSensorPosition(0, 0, Constants.kTimeoutMs);
	}
	
	public void configPIDTurn(double kP, double kI, double kD, double deadBand, double maxTurnVoltage) {
	pidTurnSource = new PIDSource() {				
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {				
		}
		@Override
		public double pidGet() {
			return currentAngle();
		}				
		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}
	};
	
	pidTurnController = new PIDController(kP, kI, kD, pidTurnSource, turnMotor);
	pidTurnController.setAbsoluteTolerance(deadBand);
	pidTurnController.setInputRange(0, 360);
	pidTurnController.setContinuous(true);
	pidTurnController.setOutputRange(-maxTurnVoltage, maxTurnVoltage);
	pidTurnController.enable();
	
	}
	/**
	 * @parameter phase	used to change the phase of the talon sensor
	*/
	public void configMotionMagic(int velocity, int accel, boolean phase) {
	//set motion magic config
		driveMotor.configMotionCruiseVelocity(velocity, Constants.kTimeoutMs);
		driveMotor.configMotionAcceleration(accel, Constants.kTimeoutMs);
		driveMotor.configNeutralDeadband(0.001, Constants.kTimeoutMs);
		//set coast mode
		driveMotor.setNeutralMode(NeutralMode.Coast);
		//set Velocity Mode for drive motors
		driveMotor.set(ControlMode.Velocity, 0.0);
		driveMotor.setSensorPhase(phase); 
	}
	
	public double currentAngle() {
		   int motorNumber = turnMotor.getDeviceID();
		   // Convert timeUs Pulse to angle
		   /* get the period in us, rise-to-rise in microseconds */
		   double timeUs = turnMotor.getSensorCollection().getPulseWidthRiseToRiseUs();
		   // Convert timeUs Pulse to angle	   
		   double degrees = turnMotor.getSensorCollection().getPulseWidthRiseToFallUs()*(360.0/timeUs);
		   SmartDashboard.putNumber("RawAngle_"+motorName.name(), degrees);
		   degrees = Utils.wrapAngle0To360Deg(degrees) - Constants.AngleBias[motorName.value];
		   degrees = Utils.wrapAngle0To360Deg(degrees);
		   SmartDashboard.putNumber(motorName.name()+" turn", degrees);
		   return degrees;
	}
	
	public void drive(double speed) {
		driveMotor.set(ControlMode.Velocity, speed*4800*4096/600);
	}
	
	public void turn(double angle) {
		pidTurnController.setSetpoint(angle);
	}
	
	public void stop() {
		driveMotor.set(ControlMode.Velocity, 0);
		pidTurnController.disable();
		turnMotor.set(ControlMode.PercentOutput, 0.0);
	}
	
	public void brakeOn() {
		driveMotor.setNeutralMode(NeutralMode.Brake);
		driveMotor.neutralOutput();
	  	pidTurnController.disable();

	}
	
	public void brakeOff() {
		driveMotor.setNeutralMode(NeutralMode.Coast);
		driveMotor.set(ControlMode.Velocity, 0);
	  	pidTurnController.enable();
	}
	
	public double getAmps() {
		return driveMotor.getOutputCurrent();
	}
	
	public double getVoltage() {
		return driveMotor.getMotorOutputVoltage();
	}
	
	public void setDriveCurrentLimit(int peakAmps, int durationMs, int continousAmps) {
		driveMotor.configPeakCurrentLimit(peakAmps, Constants.kTimeoutMs); /* 35 A */
		driveMotor.configPeakCurrentDuration(durationMs, Constants.kTimeoutMs); /* 200ms */
		driveMotor.configContinuousCurrentLimit(continousAmps, Constants.kTimeoutMs); /* 30A */
		driveMotor.enableCurrentLimit(true); /* turn it on */
	}
}
