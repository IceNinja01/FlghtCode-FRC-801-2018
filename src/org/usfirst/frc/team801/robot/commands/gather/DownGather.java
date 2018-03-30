package org.usfirst.frc.team801.robot.commands.gather;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class DownGather extends InstantCommand{
		
		
		public DownGather() {
        // Use requires() here to declare subsystem dependencies
		requires(Robot.gatherPinchers);
			
    }

	// Called once when the command executes
    protected void initialize() {
    	Robot.gatherPinchers.setToggle();
    	
    }

}
