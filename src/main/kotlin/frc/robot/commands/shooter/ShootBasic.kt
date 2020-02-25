package frc.robot.commands.shooter

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Shooter

class ShooterBasic(val percent: Double): CommandBase() {

  init {
    addRequirements(Shooter)
  }

  override fun execute() {
    Shooter.setMotor(percent)
  }

  override fun isFinished(): Boolean {
    return false;
  }

  override fun end(interrupted: Boolean) {
    // DO NOT set the velocity control to 0 rpm. That is a bad idea
    Shooter.setMotor(0.0)
  }

}