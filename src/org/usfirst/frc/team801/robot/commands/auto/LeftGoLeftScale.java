package org.usfirst.frc.team801.robot.commands.auto;

import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.commands.arm.ArmDown;
import org.usfirst.frc.team801.robot.commands.arm.ArmUp;
import org.usfirst.frc.team801.robot.commands.chassis.CMD_Angle_Drive;
import org.usfirst.frc.team801.robot.commands.chassis.TurnCMD;
import org.usfirst.frc.team801.robot.commands.chassis.TurnRight;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendHigh;
import org.usfirst.frc.team801.robot.commands.elevator.ExtendLow;
import org.usfirst.frc.team801.robot.commands.elevator.Shrink;
import org.usfirst.frc.team801.robot.commands.elevator.Shrink_ArmUp;
import org.usfirst.frc.team801.robot.commands.pinchers.OpenPinchers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftGoLeftScale extends CommandGroup {

    private double wallDistance;

	public LeftGoLeftScale() {
    	addSequential(new ArmUp());

       	addSequential(new Drive_And_ExtendHigh(288.0, 92, 0.7, 0));
    	addSequential(new TurnCMD(90));
    	wallDistance =71.5 - (40 + Robot.chassis.getReverseDist());
    	addSequential(new CMD_Angle_Drive(wallDistance, 0, 0.4, 90));
    	addSequential(new ArmDown());
    	addSequential(new OpenPinchers());
    	addSequential(new CMD_Angle_Drive(20.0, 180, 0.4, 90));
    	addSequential(new ArmUp());
    	addSequential(new Shrink());

//    	addSequential(new Drive_And_ExtendLow(36.0, 270, 0.4, 90));
    	
    }
}
