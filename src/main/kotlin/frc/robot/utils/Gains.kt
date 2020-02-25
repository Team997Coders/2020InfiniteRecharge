package frc.robot.utils

import org.team997coders.spartanlib.helpers.PIDConstants

class Gains {
  var P: Double = 0.0
  var I: Double = 0.0
  var D: Double = 0.0
  var F: Double = 0.0

  fun toPIDConstants(): PIDConstants {
    return PIDConstants(P, I, D)
  }
}