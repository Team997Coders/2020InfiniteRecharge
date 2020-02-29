/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

public class AutoSlurpBallsAndDrive extends CommandBase {
  
  private double distance = 0.0; // in feet
  private double initAngle = 0.0;
  private SpartanPID pidcontroller;

  public AutoSlurpBallsAndDrive(double distance) {
    addRequirements(Intake.getInstance(), DriveTrain.getInstance());
    this.distance = distance;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pidcontroller = new SpartanPID(new PIDConstants(Constants.Values.VISION_TURNING_P,
      Constants.Values.VISION_TURNING_I, Constants.Values.VISION_TURNING_D));
    initAngle = DriveTrain.getInstance().getGyroAngle();
    Intake.getInstance().setPiston(true);
    Robot.autoLoadHopper = true;
    DriveTrain.getInstance().resetEncoders();
    DriveTrain.getInstance().setBrake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Intake.getInstance().setPercent(Constants.Values.INTAKE_IN);
    double turn = pidcontroller.WhatShouldIDo((DriveTrain.getInstance().getGyroAngle() - initAngle), Robot.getDeltaT() * 1000);
    DriveTrain.getInstance().setMotors(0.15 + turn, 0.15 - turn);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Intake.getInstance().setPiston(false);
    Intake.getInstance().setPercent(0);
    Robot.autoLoadHopper = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return DriveTrain.getInstance().getLeftFeet() >= distance;
  }
}
