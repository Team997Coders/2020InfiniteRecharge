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
import frc.robot.OI;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;
import java.util.concurrent.TimeUnit;

public class AutoFaceTargetAndDrive extends CommandBase {
  
  private SpartanPID pid;
  private PIDConstants pidConstants;

  private long oldTime;
  private long currentTime;
  private long targetLossTimeout;
  private long onTargetTime;

  public AutoFaceTargetAndDrive() {
    addRequirements(DriveTrain.getInstance());
  }

  @Override
  public void initialize() {
    LimeLight.getInstance().setDouble(LimeLight.LED_MODE, LimeLight.LEDState.ForceOn);
    pidConstants = new PIDConstants(Constants.Values.VISION_TURNING_P,
      Constants.Values.VISION_TURNING_I, Constants.Values.VISION_TURNING_D);
    pid = new SpartanPID(pidConstants);
    pid.setSetpoint(0);

    oldTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
  }

  @Override
  public void execute() {
    /*if (!(LimeLight.getInstance().getDouble(LimeLight.LED_MODE, 3.0) == 3.0)) {
      LimeLight.getInstance().setDouble(LimeLight.LED_MODE, LimeLight.LEDState.ForceOn);
    }*/
    currentTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
    double deltaT = currentTime - oldTime;
    double output = pid.WhatShouldIDo(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0), Math.abs(deltaT));
    double joystick = (OI.getInstance().getGamepad1Axis(1) / 2);

    //DriveTrain.getInstance().setMotors(-output - joystick, output - joystick);

    if (Math.abs(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0)) < Constants.Values.VISION_ANGLE_TOLERANCE) {
      onTargetTime += deltaT;
    } else {
      onTargetTime = 0;
    }

    SmartDashboard.putNumber("Shooter/PidError", LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0));
    SmartDashboard.putBoolean("Shooter/onTarget", Math.abs(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0)) < Constants.Values.VISION_ANGLE_TOLERANCE);

    targetLossTimeout = (LimeLight.getInstance().getDouble(LimeLight.TARGET_VISIBLE, 0) == 0 ? targetLossTimeout += deltaT : 0);
    oldTime = currentTime;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) { System.out.println("AutoFaceTargetAndDrive was interrupted"); }
    if (targetLossTimeout > Constants.Values.VISION_TIMEOUT) { System.out.println("AutoFaceTargetAndDrive lost vision target for " + targetLossTimeout + "ms"); }
    else { System.out.println("AutoFaceTargetAndDrive ended on target."); }
    System.out.println("AutoFaceTargetAndDrive ended on cycle " + Robot.cycles + ", and was onTarget for " + onTargetTime);
    LimeLight.getInstance().setDouble(LimeLight.LED_MODE, LimeLight.LEDState.ForceOff);
  }

  @Override
  public boolean isFinished() {
    return false;
    //return ((onTargetTime >= 250) || (targetLossTimeout > Constants.Values.visionTimeout));
  }
}
