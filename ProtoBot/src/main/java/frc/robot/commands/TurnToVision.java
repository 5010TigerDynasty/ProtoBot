package frc.robot.commands;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class TurnToVision extends Command {

	public TurnToVision() {
		requires(RobotMap.drivetrain);
		requires(RobotMap.vision);
	}

	protected void initialize() {
		
	}

	protected void execute() {
		// double steerAmt = RobotMap.vision.turnTowardsTarget();
		// RobotMap.drivetrain.drive(steerAmt, -steerAmt);

		double moveAmt = RobotMap.vision.moveTowardsTarget();
		RobotMap.drivetrain.drive(moveAmt, moveAmt);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		RobotMap.drivetrain.stop(0);

	}

	protected void interrupted() {
		end();
	}

}