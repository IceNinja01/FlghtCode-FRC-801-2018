package org.usfirst.frc.team801.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GatherPinchers extends Subsystem{
	
	
	DoubleSolenoid pincher1 = new DoubleSolenoid(2,3);  
	DoubleSolenoid pincher2 = new DoubleSolenoid(4,5);  


	public GatherPinchers() {

	}
	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	////Pinchers code below
	public void closeGatherPinchers() {
		
		pincher1.set(DoubleSolenoid.Value.kForward);
//		pincher2.set(DoubleSolenoid.Value.kForward);

	}
	
	public void openGatherPinchers() {
		
		pincher1.set(DoubleSolenoid.Value.kReverse);
//		pincher2.set(DoubleSolenoid.Value.kReverse);


	}
	
	public void upGather() {
		pincher2.set(DoubleSolenoid.Value.kForward);
	}
	
	public void downGather() {
		pincher2.set(DoubleSolenoid.Value.kReverse);
	}
	
}
