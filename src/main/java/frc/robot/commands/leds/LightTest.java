/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.leds;

import org.team997coders.spartanlib.limelight.LimeLight;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LEDManager;

public class LightTest extends CommandBase {
  /**
   * Creates a new LightTest.
   */
  int i, j, delay;

  public LightTest() {
    addRequirements(LEDManager.getInstance());
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    i = 0;
    j = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (delay % 20 == 0) {
      LEDManager.getInstance().setColorCoordinate(j, i, 0, 0, 255);
      j++;
      if (j == Constants.Values.LED_WIDTH) {
        j = 0;
        i++;
      }
    }

    delay++;
    /*LEDManager.getInstance().setColorCoordinate(0, 0, 0, 255, 0);
    LEDManager.getInstance().setColorCoordinate(1, 1, 0, 0, 255);
    LEDManager.getInstance().setColorCoordinate(2, 2, 255, 255, 0);
    LEDManager.getInstance().setColorCoordinate(3, 3, 255, 255, 0);
    LEDManager.getInstance().setColorCoordinate(4, 4, 255, 255, 0);*/
    //LEDManager.getInstance().setColorCoordinate((int)Math.floor(OI.getInstance().getGamepad2Axis(0) * 3.5) + 3, (int)Math.floor(-OI.getInstance().getGamepad2Axis(1) * 2.5) + 2, 0, 255, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    LimeLight.getInstance().setDouble(LimeLight.LED_MODE, 1.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return false;
    return i == 4 && j == 6;
  }
}

