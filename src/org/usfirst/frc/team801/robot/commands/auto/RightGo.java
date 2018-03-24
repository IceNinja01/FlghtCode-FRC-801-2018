package org.usfirst.frc.team801.robot.commands.auto;

import org.usfirst.frc.team801.robot.commands.arm.ArmUp;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightGo extends CommandGroup {

    public RightGo() {
    	
    	addSequential(new ArmUp());
    	addSequential(new CMD_Angle_Drive(144.0, 90, 0.4, 0));
    	Timer.delay(0.5);
    	addSequential(new CMD_Angle_Drive(10, 90, 0.01, 0), 0.5);

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
