package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import SwerveClass.Team801TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Team801TalonSRX elevaMotor= RobotMap.elevator;
	
	double maxPosition = 12000;
	double min= 0;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Elevator() {
    	for(int i=0;i<4;i++){
    		elevaMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.kTimeoutMs);
			/* set the peak and nominal outputs, 12V means full */
    		elevaMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
    		elevaMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
    		elevaMotor.configPeakOutputForward(11, Constants.kTimeoutMs);
    		elevaMotor.configPeakOutputReverse(-11, Constants.kTimeoutMs);
			
    		elevaMotor.selectProfileSlot(0, 0);
    		elevaMotor.config_kF(0, 0.2, Constants.kTimeoutMs);
    		elevaMotor.config_kP(0, 0.5, Constants.kTimeoutMs);
    		elevaMotor.config_kI(0, 0, Constants.kTimeoutMs);
    		elevaMotor.config_kD(0, 0.2, Constants.kTimeoutMs);
			/* set acceleration and vcruise velocity - see documentation */
    		elevaMotor.configMotionCruiseVelocity(4096, Constants.kTimeoutMs);
    		elevaMotor.configMotionAcceleration(4096, Constants.kTimeoutMs);
    		elevaMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    	}
    }
    
    public void shrink() {
    	//compress to lift
    	
    }
    
    public void extendMid() {
    	//extend to grab rung
    }
    
    public void extendHigh() {
    	
    }
}

