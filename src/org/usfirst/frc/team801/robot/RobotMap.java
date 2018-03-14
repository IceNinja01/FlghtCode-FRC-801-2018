/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team801.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.usfirst.frc.team801.robot.Utilities.Adis16448_IMU;
import org.usfirst.frc.team801.robot.Utilities.BufferedWriterFRC;

import SwerveClass.SwerveDrive;
import SwerveClass.Team801TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//Make all the swerve drive talons here
	public static Team801TalonSRX frontRightDrive = new Team801TalonSRX(1);
	public static Team801TalonSRX frontRightTurn = new Team801TalonSRX(5);
	
	public static Team801TalonSRX frontLeftDrive = new Team801TalonSRX(14);
	public static Team801TalonSRX frontLeftTurn = new Team801TalonSRX(10);
	
	public static Team801TalonSRX backRightDrive = new Team801TalonSRX(0);
	public static Team801TalonSRX backRightTurn = new Team801TalonSRX(4);
	
	public static Team801TalonSRX backLeftDrive = new Team801TalonSRX(15);
	public static Team801TalonSRX backLeftTurn = new Team801TalonSRX(11);

	
	public static Team801TalonSRX elevator = new Team801TalonSRX(2);
	
	public static Team801TalonSRX arm = new Team801TalonSRX(9);
	//Lift Motor 
	public static Team801TalonSRX lift = new Team801TalonSRX(6);
	//Wench motor
	public static Team801TalonSRX theWinchThatStoleChristmas = new Team801TalonSRX(13);

	public static final PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	public static AnalogInput ultraSonic;
	public static Adis16448_IMU imu;
	
	public static SwerveDrive swerveDrive;
	
	//Data Logger
	public static BufferedWriterFRC logFileCreator;
	public static HardwareTimer timer;
	public static final File logPath = new File("/u/logs/");
	
	public static void init() 
	{
		//IMU setup
		imu = new Adis16448_IMU();
		imu.calibrate();
		imu.reset();
//		backLeftTurn.setInverted(true);
		swerveDrive = new SwerveDrive(frontRightDrive,frontLeftDrive,backLeftDrive,backRightDrive,
			frontRightTurn,frontLeftTurn,backLeftTurn,backRightTurn,
			2);
	
	}
	
	public static void dataWriterInit()
	{					
		//Data Logger
		if(Constants.LOGGING_ENABLE)
		{
			timer = new HardwareTimer();
			if(logPath.isDirectory())
			{
				System.out.println("I think a USB drive is mounted as U");		
//				System.out.println("Size " + logPath.getFreeSpace());
				logFileCreator = createLogFile(logPath);
			}
			else
			{
				System.out.println("No USB Drive mounted");
				logFileCreator = null;
			}
		} //end Data Logger
	}
	private static BufferedWriterFRC createLogFile(File path)
	{
		LocalDateTime dateTime = LocalDateTime.now();
		File outFile = new File(path.toString() + "/log_" + dateTime.format(DateTimeFormatter.ofPattern("uu_MM_dd_HH_mm_ss")) + ".txt");
		try
		{
			BufferedWriterFRC w = new BufferedWriterFRC(new OutputStreamWriter(new FileOutputStream(outFile)));
			System.out.println("Log created at " + path.getName() + "/" + outFile.getName());
			return w;
		}
		catch (FileNotFoundException e)
		{
			System.out.println("WARNING: No drive available. If drive is mounted, try restarting the roboRIO");	
			return null;
		}
		//return w;
	}
	
}
