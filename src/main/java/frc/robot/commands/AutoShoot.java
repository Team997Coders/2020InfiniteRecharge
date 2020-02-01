/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class AutoShoot extends CommandBase {
  
  long tiempo;
  double seconds;

  public AutoShoot(double seconds) {
    this.seconds = seconds;
    
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    tiempo = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Shooter.getInstance().SetYeeterRPM((double)4500);
    Hopper.getInstance().setUpperSpeed(Constants.Values.shooterBeltSpeed);
    
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
    if (System.currentTimeMillis() - tiempo >= seconds * 1000) {
      return true;
    }
    return false;
  }
}
