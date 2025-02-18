/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team801.robot;

import org.usfirst.frc.team801.robot.Utilities.XBOXJoystick;
import org.usfirst.frc.team801.robot.commands.Square;
import org.usfirst.frc.team801.robot.commands.arm.ArmDown;
import org.usfirst.frc.team801.robot.commands.arm.ArmDrive;
import org.usfirst.frc.team801.robot.commands.arm.ArmUp;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Drive;
import org.usfirst.frc.team801.robot.commands.chassis.MotionMagicDrive;
import org.usfirst.frc.team801.robot.commands.chassis.ToggleDriveOrientation;
import org.usfirst.frc.team801.robot.commands.chassis.TurnBack;
import org.usfirst.frc.team801.robot.commands.chassis.TurnFront;
import org.usfirst.frc.team801.robot.commands.chassis.TurnLeft;
import org.usfirst.frc.team801.robot.commands.chassis.TurnRight;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendHigh;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendHook;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendLow;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendMid;
import org.usfirst.frc.team801.robot.commands.elevator.GrabHook;
import org.usfirst.frc.team801.robot.commands.elevator.Shrink;
import org.usfirst.frc.team801.robot.commands.elevator.Shrink_ArmUp;
import org.usfirst.frc.team801.robot.commands.gather.BoxFromGather;
import org.usfirst.frc.team801.robot.commands.gather.CloseGatherPinchers;
import org.usfirst.frc.team801.robot.commands.gather.DownGather;
import org.usfirst.frc.team801.robot.commands.gather.EjectBox;
import org.usfirst.frc.team801.robot.commands.gather.GatherUp;
import org.usfirst.frc.team801.robot.commands.gather.OpenGatherPinchers;
import org.usfirst.frc.team801.robot.commands.gather.UpGather;
import org.usfirst.frc.team801.robot.commands.lift.LiftMotorDown;
import org.usfirst.frc.team801.robot.commands.lift.LiftMotorExtend;
import org.usfirst.frc.team801.robot.commands.lift.LiftMotorShrink;
import org.usfirst.frc.team801.robot.commands.lift.LiftMotorUp;
import org.usfirst.frc.team801.robot.commands.lift.WinchUp;
import org.usfirst.frc.team801.robot.commands.lift.WinchUp_ArmDown;
import org.usfirst.frc.team801.robot.commands.pinchers.ClosePinchers;
import org.usfirst.frc.team801.robot.commands.pinchers.OpenPinchers;
import org.usfirst.frc.team801.robot.commands.pinchers.PickUpBox;

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

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
    
   
        	
	    public XBOXJoystick driver = new XBOXJoystick(0);
//	    public XboxController manip = new XboxController(1);
	    public XBOXJoystick manip = new XBOXJoystick(1);

//	    public Button openGatherPinchers = new JoystickButton(driver,2);
	    public Button driverArmUp = new JoystickButton(driver,1);
	    public Button gatherDown = new JoystickButton(driver,6);
	    public Button eject = new JoystickButton(driver,8);
	    public Button gather = new JoystickButton(driver, 7);
//	    public Button turnBack = new JoystickButton(driver,3);
	    public Button toggleDriveOrientation = new JoystickButton(driver, 3);
	    public Button openGatherPinchers = new JoystickButton(driver,5);
	    public Button pickUpBox = new JoystickButton(driver,4);
	    

	    
	    public Button bottomElevator = new JoystickButton(manip, 3);
	    public Button lowElevator = new JoystickButton(manip, 2);
	    public Button midElevator = new JoystickButton(manip, 4);
	    public Button highElevator = new JoystickButton(manip, 1);
	    public Button grabHook = new JoystickButton(manip, 5);
	    public Button winch = new JoystickButton(driver, 10);
	    public Button armUp = new JoystickButton(manip, 6);
	    public Button boxFromGather = new JoystickButton(manip,8);
	    
	    //	    public Button square = new JoystickButton(manip, 5);
    
    public OI() {
    //	turnFront.whileHeld(new TurnFront());
//    	turnBack.whileHeld(new TurnBack());
  //  	turnRight.whileHeld(new TurnRight());
//    	turnLeft.whileHeld(new TurnLeft());	
    	driverArmUp.whenPressed(new ArmUp());
    	toggleDriveOrientation.whileHeld(new ToggleDriveOrientation());
    	openGatherPinchers.whenPressed(new OpenGatherPinchers());
    	//    	liftUp.whenPressed(new LiftMotorExtend());
//    	liftDown.whenPressed(new LiftMotorShrink());
    	boxFromGather.whenPressed(new BoxFromGather());
    	grabHook.whenPressed(new GrabHook());
    	bottomElevator.whenPressed(new Shrink_ArmUp());
    	highElevator.whenPressed(new ExtendHigh());
    	midElevator.whenPressed(new ExtendMid());	
    	lowElevator.whenPressed(new ExtendLow());
    	pickUpBox.whenPressed(new PickUpBox());
//    	closePinch.whenPressed(new ClosePinchers());
    	winch.whileHeld(new WinchUp());
    	armUp.whenPressed(new ArmUp());
    	gatherDown.whenPressed(new DownGather());
//    	gatherDown.whenPressed(new DownGather());
    	eject.whileHeld(new EjectBox());
    	gather.whileHeld(new GatherUp());
//    	square.whenPressed(new Square());


    }
}
