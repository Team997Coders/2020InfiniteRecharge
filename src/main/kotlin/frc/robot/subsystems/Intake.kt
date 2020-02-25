package frc.robot.subsystems

import com.revrobotics.CANEncoder
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj2.command.Subsystem
import frc.robot.Constants

object Intake: Subsystem {

  private val mPiston: Solenoid = Solenoid(Constants.Ports.INTAKE_SOLENOID).apply {
    set(false)
  }

  private val mMaster: CANSparkMax = CANSparkMax(Constants.Ports.INTAKE_1, MotorType.kBrushless).apply {
    restoreFactoryDefaults()
    setSmartCurrentLimit(50)
    setIdleMode(CANSparkMax.IdleMode.kCoast)
  }

  private val mEncoder: CANEncoder = CANEncoder(mMaster)

  init {
    register()
  }

  public fun setMotor(perc: Double) {
    mMaster.set(perc)
  }

  public fun getRPM(): Double {
    return mEncoder.position;
  }

  public fun setPiston(extended: Boolean) {
    mPiston.set(extended)
  }

  public fun togglePiston() {
    mPiston.set(!mPiston.get())
  }

  public fun getPiston(): Boolean {
    return mPiston.get()
  }

  override fun periodic() {
    // TODO: POPULATE STTUFDUDFSDFUDSAFGUIKDSLIU
  }

}