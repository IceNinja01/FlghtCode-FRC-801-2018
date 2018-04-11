package org.usfirst.frc.team801.robot.commands;



import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 */
public class UpdateSD extends Command {
	
    public UpdateSD()
    {
    	requires(Robot.sdUpdater);
    }

    protected void initialize()
    {
    	DriverStation.reportError("SD Updater Initiated", false);
    }

    protected void execute()
    {
    	SmartDashboard.putNumber("Chassis/Gyro X [deg]", Robot.chassis.getAngleX());
    	SmartDashboard.putNumber("Chassis/Gyro Y [deg]", Robot.chassis.getAngleY());
    	SmartDashboard.putNumber("Chassis/Gyro Z [deg]", Robot.chassis.getGyroAngle());
    	
    	SmartDashboard.putNumber("FrontDist [inches]", Robot.chassis.getFrontDist());
    	SmartDashboard.putNumber("RevDist [inches]", Robot.chassis.getReverseDist());
    	
		SmartDashboard.putNumber("joy X", Robot.oi.driver.getX());
		SmartDashboard.putNumber("joy Y", Robot.oi.driver.getY());
		SmartDashboard.putNumber("joy Z", Robot.oi.driver.getRawAxis(4));
		
		SmartDashboard.putNumber("HeadingCMD", Robot.chassis.headingCMD);
		SmartDashboard.putNumber("HeadingError", Robot.chassis.headingError);
		SmartDashboard.putNumber("zRateCmd", Robot.chassis.zRateCmd);
		
		SmartDashboard.putBoolean("ArmUp", Robot.arm.getArmState());
    	SmartDashboard.putNumber("ElevatorPos", Robot.elevator.getCurrentPosition());

		SmartDashboard.putNumber("Chassis Position", Robot.chassis.getChassisPosition());
		SmartDashboard.putNumberArray("Chassis_Error", Robot.chassis.getChassisError());
    	
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
    }
    
    protected void interrupted()
    {
    	end();
    }
}
