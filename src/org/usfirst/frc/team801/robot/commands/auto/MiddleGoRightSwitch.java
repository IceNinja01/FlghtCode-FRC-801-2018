package org.usfirst.frc.team801.robot.commands.auto;

import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.commands.arm.ArmDown;
import org.usfirst.frc.team801.robot.commands.arm.ArmUp;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;
import org.usfirst.frc.team801.robot.commands.chassis.StopDrive;
import org.usfirst.frc.team801.robot.commands.chassis.TurnCMD;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendLow;
import org.usfirst.frc.team801.robot.commands.elevator.Shrink_ArmUp;
import org.usfirst.frc.team801.robot.commands.gather.BoxFromGather;
import org.usfirst.frc.team801.robot.commands.gather.CloseGatherPinchers;
import org.usfirst.frc.team801.robot.commands.gather.DownGather;
import org.usfirst.frc.team801.robot.commands.gather.EjectBox;
import org.usfirst.frc.team801.robot.commands.gather.GatherUp;
import org.usfirst.frc.team801.robot.commands.gather.OpenGatherPinchers;
import org.usfirst.frc.team801.robot.commands.gather.UpGather;
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

    	//Box 1
    	addSequential(new ArmUp());
    	addSequential(new ClosePinchers());
    	addSequential(new Drive_And_ExtendLow(108.0 - 30, 63.0, 0.5, 0));
    	addSequential(new ArmDown());
    	addSequential(new OpenPinchers());

    	//Box 2
       	addSequential(new CMD_Angle_Drive(75, 240, 0.5, 0));
       	addSequential(new ArmUp());
    	addSequential(new OpenPinchers());
       	addSequential(new DownGather(), 1);
       	addSequential(new OpenGatherPinchers());
       	addSequential(new CMD_Angle_Drive(24.0, 90, 0.4, 0));
       	addSequential(new OpenGatherPinchers());
       	addSequential(new GatherUp(),1);
       	addSequential(new CMD_Angle_Drive(40.0, 350, 0.6, 0));
       	addSequential(new UpGather());
       	addSequential(new CMD_Angle_Drive(36.0, 90, 0.6, 0));
       	addSequential(new EjectBox(),3);


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
