package org.usfirst.frc.team801.robot.commands.chassis;

import org.usfirst.frc.team801.robot.Robot;

import MotionProfile.DistanceFollower;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TrajectoryDrive extends Command {

	private DistanceFollower dist_fw[] = new DistanceFollower[4];
	private double[] distance = new double[2];
	private double angle;
	private double heading;
	private double maxVel;
	private double maxAccel;
	private double[] dist = new double[2];
	private double errorX;
	private double errorY;
	
	 /**
     * Command the robot by distance way point[x,y] inches from relative position
     * @param distance	way point to drive to in inches [x,y] input
     * @param heading	Heading of the robot to hold/rotate during translation
     * @param maxVel	Max Velocity of motor -1 to 1 ~ 10ft/sec at 1
     * @param maxAccel	Max Acceleration of motors in ft/sec
     */
    public TrajectoryDrive(double[] distance, double angle, double heading, double maxVel, double maxAccel) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
        this.distance = distance;
        this.angle = angle;
        this.heading = heading;
        this.maxVel = maxVel;
        this.maxAccel = maxAccel;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.initFollowers(distance, maxVel, maxAccel);
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.chassis.setFollowers(heading);
    	dist = Robot.chassis.getChassisError();;
    	errorX = (distance[0]-dist[0]);
    	errorY = (distance[1]-dist[1]);


    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (errorX < 2.0 && errorY < 2.0);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
