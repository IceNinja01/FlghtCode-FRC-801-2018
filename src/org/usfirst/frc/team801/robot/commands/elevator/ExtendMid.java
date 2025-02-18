package org.usfirst.frc.team801.robot.commands.elevator;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ExtendMid extends Command {
	
	
	private double error;

	public ExtendMid() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.print("Extend:   ");
    	Robot.elevator.getCurrentPosition() ;
    	Robot.elevator.coastMotor();
    	Robot.elevator.extendMid();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

//    	Robot.elevator.getCurrentPosition();
    	error = Constants.elevatorMotorMidPos - Robot.elevator.getCurrentPosition();

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(error) < 0.5);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.getCurrentPosition();
    	Robot.elevator.stopMotor();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevator.stopMotor();
    }
}
