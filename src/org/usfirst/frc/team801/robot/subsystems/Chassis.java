package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.Utilities.Adis16448_IMU;
import org.usfirst.frc.team801.robot.Utilities.Adis16448_IMU_2018;
import org.usfirst.frc.team801.robot.Utilities.RollingAverage;
import org.usfirst.frc.team801.robot.Utilities.Utils;
import org.usfirst.frc.team801.robot.commands.DriveWithJoysticks;
import com.ctre.phoenix.motorcontrol.ControlMode;

import MotionProfile.DistanceFollower;
import SwerveClass.SwerveDrive;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls the movement of the robot
 * @param <ultraCMD>
 */
@SuppressWarnings("unused")
public class Chassis<ultraCMD> extends PIDSubsystem {
	Adis16448_IMU_2018 adis = RobotMap.imu;
//	AnalogInput ultraSonic = RobotMap.ultraSonic;
//	public static PIDSource ultraPidSource;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	// private SwerveDriveTwoMotors chassisSwerveDrive =
	// RobotMap.swerveDriveTwoMotors;
	AnalogInput ultraFwd = RobotMap.ultraSonicFront;
	AnalogInput ultraRev = RobotMap.ultraSonicReverse;
	public SwerveDrive chassisSwerveDrive = RobotMap.swerveDrive;
//	public double distance;
	public double angle;
	public double headingError;
	public double headingCMD;
	public double zRateCmd;
//	public boolean gottaGoFast = false;
	private double x;
	private double y;
	private double z;
	private double[] turnDistance;
	private double turnRadius = 3;
	private double cruiseVelocity;
	private DistanceFollower dist_fw[] = new DistanceFollower[4];




	private double acceleration;
private double elevatorHeight;
private boolean robotOrient = false;
private double biasAngle = 0.0;
private double mmtoInches = 0.03937;
private PIDSource pidUltraSource;
private PIDController pidUltraController;

private double ultraCMD;

private double ultraError;

private double strafeOut;

private double ultra_ki = 0.0001;

private double ultra_kd = 0.001;

private double ultra_kp = 0.01;

private RollingAverage integral = new RollingAverage((int) (1/ultra_ki));

	public Chassis() {

		super(0.02, 0.0000001, 0.8, 0.001);
		getPIDController().setAbsoluteTolerance(2.0);
		getPIDController().setInputRange(0.0, 360.0);
		getPIDController().setContinuous(true);
		getPIDController().setOutputRange(-0.3, 0.3);
		enable();
    	for(int i = 0; i<4; i++) {
    		dist_fw[i] = new DistanceFollower(null, i);
    	}
		
//		pidUltraSource = new PIDSource() {				
//			@Override
//			public void setPIDSourceType(PIDSourceType pidSource) {				
//			}
//			@Override
//			public double pidGet() {
//				return getReverseDist();
//			}				
//			@Override
//			public PIDSourceType getPIDSourceType() {
//				return PIDSourceType.kDisplacement;
//			}
//		};
//		
//		pidUltraController = new PIDController(.002, 0.000001, 0.001, pidUltraSource, ultraPID);
//		pidUltraController.setAbsoluteTolerance(2);
//		pidUltraController.setInputRange(0, 360);
//		pidUltraController.setContinuous(false);
//		pidUltraController.setOutputRange(-0.5, 0.5);
//		pidUltraController.enable();

	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for the chassis subsystem here.
		setDefaultCommand(new DriveWithJoysticks());
	}

