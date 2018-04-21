package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.Utilities.Utils;
import org.usfirst.frc.team801.robot.commands.lift.WinchStop;

import com.ctre.phoenix.motorcontrol.ControlMode;

import SwerveClass.Team801TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gatherer extends Subsystem{
	Team801TalonSRX gatherMotor1= RobotMap.gatherMotor1;
	Team801TalonSRX gatherMotor2= RobotMap.gatherMotor2;
	private double x;
	//Gather Motors
	public Gatherer() {
//		gatherMotor1.configNominalOutputForward(0, Constants.kTimeoutMs);
//		gatherMotor1.configNominalOutputReverse(0, Constants.kTimeoutMs);
//		gatherMotor1.configPeakOutputForward(11, Constants.kTimeoutMs);
//		gatherMotor1.configPeakOutputReverse(-11, Constants.kTimeoutMs);
//		gatherMotor2.configNominalOutputForward(0, Constants.kTimeoutMs);
//		gatherMotor2.configNominalOutputReverse(0, Constants.kTimeoutMs);
//		gatherMotor2.configPeakOutputForward(11, Constants.kTimeoutMs);
//		gatherMotor2.configPeakOutputReverse(-11, Constants.kTimeoutMs);
//		gatherMotor1.enableVoltageCompensation(true);
//		gatherMotor2.enableVoltageCompensation(true);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        
    	
    }
    
    public void gatherUp() {
    	

    	gatherMotor1.set(ControlMode.PercentOutput, 0.8);
    	gatherMotor2.set(ControlMode.PercentOutput, 0.8);
    }
    
    public void eject() {

    	gatherMotor1.set(ControlMode.PercentOutput, -1.0);
    	gatherMotor2.set(ControlMode.PercentOutput, -1.0);
    }
	public void stopGather() {
		// TODO Auto-generated method stub
		gatherMotor1.set(ControlMode.PercentOutput, 0.0);
		gatherMotor2.set(ControlMode.PercentOutput, 0.0);

	}

	public void ejectGrab() {

    	gatherMotor1.set(ControlMode.PercentOutput, -0.1);
    	gatherMotor2.set(ControlMode.PercentOutput, -0.1);		
	}
}
