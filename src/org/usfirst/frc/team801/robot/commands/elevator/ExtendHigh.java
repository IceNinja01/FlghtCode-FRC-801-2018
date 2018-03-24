package org.usfirst.frc.team801.robot.commands.elevator;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ExtendHigh extends Command {
	
	
	private double error;
	private double a;
	private double b;

	public ExtendHigh() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.print("Extend:   ");
    	Robot.elevator.getCurrentPosition() ;
    	Robot.elevator.coastMotor();
    	Robot.elevator.setUpSpeed();
    	Robot.elevator.extendHigh();
    	a = Timer.getFPGATimestamp();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.getCurrentPosition();
    	error = Constants.elevatorMotorTopPos - Robot.elevator.getCurrentPosition();

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(error) < 0.5);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.getCurrentPosition();
    	Robot.elevator.stopMotor();
    	Robot.elevator.setDownSpeed();
    	b = Timer.getFPGATimestamp() - a;
    	SmartDashboard.putNumber("ExtendTime", b);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevator.stopMotor();
    }
}
