package org.usfirst.frc.team801.robot.Utilities;

public class PathBuilder {

	public PathBuilder (String loc, String fieldLayout) {
		
		String[] sides = new String[3];
		
		for (int i = 0; i < fieldLayout.length(); i++) {
			sides[i] = fieldLayout.substring(i, i+1);
		}
	}
}
