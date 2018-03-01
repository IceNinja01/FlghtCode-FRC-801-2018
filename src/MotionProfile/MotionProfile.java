package MotionProfile;

import org.usfirst.frc.team801.robot.Constants;

public class MotionProfile {
	
	private Object heading;

	/*
	This method outputs a 3 column array, [position, velocity, ms], is used for a 1D motion control
	Uses a trapezoidal method for ramp on and ramp on.
	 1. v = V(t) + Accel;
	 2. p = P(t) + v + 1 / 2*Accel;
	 Where V(t) is equal to the motors current velocity
	 and where P(t) is equal to the motors current position
	 */
	public double[][] OneDimensionMotion(double distance, double maxVel, double accel)
	{
		/*
		 * distance is units ft MaxVelocity is ft/sec accel is ft/sec^2
		 */
		distance *= Constants.wheelRotPerInch*4096;  //number of shaft rotations to travel in native units
		maxVel *= Constants.wheelRotPerInch*4096/10;
		accel *= Constants.wheelRotPerInch*4096/10;
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
			System.out.print("position: " + path[i][0]);
			System.out.print("\tvelocity: " + path[i][1]);
			System.out.println("\tdt: " + dt);
		}

		path[segments - 1][0] = distance;
		path[segments - 1][1] = 0;
		path[segments - 1][2] = dt;
		
		return path;
	}
	
	public double[][] XYCoord(double distance, double maxVel, double accel, double heading){
		/*
		 * Converts the 1-d motion into X and Y values for input as if Joystick
		 */
		double[][] path= OneDimensionMotion(distance, maxVel, accel);
		double[][] xy = new double[path.length][2];
		double cosAngle = Math.cos(Math.toRadians(90));
		double sinAngle = Math.sin(Math.toRadians(90));
		double maxV = 4800*4096/600;
		for(int i=0; i<path.length; i++) {
			xy[i][0] = (path[i][1]*cosAngle)/maxV;
			xy[i][1] = (path[i][1]*sinAngle)/maxV;;
		}
		System.out.println("MaxVelocity" + maxV);
		System.out.println("Cos Angle" + cosAngle);
		System.out.println("Sin Angle" + sinAngle);

		return xy;
	}
	
	
