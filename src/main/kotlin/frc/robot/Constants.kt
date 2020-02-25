package frc.robot

import frc.robot.utils.Gains

object Constants {

  object Ports {

    val DRIVETRAIN_LEFT_1 = 1
    val DRIVETRAIN_LEFT_2 = 2
    val DRIVETRAIN_RIGHT_1 = 3
    val DRIVETRAIN_RIGHT_2 = 4

    val HOPPER_BOTTOM = 5
    val HOPPER_TOP = 6

    val INTAKE_1 = 7
    val INTAKE_SOLENOID = 3

    val SHOOTER_1 = 8
    val SHOOTER_2 = 9

    val CLIMBER_1 = 10
    val CLIMBER_SOLENOID = 2

    val INTAKE_IR = 0
    val SHOOTER_IR = 1
    val OVERFLOW_IR = 2

  }

  object Values {

    val DRIVE_VELOCITY_GAINS: Gains = Gains().apply {
      P = 0.6
      I = 0.012
      D = 6.8
      F = 1023.0 / 21500.0
    }
    val SHOOTER_VELOCITY_GAINS: Gains = Gains().apply {
      P = 0.001
      I = 0.0
      D = 0.005
      F = (1.0 / (4060.0 * (22.0 / 18.0) * 0.5))
    }

  }

  public fun deadband(value: Double, deadband: Double): Double {
    if (Math.abs(value) < deadband) return 0.0
    return value
  }

}