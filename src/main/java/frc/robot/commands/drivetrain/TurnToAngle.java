package frc.robot.commands.drivetrain;

import org.team997coders.spartanlib.controllers.SpartanPID;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

public class TurnToAngle extends CommandBase {

  private double mTargetTheta = 0.0, mStartTheta = 0.0, mThreshold = 2.5;
  private SpartanPID mController = null;

  public TurnToAngle(double targetTheta) { mTargetTheta = targetTheta; }
  public TurnToAngle(double targetTheta, double threshold) { mTargetTheta = targetTheta; mThreshold = threshold; }

  @Override
  public void initialize() {
    mStartTheta = DriveTrain.getInstance().getYaw();
    mController = new SpartanPID(Constants.Values.DRIVE_TURN_GAINS.toPIDConstants());
    mController.setOutputRange(-0.4, 0.4);
  }

  @Override
  public void execute() {
    double output = mController.WhatShouldIDo(DriveTrain.getInstance().getYaw(), Robot.getDeltaT());
    DriveTrain.getInstance().setMotors(output, -output);
  }

  public double getAbsError() {
    return DriveTrain.angleLimiter(Math.abs(mTargetTheta - mStartTheta));
  }

  @Override
  public boolean isFinished() {
    if (getAbsError() < mThreshold) {
      return true;
    } else {
      return false;
    }
  }

}