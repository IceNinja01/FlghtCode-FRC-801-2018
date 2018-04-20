package org.usfirst.frc.team801.robot.commands.gather;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GatherStop extends Command{

	public GatherStop() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.gatherer);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gatherer.stopGather();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	Robot.gatherer.gatherUp();

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

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
