/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team801.robot;


import org.usfirst.frc.team801.robot.commands.elevator.ExtendHigh;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendMid;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendLow;
import org.usfirst.frc.team801.robot.commands.elevator.Shrink;
import org.usfirst.frc.team801.robot.commands.chassis.TurnBack;
import org.usfirst.frc.team801.robot.commands.chassis.TurnFront;
import org.usfirst.frc.team801.robot.commands.chassis.TurnLeft;
import org.usfirst.frc.team801.robot.commands.chassis.TurnRight;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
    //
    //	      5	    _                            _    6
    //	       _.-'` `-._                    _,-' `'-._
    //	    ,-'          `-.,____________,.-'    .-.   `-.
    //	   /   .---.             ___            ( 4 )     \
    //	  /  ,' ,-. `.     __   / X \   __   .-. `-` .-.   \
    //	 /   | | 9 | |    (_7) | / \ | (_8) ( 3 )   ( 2 )   \
    //	/    `. `-' ,'    __    \___/        `-` ,-. `-`     \
    //	|      `---`   ,-`  `-.       .---.     ( 1 )        |
    //	|             / -'  `- \    ,'  .  `.    `-`         |
    //	|            |          |   | -10 - |                |
    //	!             \ -.  ,- /    `.  '  ,'                |
    //	|              `-.__,-'       `---`                  |
    //	|                  ________________                  |
    //	|             _,-'`                ``-._             |
    //	|          ,-'                          `-.          |
    //	 \       ,'                                `.       /
    //	  `.__,-'                                    `-.__,'
    //

	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
	   public XBOXJoystick driver = new XBOXJoystick(0);
//	    public XboxController manip = new XboxController(1);
	    public XBOXJoystick manip = new XBOXJoystick(1);

	    public Button turnFront = new JoystickButton(driver,4);
	    public Button turnRight = new JoystickButton(driver,2);
	    public Button turnLeft = new JoystickButton(driver,3);
	    public Button turnBack = new JoystickButton(driver,1);
	    
	    public Button bottomElevator = new JoystickButoon(manip, 1);
	    public Button lowElevator = new JoystickButoon(manip, 2);
	    public Button midElevator = new JoystickButoon(manip, 3);
	    public Button highElevator = new JoystickButoon(manip, 4);
    
    public OI() {
    	turnFront.whileHeld(new TurnFront());
    	turnBack.whileHeld(new TurnBack());
    	turnRight.whileHeld(new TurnRight());
    	turnLeft.whileHeld(new TurnLeft());	
    	
    	bottomElevator.whenPressed(new Shrink());
    	highElevator.whenPressed(new ExtendHigh());
    	midElevator.whenPressed(new ExtendMid());	
    	lowElevator.whenPressed(new ExtendLow());

    }
}
