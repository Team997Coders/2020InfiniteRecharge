/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.leds;

import org.team997coders.spartanlib.math.MathUtils;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.LEDManager;

public class Pong extends CommandBase {
  int p1Y, p2Y, ballX, ballY, ballXV, ballYV, p1Score, p2Score;
  long delay;

  public Pong() {
    addRequirements(LEDManager.getInstance());
  }

  @Override
  public void initialize() {
    p1Y = 1;
    p2Y = 2;
    ballX = 3;
    ballY = 2;
    ballXV = -1;
    ballYV = 0;
    delay = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if ((delay % 2) == 0) {
      p1Y += Math.ceil(OI.getInstance().getGamepad1Axis(5));
      p2Y += Math.ceil(OI.getInstance().getGamepad2Axis(5));

      p1Y = MathUtil.clamp(p1Y, 0, 3);
      p2Y = MathUtil.clamp(p2Y, 0, 3);
    }

    if ((delay % 10) == 0 && delay >= 0) {
      if (ballX == 1 && ballY == p1Y) {
        ballXV = 1;
        ballYV = -1;
      }
      if (ballX == 1 && ballY == (p1Y + 1)) {
        ballXV = 1;
        ballYV = 1;
      }
      if (ballX == (Constants.Values.LED_WIDTH - 2) && ballY == p2Y) {
        ballXV = -1;
        ballYV = -1;
      }
      if (ballX == (Constants.Values.LED_WIDTH - 2) && ballY == (p2Y + 1)) {
        ballXV = -1;
        ballYV = 1;
      }
      if (ballX == 0) {
        p2Score++;
        ballX = 3;
        ballY = 2;
        ballXV = 1;
        ballYV = 0;
        delay = -100;
      }
      if (ballX == (Constants.Values.LED_WIDTH - 1)) {
        p1Score++;
        ballX = 3;
        ballY = 2;
        ballXV = -1;
        ballYV = 0;
        delay = -100;
      }
      if (ballY == 0) ballYV = 1;
      if (ballY == (Constants.Values.LED_ROWS - 1)) ballYV = -1;
      ballX += ballXV;
      ballY += ballYV;        
    }
    delay++;

    LEDManager.getInstance().clear();
    LEDManager.getInstance().setColorCoordinate(0, p1Y, 255, 0 + (int)MathUtils.clamp((p1Score * 25), 0, 255), 0 + (int)MathUtils.clamp((p1Score * 25), 0, 255));
    LEDManager.getInstance().setColorCoordinate(0, (p1Y + 1), 255, 0 + (int)MathUtils.clamp((p1Score * 25), 0, 255), 0 + (int)MathUtils.clamp((p1Score * 25), 0, 255));
    LEDManager.getInstance().setColorCoordinate(6, p2Y, 0 + (int)MathUtils.clamp((p2Score * 25), 0, 255), 0 + (int)MathUtils.clamp((p2Score * 25), 0, 255), 255);
    LEDManager.getInstance().setColorCoordinate(6, (p2Y + 1), 0 + (int)MathUtils.clamp((p2Score * 25), 0, 255), 0 + (int)MathUtils.clamp((p2Score * 25), 0, 255), 255);
    LEDManager.getInstance().setColorCoordinate(ballX, ballY, 255, 255, 255);
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