//	private double findMax(double[][] array) {
//		double max= array[0][0];
//		
//		for(int i=0; i<array.length; i++) {
//				if(array[i][1]> max) {
//				max = array[i][1];
//			}
//		}
//		
//		
//	// TODO Auto-generated method stub
//	return max;
//}


	public double[][] rampUp(double[] wayPoint){
		/* distance is units ft
		 * MaxVelocity is ft/sec
		 * accel is ft/sec^2
		 * array = [distance, maxVel, accel, Heading, endingVel, turnOrstaright]
		 */
		double distance = wayPoint[0];
		double maxVel = wayPoint[1];
		double accel = wayPoint[2];
		double startingheading = wayPoint[3];
		double endingHeading = wayPoint[4];
		double endingVel = wayPoint[5];
		//convert inputs from inches or inches per sec to native units

		distance *= Constants.wheelRotPerInch*4096;  //number of shaft rotations to travel in native units
		maxVel *= Constants.wheelRotPerInch*4096/10;
		accel *= Constants.wheelRotPerInch*4096/10;
		
		double pathTime = 0;
		double ramp1Time = maxVel/accel;
		double ramp2Time = (maxVel - endingVel)/accel;
	    double accel1Dist = 0.5 * accel*Math.pow(ramp1Time, 2);
	    double accel2Dist = 0.5 * accel*Math.pow(ramp2Time, 2);

	    //Trapezoidal
	        pathTime = ramp1Time + ramp2Time + (distance - accel1Dist - accel2Dist)  / maxVel;
	    
	    double dt = Constants.kTimeoutMs/1000; // steps are equal to 10ms
	    int segments = (int) (pathTime*(1/0.01)) + 1;
	    // [position, velocity, time]
		double[][] path = new double[segments][4];
		path[0][0] = 0;
		path[0][1] = 0;
		path[0][2] = dt;
		path[0][3] = endingHeading;
		
		for(int i = 1; i < segments; i++)
		{
			//v = V(t) + Accel;
			double accelCurrent = 0;
			//Trapezoidal		
			if(i <= ramp1Time/dt)
			{// upwards on vel Ramp
				accelCurrent = accel;
			}
			else if(i > ramp1Time/dt && i < segments-ramp2Time/dt)
			{// at maxVelocity, no accel
				accelCurrent = 0;
			}
			else if(i > segments-ramp2Time/dt)
			{// downwards on vel ramp, deaccel
				accelCurrent = -accel;
			}
			path[i][0] = path[i-1][0] + ((path[i-1][1] + (accelCurrent*dt))*dt) + 0.5*accelCurrent*Math.pow(dt,2); 	//position
			path[i][1] = path[i-1][1] + (accelCurrent*dt); 								 							//velocity
			path[segments-1][2] = endingHeading;
			path[segments-1][3] = dt*1000;	
		}
		
		path[segments-1][0] = distance;
		path[segments-1][1] = 0;
		path[segments-1][2] = endingHeading;
		path[segments-1][3] = dt*1000;
		
		return path;	
	}

	public void addWayPoints(double[][] wayPoints) {
		// TODO Auto-generated method stub
		
	}

	public double[][] addTurn(double[] wayPoint) {
		/* distance is units ft
		 * MaxVelocity is ft/sec
		 * accel is ft/sec^2
		 * array = [distance, maxVel, accel, Heading, endingVel, turnOrstaright]
		 */
	     ////needs to be simplified for turing

		double distance = wayPoint[0];
		double maxVel = wayPoint[1];
		double accel = wayPoint[2];
		double startingheading = wayPoint[3];
		double endingHeading = wayPoint[4];
		double endingVel = wayPoint[5];
		//convert inputs from inches or inches per sec to native units

		distance *= Constants.wheelRotPerInch*4096;  //number of shaft rotations to travel in native units
		maxVel *= Constants.wheelRotPerInch*4096/10;
		accel *= Constants.wheelRotPerInch*4096/10;
		
		double pathTime = 0;
		double ramp1Time = maxVel/accel;
		double ramp2Time = (maxVel - endingVel)/accel;
	    double accel1Dist = 0.5 * accel*Math.pow(ramp1Time, 2);
	    double accel2Dist = 0.5 * accel*Math.pow(ramp2Time, 2);

	    //Trapezoidal
	        pathTime = ramp1Time + ramp2Time + (distance - accel1Dist - accel2Dist)  / maxVel;
	    
	    double dt = Constants.kTimeoutMs/1000; // steps are equal to 10ms
	    int segments = (int) (pathTime*(1/0.01)) + 1;
	    // [position, velocity, time]
		double[][] path = new double[segments][4];
		path[0][0] = 0;
		path[0][1] = 0;
		path[0][2] = dt;
		
		for(int i = 1; i < segments; i++)
		{
			//v = V(t) + Accel;
			double accelCurrent = 0;
			//Trapezoidal		
			if(i <= ramp1Time/dt)
			{// upwards on vel Ramp
				accelCurrent = accel;
			}
			else if(i > ramp1Time/dt && i < segments-ramp2Time/dt)
			{// at maxVelocity, no accel
				accelCurrent = 0;
			}
			else if(i > segments-ramp2Time/dt)
			{// downwards on vel ramp, deaccel
				accelCurrent = -accel;
			}
			path[i][0] = path[i-1][0] + ((path[i-1][1] + (accelCurrent*dt))*dt) + 0.5*accelCurrent*Math.pow(dt,2); 	//position
			path[i][1] = path[i-1][1] + (accelCurrent*dt); 								 							//velocity
			path[segments-1][2] = endingHeading;
			path[segments-1][3] = dt*1000;		
		}
		
		path[segments-1][0] = distance;
		path[segments-1][1] = 0;
		path[segments-1][2] = endingHeading;
		path[segments-1][3] = dt*1000;
		
		return path;	
	}


}
