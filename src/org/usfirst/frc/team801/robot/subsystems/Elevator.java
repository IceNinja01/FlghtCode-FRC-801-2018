package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Constants;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private TalonSRX motor = new TalonSRX(9);
	
	public void Lifter() {
		for(int i = 0; i < 2; i++) {
			motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, Constants.kTimeoutMs);
			/* set the peak and nominal outputs, 12V means full */
			motor.configNominalOutputForward(0, Constants.kTimeoutMs);
			motor.configNominalOutputReverse(0, Constants.kTimeoutMs);
			motor.configPeakOutputForward(11.0, Constants.kTimeoutMs);
			motor.configPeakOutputReverse(-11.0, Constants.kTimeoutMs);
		}
	}


	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void shrink() {
    	//compress to lift
    	
    }
    
    public void extendMid() {
    	//extend to grab rung
    }
    
    public void extendHigh() {
    	//extend to grab rung
    }
}

