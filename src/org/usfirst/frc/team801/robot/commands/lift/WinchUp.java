package org.usfirst.frc.team801.robot.commands.lift;

import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WinchUp extends Command {

    public WinchUp() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lift.winchUp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.lift.winchUp();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    	}

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.stopWinch();
    	Robot.lift.getCurrentPosition();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lift.stopMotor();

    }
}
