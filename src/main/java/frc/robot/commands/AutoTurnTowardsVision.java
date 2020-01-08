/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team997coders.spartanlib.limelight.LimeLight.LEDState;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class AutoTurnTowardsVision extends CommandBase {
  /**
   * Creates a new AutoTurnTowardsVision.
   */
  public AutoTurnTowardsVision() {
    addRequirements(Robot.m_driveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.m_limelight.setLED(LEDState.ForceOn);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //double Robot.m_limelight.x;
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
