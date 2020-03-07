/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;

public class HoodToPosition extends CommandBase {
  
  double targetPosition, currentPosition, tolerance;
  SpartanPID pid;
  PIDConstants pidConstants;
  long time;

  public HoodToPosition(double target) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter.getInstance());
    targetPosition = target;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    currentPosition = Shooter.getInstance().getHoodEncoder();
    pidConstants = new PIDConstants(Constants.Values.HOOD_P, Constants.Values.HOOD_I, Constants.Values.HOOD_D);
    pid = new SpartanPID(pidConstants);
    pid.setSetpoint(targetPosition);
    time = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Shooter.getInstance().setHoodMotor(pid.WhatShouldIDo(currentPosition, System.currentTimeMillis() - time));;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.getInstance().setHoodMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(Shooter.getInstance().getHoodEncoder() - targetPosition) < tolerance); 

  }
}
