package org.usfirst.frc.team801.robot.Utilities;

import org.usfirst.frc.team801.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;

public class MotionProfile
{
	private TalonSRX[] motors;
	
	class PeriodicRunnable implements Runnable
	{
		public void run()
		{
			control();
		}
	}
	
	private boolean started = false;
	private Notifier notifer = new Notifier(new PeriodicRunnable());
	private double[][] profile;
	private int position = 0;

	public MotionProfile(TalonSRX[] motors, double distance, double maxVel, double accel)
	{
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
			/*
			motors[i].config_kF(0, 0.2, Constants.kTimeoutMs);
			motors[i].config_kP(0, 0.02, Constants.kTimeoutMs);
			motors[i].config_kI(0, 0.0, Constants.kTimeoutMs);
			motors[i].config_kD(0, 0.002, Constants.kTimeoutMs);
			*/

			motors[i].setSelectedSensorPosition(0, 0, 10);
		}
		notifer.startPeriodic(0.01);
		this.motors = motors;
		profile = OneDimensionMotion(distance, maxVel, accel);
	}
	
	public void start()
	{
		started = true;
	}

	public void stop()
	{
		started = false;
		position = 0;
	}

	/**
	 * Called to clear Motion profile buffer and reset state info during disabled
	 * and when Talon is not in MP control mode.
	 */
	public void reset()
	{
		started = false;
		start();
	}
	
	public void control()
	{
		if(started)
		{	
			if(position < profile.length-1)
			{
				for (int i = 0; i < motors.length; i++)
				{
					motors[i].set(ControlMode.PercentOutput, profile[position][1]/4800);
				}
				position++;
			}
			else
			{
				started = false;
			}
		}
	}
	
	/*
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
				
			}
		}
	}
	*/
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
