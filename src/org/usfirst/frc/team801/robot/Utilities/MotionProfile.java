package org.usfirst.frc.team801.robot.Utilities;

public class MotionProfile {
	
	/*
	This method outputs a 3 column array, [position, velocity, ms], is used for a 1D motion control
	Uses a trapezoidal method for ramp on and ramp on.
	 1. v = V(t) + Accel;
	 2. p = P(t) + v + 1 / 2*Accel;
	 Where V(t) is equal to the motors current velocity
	 and where P(t) is equal to the motors current position
	 */
	public static double[][] OneDimenisonMotion(double distance, double maxVel, double accel) {
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
				{
					accelCurrent = accel;
				}
				else if(i > rampTime/dt && i < segments-rampTime/dt)
				{
					accelCurrent = 0;
				}
				else if(i > segments-rampTime/dt)
				{
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
