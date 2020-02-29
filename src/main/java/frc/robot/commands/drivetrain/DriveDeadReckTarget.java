package frc.robot.commands.drivetrain;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;
import org.team997coders.spartanlib.limelight.LimeLight;
import org.team997coders.spartanlib.limelight.LimeLight.LEDState;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

public class DriveDeadReckTarget extends CommandBase {

  private double mFeet = 0.0;

  private SpartanPID mController;

  public DriveDeadReckTarget(double feet) {
    mController = new SpartanPID(new PIDConstants(Constants.Values.VISION_TURNING_P,
      Constants.Values.VISION_TURNING_I, Constants.Values.VISION_TURNING_D));

    mFeet = feet;

    addRequirements(DriveTrain.getInstance());
  }

  @Override
  public void initialize() {
    mController.setSetpoint(0.0);
    DriveTrain.getInstance().resetEncoders();
    LimeLight.getInstance().setLED(LEDState.ForceOn.getValue());
    DriveTrain.getInstance().setBrake();
  }

  @Override
  public void execute() {
    double turn = mController.WhatShouldIDo(LimeLight.getInstance().getDouble("tx", 0.0), Robot.getDeltaT() * 1000);
    DriveTrain.getInstance().setMotors(0.15 - turn, 0.15 + turn);
  }

  @Override
  public boolean isFinished() {
    if (DriveTrain.getInstance().getLeftFeet() >= mFeet) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void end(boolean interrupted) {
    DriveTrain.getInstance().setMotors(0.0, 0.0);
  }

}