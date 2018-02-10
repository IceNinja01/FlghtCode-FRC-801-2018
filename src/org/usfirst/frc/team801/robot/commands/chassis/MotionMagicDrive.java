package org.usfirst.frc.team801.robot.commands.chassis;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MotionMagicDrive extends Command {

    private double distance;
	private double cruiseVelocity;
	private double acceleration;
	private double[][] turnAngle;
	private int i;
	public MotionMagicDrive(double distance,double cruiseVelocity, double acceleration, double[][] turnAngle) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
        this.distance = distance;
        this.cruiseVelocity = cruiseVelocity;
        this.acceleration = acceleration;
        this.turnAngle=turnAngle; //array of distance to turn, the angle to turn from current 90 degree left = -90, rad/sec
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.pointWheels(Robot.chassis.getGyroAngle()); //point wheels strait
    	Timer.delay(0.3);
    	Robot.chassis.setMotionMagic(3.0, 1.0);
    	Robot.chassis.driveMotionMagic(48.0);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.driveMotionMagic(48.0);


//    	Robot.chassis.getTurnAngles(turnAngle);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
