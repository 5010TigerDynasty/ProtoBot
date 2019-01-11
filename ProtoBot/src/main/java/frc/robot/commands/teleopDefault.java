/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class teleopDefault extends Command {

  private double leftPower;
  private double rightPower;
  private double deadZone = .15;


  public teleopDefault() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(RobotMap.drivetrain);
  }
  public double scaleInputs(double input) {
		if (Math.abs(input) < deadZone) {
			input = 0;
		} else if (input > 0) {
			input = (input - deadZone) * 1 / (1 - deadZone);
		} else if (input < 0) {
			input = (input + deadZone) * 1 / (1 - deadZone);
		}

		return input * input * input;
  }
  

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    leftPower = scaleInputs(Robot.oi.driver.getRawAxis(1));
    rightPower = scaleInputs(Robot.oi.driver.getRawAxis(4)) * 0.5;

    if (Robot.oi.driver.getRawButton(6) && RobotMap.vision.x != 0) {
      double steerAmt = RobotMap.vision.turnTowardsTarget();
      double moveAmt = RobotMap.vision.moveTowardsTarget();
      RobotMap.drivetrain.drive(steerAmt + moveAmt, -steerAmt + moveAmt);
    } else {
      RobotMap.vision.updateValues();
      RobotMap.drivetrain.drive(leftPower + rightPower, leftPower - rightPower);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
