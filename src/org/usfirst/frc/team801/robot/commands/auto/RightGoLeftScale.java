package org.usfirst.frc.team801.robot.commands.auto;

import org.usfirst.frc.team801.robot.commands.arm.ArmDown;
import org.usfirst.frc.team801.robot.commands.arm.ArmUp;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;
import org.usfirst.frc.team801.robot.commands.chassis.TurnCMD;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendHigh;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendLow;
import org.usfirst.frc.team801.robot.commands.pinchers.OpenPinchers;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightGoLeftScale extends CommandGroup {

    public RightGoLeftScale() {
    	addSequential(new ArmUp());

       	addSequential(new CMD_Angle_Drive(209.0, 90, 0.7, 0));
    	addSequential(new TurnCMD(90));
//    	wallDistance =71.5 - (40 + Robot.chassis.getReverseDist());
//    	wallDistance =10.0;
    	addSequential(new CMD_Angle_Drive(210, 180, 0.7, 90));
//    	addSequential(new Drive_And_ExtendHigh(70, 90, 0.4, 90)); 

//    	addSequential(new ArmDown());
//    	addSequential(new OpenPinchers());
//    	addSequential(new CMD_Angle_Drive(20.0, 180, 0.4, 90));

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
