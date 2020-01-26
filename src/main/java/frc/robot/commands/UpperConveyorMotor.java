/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Hopper;

public class UpperConveyorMotor extends CommandBase {
  double speed;

  public UpperConveyorMotor(double setSpeep) {
    //addRequirements(Hopper.getInstance());
    speed = setSpeep;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.autoLoadHopper = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Hopper.getInstance().setUpperSpeed(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Hopper.getInstance().setUpperSpeed(0);
    Robot.autoLoadHopper = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
