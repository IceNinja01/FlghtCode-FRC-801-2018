package org.usfirst.frc.team801.robot.subsystems;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GatherPinchers extends Subsystem{
	
	private boolean toggleOn = true;
	private DoubleSolenoid pincher1 = new DoubleSolenoid(2,3);  
	private DoubleSolenoid pincher2 = new DoubleSolenoid(4,5);
	private Value state2;
	private Value state1;  
	

	public GatherPinchers() {

	}
	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	////Pinchers code below
	public void closeGatherPinchers() {
		
		pincher1.set(DoubleSolenoid.Value.kForward);
		pincher2.set(DoubleSolenoid.Value.kForward);

	}
	
	public void openGatherPinchers() {
		
		pincher1.set(DoubleSolenoid.Value.kReverse);
		pincher2.set(DoubleSolenoid.Value.kReverse);


	}
	
	public void upGather() {
		pincher2.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void downGather() {
		pincher2.set(DoubleSolenoid.Value.kForward);
	}
	
	public void setToggle() {
		state2 = pincher2.get();
		
	    switch (state2) {
	      case kOff:
		  		pincher2.set(DoubleSolenoid.Value.kForward);

	  		SmartDashboard.putString("Toggle", "stateOff");
	        break;
	      case kForward:
	  		pincher2.set(DoubleSolenoid.Value.kReverse);
//	  		SmartDashboard.putString("Toggle", "SateForward");
	        break;
	      case kReverse:
	  		pincher2.set(DoubleSolenoid.Value.kForward);
//	  		SmartDashboard.putString("Toggle", "SateRev");
	        break;
	      default:
//		  		SmartDashboard.putString("Toggle", "SateRDef");
	        throw new AssertionError("Illegal value: " + state2);
	        
		}
	}

	public void setToggleElbow() {
		state1 = pincher1.get();
		
	    switch (state1) {
	      case kOff:
		  		pincher1.set(DoubleSolenoid.Value.kForward);

//	  		SmartDashboard.putString("Toggle", "stateOff");
	        break;
	      case kForward:
	  		pincher1.set(DoubleSolenoid.Value.kReverse);
//	  		SmartDashboard.putString("Toggle", "SateForward");
	        break;
	      case kReverse:
	  		pincher1.set(DoubleSolenoid.Value.kForward);
//	  		SmartDashboard.putString("Toggle", "SateRev");
	        break;
	      default:
//		  		SmartDashboard.putString("Toggle", "SateRDef");
	        throw new AssertionError("Illegal value: " + state1);
	        
	    }
	}
		
}
