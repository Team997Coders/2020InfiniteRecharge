/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class AutoIntakeBall extends CommandBase {
  
  long tiempo;
  int ballsCollected;

  public AutoIntakeBall() {
    // Use addRequirements() here to declare subsystem dependencies.
    ballsCollected = 0;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    tiempo = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Intake.getInstance().IntakePercent(.75);
    if (Intake.getInstance().getIntakeIR() && System.currentTimeMillis() - tiempo >= 100) {
      tiempo = System.currentTimeMillis();
      ballsCollected += 1;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Intake.getInstance().IntakePercent(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (ballsCollected >= 1) {
      return true;
    }
    return false;
  }
}
