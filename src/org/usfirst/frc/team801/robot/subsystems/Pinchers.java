package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.RobotMap;

import SwerveClass.Team801TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;



public class Pinchers extends Subsystem {
	
	DoubleSolenoid pincher = new DoubleSolenoid(0,1); 

	public Pinchers() {

	}
	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	////Pinchers code below
	public void closePinchers() {
		
		pincher.set(DoubleSolenoid.Value.kForward);

	}
	
	public void openPinchers() {
		
		pincher.set(DoubleSolenoid.Value.kReverse);

	}	

}
