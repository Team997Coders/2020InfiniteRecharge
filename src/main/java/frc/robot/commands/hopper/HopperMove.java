/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.hopper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Hopper;

public class HopperMove extends CommandBase {
  
  private double mSpeed;

  /**
   * Boring command that just moves the hopper at a speed.
   */
  public HopperMove(double speed) {
    mSpeed = speed;
  }

  @Override
  public void initialize() {
    Robot.autoLoadHopper = true;
  }

  @Override
  public void execute() {
    Hopper.getInstance().setSpeed(mSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    Hopper.getInstance().setSpeed(0.0);
    Robot.autoLoadHopper = false;
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
