package org.usfirst.frc.team801.robot.elevator.commands;

import org.usfirst.frc.team801.robot.Robot;

public class ExtendMid {
	
	
	public ExtendMid() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.print("Extend:   ");
    	Robot.elevator.getCurrentPosition() ;
    	Robot.elevator.coastMotor();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.extendMid();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.print("  ");

    	Robot.elevator.getCurrentPosition();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevator.stopMotor();
    }
}
