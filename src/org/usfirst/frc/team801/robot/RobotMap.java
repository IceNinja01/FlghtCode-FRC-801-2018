/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team801.robot;

import org.usfirst.frc.team801.robot.Utilities.Adis16448_IMU;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import SwerveClass.SwerveDrive;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//Make all the swerve drive talons here
	public static TalonSRX frontRightDrive = new TalonSRX(1);
	public static TalonSRX frontRightTurn = new TalonSRX(5);
	
	public static TalonSRX frontLeftDrive = new TalonSRX(14);
	public static TalonSRX frontLeftTurn = new TalonSRX(1);
	
	public static TalonSRX backRightDrive = new TalonSRX(0);
	public static TalonSRX backRightTurn = new TalonSRX(4);
	
	public static TalonSRX backLeftDrive = new TalonSRX(15);
	public static TalonSRX backLeftTurn = new TalonSRX(11);
	
	public static SwerveDrive swerveDrive = new SwerveDrive(frontRightDrive,frontLeftDrive,backRightDrive,backLeftDrive,
			frontRightTurn,frontLeftTurn,backRightTurn,backLeftTurn,
			10);
	public static AnalogInput ultraSonic;
	public static Adis16448_IMU imu;
	
	
}
