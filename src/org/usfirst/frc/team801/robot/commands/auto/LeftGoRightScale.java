package org.usfirst.frc.team801.robot.commands.auto;

import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.commands.arm.ArmDown;
import org.usfirst.frc.team801.robot.commands.arm.ArmUp;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive_Ultra;
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

       	addSequential(new CMD_Angle_Drive(203.0, 90, 0.9, 0));
    	addSequential(new TurnCMD(270));
    	addSequential(new CMD_Angle_Drive(12, 0, 0.3, 270));
    	addSequential(new CMD_Angle_Drive(170, 0, 0.8, 270));
    	addSequential(new CMD_Angle_Drive_Ultra(20.0, 0, 0.5, 270));
    	addSequential(new Drive_And_ExtendHigh(49, 90, 0.4, 270)); 
    	wallDistance = 71.5 - (40 + Robot.chassis.getReverseDist());
    	addSequential(new CMD_Angle_Drive(wallDistance, 180, 0.4, 270));
    	
    	addSequential(new ArmDown());
    	addSequential(new OpenPinchers()); 	
    	addSequential(new CMD_Angle_Drive(20.0, 0, 0.4, 270));

       
    }
}
