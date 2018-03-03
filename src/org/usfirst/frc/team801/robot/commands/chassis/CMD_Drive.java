package org.usfirst.frc.team801.robot.commands.chassis;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team801.robot.Utilities.*;

import MotionProfile.MotionProfile;
/**
 *
 */
public class CMD_Drive extends Command {

	private int j=0;
    private MotionProfile profile = new MotionProfile();
	private double distance;
	private double maxVel;
	private double accel;
	private double heading;
	private double dist;
	private double error;
	private double newDistance;
	private double x;
	private double y;
	public CMD_Drive(double X, double Y, double heading, double distance) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
//    	x_y = profile.XYCoord(distance, maxVel, accel, heading);
    	this.distance = distance;
    	this.x = X;
    	this.y = Y;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	Robot.chassis.pointWheels(0); //point wheels strait
//    	Timer.delay(0.5);
    	Robot.chassis.setMotionMagic();
    	distance += -Robot.chassis.getChassisPosition();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.chassis.cmdDrive(x, -y, heading, Robot.chassis.getGyroAngle());
//    	System.out.print("X: " + x_y[j][0]);
//    	System.out.println("\tY: " + x_y[j][1]);
    	dist = -Robot.chassis.getChassisPosition();;
    	error = Math.abs(dist - distance);
       	System.out.print("target:\t" + distance);
    	System.out.print("\tpos:\t" + dist);
    	System.out.println("\terror:\t" + error);
//    	j++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (error < 1.5);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
