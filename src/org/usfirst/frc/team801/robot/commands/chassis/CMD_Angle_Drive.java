package org.usfirst.frc.team801.robot.commands.chassis;

import org.usfirst.frc.team801.robot.Constants;
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
	private double accel = 0.5;
	private double heading;
	private double dist;
	private double error;
	private double newDistance;
	private double x;
	private double y;
	private double gyro;
	private double dt;
	private double initial_dt;
	private double i;
	private double accelDist;
	private double maxVel;
	private double rampTime;
	private double max_x;
	private double max_y;
	private double decelDist;
	public CMD_Angle_Drive(double distance, double heading, double maxVel, double gyro) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    	this.distance = distance;
    	double radians = heading *Math.PI/180.00;
    	this.max_x = Utils.limitMagnitude(Math.cos(radians)*maxVel, 0.05, 1.0);
    	this.max_y = Utils.limitMagnitude(Math.sin(radians)*maxVel, 0.05, 1.0);
    	this.gyro =  gyro;
    	this.maxVel = maxVel;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	rampTime = maxVel / accel;
		accelDist = 0.5 * accel * Math.pow(rampTime, 2) * Constants.ftpersec;
    	
    	initial_dt = Timer.getFPGATimestamp();
    	distance += Robot.chassis.getChassisPosition();
//    	//Ramp up motors;
//    	for(int i = 0 ; i<100; i++) { //assuming cycle time is 50 ms, so a total of 500ms
//
//        	Robot.chassis.cmdDrive(x*i/100, y*i/100, gyro, Robot.chassis.getGyroAngle());
//    	
//    	}
    	i=0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//calculate x and y i.e the amount of velocity to increase, steady or decrease
    	//assume ramp time is based off cycle speed of 50msec
    	//ramptime is then equal to acceleration 6 ft/sec is roughly equal to a x and y of 0.5
    	//so 0.5/1000
    	dt = Timer.getFPGATimestamp() - dt;
    	
    	dist = Robot.chassis.getChassisPosition();;
    	error = (distance-dist);
       	System.out.print("target:\t" + distance);
    	System.out.print("\tpos:\t" + dist);
    	System.out.println("\terror:\t" + error);
    	if(error > distance - accelDist) { //accelerate
    		i += accel*dt;
    		x = max_x*i;
    		y = max_y*i;
    	}
    	if(error <= accelDist) {//deaccelerate
    		i -= accel*dt;
    		x = max_x*i;
    		y = max_y*i;
    	}
    	else {//maxVelocity
    		x = max_x;
    		y = max_y;
    	}
    	Robot.chassis.cmdDrive(x, y, gyro, Robot.chassis.getGyroAngle());

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
