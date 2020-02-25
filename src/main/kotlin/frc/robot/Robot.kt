/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler
import frc.robot.commands.drivetrain.ArcadeDrive
import frc.robot.commands.hopper.HopperAutoIndex
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.Hopper

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
class Robot : TimedRobot() {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  companion object {
    var EnableAutoLoader: Boolean = false
  }

  override fun robotInit() {
    Drivetrain.setDefaultCommand(ArcadeDrive())
    Hopper.setDefaultCommand(HopperAutoIndex())
  }

  override fun disabledInit() {}

  override fun disabledPeriodic() {
    CommandScheduler.getInstance().run()
  }

  override fun autonomousInit() {
    // TODO: Add auto command
  }

  override fun autonomousPeriodic() {
    CommandScheduler.getInstance().run()
  }

  override fun teleopInit() {
    // TODO: Cancel auto command
  }

  override fun teleopPeriodic() {
    CommandScheduler.getInstance().run()
  }

  override fun testInit() {
    CommandScheduler.getInstance().cancelAll()
  }

  override fun testPeriodic() {}
}
