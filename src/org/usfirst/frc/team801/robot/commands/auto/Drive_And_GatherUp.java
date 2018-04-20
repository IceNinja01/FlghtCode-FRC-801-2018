package org.usfirst.frc.team801.robot.commands.auto;

import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendHigh;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendLow;
import org.usfirst.frc.team801.robot.commands.gather.GatherStop;
import org.usfirst.frc.team801.robot.commands.gather.GatherUp;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class Drive_And_GatherUp extends CommandGroup {

	public Drive_And_GatherUp(double distance, double angle, double speed, double gyro) {
    		addParallel(new CMD_Angle_Drive(distance, angle, speed, gyro),4);
    		addSequential(new GatherUp());

	}
	

}
