package org.usfirst.frc.team801.robot.Utilities;

import org.usfirst.frc.team801.robot.Constants;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;

<<<<<<< HEAD
public class MotionProfile
{
	private static final int kMinPointsInTalon = 10;

=======
public class MotionProfile {
	private static final int kMinPointsInTalon = 5;
	
>>>>>>> 22bee8f980a33173208c1ea4ba4fe1caa3b1e437
	private TalonSRX[] motors;

	class PeriodicRunnable implements java.lang.Runnable
	{
		public void run()
		{
			for (int i = 0; i < motors.length; i++)
			{
				motors[i].processMotionProfileBuffer();
			}
		}
	}

	private boolean started = false;
	private MotionProfileStatus[] status;
	private SetValueMotionProfile[] setValue;
	private Notifier notifer = new Notifier(new PeriodicRunnable());
	private double[][] profile;

	public MotionProfile(TalonSRX[] motors, double distance, double maxVel, double accel)
	{
		status = new MotionProfileStatus[motors.length];
		setValue = new SetValueMotionProfile[motors.length];
		for (int i = 0; i < motors.length; i++)
		{
			/* set the peak and nominal outputs */
			motors[i].configNominalOutputForward(0, Constants.kTimeoutMs);
			motors[i].configNominalOutputReverse(0, Constants.kTimeoutMs);
			motors[i].configPeakOutputForward(11, Constants.kTimeoutMs);
			motors[i].configPeakOutputReverse(-11, Constants.kTimeoutMs);

			/* set closed loop gains in slot0 - see documentation */
			motors[i].configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
			motors[i].setSensorPhase(false); /* keep sensor and motor in phase */
			motors[i].configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);

			motors[i].config_kF(0, 0.2, Constants.kTimeoutMs);
			motors[i].config_kP(0, 0.5, Constants.kTimeoutMs);
			motors[i].config_kI(0, 0.0, Constants.kTimeoutMs);
<<<<<<< HEAD

			motors[i].config_kD(0, 0.2, Constants.kTimeoutMs);
=======
			
			motors[i].config_kD(0, 0.002, Constants.kTimeoutMs);
>>>>>>> 22bee8f980a33173208c1ea4ba4fe1caa3b1e437
			/* Our profile uses 10ms timing */
			motors[i].configMotionProfileTrajectoryPeriod(10, Constants.kTimeoutMs);
			/*
			 * status 10 provides the trajectory target for motion profile AND motion magic
			 */
			motors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
			motors[i].changeMotionControlFramePeriod(5);
			motors[i].set(ControlMode.MotionProfile, 70);

			motors[i].setSelectedSensorPosition(0, 0, 10);

			status[i] = new MotionProfileStatus();
			setValue[i] = SetValueMotionProfile.Disable;
		}
		notifer.startPeriodic(0.005);
		this.motors = motors;
		profile = OneDimensionMotion(distance, maxVel, accel);
	}

	public void start()
	{
		startFilling();
	}

	public void stop()
	{
		started = false;
	}

	/**
	 * Called to clear Motion profile buffer and reset state info during disabled
	 * and when Talon is not in MP control mode.
	 */
	public void reset()
	{
		for (int i = 0; i < motors.length; i++)
		{
			/*
			 * Let's clear the buffer just in case user decided to disable in the middle of
			 * an MP, and now we have the second half of a profile just sitting in memory.
			 */
			motors[i].clearMotionProfileTrajectories();
		}
	}

