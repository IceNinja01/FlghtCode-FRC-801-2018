package org.usfirst.frc.team801.robot.commands.chassis;

import org.usfirst.frc.team801.robot.Robot;

import MotionProfile.MotionProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MotionMagicDrive extends Command {

    private double distance;

	private double turnAngle;

	private double dist;

	private double error;

	private MotionProfile profile;

	private double[][] path;

	private int j = 0;
	
	public MotionMagicDrive(double distance, double turnAngle) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
        this.distance = distance;

        this.turnAngle=turnAngle; //array of distance to turn, the angle to turn from current 90 degree left = -90, rad/sec
    	profile = new MotionProfile();

	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.pointWheels(turnAngle); //point wheels strait
    	Timer.delay(0.3);
    	
    	path = MotionProfile.OneDimenisonMotion(24, 50, 50, 0, 0);
//    	Robot.chassis.setMotionMagic();
    	j  = 0;
    	SmartDashboard.putBoolean("Start Motion", true);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.pointWheelsDrive(turnAngle, path[j][1]);
    	System.out.println(path[j][1]);
//    	error = Robot.chassis.getChassisError();
    	j++;
//    	Robot.chassis.getTurnAngles(turnAngle);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; //200 is 0.05% of 4096 which is 1 revolution of the shaft
    }

    // Called once after isFinished returns true
    protected void end() {
		SmartDashboard.putBoolean("Start Motion", false);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
