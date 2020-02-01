/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class AutoShoot extends CommandBase {
  /**
   * Creates a new AutoShoot.
   */
  int ballsShot, numOfBalls;
  long tiempo;

  boolean sensingBall;
  public AutoShoot(int numOfBalls) {
    // Use addRequirements() here to declare subsystem dependencies.
  this.numOfBalls = numOfBalls;  
  ballsShot = 0;
  tiempo = 0;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Shooter.getInstance().SetYeeterRPM((double)4500);
    Hopper.getInstance().setUpperSpeed(Constants.Values.shooterBeltSpeed);
    if (Shooter.getInstance().getShooterIR() && System.currentTimeMillis() - tiempo >= 100) {
      tiempo = System.currentTimeMillis();
      ballsShot += 1;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.getInstance().SetYeeterRPM(0);
    Hopper.getInstance().setUpperSpeed(0);
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (ballsShot >= numOfBalls) {
      return true;
    }
    return false;
  }
}
