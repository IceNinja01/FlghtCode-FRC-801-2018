package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import SwerveClass.Team801TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Team801TalonSRX elevaMotor= RobotMap.elevator;
	private double rotPerinch = 0.5;

	private int vel = 50;
	private int acc = 25;
	private double targetPosition;
	
	public Elevator() {
    	
    		elevaMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);
//    		elevaMotor.configSetParameter(430, edgesPerRotation, 0x00, 0x00, 0);
//    		elevaMotor.configSetParameter(431, filterWindowSize, 0x00, 0x00, 0);
    		//Soft limit
    		elevaMotor.configReverseSoftLimitThreshold((int) (0*4096*rotPerinch), Constants.kTimeoutMs);
    		elevaMotor.configForwardSoftLimitThreshold((int) (60*4096*rotPerinch), Constants.kTimeoutMs);
			/* set the peak and nominal outputs, 12V means full */
    		elevaMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
    		elevaMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
    		elevaMotor.configPeakOutputForward(11, Constants.kTimeoutMs);
    		elevaMotor.configPeakOutputReverse(-11, Constants.kTimeoutMs);
			
//    		elevaMotor.selectProfileSlot(0, 0);
    		elevaMotor.config_kF(0, 0.1, Constants.kTimeoutMs);
    		elevaMotor.config_kP(0, 1.0, Constants.kTimeoutMs);
    		elevaMotor.config_kI(0, 0, Constants.kTimeoutMs);
    		elevaMotor.config_kD(0, 0.2, Constants.kTimeoutMs);
			/* set acceleration and vcruise velocity - see documentation */
    		elevaMotor.configMotionCruiseVelocity((int) (4096*rotPerinch*vel/10) , Constants.kTimeoutMs);
    		elevaMotor.configMotionAcceleration((int) (4096*rotPerinch*acc/10), Constants.kTimeoutMs);
    		elevaMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    	
    		setDriveCurrentLimit(25, 200, 25);
    		getCurrentPosition();
	}
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
//        setDefaultCommand(new ElevatorMotorInt());
    }
    
    public void percentOutput() {
    	elevaMotor.set(ControlMode.PercentOutput, Robot.oi.manip.getY());
    }
    
    public void shrink() {
//    	setPosition = getCurrentPosition() - Constants.liftMotorTopLimit;
    	targetPosition = (int) Constants.elevatorMotorBottomPos*rotPerinch*4096;
    	elevaMotor.set(ControlMode.MotionMagic, targetPosition);
    	 getCurrentPosition();
    	//compress elevator
    }
    
    public void extendLow() {
    	targetPosition = (int) Constants.elevatorMotorLowPos*rotPerinch*4096;
    	elevaMotor.set(ControlMode.MotionMagic, targetPosition);
    	getCurrentPosition();
    	//extend to Lower Switch
    }
    
    public void extendMid() {
    	targetPosition = (int) Constants.elevatorMotorMidPos*rotPerinch*4096;
    	elevaMotor.set(ControlMode.MotionMagic, targetPosition);
    	getCurrentPosition();
    	//extend to Lower Switch
    }
    
    
    public void extendHigh() {
    	targetPosition =(int) Constants.elevatorMotorTopPos*rotPerinch*4096;
    	elevaMotor.set(ControlMode.MotionMagic, targetPosition);
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
    
    public void stopMotor() {
    	elevaMotor.setNeutralMode(NeutralMode.Brake);
    }
    
    public void coastMotor() {
    	elevaMotor.setNeutralMode(NeutralMode.Coast);

    }
    
    public double getCurrentPosition() {
    	double pos = elevaMotor.getSelectedSensorPosition(0);

    	pos /= rotPerinch*4096;
    	SmartDashboard.putNumber("ElevatorPos", pos);
    	return pos;
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


	public void setShrink() {
		// TODO Auto-generated method stub
		elevaMotor.config_kD(0, 0.75, Constants.kTimeoutMs);

	}
	public void endShrink() {
		elevaMotor.config_kD(0, 0.2, Constants.kTimeoutMs);

	}
}

