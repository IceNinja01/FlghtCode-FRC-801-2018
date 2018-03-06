package org.usfirst.frc.team801.robot.commands.elevator;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Shrink extends Command {

	
	private double error;

	public Shrink() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.print("Shrink:   ");
    	Robot.elevator.getCurrentPosition();
    	Robot.elevator.coastMotor();
		Robot.elevator.setShrink();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	Robot.elevator.getCurrentPosition();
    	Robot.elevator.shrink();
    	error = Constants.elevatorMotorBottomPos - Robot.elevator.getCurrentPosition();

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(error) < 0.5);
    	}

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.getCurrentPosition();
    	Robot.elevator.endShrink();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevator.stopMotor();

    }
}
