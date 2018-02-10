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

	public Chassis() {

		super(Constants.ultrakP, Constants.ultrakI, Constants.ultrakD, 0.01);
		getPIDController().setAbsoluteTolerance(1.0);
		getPIDController().setInputRange(0.0, 360.0);
		getPIDController().setContinuous(true);
		getPIDController().setOutputRange(-1.0, 1.0);
		enable();
//		tilt = new RollingAverage(5);
//		x_g = new RollingAverage(5);
//		y_g = new RollingAverage(5);
//		z_g = new RollingAverage(5);
		// xAvg.add(0.01);
		// yAvg.add(-0.01);

	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for the chassis subsystem here.
		setDefaultCommand(new DriveWithJoysticks());
	}

	public void motorDrive(double angleCmd_Deg) {
		x = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getX(Hand.kLeft), 1.5), 0.01, 1.0);
		y = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getY(Hand.kLeft), 1.5), 0.01, 1.0);
		z = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getX(), 1.5), 0.01, 1.0);
//		SmartDashboard.putNumber("Joystick X", x);
//		SmartDashboard.putNumber("Joystick Y", y);

		chassisSwerveDrive.drive(x, y, z, angleCmd_Deg);
		
//		getTilt();
//		// if(tilt.getAverage()>30) {
//		if (gottaGoFast) {
//			chassisSwerveDrive.setMaxDriveVoltage(0.9);
//			x = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getX(), 1.5), 0.01, 1.0);
//			y = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getY(), 1.5), 0.01, 1.0);
//			// average x and y
//			headingCMD = angleCmd_Deg;
//			headingError = Robot.chassis.getGyroAngle() - headingCMD;
//			chassisSwerveDrive.drive(x, y, 0.0, angleCmd_Deg);
//			SmartDashboard.putString("Speed", "Fast");
//		} else {
//			chassisSwerveDrive.setMaxDriveVoltage(0.65);
//			x = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getX(), 1.5), 0.01, 1.0);
//			y = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getY(), 1.5), 0.01, 1.0);
//			z = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.driver.getRawAxis(4), 1.5), 0, 1.0);
//			chassisSwerveDrive.drive(x, y, z, angleCmd_Deg);
//			SmartDashboard.putString("Speed", "Slow");
//		}
		// }
		// else{chassisSwerveDrive.drive(0, 0, 0.0, angleCmd_Deg);}
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
	
	public void setMotionMagic(double cruiseVelocity, double acceleration) {
		this.cruiseVelocity = cruiseVelocity;
		this.acceleration = acceleration;
		chassisSwerveDrive.motionMagicInit(cruiseVelocity, acceleration);
	}
	
	public void driveMotionMagic(double distance) {
		chassisSwerveDrive.motionMagicDrive(distance);
	}

	public void getTurnAngles(double[][] turnAngle) {
		// This method calculates the angle to turn the turn motors while driving a motion magic profile at some 
		// drive position
		double angularTime;
		double pos = getChassisPosition();
		int j = 0;
		for(int i=0; i<turnAngle.length; i++) {
			double newAngle = turnAngle[i-1][1];
			if(turnAngle[i][0] - getChassisPosition() < 0.5){ //distance is less than one inch to turn point
				angularTime = (turnAngle[i][1] - turnAngle[i-1][1])/ turnAngle[i][2]  ;// angluar speed[i][2]
				j = (int) (angularTime / Constants.kTimeoutMs);
				double dA = turnAngle[i][1] / j;
//				dA *= turnAngle[i][3];
				for(int k=0; k<j; k++) {//start turning
					newAngle += dA;
					newAngle = Utils.wrapAngle0To360Deg(newAngle);
					chassisSwerveDrive.turnMotors(newAngle);
				}
			}
			else{
				chassisSwerveDrive.turnMotors(newAngle);
			}
		}
		
	}
	
	public double getChassisPosition() {
		return chassisSwerveDrive.getTraveledDistance();
		
	}
	
	
	
}
//
//	public void motorDriveHoldHeading(double x, double y, double angleCmd_Deg) {
//		headingCMD = angleCmd_Deg;
//		headingError = Robot.chassis.getGyroAngle() - headingCMD;
//		chassisSwerveDrive.drive(x, y, zRateCmd, 0.0);
//		SmartDashboard.putNumber("HeadingCMD", headingCMD);
//		SmartDashboard.putNumber("HeadingError", headingError);
//		SmartDashboard.putNumber("zRateCmd", zRateCmd);
//	}
//
//	public void toggleFastMode() {
//		gottaGoFast = true;
//	}
//
//	public void toggleSlowMode() {
//		gottaGoFast = false;
//	}
//
//	public void motorCMDDrive(double x, double y, double z, double headingCmd) {
//
//		chassisSwerveDrive.drive(x, y, z, headingCmd);
//
//	}
//
//	public void motorCMDDriveAngle(double x, double y, double z, double headingCmd) {
//		headingCMD = headingCmd;
//		headingError = Robot.chassis.getGyroAngle() - headingCMD;
//		chassisSwerveDrive.drive(x, y, zRateCmd, headingCmd);
//
//	}

//
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

//
//public double getUltraDistance() {
//	distance = ultraSonic.getVoltage() * (1000 / 9.8); // 9.8mv/inch*1000
//	SmartDashboard.putNumber("UltraSonic", distance);
//	return distance;
//}
//

