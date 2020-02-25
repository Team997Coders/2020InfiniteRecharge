package frc.robot.commands.intake

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.Robot
import frc.robot.subsystems.Hopper
import frc.robot.subsystems.Intake
import frc.robot.utils.TimedTrigger

class IntakeMove(val speed: Double, val enableAutoLoader: Boolean): CommandBase() {

  private val mPistonTrigger: TimedTrigger = TimedTrigger(Constants.Values.INTAKE_EXTEND_DELAY)

  init {
    addRequirements(Intake)
  }

  override fun initialize() {
    Intake.setPiston(true)
    mPistonTrigger.reset()
    mPistonTrigger.trigger()
  }

  override fun execute() {
    if (mPistonTrigger.get(false)) {
      Robot.EnableAutoLoader = enableAutoLoader
      if (enableAutoLoader) {
        if (!Hopper.getShooterBall()) Intake.setMotor(speed) // TODO: Get shooter ball
        else Intake.setMotor(0.0)
      } else { // Basically only for ejecting balls
        Intake.setMotor(speed)
      }
    } else {
      Intake.setMotor(0.0)
    }
  }

  override fun end(interrupted: Boolean) {
    Intake.setMotor(0.0)
    Robot.EnableAutoLoader = false;
    Intake.setPiston(false)
  }

}