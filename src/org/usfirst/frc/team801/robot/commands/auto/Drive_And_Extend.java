package org.usfirst.frc.team801.robot.commands.auto;

import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendHigh;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendLow;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Drive_And_Extend extends CommandGroup {

	public Drive_And_Extend(double distance, double angle, double speed, double gyro, boolean up) {
    	addParallel(new CMD_Angle_Drive(distance, angle, speed, gyro));
    	if(up=false) {
    		addSequential(new ExtendLow());
    	}
    	else {
        	addSequential(new ExtendHigh());

    	}
	}
	

}
