package org.usfirst.frc.team801.robot.Utilities;



import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.commands.auto.GoFwd;
import org.usfirst.frc.team801.robot.commands.auto.LeftGoLeftScale;
import org.usfirst.frc.team801.robot.commands.auto.LeftGoLeftSwitch;
import org.usfirst.frc.team801.robot.commands.auto.LeftGoRight;
import org.usfirst.frc.team801.robot.commands.auto.LeftGoRightScale;
import org.usfirst.frc.team801.robot.commands.auto.MiddleGoLeftSwitch;
import org.usfirst.frc.team801.robot.commands.auto.MiddleGoRightSwitch;
import org.usfirst.frc.team801.robot.commands.auto.RightGoLeft;
import org.usfirst.frc.team801.robot.commands.auto.RightGoLeftScale;
import org.usfirst.frc.team801.robot.commands.auto.RightGoRightScale;
import org.usfirst.frc.team801.robot.commands.auto.RightGoRightSwitch;

import edu.wpi.first.wpilibj.command.Command;

public class PathBuilder {

	private Command pathCommand;
	
	public PathBuilder (int location, String fieldLayout, int priority) {
		
		String[] sides = new String[3];
		
		for (int i = 0; i < fieldLayout.length(); i++) {
			sides[i] = fieldLayout.substring(i, i+1);
		}
		
		for (int i = 0; i < sides.length; i ++)
			System.out.println(sides[i]);
		switch (priority) {
		case 0:
			//For the Switch
			switch (sides[0]) {
			case "L":
				switch(location) {
					case Constants.LEFT:
						//Go forward
						pathCommand = new LeftGoLeftSwitch();
						break;
					case Constants.CENTER:
						//Go left then forward
						pathCommand = new MiddleGoLeftSwitch();
						break;
					case Constants.RIGHT:
						//Go forward
						pathCommand = new GoFwd();
						break;
					}
					location = Constants.LEFT;
					break;
				case "R":
				switch(location) {
					case Constants.LEFT:
						//Go forward
						pathCommand = new GoFwd();
						break;
					case Constants.CENTER:
						//Go right then forward
						pathCommand = new MiddleGoRightSwitch();
						break;
					case Constants.RIGHT:
						//Go forward
					pathCommand = new RightGoRightSwitch();
					break;
				}
			location = Constants.RIGHT;
			break;
			}//End Switch
		break;
		case 1:  //Go For Scale
			switch (sides[1]) {
			case "L":
				switch(location) {
					case Constants.LEFT:
						//Go forward
						pathCommand = new LeftGoLeftScale();
						break;
					case Constants.CENTER:
						//Go left then forward
						pathCommand = new GoFwd();
						break;
					case Constants.RIGHT:
						//Go forward
						pathCommand = new RightGoLeftScale();
						break;
					}
					location = Constants.LEFT;
					break;
				case "R":
				switch(location) {
					case Constants.LEFT:
						//Go forward
						pathCommand = new LeftGoRightScale();
						break;
					case Constants.CENTER:
						//Go right then forward
						pathCommand = new GoFwd();
						break;
					case Constants.RIGHT:
						//Go forward
						pathCommand = new RightGoRightScale();
						break;
				}
					location = Constants.RIGHT;
					break;
			
			}//End Scale
			break;
		case 2:  //Priority Left/Right
			switch (sides[1]) {  //scale
			case "L":
				switch(location) {
					case Constants.LEFT:
						//Go forward
						pathCommand = new LeftGoLeftScale();
						break;
					case Constants.CENTER:
						//Go forward
						pathCommand = new GoFwd();
						break;
					case Constants.RIGHT:
//						switch(sides[0]) {
//							case "R":
//								pathCommand = new RightGoRightSwitch();
//								break;
//							case "L":
								pathCommand = new RightGoLeft();
//								break;
//						};
					break;
					}
					location = Constants.LEFT;
					break;
				case "R":
				switch(location) {
					case Constants.LEFT:
//						switch(sides[0]) {
//						case "L": //switch is on the left
//							pathCommand = new LeftGoLeftSwitch();
//						break;
//						case "R":
							pathCommand = new LeftGoRight();	
//						break;
//						}
					break;
					case Constants.CENTER:
						//Go right then forward
						pathCommand = new GoFwd();
						break;
					case Constants.RIGHT:
						//Go forward
					pathCommand = new RightGoRightScale();
					break;
				}
			location = Constants.RIGHT;
			break;
			
			}//End Scale
			break;
		}
	}
		//End Switch
		
//		//For the Scale
//		switch (sides[1]) {
//		case "L":
//			switch(location) {
//			case Constants.LEFT:
//				//Go forward
//			case Constants.RIGHT:
//				//Go left x2 then forward
//			}
//			break;
//		case "R":
//			switch(location) {
//			case Constants.LEFT:
//				//Go right x2 then forward
//			case Constants.RIGHT:
//				//Go forward
//			}
//			break;
//		}
//		
//		switch (sides[2]) {
//		case "L":
//			break;
//		case "R":
//			break;
//		}
//	}
//	//end Scale
	
	public Command getPath() {
		return pathCommand;
	}
}

