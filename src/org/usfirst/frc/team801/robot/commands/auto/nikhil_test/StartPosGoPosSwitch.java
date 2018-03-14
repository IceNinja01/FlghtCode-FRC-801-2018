package org.usfirst.frc.team801.robot.commands.auto.nikhil_test;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.commands.arm.ArmDown;
import org.usfirst.frc.team801.robot.commands.arm.ArmUp;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendLow;
import org.usfirst.frc.team801.robot.commands.pinchers.OpenPinchers;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartPosGoPosSwitch extends CommandGroup {
	
	public StartPosGoPosSwitch(int direction) {
		if (direction != Constants.LEFT && direction != Constants.RIGHT)
			throw new IllegalArgumentException();
		addSequential(new ArmUp());
    	addSequential(new CMD_Angle_Drive(104, 90, 0.2));
    	addSequential(new ExtendLow());
    	addSequential(new ArmDown());
    	addSequential(new OpenPinchers());
    	addSequential(new ArmUp());
	}

}
