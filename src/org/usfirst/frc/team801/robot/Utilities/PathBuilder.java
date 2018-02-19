package org.usfirst.frc.team801.robot.Utilities;

import static org.usfirst.frc.team801.robot.Robot.LEFT;
import static org.usfirst.frc.team801.robot.Robot.CENTER;
import static org.usfirst.frc.team801.robot.Robot.RIGHT;

public class PathBuilder {

	public PathBuilder (int location, String fieldLayout) {
		
		String[] sides = new String[3];
		
		for (int i = 0; i < fieldLayout.length(); i++) {
			sides[i] = fieldLayout.substring(i, i+1);
		}
		
		for (int i = 0; i < sides.length; i ++)
			System.out.println(sides[i]);
		
		switch (sides[0]) {
		case "L":
			switch(location) {
			case: LEFT
			}
			break;
		case "R":
			break;
		}
		
		switch (sides[1]) {
		case "L":
			break;
		case "R":
			break;
		}
		
		switch (sides[2]) {
		case "L":
			break;
		case "R":
			break;
		}
	}
}
