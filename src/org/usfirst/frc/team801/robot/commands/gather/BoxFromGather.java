package org.usfirst.frc.team801.robot.commands.gather;

import java.sql.Time;

import org.usfirst.frc.team801.robot.commands.arm.ArmDown;
import org.usfirst.frc.team801.robot.commands.gather.OpenGatherPinchers;
import org.usfirst.frc.team801.robot.commands.pinchers.ClosePinchers;
import org.usfirst.frc.team801.robot.commands.pinchers.OpenPinchers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class BoxFromGather extends CommandGroup {

    public BoxFromGather() {
        // Add Commands here:
    	addSequential(new OpenPinchers());
    	addSequential(new UpGather());
    	addSequential(new WaitCommand(1.0));
//    	addParallel(new EjectBox(), 0.5);
    	addSequential(new ClosePinchers());
    	addSequential(new OpenGatherPinchers());
    	end();
        
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
