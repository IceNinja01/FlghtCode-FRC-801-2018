package org.usfirst.frc.team801.robot.commands.pinchers;

import org.usfirst.frc.team801.robot.commands.arm.ArmDown;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickUpBox extends CommandGroup {

    public PickUpBox() {
        // Add Commands here:
    	addParallel(new OpenPinchers());
        addSequential(new ArmDown());
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
