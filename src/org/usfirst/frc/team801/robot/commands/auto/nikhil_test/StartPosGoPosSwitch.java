package org.usfirst.frc.team801.robot.commands.auto.nikhil_test;

import org.usfirst.frc.team801.robot.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartPosGoPosSwitch extends CommandGroup {
	
	private int position;

	public StartPosGoPosSwitch(int position) throws IllegalArgumentException {
		if (position != Constants.LEFT && position != Constants.RIGHT) {
			throw new IllegalArgumentException("'position' must either be 'Constants.LEFT' or 'Constants.RIGHT,' not " + position);
		}
		this.position = position;
	}

}
