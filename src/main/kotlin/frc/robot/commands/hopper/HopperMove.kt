package frc.robot.commands.hopper

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Hopper

class HopperMove(val speed: Double): CommandBase() {

  init {
    addRequirements(Hopper)
  }

  override fun execute() {
    Hopper.setSpeed(speed)
  }

  override fun end(interrupted: Boolean) {
    Hopper.setSpeed(0.0)
  }

}