/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


/**
 * Add your docs here.
 */
public class Vision extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  
  public NetworkTable table;
  public NetworkTableEntry tx;
  public NetworkTableEntry ty;
  public NetworkTableEntry ta;

  public double x;
  public double y;
  public double a;

  double steerKp = 0.02;
  double steerMin = 0.1;
  double moveKp = 0.2;
  double moveMin = 0.1;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void updateValues() {
    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");

    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    a = ta.getDouble(0.0);
  }

  //returns a motor output to turn towards target
  public double turnTowardsTarget() {
    updateValues();
    double steerAmt = steerKp * x;

		if (x > 1.0) {
			steerAmt += steerMin;
		} else if (x < 1.0) {
			steerAmt -= steerMin;
		}

		return steerAmt;
  }

  public double moveTowardsTarget() {
    double moveAmt;
    if (x != 0) {
      updateValues();
      moveAmt = moveKp * (8 - y);
      return -moveAmt;
    }
    return 0;
  }
}
