package org.usfirst.frc.team801.robot.subsystems;
import edu.wpi.first.wpilibj.Compressor;

import edu.wpi.first.wpilibj.command.Subsystem;



public class Pinchers extends Subsystem {
	
	Compressor arm = new Compressor(0);
	
	public Pinchers() {

	}
	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	

	
	public void compressorOn() {
		arm.setClosedLoopControl(true);
	}
	
	public void compressorOff() {
		arm.setClosedLoopControl(false);
	}
	

}
