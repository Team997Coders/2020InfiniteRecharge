package frc.robot.commands.hopper

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.Robot
import frc.robot.subsystems.Hopper
import frc.robot.utils.TimedTrigger

class HopperAutoIndex: CommandBase() {

  private var detected: Boolean = false
  private val handoff: TimedTrigger = TimedTrigger(Constants.Values.HOPPER_HANDOFF_DELAY)
  private val roll: TimedTrigger = TimedTrigger(Constants.Values.HOPPER_HANDOFF_ROLL_TIME)

  init {
    // addRequirements(Hopper)
  }

  override fun execute() {

    SmartDashboard.putBoolean("Hopper/Detected", detected)
    SmartDashboard.putBoolean("Hopper/Enable Hopper Loader", Robot.EnableAutoLoader)
    SmartDashboard.putBoolean("Hopper/Handoff", handoff.get(false))
    SmartDashboard.putBoolean("Hopper/Roll", roll.get(false))

    if (Robot.EnableAutoLoader) {
      if (detected) {
        handoff.trigger()
        if (handoff.get(false)) {
          roll.trigger()
          Hopper.setSpeed(Constants.Values.HOPPER_INTAKE_SPEED)
          if (Hopper.getShooterBall()) {
            Hopper.setSpeed(0.0)
            handoff.reset()
            roll.reset()
            detected = false
          }
          if (roll.get(true)) {
            handoff.reset()
            detected = false
            Hopper.setSpeed(0.0)
          }
        }
      } else {
        // System.out.println("AAA: " + Hopper.getIntakeBall().toString())
        Hopper.setSpeed(0.0)
        detected = Hopper.getIntakeBall()
      }
    } else {
      handoff.reset()
      roll.reset()
      detected = false
    }
  }

}