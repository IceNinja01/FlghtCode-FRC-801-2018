package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.Utilities.Adis16448_IMU;
import org.usfirst.frc.team801.robot.Utilities.Utils;
import org.usfirst.frc.team801.robot.commands.DriveWithJoysticks;
import com.ctre.phoenix.motorcontrol.ControlMode;

import SwerveClass.SwerveDrive;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls the movement of the robot
 */
@SuppressWarnings("unused")
public class Chassis extends PIDSubsystem {
	Adis16448_IMU adis = RobotMap.imu;
//	AnalogInput ultraSonic = RobotMap.ultraSonic;
//	public static PIDSource ultraPidSource;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	// private SwerveDriveTwoMotors chassisSwerveDrive =
	// RobotMap.swerveDriveTwoMotors;
	public SwerveDrive chassisSwerveDrive = RobotMap.swerveDrive;
//	public double distance;
	public double angle;
	public double headingError;
	public double headingCMD;
	private double zRateCmd;
//	public boolean gottaGoFast = false;
	private double x;
	private double y;
	private double z;
	private double[] turnDistance;
	private double turnRadius = 3;
	private double cruiseVelocity;

//	private RollingAverage xAvg;
//	private RollingAverage yAvg;
//	private RollingAverage x_g;
//	private RollingAverage y_g;
//	private RollingAverage z_g;
//	private RollingAverage tilt;

	private double acceleration;
private double elevatorHeight;
private boolean robotOrient = false;

	public Chassis() {

		super(Constants.ultrakP, Constants.ultrakI, Constants.ultrakD, 0.001);
		getPIDController().setAbsoluteTolerance(1.0);
		getPIDController().setInputRange(0.0, 360.0);
		getPIDController().setContinuous(true);
		getPIDController().setOutputRange(-0.3, 0.3);
		enable();

	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for the chassis subsystem here.
		setDefaultCommand(new DriveWithJoysticks());
	}

	public void motorDrive(double angleCmd_Deg) {
		elevatorHeight = Robot.elevator.getCurrentPosition();
		
		if(elevatorHeight > 48.0) {
			
			chassisSwerveDrive.setMaxDriveVoltage(0.4);
		}
		else {
			chassisSwerveDrive.setMaxDriveVoltage(1.0);

		}
		
		

		x = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getX(), 1.5), 0.01, 1.0);
		y = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getY(), 1.5), 0.01, 1.0);
		z = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getRawAxis(4), 1.5), 0.01, 0.5);

			chassisSwerveDrive.drive(x, y, z, angleCmd_Deg);
		
		
	}

	public void turnToHeading(double gyroCMD, double angleCmd) {
		x = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getX(Hand.kLeft), 1.5), 0.01, 1.0);
		y = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getY(Hand.kLeft), 1.5), 0.01, 1.0);
		headingCMD = gyroCMD;
		headingError = Robot.chassis.getGyroAngle() - headingCMD;
		chassisSwerveDrive.drive(x, y, zRateCmd, angleCmd);
		SmartDashboard.putNumber("HeadingCMD", headingCMD);
		SmartDashboard.putNumber("HeadingError", headingError);
		SmartDashboard.putNumber("zRateCmd", zRateCmd);

	}	
	public void cmdDrive(double x, double y, double gyroCMD, double angleCmd) {
		headingCMD = gyroCMD;
		headingError = Robot.chassis.getGyroAngle() - headingCMD;
		chassisSwerveDrive.CMDdrive(x, y, zRateCmd, angleCmd);
		SmartDashboard.putNumber("HeadingCMD", headingCMD);
		SmartDashboard.putNumber("HeadingError", headingError);
		SmartDashboard.putNumber("zRateCmd", zRateCmd);

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
		angle = Utils.wrapAngle0To360Deg(adis.getAngleZ());
		SmartDashboard.putNumber("IMU", angle);
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
	
	public void driveInit() {
		chassisSwerveDrive.driveInit();
	}
	
	public void setMotionMagic() {
		chassisSwerveDrive.motionMagicInit();
	}
	
	public void resetEnocdersPosition() {
		
	}
	
	public void driveMotionMagic(double distance, double angle) {
		chassisSwerveDrive.motionMagicDrive(distance, angle);
	}

	
	public double getChassisPosition() {
		return chassisSwerveDrive.getTraveledDistance();
		
	}
	
	public double getChassisError() {
		return chassisSwerveDrive.getPositionErrorDrive();
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
		
		if(elevatorHeight > 48.0) {
			
			chassisSwerveDrive.setMaxDriveVoltage(0.5);
		}
		else {
			chassisSwerveDrive.setMaxDriveVoltage(1.0);

		}
		
		

		x = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getX(), 1.5), 0.01, 1.0);
		y = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getY(), 1.5), 0.01, 1.0);
		z = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getRawAxis(4), 1.5), 0.01, 0.5);

			chassisSwerveDrive.drive(x, y, z, 0);
		
		
	
	}
	
	public void getVolts() {
		chassisSwerveDrive.getAmps();
	}

}


//public void getTilt() {
//	x_g.add(adis.getAccelX());
//	y_g.add(adis.getAccelY());
//	z_g.add(-adis.getAccelZ());
//	SmartDashboard.putNumber("x_g", x_g.getAverage());
//	SmartDashboard.putNumber("y_g", y_g.getAverage());
//	SmartDashboard.putNumber("z_g", z_g.getAverage());
//	tilt.add(Math.atan2(z_g.getAverage(),
//			(Math.sqrt(y_g.getAverage() * y_g.getAverage() + x_g.getAverage() * x_g.getAverage()))) * 180
//			/ Math.PI);
//	SmartDashboard.putNumber("tilt", tilt.getAverage());
//
//}


//
//public double getUltraDistance() {
//	distance = ultraSonic.getVoltage() * (1000 / 9.8); // 9.8mv/inch*1000
//	SmartDashboard.putNumber("UltraSonic", distance);
//	return distance;
//}
//

