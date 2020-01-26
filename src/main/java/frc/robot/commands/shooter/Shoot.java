/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class Shoot extends CommandBase {

  private double target = 3700.0;

  public Shoot() {
    addRequirements(Shooter.getInstance());
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    //Shooter.getInstance().SetYeeterPercent(1);
    Shooter.getInstance().setRPM(target);
    if (eps(Shooter.getInstance().getRPMs(), target, 80) && Hopper.getInstance().mBallCount > 0) {
      Hopper.getInstance().setSpeed(0.75);
    } else {
      Hopper.getInstance().setSpeed(0.0);
    }
  }

  public boolean eps(double a, double b, double eps) {
    return Math.abs(a - b) < eps;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    Shooter.getInstance().GoodStop();
    Hopper.getInstance().setSpeed(0.0);
  }

}
