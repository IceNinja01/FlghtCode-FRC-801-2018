package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Compressor801 extends Subsystem {
	
	
	private boolean lastState = false;
	public edu.wpi.first.wpilibj.Compressor compressor = RobotMap.compressor;
	
	public Compressor801() {

	}
	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	//////Compressor code below
		
	public void compressorOn() {
		compressor.start();
	}
	
	public void compressorOff() {
		compressor.stop();
	}
	
	public boolean compressorOnOff(boolean b) {
		if(b = true && !lastState) {
			compressorOn();
		}
		if(b = false && !lastState) {
			compressorOff();
		}
		lastState = b;
		return b;
	}
	
	public double getCurrent() {
		return compressor.getCompressorCurrent();
	}
	

}
