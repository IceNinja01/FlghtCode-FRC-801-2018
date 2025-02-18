package org.usfirst.frc.team801.robot.commands;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
@SuppressWarnings("unused")
public class DriveWithJoysticks extends Command {
	private double x,y,z;
	
    public DriveWithJoysticks() {
    	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    			
    	Robot.chassis.motorDrive(Robot.chassis.getGyroAngle());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    	Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	end();
    }
}
