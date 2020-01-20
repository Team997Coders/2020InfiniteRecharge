/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.security.GeneralSecurityException;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class AutoDriveForward extends CommandBase {
  /**
   * Creates a new AutoDriveForward2.
   */
  double ticks;
  SpartanPID podLoop, gyroPodLoop;
  PIDConstants podstuff, gyroPodStuff;
  long tiempo;
  
  

  public AutoDriveForward(double inches) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.ticks = DriveTrain.getInstance().calcualteEncoderTicksFromInches(inches);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    podstuff = new PIDConstants(Constants.Values.autoDriveForwardp, Constants.Values.autoDriveForwardi, Constants.Values.autoDriveForwardd);
    podLoop = new SpartanPID(podstuff);
    podLoop.setSetpoint(ticks);
    tiempo = System.nanoTime();
    gyroPodStuff = new PIDConstants(Constants.Values.autoGyrop, Constants.Values.autoGyroi, Constants.Values.autoGyrod);
    gyroPodLoop = new SpartanPID(gyroPodStuff);
    gyroPodLoop.setSetpoint(DriveTrain.getInstance().getGyroAngle());;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double podLoopNum = podLoop.WhatShouldIDo((DriveTrain.getInstance().getLeftSensor() + DriveTrain.getInstance().getRightSensor()) / 2, System.nanoTime() - tiempo);
    double gyroPodLoopNum = gyroPodLoop.WhatShouldIDo(DriveTrain.getInstance().getGyroAngle(), System.nanoTime() - tiempo);
    tiempo = System.nanoTime();
    DriveTrain.getInstance().setMotors(podLoopNum - gyroPodLoopNum, podLoopNum + gyroPodLoopNum);
    
    
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(ticks - (DriveTrain.getInstance().getLeftSensor() + DriveTrain.getInstance().getRightSensor()) / 2) <= 69) {
      return true;
    } else {
      return false;
    }

    
  }
}
