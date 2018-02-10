package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.commands.ElevatorMotorInt;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
        setDefaultCommand(new ElevatorMotorInt());
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
//    	setPosition = getCurrentPosition() - Constants.liftMotorTopLimit;
    	elevaMotor.set(ControlMode.MotionMagic, Constants.elevatorMotorBottomLimit);
    	 getCurrentPosition();
    	//compress elevator
    }
    
    public void extendMid() {
    	
    	getCurrentPosition();
    	//extend to Lower Switch
    }
    
    public void extendHigh() {
    	elevaMotor.set(ControlMode.MotionMagic, Constants.elevatorMotorTopLimit);
    	getCurrentPosition();
    	//extend to High Switch
    }
    public void setZeroPos() {
    	
    	/* zero the sensor */
    	elevaMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); 

    }
    
    public void setInvert(boolean b) {
    	elevaMotor.setInverted(b);
    }
    
    public double getCurrentPosition() {
    	System.out.print(elevaMotor.getSelectedSensorPosition(0));
    	System.out.print("\terr:");
    	System.out.println(elevaMotor.getClosedLoopError(0));
    	return elevaMotor.getClosedLoopError(0);
    	
    }
    
    public void setDriveCurrentLimit(int peakAmps, int durationMs, int continousAmps) {
    	/* Peak Current and Duration must be exceeded before current limit is activated.
    	When activated, current will be limited to Continuous Current.
    	Set Peak Current params to 0 if desired behavior is to immediately current-limit. */
    		
    	elevaMotor.configPeakCurrentLimit(peakAmps, Constants.kTimeoutMs); /* 35 A */
    	elevaMotor.configPeakCurrentDuration(durationMs, Constants.kTimeoutMs); /* 200ms */
    	elevaMotor.configContinuousCurrentLimit(continousAmps, Constants.kTimeoutMs); /* 30A */
    	elevaMotor.enableCurrentLimit(true); /* turn it on */
    		
    	}
}

