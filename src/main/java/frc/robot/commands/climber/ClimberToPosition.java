/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climber;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class ClimberToPosition extends CommandBase {

  double target;
  SpartanPID pid;

  long currentTime, oldTime;

  public ClimberToPosition(double position) {
    addRequirements(Climber.getInstance());
    target = position;
    pid = new SpartanPID(new PIDConstants(Constants.Values.CLIMBER_P, Constants.Values.CLIMBER_I, Constants.Values.CLIMBER_D));
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    oldTime = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentTime = System.currentTimeMillis();
    Climber.getInstance().setSpeed(pid.WhatShouldIDo(Climber.getInstance().getEncoder(), currentTime - oldTime));
    oldTime = System.currentTimeMillis();
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
