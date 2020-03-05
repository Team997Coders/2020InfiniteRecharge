/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

public class WorkingSwerve extends CommandBase {
  /**
   * Creates a new ArcadeDrive.
   */
  double left, right, newAngle, turnAngle, startAngle;
  public WorkingSwerve() {
    addRequirements(DriveTrain.getInstance());
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startAngle = DriveTrain.getInstance().getGyroAngle();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //newAngle = joystick thing
    turnAngle = DriveTrain.getInstance().findAngleTurn(newAngle);
    if (Math.abs(DriveTrain.getInstance().getGyroAngle()) {
      
    }



    //left = (-1 * Robot.m_oi.getAxis(1)) + Robot.m_oi.getAxis(4);
    //right = (-1 * Robot.m_oi.getAxis(1)) - Robot.m_oi.getAxis(4);
    //DriveTrain.getInstance().setMotors(left, right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveTrain.getInstance().setMotors(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
