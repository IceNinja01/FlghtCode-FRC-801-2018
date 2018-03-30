package org.usfirst.frc.team801.robot.commands.chassis;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team801.robot.Utilities.*;

import MotionProfile.MotionProfile;
/**
 *
 */
public class CMD_Angle_Drive extends Command {

	private int j=0;
    private MotionProfile profile = new MotionProfile();
	private double distance;
	private double accel;
	private double heading;
	private double dist;
	private double error;
	private double newDistance;
	private double x;
	private double y;
	private double gyro;
	public CMD_Angle_Drive(double distance, double heading, double maxVel, double gyro) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    	this.distance = distance;
    	double radians = heading *Math.PI/180.00;
    	this.x = Utils.limitMagnitude(Math.cos(radians)*maxVel, 0.05, 1.0);
    	this.y = Utils.limitMagnitude(Math.sin(radians)*maxVel, 0.05, 1.0);
    	this.gyro =  gyro;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	distance += Robot.chassis.getChassisPosition();
    	//Ramp up motors;
    	for(int i = 0 ; i<100; i++) { //assuming cycle time is 50 ms, so a total of 500ms

        	Robot.chassis.cmdDrive(x*i/100, y*i/100, gyro, Robot.chassis.getGyroAngle());
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	Robot.chassis.cmdDrive(x, y, gyro, Robot.chassis.getGyroAngle());
    	dist = Robot.chassis.getChassisPosition();;
    	error = (distance-dist);
       	System.out.print("target:\t" + distance);
    	System.out.print("\tpos:\t" + dist);
    	System.out.println("\terror:\t" + error);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (error < 2.0);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.chassis.stop();

    }
}
