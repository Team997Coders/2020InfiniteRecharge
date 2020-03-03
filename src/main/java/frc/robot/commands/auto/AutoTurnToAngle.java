/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

public class AutoTurnToAngle extends CommandBase {

  private SpartanPID pidLoop;
  private double target = 0.0;
  private int onTargetTime;

  public AutoTurnToAngle(double angle) {
    addRequirements(DriveTrain.getInstance());
    target = angle;
    pidLoop = new SpartanPID(new PIDConstants(Constants.Values.VISION_TURNING_P, Constants.Values.VISION_TURNING_I, Constants.Values.VISION_TURNING_D));
  }

  @Override
  public void initialize() {
    pidLoop.setSetpoint(target);
  }

  @Override
  public void execute() {
    double turn = pidLoop.WhatShouldIDo(DriveTrain.getInstance().getGyroAngle(), Robot.getCurrentSeconds() * 1000);

    DriveTrain.getInstance().setMotors(turn, -turn);

    if (Math.abs(target - DriveTrain.getInstance().getGyroAngle()) < Constants.Values.VISION_TOLERANCE) {
      onTargetTime++;
    } else {
      onTargetTime = 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (onTargetTime >= 250);
  }
}
