/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.util.Logger;

public class GetMaxAccleration extends CommandBase {
  
  private boolean isAccelerating;
  private double leftLastDistance, rightLastDistance, leftLastSpeed, rightLastSpeed, leftLastAcceleration, rightLastAcceleration;
  private double maxSpeedLeft, maxSpeedRight, avgAccelLeft, avgAccelRight;
  private long lastTime, currentTime, cycles;

  public GetMaxAccleration() {
    addRequirements(DriveTrain.getInstance());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveTrain.getInstance().setBrake();
    DriveTrain.getInstance().resetEncoders();
    isAccelerating = true;
    maxSpeedLeft = 0;
    maxSpeedRight = 0;
    avgAccelLeft = 0;
    avgAccelRight = 0;
    cycles = 1;

    Logger.getInstance().write("Hi mom");
  }

  // Measures stuff in ticks/time
  @Override
  public void execute() {
    currentTime = System.currentTimeMillis() / 1000; // seconds

    DriveTrain.getInstance().setMotors(1, 1);
    double leftDistance = ((DriveTrain.getInstance().getLeftSensor()) - leftLastDistance);
    double rightDistance = ((DriveTrain.getInstance().getRightSensor()) - rightLastDistance);

    double leftSpeed = leftDistance / (currentTime - lastTime);
    double rightSpeed = rightDistance / (currentTime - lastTime);

    double leftAcceleration = (leftSpeed - leftLastSpeed) / (currentTime - lastTime);
    double rightAcceleration = (rightSpeed - rightLastSpeed) / (currentTime - lastTime);

    double leftJerk = (leftAcceleration - leftLastAcceleration) / (currentTime - lastTime);
    double rightJerk = (rightAcceleration - rightLastAcceleration) / (currentTime - lastTime);

    if (leftSpeed > maxSpeedLeft) maxSpeedLeft = leftSpeed;
    if (rightSpeed > maxSpeedRight) maxSpeedRight = rightSpeed;

    avgAccelLeft = ((avgAccelLeft * (cycles - 1)) + leftAcceleration) / cycles;
    avgAccelRight = ((avgAccelRight * (cycles - 1)) + rightAcceleration) / cycles;

    SmartDashboard.putNumber("DriveTrain/leftSpeedTest", leftSpeed);
    SmartDashboard.putNumber("DriveTrain/rightSpeedTest", rightSpeed);
    SmartDashboard.putNumber("DriveTrain/leftMaxSpeedTest", maxSpeedLeft);
    SmartDashboard.putNumber("DriveTrain/rightMaxSpeedTest", maxSpeedRight);
    SmartDashboard.putNumber("DriveTrain/leftAccelTest", leftAcceleration);
    SmartDashboard.putNumber("DriveTrain/rightAccelTest", rightAcceleration);
    SmartDashboard.putNumber("DriveTrain/leftAvgAccelTest", avgAccelLeft);
    SmartDashboard.putNumber("DriveTrain/rightAvgAccelTest", avgAccelRight);
    SmartDashboard.putNumber("DriveTrain/leftJerkTest", leftJerk);
    SmartDashboard.putNumber("DriveTrain/rightJerkTest", rightJerk);

    leftLastDistance = leftDistance;
    rightLastDistance = rightDistance;
    leftLastSpeed = leftSpeed;
    rightLastSpeed = rightSpeed;
    leftLastAcceleration = leftAcceleration;
    rightLastAcceleration = rightAcceleration;
    cycles++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveTrain.getInstance().setMotors(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (leftLastAcceleration <= 0.05 && rightLastAcceleration <= 0.05);
  }
}
