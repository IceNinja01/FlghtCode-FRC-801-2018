package org.usfirst.frc.team801.robot.Utilities;

public class MotionProfile {
	
	/*
	This method outputs a 3 column array, [position, velocity, ms], is used for a 1D motion control
	Uses a trapezoidal method for ramp on and ramp on.
	 1. v = V(t) + Accel;
	 2. p = P(t) + v + I / 2*Accel;
	 Where V(t) is equal to the motors current velocity
	 and where P(t) is equal to the motors current position
	 */
	public void OneDimenisonMotion(double distance, double maxVel, double accel) {
		/* 
		 */
		
		//double rampTime = maxVel/accel;
		
		double pathTime = 0;
		double rampTime = maxVel/accel;
	    double accelDist = 0.5 * maxVel * maxVel / accel;
	    // Tests if we can even reach our maxVelocity, if we can't, then the boolean is true
	    if (accelDist * 2 > distance)
	    {//Triangle
	        pathTime = rampTime * 2;
	    } else
	    {//Trapezoidal
	        pathTime = rampTime * 2 + (distance - accelDist * 2) / maxVel;
	    }
	    
	}
	
	public void TwoDimensionMotion() {
		
		
	}
	
	
}
