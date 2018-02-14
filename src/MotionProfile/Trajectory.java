package MotionProfile;

public class Trajectory {
	
	private static double[][] seg;
	public static MotionProfile profile;
	
	public Trajectory(double[][] wayPoints) {
		//Input is an array of Way point {x,y,heading}
		//or a turnpoint
		profile = new MotionProfile();
		for(int i=0;i<wayPoints.length; i++) {
			if(wayPoints[i][6] == 0) {
				seg = profile.rampUp(wayPoints[i]);
			}
			else {
				seg = profile.addTurn(wayPoints[i]);
			}
		}
	}
	
}
