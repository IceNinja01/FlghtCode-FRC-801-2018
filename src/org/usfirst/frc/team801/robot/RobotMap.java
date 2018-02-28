/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team801.robot;

import org.usfirst.frc.team801.robot.Utilities.Adis16448_IMU;

import SwerveClass.SwerveDrive;
import SwerveClass.Team801TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//Make all the swerve drive talons here
//	public static Team801TalonSRX frontRightDrive = new Team801TalonSRX(1);
//	public static Team801TalonSRX frontRightTurn = new Team801TalonSRX(5);
//	
//	public static Team801TalonSRX frontLeftDrive = new Team801TalonSRX(14);
//	public static Team801TalonSRX frontLeftTurn = new Team801TalonSRX(10);
	
//	public static Team801TalonSRX backRightDrive = new Team801TalonSRX(0);
//	public static Team801TalonSRX backRightTurn = new Team801TalonSRX(4);
//	
//	public static Team801TalonSRX backLeftDrive = new Team801TalonSRX(15);
//	public static Team801TalonSRX backLeftTurn = new Team801TalonSRX(11);
	
	public static Team801TalonSRX elevator = new Team801TalonSRX(6);

	public static AnalogInput ultraSonic;
	public static Adis16448_IMU imu;
	
	public static SwerveDrive swerveDrive;
	
	
	public static void init() {
		//IMU setup
		imu = new Adis16448_IMU();
		imu.calibrate();
		imu.reset();
		
//		swerveDrive = new SwerveDrive(frontRightDrive,frontLeftDrive,backLeftDrive,backRightDrive,
//			frontRightTurn,frontLeftTurn,backLeftTurn,backRightTurn,
//			10);
	
	}
	
}
