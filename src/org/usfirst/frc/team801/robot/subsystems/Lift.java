package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.commands.LiftMotorInt;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
	
double maxPosition = 12000;
double min= 0;

private double setPosition;

private double cpos;

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
	liftMotor.configMotionCruiseVelocity(4096, Constants.kTimeoutMs);
	liftMotor.configMotionAcceleration(4096, Constants.kTimeoutMs);
	liftMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); 

	setDriveCurrentLimit(20, 200, 25);
}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new LiftMotorInt());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void shrink() {
//    	setPosition = getCurrentPosition() - Constants.liftMotorTopLimit;
    	liftMotor.set(ControlMode.MotionMagic, Constants.liftMotorBottomLimit);
    	 getCurrentPosition();
    	//compress to lift
    }
    
    public void extend() {
    	liftMotor.set(ControlMode.MotionMagic, Constants.liftMotorTopLimit);
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
    	System.out.print(liftMotor.getSelectedSensorPosition(0));
    	System.out.print("\terr:");
    	System.out.println(liftMotor.getClosedLoopError(0));
    	return liftMotor.getClosedLoopError(0);
    	
    }
    
    public void stopMotor() {
    	liftMotor.setNeutralMode(NeutralMode.Brake);
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

