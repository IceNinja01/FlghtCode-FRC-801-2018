package org.usfirst.frc.team801.robot.commands.chassis;

import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.Utilities.Utils;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnFront extends Command {

    private double angleCMD;
	private double error; 

	public TurnFront() {
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	angleCMD =0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.chassis.turnToHeading(angleCMD, Robot.chassis.getGyroAngle());
    	error = angleCMD - Robot.chassis.getGyroAngle();

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(error) < 2);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
