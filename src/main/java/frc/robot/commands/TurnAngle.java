/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class TurnAngle extends CommandBase {
  /**
   * Creates a new TurnToAngle.
   */

  //yaw (-180, 180)
  double startAngle;
  SpartanPID gyroPidLoop;
  PIDConstants gyroPidStuff;
  double tiempo;
  double degrees;


  public TurnAngle(double degrees) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.degrees = degrees;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startAngle = 0;
    gyroPidStuff = new PIDConstants(Constants.Values.autoGyrop, Constants.Values.autoGyroi, Constants.Values.autoGyrod);
    gyroPidLoop = new SpartanPID(gyroPidStuff);
    gyroPidLoop.setSetpoint(degrees);
    tiempo = System.nanoTime();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double gyroPidLoopNum = gyroPidLoop.WhatShouldIDo(DriveTrain.getInstance().getSensorAverage(), System.nanoTime() - tiempo);
    tiempo = System.nanoTime();
    DriveTrain.getInstance().setMotors(gyroPidLoopNum, -gyroPidLoopNum);
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
