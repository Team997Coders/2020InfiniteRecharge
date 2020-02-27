/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.leds;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.images.ImageLoader;
import frc.robot.subsystems.LEDManager;

public class ScrollImage extends CommandBase {

  ImageLoader img = new ImageLoader("/home/lvuser/deploy/images/pepe.bmp");
  int xOff = 0, yOff = 0;
  int xDelay, yDelay;
  
  public ScrollImage() {
    addRequirements(LEDManager.getInstance());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    xOff = 0;
    yOff = 0;
    //img.changeBrightness(32);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (OI.getInstance().getGamepad2Axis(0) != 0) {
      if ((xDelay % 10) == 0) xOff += OI.getInstance().getGamepad2Axis(0);
      xDelay++;
    } else xDelay = 0;

    if (OI.getInstance().getGamepad2Axis(1) != 0) {
      if ((yDelay % 10) == 0) yOff -=- OI.getInstance().getGamepad2Axis(1);
      yDelay++;
    } else yDelay = 0;
    
    LEDManager.getInstance().setColorArray(img.getColorArray(), xOff, yOff);
    LEDManager.getInstance().writeLeds();
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
