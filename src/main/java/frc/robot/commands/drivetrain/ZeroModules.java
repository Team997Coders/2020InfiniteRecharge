/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class ZeroModules extends CommandBase {
  /**
   * Creates a new ZeroModules.
   */
  public ZeroModules() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(DriveTrain.getInstance());
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    try {
      File zeroes = new File("Data/zeroes.txt");
      if (zeroes.createNewFile()) {
        System.out.println("Created File: " + zeroes.getName());
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("Error: Could not create file.");
      e.printStackTrace();
    }


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    
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
