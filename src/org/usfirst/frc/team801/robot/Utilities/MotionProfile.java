package org.usfirst.frc.team801.robot.Utilities;

import org.usfirst.frc.team801.robot.Constants;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;

public class MotionProfile {
	private MotionProfileStatus _status = new MotionProfileStatus();

	private TalonSRX[] motionProfileMotors;
	
	class PeriodicRunnable implements java.lang.Runnable {
	    public void run()
	    {
	    	for(int i = 0; i < motionProfileMotors.length; i++)
	    	{
	    		motionProfileMotors[i].processMotionProfileBuffer();
	    	}
	    }
	}
	private boolean started = false;
	private Notifier _notifer = new Notifier(new PeriodicRunnable());
	private double[][] profile;
	
	public MotionProfile(TalonSRX[] motors, double distance, double maxVel, double accel)
	{
		for(int i = 0; i < motors.length; i++)
		{
			motors[i].changeMotionControlFramePeriod(5);
			_notifer.startPeriodic(0.005);
		}
		_notifer.startPeriodic(0.005);
		motionProfileMotors = motors;
		profile = OneDimensionMotion(distance, maxVel, accel);
	}
	
	public void start()
	{
		started = true;
		startFilling();
	}
	
	public void stop()
	{
		started = false;
	}
	
	/**
	 * Called to clear Motion profile buffer and reset state info during
	 * disabled and when Talon is not in MP control mode.
	 */
	public void reset()
	{
		for(int i = 0; i < motionProfileMotors.length; i++)
		{
			/*
			 * Let's clear the buffer just in case user decided to disable in the
			 * middle of an MP, and now we have the second half of a profile just
			 * sitting in memory.
			 */
			motionProfileMotors[i].clearMotionProfileTrajectories();
		}
	}
	
	private void startFilling() {
		
		TrajectoryPoint[] pointArray = new TrajectoryPoint[motionProfileMotors.length];
		System.out.print("Array Lenght");
		System.out.println(profile.length);
		for(int i = 0; i < motionProfileMotors.length; i++)
		{
			pointArray[i] = new TrajectoryPoint();
			/* did we get an underrun condition since last time we checked ? */
			if (_status.hasUnderrun) {
				/* better log it so we know about it */
				Instrumentation.OnUnderrun();
				/*
				 * clear the error. This flag does not auto clear, this way 
				 * we never miss logging it.
				 */
				motionProfileMotors[i].clearMotionProfileHasUnderrun(0);
			}
			motionProfileMotors[i].clearMotionProfileTrajectories();
			motionProfileMotors[i].configMotionProfileTrajectoryPeriod(Constants.kBaseTrajPeriodMs, Constants.kTimeoutMs);
			/* This is fast since it's just into our TOP buffer */
			for (int j = 0; j < profile.length; ++j) {
				double positionRot = profile[j][0];
				double velocityRPM = profile[j][1];
				/* for each point, fill our structure and pass it to API */
				pointArray[i].position = positionRot * Constants.kSensorUnitsPerRotation; //Convert Revolutions to Units
				pointArray[i].velocity = velocityRPM * Constants.kSensorUnitsPerRotation / 600.0; //Convert RPM to Units/100ms
				pointArray[i].headingDeg = 0; /* future feature - not used in this example*/
				pointArray[i].profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
				pointArray[i].profileSlotSelect1 = 0; /* future feature  - not used in this example - cascaded PID [0,1], leave zero */
				pointArray[i].timeDur = GetTrajectoryDuration((int)profile[i][2]);
				pointArray[i].zeroPos = false;
				if (i == 0)
					pointArray[i].zeroPos = true; /* set this to true on the first point */

				pointArray[i].isLastPoint = false;
				if ((i + 1) == profile.length)
					pointArray[i].isLastPoint = true; /* set this to true on the last point  */

				motionProfileMotors[i].pushMotionProfileTrajectory(pointArray[i]);
			}
		}
	}
	
	/**
	 * Find enum value if supported.
	 * @param durationMs
	 * @return enum equivalent of durationMs
	 */
	private TrajectoryDuration GetTrajectoryDuration(int durationMs)
	{	 
		/* create return value */
		TrajectoryDuration retval = TrajectoryDuration.Trajectory_Duration_0ms;
		/* convert duration to supported type */
		retval = retval.valueOf(durationMs);
		/* check that it is valid */
		if (retval.value != durationMs) {
			DriverStation.reportError("Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);		
		}
		/* pass to caller */
		return retval;
	}
	
	/*
	This method outputs a 3 column array, [position, velocity, ms], is used for a 1D motion control
	Uses a trapezoidal method for ramp on and ramp on.
	 1. v = V(t) + Accel;
	 2. p = P(t) + v + 1 / 2*Accel;
	 Where V(t) is equal to the motors current velocity
	 and where P(t) is equal to the motors current position
	 */
	private double[][] OneDimensionMotion(double distance, double maxVel, double accel) {
		/* distance is units ft
		 * MaxVelocity is ft/sec
		 * accel is ft/sec^2
		 */
		
		double pathTime = 0;
		double rampTime = maxVel/accel;
	    double accelDist = 0.5 * accel*Math.pow(rampTime, 2);
	    // Tests if we can even reach our maxVelocity, if we can't, then the boolean is true
	    if (accelDist * 2 > distance)
	    {//Triangle
	    	pathTime = Math.sqrt(distance/accel) * 2;
	    } else
	    {//Trapezoidal
	        pathTime = rampTime * 2 + (distance - accelDist * 2) / maxVel;
	    }
	    double dt = 0.01; // steps are equal to 10ms
	    int segments = (int) (pathTime*(1/0.01)) + 1;
	    // [position, velocity, time]
		double[][] path = new double[segments][3];
		path[0][0] = 0;
		path[0][1] = 0;
		path[0][2] = dt;
		
		for(int i = 1; i < segments; i++)
		{
			//v = V(t) + Accel;
			double accelCurrent = 0;
			//Triangle 
			//Method needs to be tested on the robot to evaluate its precision
			if(accelDist * 2 > distance)
			{
				if(i >= segments/2)
				{
					accelCurrent = -accel;
				}
				else
				{
					accelCurrent = accel;
				}
			}
			//Trapezoidal
			else
			{
				if(i <= rampTime/dt)
				{// upwards on vel Ramp
					accelCurrent = accel;
				}
				else if(i > rampTime/dt && i < segments-rampTime/dt)
				{// at maxVelocity, no accel
					accelCurrent = 0;
				}
				else if(i > segments-rampTime/dt)
				{// downwards on vel ramp, deaccel
					accelCurrent = -accel;
				}
			}

			path[i][0] = path[i-1][0] + ((path[i-1][1] + (accelCurrent*dt))*dt) + 0.5*accelCurrent*Math.pow(dt,2); //position
			path[i][1] = path[i-1][1] + (accelCurrent*dt); 								 //velocity
			path[i][2] = dt;												 //time
		}
		
		path[segments-1][0] = distance;
		path[segments-1][1] = 0;
		path[segments-1][2] = dt;
		
		return path;
	    
	}
	
	public static void TwoDimensionMotion() {
		
		
	}
	
	
}
