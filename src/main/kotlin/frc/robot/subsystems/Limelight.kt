package frc.robot.subsystems

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance

object Limelight {

  fun getTable(): NetworkTable {
    return NetworkTableInstance.getDefault().getTable("limelight")
  }

  fun setLED(mode: LEDMode) {
    setLED(mode.value)
  }

  fun setLED(value: Int) {
    setEntry("ledMode", value)
  }

  fun getVisible(): Int {
    return getInt("tv")
  }

  fun getArea(): Double {
    return getDouble("ta")
  }

  fun getX(): Double {
    return getDouble("tx")
  }

  fun getY(): Double {
    return getDouble("ty")
  }

  fun setEntry(entry: String, value: Int) {
    getTable().getEntry(entry).setNumber(value)
  }

  fun getInt(entry: String): Int {
    return getTable().getEntry(entry).getNumber(0).toInt()
  }

  fun getDouble(entry: String): Double {
    return getTable().getEntry(entry).getNumber(0.0).toDouble()
  }

  enum class LEDMode(val value: Int) {
    kOn(3),
    kOff(1)
  }

}