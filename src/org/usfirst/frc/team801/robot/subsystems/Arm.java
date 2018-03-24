package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.Utilities.Utils;
import org.usfirst.frc.team801.robot.commands.arm.ArmDrive;
import org.usfirst.frc.team801.robot.commands.arm.ArmStop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import SwerveClass.Team801TalonSRX;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arm extends Subsystem {

	Team801TalonSRX arm = RobotMap.arm;
	
	public Arm() {
		arm.configNominalOutputForward(0, Constants.kTimeoutMs);
		arm.configNominalOutputReverse(0, Constants.kTimeoutMs);
		arm.configPeakOutputForward(11, Constants.kTimeoutMs);
		arm.configPeakOutputReverse(-11, Constants.kTimeoutMs);
		arm.setNeutralMode(NeutralMode.Brake);
//		arm.enableVoltageCompensation(true); 
		arm.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		arm.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		arm.setInverted(true);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new ArmDrive());
    }
    
    public void armDrive() {
//    	double y = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.manip.getY(), 1.5), 0.01, 1.0);
//    	if(y<0) { //down
//    		y = Utils.limitMagnitude(y, 0.01, .1);
//    	}
//    	else {
//    		y = Utils.limitMagnitude(y, 0.01, .5);
//    		
//    	}

    	arm.set(ControlMode.PercentOutput, 0.05);    	

    }
    
    public void armDown() {
    	
    	arm.set(ControlMode.PercentOutput, -0.4);    	
    	
    }
    
    public void armUp() {
    	
    	arm.set(ControlMode.PercentOutput, 0.5);    	
    	
    }
    
    public boolean isFwdLimit() {
    	return arm.getSensorCollection().isFwdLimitSwitchClosed();
    	
    }

    public boolean isRevLimit() {
    	return arm.getSensorCollection().isRevLimitSwitchClosed();
    	
    }
    
    public void stop() {
    	arm.set(ControlMode.PercentOutput, 0.05);    	
//    	arm.setNeutralMode(NeutralMode.Brake);
    	
    }
        
}

