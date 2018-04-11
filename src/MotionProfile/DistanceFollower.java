package MotionProfile;

import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.Utilities.Utils;

import edu.wpi.first.wpilibj.Timer;

/**
	 * The DistanceFollower is an object designed to follow a trajectory based on distance covered input. This class can be used
	 * for Tank or Swerve drive implementations.
	 *
	 * @author Jaci
	 */
	public class DistanceFollower {

	    double kp, ki, kd, kv, ka;

	    double[] last_error = new double[2];
	    double heading;

	    double[] trajectory = new double[2];

		private double old_time =0;

		private double dt = 0;

		private double new_time = 0;

		private double max_velocity;

		private double max_acceleration;

		private double output;
		
		private double L = 33.5;
		private double W = 28.25;
		private double R = Math.sqrt(L*L+W*W);

		private int moduleNum;
		/**
		 * @param traj				The x_y position in inches, i.e way-point
		* @param moduleNum			The swerve module number 0-3
		*/
	    public DistanceFollower(double[] traj, int moduleNum) {
	        this.trajectory = traj;
	        this.moduleNum = moduleNum;
	    }

	    public DistanceFollower() { }

	    /**
	     * Set a new trajectory to follow, and reset the cumulative errors and segment counts
	     */
	    public void setTrajectory(double[] traj, int moduleNum) {
	        this.trajectory = traj;
	        this.moduleNum = moduleNum;
	        reset();
	    }

	    /**
	     * Configure the PID/VA Variables for the Follower
	     * @param kp The proportional term. This is usually quite high (0.8 - 1.0 are common values)
	     * @param ki The integral term. Currently unused.
	     * @param kd The derivative term. Adjust this if you are unhappy with the tracking of the follower. 0.0 is the default
	     * @param kv The velocity ratio. This should be 1 over your maximum velocity @ 100% throttle.
	     *           This converts m/s given by the algorithm to a scale of -1..1 to be used by your
	     *           motor controllers
	     * @param ka The acceleration term. Adjust this if you want to reach higher or lower speeds faster. 0.0 is the default
	     */
	    public void configurePIDVA(double kp, double ki, double kd, double kv, double ka) {
	        this.kp = kp; this.ki = ki; this.kd = kd;
	        this.kv = kv; this.ka = ka;
	    }
	    
	    public void configureSpeed(double max_velocity, double max_acceleration) {
	    	
	    	this.max_velocity = max_velocity;
	    	this.max_acceleration = max_acceleration;
	    }
	    
	    public void configureRobotBase(double L, double W) {
	    	
	    }

	    /**
	     * Reset the follower to start again. Encoders must be reconfigured.
	     */
	    public void reset() {
	        last_error[0] = 0;
	        last_error[1] = 0;

	    }
	    
	    /**
	     * Calculate the desired output for the motors, based on the distance the robot has covered.
	     * This does account for heading of the robot.
	     * @param distance_covered  The distance covered in meters for each swerve module
	     * @param gyroAngle			The gyro heading of the robot
	     * @param RCW				The amount of rotations, usually controlled by a PID controller input, i.e hold heading
	     * @return                  The desired output for your motor controller
	     */
	    public double calculate(double[] distance_covered, double gyroAngle, double RCW) {
	    	new_time  = Timer.getFPGATimestamp(); 	
	    	dt  = new_time - old_time;
	    	double[] error = new double[2];//for x and y clicks on encoders
	    	double[] calculated_value = new double[2];
			for(int i =0; i<2; i++) {
	            error [i] = trajectory[i] - distance_covered[i];
	            calculated_value[i] =
	                    kp * error[i] +                                    // Proportional
	                    kd * ((error[i] - last_error[i]) / dt) +          // Derivative
	                    (kv * max_velocity + ka * max_acceleration);    // V and A Terms
	            last_error[i] = error[i];
			}
	    	old_time = new_time;
			double STR = calculated_value[0];
			double FWD = calculated_value[1];
	    	double radians = gyroAngle*Math.PI/180.00;
			double temp = FWD*Math.cos(radians) + STR*Math.sin(radians);
			STR = -FWD*Math.sin(radians) + STR*Math.cos(radians);
			FWD = temp;
			double A = 0, C = 0;
			switch (moduleNum){
			case 0:
				A = STR + RCW*(L/R);
				C = FWD - RCW*(W/R);
				break;
			case 1:
				A = STR + RCW*(L/R);
				C = FWD + RCW*(W/R);
				break;
			case 2:
				A = STR - RCW*(L/R);
				C = FWD + RCW*(W/R);
				break;
			case 3:
				A = STR - RCW*(L/R);
				C = FWD - RCW*(W/R);
				break;

			}
			output = Math.sqrt(A*A + C*C); 
			heading = Utils.wrapAngle0To360Deg(Math.atan2(A,C)*180/Math.PI);
	        return output;
	        
	    }

	    /**
	     * @return the desired heading of the current point in the trajectory
	     */
	    public double getHeading() {
	        return heading;
	    }


	    /**
	     * @return 
	     * @return whether we have finished tracking this trajectory or not.
	     */
	    public void isFinished() {
//	        return  >= trajectory.length();
	    }
	    
}


