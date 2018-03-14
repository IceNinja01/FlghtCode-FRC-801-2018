/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team801.robot;


import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team801.robot.Utilities.PathBuilder;
import org.usfirst.frc.team801.robot.commands.UpdateSD;
import org.usfirst.frc.team801.robot.commands.WriteData;
import org.usfirst.frc.team801.robot.commands.auto.RightGo;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Drive;
import org.usfirst.frc.team801.robot.commands.chassis.MotionMagicDrive;
import org.usfirst.frc.team801.robot.subsystems.Arm;
import org.usfirst.frc.team801.robot.subsystems.Chassis;
import org.usfirst.frc.team801.robot.subsystems.DataWriter;
import org.usfirst.frc.team801.robot.subsystems.Elevator;
import org.usfirst.frc.team801.robot.subsystems.Lift;
import org.usfirst.frc.team801.robot.subsystems.Pinchers;
import org.usfirst.frc.team801.robot.subsystems.SmartDashUpdater;
import org.usfirst.frc.team801.robot.subsystems.Winch;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	public static OI oi;
	public static Object prefs;
	public static Chassis chassis;
	public static Pinchers pinchers;
	public static Arm arm;
	Command m_autonomousCommand;
	public static Elevator elevator;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	SendableChooser<Object> loc_chooser = new SendableChooser<>();
	private CameraServer server;
	public static Winch winch;
	public static final DataWriter dataWriter = new DataWriter();
	public static final SmartDashUpdater sdUpdater = new SmartDashUpdater();
	public static final WriteData writeData = new WriteData();
//	public static Lift lift;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{
    	prefs = Preferences.getInstance();
    	RobotMap.init();
		chassis = new Chassis();
		elevator = new Elevator();
		pinchers = new Pinchers();
		arm = new Arm();
//		lift = new Lift();
		winch = new Winch();
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		// Set the resolution
		camera.setResolution(640, 480);
//		m_chooser.addDefault("Default Auto", new ExampleCommand());
//		m_chooser.addObject("My Auto", new MyAutoCommand());
		loc_chooser.addDefault("Center", Constants.CENTER);
		loc_chooser.addObject("Location Left", Constants.LEFT);
		loc_chooser.addObject("Location Right", Constants.RIGHT);
		SmartDashboard.putData("Location", loc_chooser);
		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putBoolean("Start Motion", false);
		Robot.chassis.setMotionMagic();

		oi = new OI();
		
		new UpdateSD().start();	//Start SD updater after initialization of all subsystems
        if(DataWriter.logFile != null)
        {
        	writeData.start();
        }
	}


	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		SmartDashboard.putBoolean("Start Motion", false);

		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Gyro Angle", chassis.getGyroAngle());
		SmartDashboard.putNumber("Chassis Position", chassis.getChassisPosition());
		SmartDashboard.putNumber("Chassis_Error", chassis.getChassisError());
		chassis.getVolts();

		SmartDashboard.putNumber("ElevatorPos", lift.getCurrentPosition());

		Scheduler.getInstance().run();

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//Gyro Start time
		double a = Timer.getFPGATimestamp();
		RobotMap.imu.reset();
		double b = Timer.getFPGATimestamp();
		SmartDashboard.putNumber("Reset Gyro Time", b-a);
		String fieldLayout = DriverStation.getInstance().getGameSpecificMessage();
		
		//Switch Case Selector
		a = Timer.getFPGATimestamp();
		PathBuilder logic = new PathBuilder((int) loc_chooser.getSelected(), fieldLayout);
		b = Timer.getFPGATimestamp();
		SmartDashboard.putNumber("PathBuilder Time", b-a);

		m_autonomousCommand = logic.getPath();


		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
//		SmartDashboard.putNumber("Gyro Angle", Robot.chassis.getGyroAngle());
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Gyro Angle", chassis.getGyroAngle());
		SmartDashboard.putNumber("Chassis Position", chassis.getChassisPosition());
		SmartDashboard.putNumber("Chassis_Error", chassis.getChassisError());
		chassis.getVolts();

		SmartDashboard.putNumber("ElevatorPos", lift.getCurrentPosition());
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
