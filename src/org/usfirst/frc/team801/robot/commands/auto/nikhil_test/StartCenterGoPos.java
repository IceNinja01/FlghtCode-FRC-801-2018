package org.usfirst.frc.team801.robot.commands.auto.nikhil_test;

import org.usfirst.frc.team801.robot.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartCenterGoPos extends CommandGroup {
	
	private int position;

	public StartCenterGoPos(int position) throws IllegalArgumentException {
		if (position != Constants.LEFT && position != Constants.RIGHT) {
			throw new IllegalArgumentException("'position' must either be 'Constants.LEFT' or 'Constants.RIGHT,' not " + position);
		}
		this.position = position;
		int dir = 1;
		if (position == Constants.RIGHT)
			dir = -1;

		//Insert drive code (DEFAULT is LEFT)
		//Apply a multiplication by 'dir' to all angles that represent either left turn or right turn
	}

}
