// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;
import java.util.concurrent.TimeUnit;

public class AutoDriveForward extends CommandBase {
  /** Creates a new AutoDriveForward. */

  private SpartanPID pid;
  private PIDConstants pidConstants;

  private double target;
  private double currentDistance;

  private long currentTime;
  private long oldTime;
  private long deltaT;

  public AutoDriveForward(double distance) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(DriveTrain.getInstance());
    // distance is in feet (Need to do unit conversion probably to ticks)
    target = distance;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // May need to adjust these.
    pidConstants = new PIDConstants(Constants.Values.VISION_DRIVE_P,
    Constants.Values.VISION_DRIVE_I, Constants.Values.VISION_DRIVE_D);
    pid = new SpartanPID(pidConstants);
    pid.setSetpoint(target);

    currentDistance = 0;
    initTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    deltaT = currentTime - initTime;

    double output = pid.WhatShouldIDo(currentDistance, Math.abs(deltaT));

    double xOffset = Constants.Values.TRACK_WIDTH / 2;
    double yOffset = Constants.Values.WHEEL_BASE / 2;

    SwerveDriveKinematics kine = new SwerveDriveKinematics(
      new Translation2d(xOffset, yOffset),
      new Translation2d(-xOffset, yOffset),
      new Translation2d(-xOffset, -yOffset),
      new Translation2d(xOffset, -yOffset)
    );

    SwerveModuleState[] moduleStates = kine.toSwerveModuleStates(
      new ChassisSpeeds(output, 0.0, 0.0)
    );

    DriveTrain.getInstance().setSwerveInput(DriveTrain.getInstance().toSwerveMixerData(moduleStates));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    SwerveMixerData dat = DriveTrain.getInstance().getSwerveData(0, 0, 0, DriveTrain.getInstance().getGyroAngle());
    DriveTrain.getInstance().setSwerveInput(dat);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
