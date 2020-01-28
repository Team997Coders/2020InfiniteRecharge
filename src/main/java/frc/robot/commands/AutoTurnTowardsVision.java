/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team997coders.spartanlib.limelight.LimeLight;
import org.team997coders.spartanlib.limelight.LimeLight.LEDState;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.Constants;
import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;
import java.util.concurrent.TimeUnit;

public class AutoTurnTowardsVision extends CommandBase {
  
  private SpartanPID pid;
  private PIDConstants pidConstants;

  private long oldTime;
  private long currentTime;
  private long targetLossTimeout;
  private long onTargetTime;

  public AutoTurnTowardsVision() {
    addRequirements(DriveTrain.getInstance());
    LimeLight.getInstance().setDouble(LimeLight.LED_MODE, 3.0);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveTrain driveTrain = DriveTrain.getInstance();
    pidConstants = new PIDConstants(driveTrain.P, driveTrain.I, driveTrain.D);
    pid = new SpartanPID(pidConstants);
    pid.setSetpoint(0);

    oldTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
    double deltaT = currentTime - oldTime;
    double output = pid.WhatShouldIDo(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0), Math.abs(deltaT));
    DriveTrain.getInstance().setMotors(-output, output);

    if (Math.abs(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0)) < Constants.Values.visionTolerance) {
      onTargetTime += deltaT;
    } else {
      onTargetTime = 0;
    }

    SmartDashboard.putNumber("Shooter/PidError", LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0));
    SmartDashboard.putBoolean("Shooter/onTarget", Math.abs(LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0)) < Constants.Values.visionTolerance);

    targetLossTimeout = (LimeLight.getInstance().getDouble(LimeLight.TARGET_VISIBLE, 0) == 0 ? targetLossTimeout += deltaT : 0);
    oldTime = currentTime;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (interrupted) { System.out.println("AutoTurnTowardsVision was interrupted"); }
    if (targetLossTimeout > Constants.Values.visionTimeout) { System.out.println("AutoTurnTowardsVision lost vision target for " + targetLossTimeout + "ms"); }
    else { System.out.println("AutoTurnTowardsVision ended on target."); }
    System.out.println("AutoTurnTowardsVision ended on cycle " + Robot.cycles + ", and was onTarget for " + onTargetTime);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ((onTargetTime >= 250) || (targetLossTimeout > Constants.Values.visionTimeout));
  }
}
