package org.usfirst.frc.team801.robot.commands;

import org.usfirst.frc.team801.robot.commands.chassis.MotionMagicDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Square extends CommandGroup {

    public Square() {
    	addSequential(new MotionMagicDrive(24,0), 2);
    	addSequential(new MotionMagicDrive(24,90), 2);
    	addSequential(new MotionMagicDrive(24,180), 2);
    	addSequential(new MotionMagicDrive(24,270), 2);

    	
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
