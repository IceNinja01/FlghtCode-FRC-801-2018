package org.usfirst.frc.team801.robot.Utilities;



import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PathBuilder {

	private Command pathCommand;
	
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
			case Constants.LEFT:
				//Go forward
				break;
			case Constants.CENTER:
				//Go left then forward
				break;
			case Constants.RIGHT:
				//Go left x2 then forward
				break;
			}
			location = Constants.LEFT;
			break;
		case "R":
			switch(location) {
			case Constants.LEFT:
				//Go right x2 then forward
				break;
			case Constants.CENTER:
				//Go right then forward
				break;
			case Constants.RIGHT:
				//Go forward
				break;
			}
			location = Constants.RIGHT;
			break;
		}
		
		switch (sides[1]) {
		case "L":
			switch(location) {
			case Constants.LEFT:
				//Go forward
			case Constants.RIGHT:
				//Go left x2 then forward
			}
			break;
		case "R":
			switch(location) {
			case Constants.LEFT:
				//Go right x2 then forward
			case Constants.RIGHT:
				//Go forward
			}
			break;
		}
		
		switch (sides[2]) {
		case "L":
			break;
		case "R":
			break;
		}
	}
	
	
	public Command getPath() {
		return pathCommand;
	}
}

