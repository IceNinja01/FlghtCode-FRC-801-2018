package org.usfirst.frc.team801.robot.commands.auto.nikhil_test;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartCenterGoPos extends CommandGroup {
	

	public StartCenterGoPos(int position) {
		int dir = 0;
		if (position == Constants.LEFT)
			dir = 180;

		//Insert drive code (DEFAULT is RIGHT)
		//Use 'dir' as all angles that represent either left turn or right turn
    	addSequential(new CMD_Angle_Drive(70.0, dir, 0.4));
    	Timer.delay(0.5);
	}

}
