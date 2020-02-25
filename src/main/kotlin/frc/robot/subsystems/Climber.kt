package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import edu.wpi.first.wpilibj.CAN
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj2.command.Subsystem
import frc.robot.Constants

object Climber: Subsystem {

  private val mPiston: Solenoid = Solenoid(Constants.Ports.CLIMBER_SOLENOID).apply {
    set(false)
  }

  private val mMaster: CANSparkMax = CANSparkMax(Constants.Ports.CLIMBER_1, MotorType.kBrushless).apply {
    setSmartCurrentLimit(70)
    setIdleMode(CANSparkMax.IdleMode.kBrake)
  }

  init {
    register()
  }

  public fun setMotor(speed: Double) {
    mMaster.set(speed)
  }

  public fun engageBrake() {
    mPiston.set(false)
  }

  public fun disengageBrake() {
    mPiston.set(true)
  }

  override fun periodic() {
    // TODO: Populatessafdgds
  }

}