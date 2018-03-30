package org.usfirst.frc.team801.robot.commands.gather;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EjectBoxGrab extends Command{


	public EjectBoxGrab() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.gatherer);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gatherer.eject();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    
    // Called once after isFinished returns true
    protected void end() {
    	Robot.gatherer.stopGather();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.gatherer.stopGather();

    }

	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
