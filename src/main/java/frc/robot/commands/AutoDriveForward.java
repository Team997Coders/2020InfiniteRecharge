/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

/*TODO:

1a. Auto ShootLocationPath -> 2
1b. Auto AltStartPositionPath -> 2
2. Auto Shoot3Balls -> 3
3. Auto PickupPath -> 4
4. Auto Pickup3Balls 

*/

public class AutoDriveForward extends CommandBase {
  /**
   * Creates a new AutoDriveForward2.
   */
  double ticks;
  SpartanPID pidLoop, gyroPidLoop;
  PIDConstants pidstuff, gyroPidStuff;
  long tiempo;
  
  

  public AutoDriveForward(double inches) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.ticks = DriveTrain.getInstance().calcualteEncoderTicksFromInches(inches);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pidstuff = new PIDConstants(Constants.Values.autoDriveForwardp, Constants.Values.autoDriveForwardi, Constants.Values.autoDriveForwardd);
    pidLoop = new SpartanPID(pidstuff);
    pidLoop.setSetpoint(ticks);
    tiempo = System.nanoTime();
    gyroPidStuff = new PIDConstants(Constants.Values.autoGyrop, Constants.Values.autoGyroi, Constants.Values.autoGyrod);
    gyroPidLoop = new SpartanPID(gyroPidStuff);
    gyroPidLoop.setSetpoint(DriveTrain.getInstance().getGyroAngle());;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double pidLoopNum = pidLoop.WhatShouldIDo(DriveTrain.getInstance().getSensorAverage(), System.nanoTime() - tiempo);
    double gyroPidLoopNum = gyroPidLoop.WhatShouldIDo(DriveTrain.getInstance().getGyroAngle(), System.nanoTime() - tiempo);
    tiempo = System.nanoTime();
    DriveTrain.getInstance().setMotors(pidLoopNum - gyroPidLoopNum, pidLoopNum + gyroPidLoopNum);
    
    
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(ticks - DriveTrain.getInstance().getSensorAverage()) <= 69) {
      return true;
    } else {
      return false;
    }

    
  }
}
