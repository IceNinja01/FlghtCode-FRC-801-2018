package org.usfirst.frc.team801.robot.commands.auto;

import org.usfirst.frc.team801.robot.commands.arm.ArmDown;
import org.usfirst.frc.team801.robot.commands.arm.ArmUp;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;
import org.usfirst.frc.team801.robot.commands.chassis.StopDrive;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendLow;
import org.usfirst.frc.team801.robot.commands.pinchers.ClosePinchers;
import org.usfirst.frc.team801.robot.commands.pinchers.OpenPinchers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleGoLeftSwitch extends CommandGroup {
	
    public MiddleGoLeftSwitch() {
    	addSequential(new ArmUp());

    	addSequential(new CMD_Angle_Drive(18.0, 90, 0.4));
    	Timer.delay(0.5);
    	addSequential(new CMD_Angle_Drive(70.0, 180, 0.4));
    	Timer.delay(0.5);
    	addSequential(new CMD_Angle_Drive(82, 90, 0.4));
    	Timer.delay(0.5);
    	addSequential(new CMD_Angle_Drive(6, 90, 0.1),0.5);
    	
    	addSequential(new ExtendLow());
    	
    	addSequential(new ArmDown());
    	Timer.delay(1.0);
    	addSequential(new OpenPinchers());
//    	addSequential(new ArmUp());

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
