package frc.robot.commands.drivetrain

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.OI
import frc.robot.Robot
import frc.robot.subsystems.Drivetrain
import frc.robot.subsystems.Limelight
import org.team997coders.spartanlib.controllers.SpartanPID
import org.team997coders.spartanlib.math.MathUtils

class TargetVision: CommandBase() {

  val mController: SpartanPID = SpartanPID(Constants.Values.VISION_TARGETING_GAINS.toPIDConstants())

  override fun execute() {
    val output: Double = mController.WhatShouldIDo(getError(), Robot.getDeltaTime())
    val forward: Double = Constants.deadband(-OI.mGamepad1.getRawAxis(1) * 0.7, 0.05)

    Drivetrain.setMotors(forward + output, forward - output)
  }

  override fun end(interrupted: Boolean) {
    Drivetrain.setMotors(0.0, 0.0)
  }

  fun getError(): Double {
    return -Limelight.getX()
  }

}