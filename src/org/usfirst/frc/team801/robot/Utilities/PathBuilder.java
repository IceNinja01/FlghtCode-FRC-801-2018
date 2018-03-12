package org.usfirst.frc.team801.robot.Utilities;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.commands.auto.MiddleGoLeftSwitch;
import org.usfirst.frc.team801.robot.commands.auto.MiddleGoRightSwitch;
import org.usfirst.frc.team801.robot.commands.auto.nikhil_test.StartPosGoPosSwitch;
import org.usfirst.frc.team801.robot.commands.auto.nikhil_test.StartCenterGoPos;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class PathBuilder {

	private CommandGroup pathCommand;

	public PathBuilder(int location, String fieldLayout) {

		String[] cutFieldLayout = new String[3];
		int[] sides = new int[3];
		pathCommand = new CommandGroup("PathCommand");

		for (int i = 0; i < fieldLayout.length(); i++) {
			cutFieldLayout[i] = fieldLayout.substring(i, i + 1);
		}

		for (int i = 0; i < cutFieldLayout.length; i++)
			if (cutFieldLayout[i].equals("L"))
				sides[i] = Constants.LEFT;
			else
				sides[i] = Constants.RIGHT;
				
		// For the Switch
		if (location == sides[0]) {
			//If the Switch is on the same side as the robot's starting position
			//Go to Switch
			pathCommand.addSequential(new StartPosGoPosSwitch());
		} else if (location == Constants.CENTER) {
			//If the robot starts in the center
			//Move to the side with the Switch
			pathCommand.addSequential(new StartCenterGoPos(sides[0]));
			//Go to Switch
			pathCommand.addSequential(new StartPosGoPosSwitch());
		} else {
			
		}
		//The robot is now on the side with the Switch
		location = sides[0];
		
		if (location == sides[1]) {
			//If the Scale is on the same side as the Switch
		} else {
			//If the Scale is not on the same side as the Switch
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		if (cutFieldLayout[0].equals("L")) {
			// This will run if the our switch is on the LEFT side of of the
			// field.
			switch (location) {
			case Constants.LEFT:
				try {
					pathCommand.addSequential(new StartPosGoPosSwitch(location));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					pathCommand = new CommandGroup("null");
					break;
				}
				break;
			case Constants.CENTER:
				// Go left then forward
				pathCommand = new MiddleGoLeftSwitch();
				break;
			case Constants.RIGHT:
				// Go left x2 then forward
				break;
			}
			location = Constants.LEFT;
		} else {
			switch (location) {
			case Constants.LEFT:
				// Go right x2 then forward
				break;
			case Constants.CENTER:
				// Go right then forward
				pathCommand = new MiddleGoRightSwitch();
				break;
			case Constants.RIGHT:
				try {
					pathCommand.addSequential(new StartPosGoPosSwitch(location));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					pathCommand = new CommandGroup("null");
					break;
				}
				break;
			}
			location = Constants.RIGHT;
		}
	}
	// End Switch

	// //For the Scale
	// switch (cutFieldLayout[1]) {
	// case "L":
	// switch(location) {
	// case Constants.LEFT:
	// //Go forward
	// case Constants.RIGHT:
	// //Go left x2 then forward
	// }
	// break;
	// case "R":
	// switch(location) {
	// case Constants.LEFT:
	// //Go right x2 then forward
	// case Constants.RIGHT:
	// //Go forward
	// }
	// break;
	// }
	//
	// switch (cutFieldLayout[2]) {
	// case "L":
	// break;
	// case "R":
	// break;
	// }
	// }
	// //end Scale
	 */
	}

	public Command getPath() {
		return pathCommand;
	}
}
