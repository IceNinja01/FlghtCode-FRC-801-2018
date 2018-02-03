package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.commands.LiftMotorInt;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
	liftMotor.configPeakOutputForward(2, Constants.kTimeoutMs);
	liftMotor.configPeakOutputReverse(-2, Constants.kTimeoutMs);

	liftMotor.selectProfileSlot(0, 0);
	liftMotor.config_kF(0, 0.1, Constants.kTimeoutMs);
	liftMotor.config_kP(0, 0.1, Constants.kTimeoutMs);
	liftMotor.config_kI(0, 0, Constants.kTimeoutMs);
	liftMotor.config_kD(0, 0, Constants.kTimeoutMs);
	/* set acceleration and vcruise velocity - see documentation */
	liftMotor.configMotionCruiseVelocity(600, Constants.kTimeoutMs);
	liftMotor.configMotionAcceleration(600, Constants.kTimeoutMs);
	liftMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); 

	
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
    	liftMotor.set(ControlMode.MotionMagic, setPosition);
    	
    	//compress to lift
    }
    
    public void extend() {
    	liftMotor.set(ControlMode.MotionMagic, setPosition);
    	//extend to grab rung
    }
    
    public void setTopLimit() {
    	setPosition = (Constants.liftMotorTopLimit - liftMotor.getSelectedSensorPosition(0));
    	System.out.print("setPos:");
    	System.out.print(setPosition);
    }
    
    public void setBottomLimit() {
    	setPosition = Math.abs((Constants.liftMotorBottomLimit - liftMotor.getSelectedSensorPosition(0)));
    	System.out.print("setPos:");
    	System.out.print(setPosition);
    }
    
    public void setZeroPos() {
    	
    	/* zero the sensor */
    	liftMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); 

    }
    
    public void setInvert(boolean b) {
    	liftMotor.setInverted(b);
    }
    
    public double getCurrentPosition() {
    	System.out.print("\terr:");
    	System.out.println(liftMotor.getClosedLoopError(0));
    	return liftMotor.getClosedLoopError(0);
    	
    }
}

