package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.Utilities.Utils;
import org.usfirst.frc.team801.robot.commands.lift.WinchStop;
import org.usfirst.frc.team801.robot.commands.lift.WinchUp;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import SwerveClass.Team801TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Winch extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Team801TalonSRX theWinchThatStoleChristmas= RobotMap.theWinchThatStoleChristmas;
	private double x;
	//Winch Motor
	public Winch() {
	theWinchThatStoleChristmas.configNominalOutputForward(0, Constants.kTimeoutMs);
	theWinchThatStoleChristmas.configNominalOutputReverse(0, Constants.kTimeoutMs);
	theWinchThatStoleChristmas.configPeakOutputForward(11, Constants.kTimeoutMs);
	theWinchThatStoleChristmas.configPeakOutputReverse(-11, Constants.kTimeoutMs);
	
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new WinchStop());
    	
    }
    
    public void winchUp() {
    	x = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.manip.getV(), 1.5), 0.2, 1.0);

    	theWinchThatStoleChristmas.set(ControlMode.PercentOutput, 1.0);
    }

	public void stopWinch() {
		// TODO Auto-generated method stub
    	theWinchThatStoleChristmas.set(ControlMode.PercentOutput, 0.0);
		
	}
}

