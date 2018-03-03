package org.usfirst.frc.team801.robot.commands;

import org.usfirst.frc.team801.robot.commands.chassis.CMD_Drive;
import org.usfirst.frc.team801.robot.commands.chassis.MotionMagicDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Square extends CommandGroup {

    public Square() {
    	addSequential(new CMD_Drive(0.0, 0.6, 0.0, 48.0));
    	addSequential(new CMD_Drive(0.6, 0.0, 90.0, 36.0));
    	addSequential(new CMD_Drive(0.0, -0.6, 180.0, 48.0));
    	addSequential(new CMD_Drive(-0.6, 0.0, 270.0, 36.0));

    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
