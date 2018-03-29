package org.usfirst.frc.team801.robot.commands.gather;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class UpGather extends InstantCommand{
		
		
		public UpGather() {
        // Use requires() here to declare subsystem dependencies
		requires(Robot.gatherPinchers);
    }

	// Called once when the command executes
    protected void initialize() {
    	
    	Robot.gatherPinchers.upGather();
    	
    }

}
