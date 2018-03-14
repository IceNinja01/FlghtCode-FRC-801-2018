package org.usfirst.frc.team801.robot.commands;


import org.usfirst.frc.team801.robot.Robot;
import org.usfirst.frc.team801.robot.RobotMap;
import org.usfirst.frc.team801.robot.subsystems.DataWriter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *TODO A work in progress is to have the events will be added second to last before 
    	the newline command.
 */
public class WriteData extends Command
{
	private int i=0;
    public WriteData()
    {
    	requires(Robot.dataWriter);
    }
    
	protected void initialize()
    {

    	DriverStation.reportError("Writing Data Initiated", false);
    
    }

    protected void execute()
    {

    	//TimeStamp
    	DataWriter.logFile.writeFRCValue("Time [s]",RobotMap.timer.getFPGATimestamp(), "0");
    	//Control Values and Buttons
    	DataWriter.logFile.writeFRCValue("Joystick Linear", Robot.oi.driver.getY(), "1");
    	DataWriter.logFile.writeFRCValue("Joystick Turn", Robot.oi.driver.getRawAxis(4), "1");
    	DataWriter.logFile.writeFRCValue("Joystick Arm", Robot.oi.manip.getY(), "1");
    	DataWriter.logFile.writeFRCValue("Joystick Wrist", Robot.oi.manip.getV(), "1");
    	//Gyro
    	DataWriter.logFile.writeFRCValue("Gyro X [deg]", Robot.chassis.getAngleX(), "2");
    	DataWriter.logFile.writeFRCValue("Gyro Y [deg]", Robot.chassis.getAngleY(), "2");
    	DataWriter.logFile.writeFRCValue("Gyro Z [deg]", Robot.chassis.getGyroAngle(), "2");

//    	//Chassis Encoders
//    	DataWriter.logFile.writeFRCValue("Left Drive Pos [ft]", Robot.chassis.getLeftEncPos_Ft(), "3");
//    	DataWriter.logFile.writeFRCValue("Right Drive Pos [ft]", Robot.chassis.getRightEncPos_Ft(), "3");
//    	DataWriter.logFile.writeFRCValue("Left Drive Vel [RPM]", Math.abs(Robot.chassis.getLeftEncVel_RPM()), "3");
//    	DataWriter.logFile.writeFRCValue("Right Drive Vel [RPM]", Math.abs(Robot.chassis.getRightEncVel_RPM()), "3");
    	//PDP
    	DataWriter.logFile.writeFRCValue("Total Current [A]", RobotMap.pdp.getTotalCurrent(), "4");
    	DataWriter.logFile.writeFRCValue("Bus Voltage [V]", RobotMap.pdp.getVoltage(), "4");
    	DataWriter.logFile.writeFRCValue("Total Energy [J]", RobotMap.pdp.getTotalEnergy(), "4");
    	DataWriter.logFile.writeFRCValue("Total Power [W]", RobotMap.pdp.getTotalPower(), "4");
    
		//Heading Control
    	DataWriter.logFile.writeFRCValue("Heading Error [deg]", Robot.chassis.getHeadingErr(), "10");
    	DataWriter.logFile.writeFRCValue("Heading Cmd [deg]", Robot.chassis.getHeadingCmd(), "10");
    	DataWriter.logFile.writeFRCValue("ZRateCmd", Robot.chassis.getZRateCmd(), "10");
    	//Motor Current
//    	DataWriter.logFile.writeFRCValue("LeftBackMotor Current [A]", RobotMap.leftBack.getOutputCurrent(), "11");
//    	DataWriter.logFile.writeFRCValue("RightBackMotor Current [A]", RobotMap.rightBack.getOutputCurrent(), "11");
//    	DataWriter.logFile.writeFRCValue("LeftFrontMotor Current [A]", RobotMap.leftFront.getOutputCurrent(), "11");
//    	DataWriter.logFile.writeFRCValue("RightFrontMotor Current [A]", RobotMap.rightFront.getOutputCurrent(), "11");
//		//Motor Voltage
//    	DataWriter.logFile.writeFRCValue("LeftBackMotor Voltage [V]", RobotMap.leftBack.getOutputVoltage(), "12");
//    	DataWriter.logFile.writeFRCValue("RightBackMotor Voltage [V]", RobotMap.rightBack.getOutputVoltage(), "12");
//    	DataWriter.logFile.writeFRCValue("LeftFrontMotor Voltage [V]", RobotMap.leftFront.getOutputVoltage(), "12");
//    	DataWriter.logFile.writeFRCValue("RightFrontMotor Voltage [V]", RobotMap.rightFront.getOutputVoltage(), "12");
//    	//Motor Stalled
 
    	DataWriter.logFile.writeFRCEvent();
    	//Last line must stay here
    	DataWriter.logFile.writeNewLine();
    	//This is a hack to get the first HeaderLine to print towards the top not perfect but works
    	if(i==0){
        	DataWriter.logFile.writeHeadLine1();
        	DataWriter.logFile.writeNewLine();
        	DataWriter.logFile.writeGroupNum();
        	DataWriter.logFile.writeNewLine();
        	DataWriter.logFile.writeHeadLine2();
        	DataWriter.logFile.writeNewLine();
        	i = 1;
        	DataWriter.logFile.setPrintSpecFormat(false);
    	}
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
    	//Could not get this to work
//    	DataWriter.logFile.writeNewLine();
//    	DataWriter.logFile.writeHeadLine();
    	DriverStation.reportError("Writing Data Finished", false);

    }

    protected void interrupted()
    {
//    	DataWriter.logFile.writeNewLine();
//    	DataWriter.logFile.writeHeadLine();
    }
}
