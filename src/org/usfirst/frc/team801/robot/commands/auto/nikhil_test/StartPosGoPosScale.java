package org.usfirst.frc.team801.robot.commands.auto.nikhil_test;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartPosGoPosScale extends CommandGroup {
	
	private int position;

	public StartPosGoPosScale(int position) throws IllegalArgumentException {
		if (position != Constants.LEFT && position != Constants.RIGHT) {
			throw new IllegalArgumentException("'position' must either be 'Constants.LEFT' or 'Constants.RIGHT,' not " + position);
		}
		this.position = position;
		int dir = 0;
		if (position == Constants.LEFT)
			dir = 180;

		addSequential(new CMD_Angle_Drive(104, dir, 0.2));
	}

}