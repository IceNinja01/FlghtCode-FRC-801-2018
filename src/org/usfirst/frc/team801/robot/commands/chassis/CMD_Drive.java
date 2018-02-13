package org.usfirst.frc.team801.robot.commands.chassis;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CMD_Drive extends Command {

    private double[] x = new double[2000];
	private int j=0;
    
	public CMD_Drive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	x[0] =0.01;
    	for(int i=1; i<1000; i++) {
    		x[i] = 0.01 + x[i-1];
    	}
    	for(int i=0; i<1000; i++) {
    		x[i+1000] = 0.01 - x[i-1];
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.chassis.chassisSwerveDrive.drive(x[j],0,0,0);
    	j++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (j>=2000);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
