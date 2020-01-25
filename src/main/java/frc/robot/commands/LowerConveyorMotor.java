/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;

public class LowerConveyorMotor extends CommandBase {
  double speed;

  public LowerConveyorMotor(double setSpeep) {
    //addRequirements(Hopper.getInstance());
    speed = setSpeep;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //System.out.println("asdfghjkl");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Hopper.getInstance().setLowerSpeed(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Hopper.getInstance().setLowerSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
