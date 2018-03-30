package org.usfirst.frc.team801.robot.commands.auto;

import org.usfirst.frc.team801.robot.Robot;
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
public class LeftGoRightScale extends CommandGroup {

    private double wallDistance;

	public LeftGoRightScale() {
    	addSequential(new ArmUp());

       	addSequential(new CMD_Angle_Drive(209.0, 90, 0.7, 0));
    	addSequential(new TurnCMD(270));
//    	wallDistance = 71.5 - (40 + Robot.chassis.getReverseDist());
//    	wallDistance = 10.0;
    	addSequential(new CMD_Angle_Drive(210, 0, 0.4, 270));
//    	addSequential(new CMD_Angle_Drive_Ultra(48, 0, 0.4, 270);
//    	addSequential(new Drive_And_ExtendHigh(70, 90, 0.4, 270)); 

//    	addSequential(new ArmDown());
//    	addSequential(new OpenPinchers());
//    	addSequential(new CMD_Angle_Drive(20.0, 180, 0.4, 90));

       
    }
}
