package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.commands.ElevatorMotorInt;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import SwerveClass.Team801TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Team801TalonSRX elevaMotor= RobotMap.elevator;
	private double rotPerinch = 0.6;

	private int vel = 100;
	private int acc = 100;
	private double targetPosition;
	
	public Elevator() {
    	
    		elevaMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);
//    		elevaMotor.configSetParameter(430, edgesPerRotation, 0x00, 0x00, 0);
//    		elevaMotor.configSetParameter(431, filterWindowSize, 0x00, 0x00, 0);
    		//Soft limit
//    		elevaMotor.configReverseSoftLimitThreshold((int) (-57*4096*rotPerinch), Constants.kTimeoutMs);
//    		elevaMotor.configForwardSoftLimitThreshold((int) (+57*4096*rotPerinch), Constants.kTimeoutMs);
			/* set the peak and nominal outputs, 12V means full */
    		elevaMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
    		elevaMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
    		elevaMotor.configPeakOutputForward(11, Constants.kTimeoutMs);
    		elevaMotor.configPeakOutputReverse(-11, Constants.kTimeoutMs);
			
//    		elevaMotor.selectProfileSlot(0, 0);
    		elevaMotor.config_kF(0, 0.2, Constants.kTimeoutMs);
    		elevaMotor.config_kP(0, 0.5, Constants.kTimeoutMs);
    		elevaMotor.config_kI(0, 0, Constants.kTimeoutMs);
    		elevaMotor.config_kD(0, 0.2, Constants.kTimeoutMs);
			/* set acceleration and vcruise velocity - see documentation */
    		elevaMotor.configMotionCruiseVelocity((int) (4096*rotPerinch*vel/10) , Constants.kTimeoutMs);
    		elevaMotor.configMotionAcceleration((int) (4096*rotPerinch*acc/10), Constants.kTimeoutMs);
    		elevaMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    	
    }
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
//        setDefaultCommand(new ElevatorMotorInt());
    }
    
    public void percentOutput() {
    	elevaMotor.set(ControlMode.PercentOutput, Robot.oi.driver.getY());
    }
    
    public void shrink() {
//    	setPosition = getCurrentPosition() - Constants.liftMotorTopLimit;
    	targetPosition = (int) Constants.elevatorMotorBottomPos*rotPerinch*4096;
    	elevaMotor.set(ControlMode.MotionMagic, targetPosition);
    	 getCurrentPosition();
    	//compress elevator
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
    	System.out.print("\ttarget: ");
    	System.out.print(targetPosition);
    	System.out.print("\tpos: ");
    	System.out.print(elevaMotor.getSelectedSensorPosition(0));
    	System.out.print("\terr: ");
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

