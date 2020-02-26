package frc.robot.utils

class TimedTrigger(val delay: Double) {

  var mTriggered = Double.NaN

  public fun trigger() {
    if (!mTriggered.isFinite()) mTriggered = getCurrentTime()
  }

  public fun get(autoReset: Boolean): Boolean {
    if (!mTriggered.isFinite()) return false
    if (getCurrentTime() - mTriggered >= delay) {
      if (autoReset) reset()
      return true
    }
    return false
  }

  public fun reset() {
    mTriggered = Double.NaN
  }

  public fun getCurrentTime(): Double {
    return System.currentTimeMillis() / 1000.0
  }

}