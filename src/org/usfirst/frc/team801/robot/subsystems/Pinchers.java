package org.usfirst.frc.team801.robot.subsystems;
import org.usfirst.frc.team801.robot.commands.pinchers.CompressorOn;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;



public class Pinchers extends Subsystem {
	
	Compressor compress = new Compressor(0);
	Solenoid pincher1 = new Solenoid(0); 
	Solenoid pincher2 = new Solenoid(1); 
	private boolean lastState =false;
	
	public Pinchers() {

	}
	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new CompressorOn());
	}
	////Pinchers code below
	public void closePinchers() {
		
		pincher1.set(true);
		pincher2.set(true);

	}
	
	public void openPinchers() {
		
		pincher1.set(false);
		pincher2.set(false);

	}
	
	
	//////Compressor code below
		
	public void compressorOn() {
		compress.start();
	}
	
	public void compressorOff() {
		compress.stop();
	}
	
	public boolean compressorOnOff(boolean b) {
//		if(b = true && !lastState) {
//			compressorOn();
//		}
//		if(b = false && !lastState) {
//			compressorOff();
//		}
//		compressorOn();
		lastState = b;
		return b;
	}
	

}
