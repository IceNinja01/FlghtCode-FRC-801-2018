package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.Utilities.Utils;
import org.usfirst.frc.team801.robot.commands.lift.LiftMotorInt;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import SwerveClass.Team801TalonSRX;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {
Team801TalonSRX liftMotor= RobotMap.lift;
	
private double rotPerInch = 0.049;
private int vel = 50;
private int accel = 25;

public Lift(){
	liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);

	liftMotor.setInverted(false);
	liftMotor.setSensorPhase(false);
	liftMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
	liftMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
	liftMotor.configPeakOutputForward(11, Constants.kTimeoutMs);
	liftMotor.configPeakOutputReverse(-11, Constants.kTimeoutMs);

	liftMotor.selectProfileSlot(0, 0);
	liftMotor.config_kF(0, 0.2, Constants.kTimeoutMs);
	liftMotor.config_kP(0, 0.5, Constants.kTimeoutMs);
	liftMotor.config_kI(0, 0, Constants.kTimeoutMs);
	liftMotor.config_kD(0, 0.2, Constants.kTimeoutMs);
	/* set acceleration and vcruise velocity - see documentation */
	liftMotor.configMotionCruiseVelocity((int) (4096*rotPerInch *vel/10), Constants.kTimeoutMs);
	liftMotor.configMotionAcceleration((int) (4096*rotPerInch *accel /10), Constants.kTimeoutMs);
	liftMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); 
//	arm.enableVoltageCompensation(true); 
	liftMotor.setInverted(true);
	liftMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
	liftMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);

	
	
	setDriveCurrentLimit(20, 200, 25);
}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new LiftMotorInt());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void driveLift() {
    	double y = Utils.limitMagnitude(Utils.joyExpo(Robot.oi.manip.getY(), 1.5), 0.01, 1.0);
    	liftMotor.set(ControlMode.PercentOutput, -y);
    }
    
    public void driveUp() {
    	liftMotor.set(ControlMode.PercentOutput, 1.0);
    }
    
    public void driveDown() {
    	liftMotor.set(ControlMode.PercentOutput, -0.4);
    }
    
    public void shrink() {
//    	setPosition = getCurrentPosition() - Constants.liftMotorTopLimit;
    	liftMotor.set(ControlMode.MotionMagic, Constants.liftMotorBottomLimit*rotPerInch*4096);
    	 getCurrentPosition();
    	 
    	//compress to lift
    }
    
    public void extend() {
    	liftMotor.set(ControlMode.MotionMagic, Constants.liftMotorTopLimit*rotPerInch*4096);
    	getCurrentPosition();
    	//extend to grab rung
    }
       
    public void setZeroPos() {
    	
    	/* zero the sensor */
    	liftMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); 

    }
    
    public void setInvert(boolean b) {
    	liftMotor.setInverted(b);
    }
    
    public double getCurrentPosition() {
//    	System.out.print(liftMotor.getSelectedSensorPosition(0));
//    	System.out.print("\terr:");
//    	System.out.println(liftMotor.getClosedLoopError(0));
    	return liftMotor.getClosedLoopError(0);
    	
    }
    
    public void stopMotor() {
    	liftMotor.set(ControlMode.PercentOutput, 0.0);
//    	liftMotor.setNeutralMode(NeutralMode.Brake);
    }
    
    public void coastMotor() {
    	liftMotor.setNeutralMode(NeutralMode.Coast);

    }
    
    public void setDriveCurrentLimit(int peakAmps, int durationMs, int continousAmps) {
    	/* Peak Current and Duration must be exceeded before current limit is activated.
    	When activated, current will be limited to Continuous Current.
    	Set Peak Current params to 0 if desired behavior is to immediately current-limit. */
    		
    	liftMotor.configPeakCurrentLimit(peakAmps, Constants.kTimeoutMs); /* 35 A */
    	liftMotor.configPeakCurrentDuration(durationMs, Constants.kTimeoutMs); /* 200ms */
    	liftMotor.configContinuousCurrentLimit(continousAmps, Constants.kTimeoutMs); /* 30A */
    	liftMotor.enableCurrentLimit(true); /* turn it on */
    		
    	}
    
 
}



