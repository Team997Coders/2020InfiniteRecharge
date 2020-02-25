package frc.robot.commands.climber

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Climber

class ClimberMove(val speed: Double): CommandBase() {

  init {
    addRequirements(Climber)
  }

  override fun initialize() {
    Climber.disengageBrake()
  }

  override fun execute() {
    Climber.setMotor(speed)
  }

  override fun end(interrupted: Boolean) {
    Climber.setMotor(0.0)
    Climber.engageBrake()
  }

}