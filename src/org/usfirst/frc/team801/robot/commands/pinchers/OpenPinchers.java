package org.usfirst.frc.team801.robot.commands.pinchers;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class OpenPinchers extends InstantCommand {

    public OpenPinchers() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.pinchers);
    }

    // Called once when the command executes
    protected void initialize() {
    	
    	Robot.pinchers.openPinchers();
    	
    }

}
