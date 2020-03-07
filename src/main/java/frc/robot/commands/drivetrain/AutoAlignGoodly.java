/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import org.team997coders.spartanlib.limelight.LimeLight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.Constants;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;
import org.team997coders.spartanlib.helpers.SwerveMixerData;

public class AutoAlignGoodly extends CommandBase {
  
  private SpartanPID strafePid, forwardPid;
  private PIDConstants strafePidConstants, forwardConstants;

  private double targetDistance; // inches

  private long oldTime;
  private long currentTime;
  private long targetLossTimeout;
  private long onTargetTime;

  public AutoAlignGoodly(double distance) {
    addRequirements(DriveTrain.getInstance());
    LimeLight.getInstance().setDouble(LimeLight.LED_MODE, 3.0);
  }

  @Override
  public void initialize() {
    strafePidConstants = new PIDConstants(Constants.Values.VISION_DRIVE_P,
      Constants.Values.VISION_DRIVE_I, Constants.Values.VISION_DRIVE_D);
    strafePid = new SpartanPID(strafePidConstants);
    strafePid.setSetpoint(0);
    forwardConstants = new PIDConstants(Constants.Values.VISION_DRIVE_P,
      Constants.Values.VISION_DRIVE_I, Constants.Values.VISION_DRIVE_D);
    forwardPid = new SpartanPID(forwardConstants);
    forwardPid.setSetpoint(targetDistance);

    oldTime = System.currentTimeMillis();
  }

  @Override
  public void execute() {
    currentTime = System.currentTimeMillis();
    double deltaT = currentTime - oldTime;
    double strafeOutput = strafePid.WhatShouldIDo(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0), Math.abs(deltaT));
    double forwardOutput = forwardPid.WhatShouldIDo(DriveTrain.getInstance().getDistanceToTarget(), Math.abs(deltaT));
    SwerveMixerData input = DriveTrain.getInstance().getSwerveData(forwardOutput, strafeOutput, 0.0, DriveTrain.getInstance().getGyroAngle());
    DriveTrain.getInstance().setSwerveInput(input);

    if (Math.abs(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0)) < Constants.Values.VISION_ANGLE_TOLERANCE) {
      onTargetTime += deltaT;
    } else {
      onTargetTime = 0;
    }

    //SmartDashboard.putNumber("Shooter/PidError", LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0));
    //SmartDashboard.putBoolean("Shooter/onTarget", Math.abs(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0)) < Constants.Values.VISION_ANGLE_TOLERANCE);

    targetLossTimeout = (LimeLight.getInstance().getDouble(LimeLight.TARGET_VISIBLE, 0) == 0 ? targetLossTimeout += deltaT : 0);
    oldTime = currentTime;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) { System.out.println("AutoTurnTowardsVision was interrupted"); }
    if (targetLossTimeout > Constants.Values.VISION_TIMEOUT) { System.out.println("AutoTurnTowardsVision lost vision target for " + targetLossTimeout + "ms"); }
    else { System.out.println("AutoTurnTowardsVision ended on target."); }
    System.out.println("AutoTurnTowardsVision ended on cycle " + Robot.cycles + ", and was onTarget for " + onTargetTime);
    LimeLight.getInstance().setDouble(LimeLight.LED_MODE, LimeLight.LEDState.ForceOff);
  }

  @Override
  public boolean isFinished() {
    return ((onTargetTime >= 250) || (targetLossTimeout > Constants.Values.VISION_TIMEOUT));
  }
}
