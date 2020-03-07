/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.vision;

import org.team997coders.spartanlib.limelight.LimeLight;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDManager;

public class Compass extends CommandBase {
  /**
   * Creates a new Compass.
   */
  public Compass() {
    addRequirements(LEDManager.getInstance());
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    LEDManager.getInstance().targetReticle();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    LimeLight.getInstance().setDouble(LimeLight.LED_MODE, 1.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
