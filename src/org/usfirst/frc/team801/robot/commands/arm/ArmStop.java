package org.usfirst.frc.team801.robot.commands.arm;

import org.usfirst.frc.team801.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;


    /**
     *
     */
    public class ArmStop extends Command {

        public ArmStop() {
            // Use requires() here to declare subsystem dependencies
        	requires(Robot.arm);
        }

        // Called just before this Command runs the first time
        protected void initialize() {
        	Robot.arm.stop();

        }

        // Called repeatedly when this Command is scheduled to run
        protected void execute() {
        	Robot.arm.stop();

        }

        // Make this return true when this Command no longer needs to run execute()
        protected boolean isFinished() {
            return false;
        }

        // Called once after isFinished returns true
        protected void end() {
        	Robot.arm.stop();
        }

        // Called when another command which requires one or more of the same
        // subsystems is scheduled to run
        protected void interrupted() {
        }
 }