	public void control()
	{
<<<<<<< HEAD
		for (int i = 0; i < motors.length; i++)
		{
			motors[i].getMotionProfileStatus(status[i]);
			if (motors[i].getControlMode() == ControlMode.MotionProfile)
			{
				/*
				 * if(status[i].btmBufferCnt > kMinPointsInTalon) { setValue[i] =
				 * SetValueMotionProfile.Enable; started = true; } if
				 * (status[i].activePointValid && status[i].isLast) { // because we set the last
				 * point's isLast to true, we will get here when the MP is done setValue[i] =
				 * SetValueMotionProfile.Hold; started = false; }
				 */
=======
		for(int m = 0; m < motors.length; m++)
		{
			motors[m].getMotionProfileStatus(status[m]);
			if(status[m].btmBufferCnt == 0)
			{ //buffer is empty do nothing
				setValue[m] = SetValueMotionProfile.Disable;
				motors[m].set(ControlMode.MotionProfile, setValue[m].value);
			}
			
			if(status[m].btmBufferCnt > kMinPointsInTalon)
			{ //buffer has something goahead and go
				setValue[m] = SetValueMotionProfile.Enable;
				motors[m].set(ControlMode.MotionProfile, setValue[m].value);
			}
			
			if(status[m].activePointValid && status[m].isLast)
			{
				setValue[m] = SetValueMotionProfile.Hold;
				motors[m].set(ControlMode.MotionProfile, setValue[m].value);
>>>>>>> 22bee8f980a33173208c1ea4ba4fe1caa3b1e437
			}
			// Get the motion profile status every loop
			motors[m].getMotionProfileStatus(status[m]);
			System.out.print("Actual Rotations: "+(motors[m].getSelectedSensorPosition(0))+"\t");
			System.out.println("Actual Velocity: "+(motors[m].getSelectedSensorVelocity(0)));
		}
	}

	private void startFilling()
	{
		TrajectoryPoint point = new TrajectoryPoint();
		for (int m = 0; m < motors.length; m++)
		{
			motors[m].clearMotionProfileTrajectories();
			motors[m].configMotionProfileTrajectoryPeriod(Constants.kBaseTrajPeriodMs, Constants.kTimeoutMs);
		}
		for (int p = 0; p < profile.length; p++)
		{
			// This is fast since it's just into our TOP buffer
			for (int m = 0; m < motors.length; m++)
			{
				// did we get an underrun condition since last time we checked ?
				if (status[m].hasUnderrun)
				{
					Instrumentation.OnUnderrun();
					motors[m].clearMotionProfileHasUnderrun(0);
				}
				double positionRot = profile[p][0];
				double velocityRPM = profile[p][1];
				// for each point, fill our structure and pass it to API
				point.position = positionRot * Constants.kSensorUnitsPerRotation; // Convert Revolutions to Units
				point.velocity = velocityRPM * Constants.kSensorUnitsPerRotation / 600; // Convert RPM to Units/100ms
				System.out.print("Target Rotations: " + (positionRot) + "\t");
				System.out.print("Target Velocity: " + (velocityRPM) + "\t");
				point.headingDeg = 0; // future feature - not used in this example
				point.profileSlotSelect0 = 0; // which set of gains would you like to use [0,3]?
				point.profileSlotSelect1 = 0; // future feature - not used in this example - cascaded PID [0,1], leave
												// zero
				point.timeDur = GetTrajectoryDuration((int) profile[p][2]);
				point.zeroPos = false;
				if (p == 0)
					point.zeroPos = true; // set this to true on the first point

				point.isLastPoint = false;
				if ((p + 1) == profile.length)
					point.isLastPoint = true; // set this to true on the last point

				motors[m].pushMotionProfileTrajectory(point);
<<<<<<< HEAD

				if (status[m].btmBufferCnt == 0)
				{ // buffer is empty do nothing
					setValue[m] = SetValueMotionProfile.Disable;
					motors[m].set(ControlMode.MotionProfile, setValue[m].value);
				}

				if (status[m].btmBufferCnt > kMinPointsInTalon)
				{ // buffer has something goahead and go
					setValue[m] = SetValueMotionProfile.Enable;
					motors[m].set(ControlMode.MotionProfile, setValue[m].value);
				}

				if (status[m].activePointValid && status[m].isLast)
				{
					setValue[m] = SetValueMotionProfile.Hold;
					motors[m].set(ControlMode.MotionProfile, setValue[m].value);
				}
				// Get the motion profile status every loop
				motors[m].getMotionProfileStatus(status[m]);
				System.out.print("Actual Rotations: " + (motors[m].getSelectedSensorPosition(0)) + "\t");
				System.out.println("Actual Velocity: " + (motors[m].getSelectedSensorVelocity(0)));
=======
				

>>>>>>> 22bee8f980a33173208c1ea4ba4fe1caa3b1e437
			}
		}
		/*
		 * TrajectoryPoint[] pointArray = new TrajectoryPoint[motors.length]; for(int i
		 * = 0; i < motors.length; i++) { pointArray[i] = new TrajectoryPoint(); // did
		 * we get an underrun condition since last time we checked ? if
		 * (status[i].hasUnderrun) { Instrumentation.OnUnderrun();
		 * motors[i].clearMotionProfileHasUnderrun(0); }
		 * motors[i].clearMotionProfileTrajectories();
		 * motors[i].configMotionProfileTrajectoryPeriod(Constants.kBaseTrajPeriodMs,
		 * Constants.kTimeoutMs); // This is fast since it's just into our TOP buffer
		 * for (int j = 0; j < profile.length; ++j) { double positionRot =
		 * profile[j][0]; double velocityRPM = profile[j][1]; // for each point, fill
		 * our structure and pass it to API pointArray[i].position = positionRot *
		 * Constants.kSensorUnitsPerRotation; //Convert Revolutions to Units
		 * pointArray[i].velocity = velocityRPM * Constants.kSensorUnitsPerRotation /
		 * 600.0; //Convert RPM to Units/100ms pointArray[i].headingDeg = 0; // future
		 * feature - not used in this example pointArray[i].profileSlotSelect0 = 0; //
		 * which set of gains would you like to use [0,3]?
		 * pointArray[i].profileSlotSelect1 = 0; // future feature - not used in this
		 * example - cascaded PID [0,1], leave zero pointArray[i].timeDur =
		 * GetTrajectoryDuration((int)profile[i][2]); pointArray[i].zeroPos = false; if
		 * (i == 0) pointArray[i].zeroPos = true; // set this to true on the first point
		 * 
		 * pointArray[i].isLastPoint = false; if ((i + 1) == profile.length)
		 * pointArray[i].isLastPoint = true; // set this to true on the last point
		 * 
		 * motors[i].pushMotionProfileTrajectory(pointArray[i]);
		 * 
		 * if(status[i].btmBufferCnt == 0) { //buffer is empty do nothing setValue[i] =
		 * SetValueMotionProfile.Disable; motors[i].set(ControlMode.MotionProfile,
		 * setValue[i].value); } if(status[i].btmBufferCnt == 1) { //buffer has
		 * something goahead and go setValue[i] = SetValueMotionProfile.Enable;
		 * motors[i].set(ControlMode.MotionProfile, setValue[i].value); }
		 * 
		 * if(status[i].isLast) { setValue[i] = SetValueMotionProfile.Hold;
		 * motors[i].set(ControlMode.MotionProfile, setValue[i].value); } // Get the
		 * motion profile status every loop motors[i].getMotionProfileStatus(status[i]);
		 * } }
		 */
	}

	/**
	 * Find enum value if supported.
	 * 
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
		if (retval.value != durationMs)
		{
			DriverStation.reportError(
					"Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);
		}
		/* pass to caller */
		return retval;
	}

	/*
	 * This method outputs a 3 column array, [position, velocity, ms], is used for a
	 * 1D motion control Uses a trapezoidal method for ramp on and ramp on. 1. v =
	 * V(t) + Accel; 2. p = P(t) + v + 1 / 2*Accel; Where V(t) is equal to the
	 * motors current velocity and where P(t) is equal to the motors current
	 * position
	 */
	private double[][] OneDimensionMotion(double distance, double maxVel, double accel)
	{
		/*
		 * distance is units ft MaxVelocity is ft/sec accel is ft/sec^2
		 */

		double pathTime = 0;
		double rampTime = maxVel / accel;
		double accelDist = 0.5 * accel * Math.pow(rampTime, 2);
		// Tests if we can even reach our maxVelocity, if we can't, then the boolean is
		// true
		if (accelDist * 2 > distance)
		{// Triangle
			pathTime = Math.sqrt(distance / accel) * 2;
		} else
		{// Trapezoidal
			pathTime = rampTime * 2 + (distance - accelDist * 2) / maxVel;
		}
		double dt = 0.01; // steps are equal to 10ms
		int segments = (int) (pathTime * (1 / 0.01)) + 1;
		// [position, velocity, time]
		double[][] path = new double[segments][3];
		path[0][0] = 0;
		path[0][1] = 0;
		path[0][2] = dt;

		for (int i = 1; i < segments; i++)
		{
			// v = V(t) + Accel;
			double accelCurrent = 0;
			// Triangle
			// Method needs to be tested on the robot to evaluate its precision
			if (accelDist * 2 > distance)
			{
				if (i >= segments / 2)
				{
					accelCurrent = -accel;
				} else
				{
					accelCurrent = accel;
				}
			}
			// Trapezoidal
			else
			{
				if (i <= rampTime / dt)
				{// upwards on vel Ramp
					accelCurrent = accel;
				} else if (i > rampTime / dt && i < segments - rampTime / dt)
				{// at maxVelocity, no accel
					accelCurrent = 0;
				} else if (i > segments - rampTime / dt)
				{// downwards on vel ramp, deaccel
					accelCurrent = -accel;
				}
			}

			path[i][0] = path[i - 1][0] + ((path[i - 1][1] + (accelCurrent * dt)) * dt)
					+ 0.5 * accelCurrent * Math.pow(dt, 2); // position
			path[i][1] = path[i - 1][1] + (accelCurrent * dt); // velocity
			path[i][2] = dt; // time
		}

		path[segments - 1][0] = distance;
		path[segments - 1][1] = 0;
		path[segments - 1][2] = dt;

		return path;

	}

	public static void TwoDimensionMotion()
	{

	}

}
