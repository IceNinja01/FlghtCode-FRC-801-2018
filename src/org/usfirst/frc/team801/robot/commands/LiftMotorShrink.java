package org.usfirst.frc.team801.robot.commands;

import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftMotorShrink extends Command {

    public LiftMotorShrink() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.print("Shrink:   ");
    	Robot.lift.setBottomLimit();
//    	Robot.lift.getCurrentPosition() ;
    	if(Robot.lift.getCurrentPosition() > 500) {
    		Robot.lift.setZeroPos();
//    		Robot.lift.setBottomLimit();

    	}
    	Robot.lift.setInvert(true);
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.lift.shrink();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    	}

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.getCurrentPosition();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