	public void motorDrive(double angleCmd_Deg) {
		elevatorHeight = Robot.elevator.getCurrentPosition();
		
		if(elevatorHeight > Constants.elevatorMotorMidPos + 5) {		
			chassisSwerveDrive.setMaxDriveVoltage(0.7);
		}
		else {
			chassisSwerveDrive.setMaxDriveVoltage(1.0);
		}
		x = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getX(), 1.5), 0.05, 1.0);
		y = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getY(), 1.5), 0.05, 1.0);
		z = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getRawAxis(4), 1.5), 0.05, 0.5);

		chassisSwerveDrive.drive(x, y, z, angleCmd_Deg);
		
	}

	public void turnToHeading(double gyroCMD, double angleCmd) {
		x = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getX(Hand.kLeft), 1.5), 0.05, 1.0);
		y = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getY(Hand.kLeft), 1.5), 0.05, 1.0);
		headingCMD = gyroCMD;
		headingError = Robot.chassis.getGyroAngle() - headingCMD;
		chassisSwerveDrive.drive(x, y, zRateCmd, angleCmd);

	}	
	
	public void cmdTurnToHeading(double gyroCMD, double angleCmd) {
		headingCMD = gyroCMD;
		headingError = Robot.chassis.getGyroAngle() - headingCMD;
		chassisSwerveDrive.CMDdrive(0, 0, zRateCmd, angleCmd);

	}	
	public void cmdDrive(double x, double y, double gyroCMD, double angleCmd) {
		headingCMD = gyroCMD;
		headingError = Robot.chassis.getGyroAngle() - headingCMD;
		chassisSwerveDrive.CMDdrive(x, y, zRateCmd, angleCmd);

	}
	
	public void cmdUltraStrafeFwd(double x, double y, double gyroCMD, double angleCmd, double ultraCMD) {
		headingCMD = gyroCMD;
		headingError = Robot.chassis.getGyroAngle() - headingCMD;
		this.ultraCMD = ultraCMD;
		ultraError = getReverseDist() - ultraCMD;
		getUltraDriveError(ultraError);
		chassisSwerveDrive.CMDdrive(strafeOut, y, zRateCmd, angleCmd);
		
	}
	
	public void stop() {
		chassisSwerveDrive.stopMotor();
	}

	public void brakeOn() {
		chassisSwerveDrive.brakeOn();
	}

	public void brakeOff() {
		chassisSwerveDrive.brakeOff();
	}

	public void pointWheels(double angle_CMD) {
		chassisSwerveDrive.turnMotors(angle_CMD);
	}

	public void pointWheelsDrive(double angle_CMD, double speed) {
		chassisSwerveDrive.turnMotorsDrive(angle_CMD, speed);
	}

	public double getGyroAngle() {
		angle = Utils.wrapAngle0To360Deg(adis.getAngleZ() - biasAngle );
		
		return angle;
	}
	
	public double getAngleX() {
		double x = adis.getAngleX();
		return x;
	}
	public double getAngleY() {
		double y = adis.getAngleY();
		return y;
	}

	
	protected double returnPIDInput() {
		return headingError;
	}
	protected void usePIDOutput(double output) {
		zRateCmd = output;
	}

	public double getHeadingErr() {
		return headingError;
	}

	public double getZRateCmd() {
		return zRateCmd;
	}

	public double getHeadingCmd() {
		return headingCMD;
	}
	
	public void resetEnocdersPosition() {
		
	}
	
	public double getChassisPosition() {
		return chassisSwerveDrive.getTraveledDistance();
		
	}
	
	public double[] getChassisError() {
		return chassisSwerveDrive.getXY_Position();
	}
	
	public void toggleFieldOrient() {
		
		robotOrient = false;
		
	}
	
	public void toggleRobotOrient() {
		
		robotOrient = true;
		
	}

	public void driveRobotOrient() {
		// TODO Auto-generated method stub
		elevatorHeight = Robot.elevator.getCurrentPosition();
		
		if(elevatorHeight > Constants.elevatorMotorMidPos + 5) {
			
			chassisSwerveDrive.setMaxDriveVoltage(0.5);
		}
		else {
			chassisSwerveDrive.setMaxDriveVoltage(1.0);

		}
		x = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getX(), 1.5), 0.05, 1.0);
		y = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getY(), 1.5), 0.05, 1.0);
		z = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getRawAxis(4), 1.5), 0.05, 0.5);

			chassisSwerveDrive.drive(x, y, z, 0);
	}
	
	public void getVolts() {
		chassisSwerveDrive.getAmps();
	}
	
	public void setGyroBias() {
		
		biasAngle = Utils.wrapAngle0To360Deg(adis.getAngleZ());
		
	}
	
	public double getFrontDist() {
		return ultraFwd.getVoltage()*(1000 / 0.977)*mmtoInches;
	}
	
	public double getReverseDist() {
		return ultraRev.getVoltage()*(1000 / 9.8);
//				*0.00098*mmtoInches;
	}
	
	public double getUltraDriveError(double error) {
		double dt = 0.01;
		double previous_error = 0;
		
		double derivative=0;
		
		integral.add(integral.getAverage() + error*dt);
		derivative =(error - previous_error) / dt;
		
		strafeOut = ultra_kp*error + ultra_ki*integral.getAverage() + ultra_kd*derivative;
		previous_error = error;
		strafeOut = Utils.limitMagnitude(strafeOut, 0.05, 1.0);
		return strafeOut;
	}
	
	public void initFollowers(double[] dist, double maxVel, double maxAccel) {
    	for(int i = 0; i<4; i++) {
    		dist_fw[i].setTrajectory(dist,i);
    		dist_fw[i].configureSpeed(maxVel, maxAccel);
    		dist_fw[i].configurePIDVA(0.5, 0, .01, 1/maxVel , 0.01);
    	}
	}
	
	public void setFollowers(double gyroCMD) {
		double[] speed = new double[4];
		headingCMD = gyroCMD;
		headingError = Robot.chassis.getGyroAngle() - headingCMD;		
    	for(int i = 0; i<4; i++) {
    		speed[i] = dist_fw[i].calculate(getChassisError(), getGyroAngle(), zRateCmd);
    	}
    	//Normalize wheelSpeeds
	    //determine max motor speed
	    double max = speed[0]; 
	    if(speed[1]>max){
	    	max=speed[1]; 
	    }
	    if(speed[2]>max){
	    	max=speed[2]; 
	    }
	    if(speed[3]>max){
	    	max=speed[3];
	    }
	    //Divide by max motor speeds
	    if(max>1){
	    	speed[0]/=max; 
	    	speed[1]/=max; 
	    	speed[2]/=max; 
	    	speed[3]/=max;
	    }
	    
	    for(int i = 0; i<4; i++) {
	    	chassisSwerveDrive.turnMotorsDrive(dist_fw[i].getHeading(), speed[i]);
	    }
	    	    
	}
	
	public double[] getServeModuleError(int i) {
		
		double[] array = new double[2];
		array = chassisSwerveDrive.getPOD_XY_Position(i);
		return array ;
	}

}
