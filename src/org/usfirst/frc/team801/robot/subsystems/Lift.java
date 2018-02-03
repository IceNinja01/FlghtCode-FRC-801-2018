package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.commands.LiftMotorInt;
import org.usfirst.frc.team801.robot.commands.LiftMotorStart;

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

public Lift(){
	liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, Constants.kTimeoutMs);
	liftMotor.config_kF(0, 0.2, Constants.kTimeoutMs);
	liftMotor.config_kP(0, 0.2, Constants.kTimeoutMs);
	liftMotor.config_kI(0, 0, Constants.kTimeoutMs);
	liftMotor.config_kD(0, 0, Constants.kTimeoutMs);
	liftMotor.configMotionCruiseVelocity(5000, Constants.kTimeoutMs);
	liftMotor.configMotionAcceleration(2100, Constants.kTimeoutMs);
	liftMotor.setSensorPhase(false);
	liftMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
	liftMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
	liftMotor.configPeakOutputForward(11, Constants.kTimeoutMs);
	liftMotor.configPeakOutputReverse(11, Constants.kTimeoutMs);
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
    	double setPosition = getCurrentPosition() - maxPosition;
    	liftMotor.set(ControlMode.MotionMagic, pos);
    	
    	//compress to lift
    }
    
    public void extend(double pos) {
    	liftMotor.set(ControlMode.MotionMagic, pos);
    	//extend to grab rung
    }
    public double getCurrentPosition() {
    	double cpos = (maxPosition - liftMotor.getSelectedSensorPosition(0));
    	return cpos; 
    	
    }
}

