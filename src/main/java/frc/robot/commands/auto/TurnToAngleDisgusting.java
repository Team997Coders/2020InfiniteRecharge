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

public class TurnToAngleDisgusting extends CommandBase {

  private double target;
  private SpartanPID leftPid, rightPid;

  /**
   * @param angle Amount of angle you want to move, not raw angle according to the gyro.
   */
  public TurnToAngleDisgusting(double angle) {
    addRequirements(DriveTrain.getInstance());
    leftPid = new SpartanPID(new PIDConstants(0.0001, 0, 0));
    rightPid = new SpartanPID(new PIDConstants(0.0001, 0, 0));
    target = (Constants.Values.DRIVETRAIN_TRACKWIDTH * angle) / Constants.Values.DRIVETRAIN_TICKS_TO_INCHES;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    leftPid.setSetpoint(target);
    rightPid.setSetpoint(-target);
    DriveTrain.getInstance().setBrake();
    DriveTrain.getInstance().resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftSpeed = leftPid.WhatShouldIDo(DriveTrain.getInstance().getLeftSensor(), Robot.getCurrentSeconds() * 1000);
    double rightSpeed = rightPid.WhatShouldIDo(DriveTrain.getInstance().getRightSensor(), Robot.getCurrentSeconds() * 1000);
    DriveTrain.getInstance().setMotors(leftSpeed, rightSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
