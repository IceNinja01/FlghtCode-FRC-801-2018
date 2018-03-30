package org.usfirst.frc.team801.robot.commands.auto;

import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.commands.arm.ArmDown;
import org.usfirst.frc.team801.robot.commands.arm.ArmUp;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;
import org.usfirst.frc.team801.robot.commands.chassis.StopDrive;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendLow;
import org.usfirst.frc.team801.robot.commands.pinchers.ClosePinchers;
import org.usfirst.frc.team801.robot.commands.pinchers.OpenPinchers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class MiddleGoRightSwitch extends CommandGroup {

    public MiddleGoRightSwitch() { 
 
    	requires(Robot.arm);
    	requires(Robot.pinchers);
    	requires(Robot.chassis);
    	requires(Robot.elevator);


    	addSequential(new ArmUp());
    	addSequential(new ClosePinchers());
    	addSequential(new Drive_And_ExtendLow(108.0 - 30, 64.0, 0.5, 0));
    	addSequential(new ArmDown());
    	addSequential(new OpenPinchers());

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
