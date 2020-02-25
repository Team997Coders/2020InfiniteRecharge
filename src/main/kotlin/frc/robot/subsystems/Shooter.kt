package frc.robot.subsystems

import com.revrobotics.CANEncoder
import com.revrobotics.CANPIDController
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel.MotorType
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj2.command.Subsystem
import frc.robot.Constants

object Shooter: Subsystem {

  private val mMaster: CANSparkMax = CANSparkMax(Constants.Ports.SHOOTER_1, MotorType.kBrushless).apply {
    restoreFactoryDefaults()
    setIdleMode(CANSparkMax.IdleMode.kCoast)
  }
  private val mFollower: CANSparkMax = CANSparkMax(Constants.Ports.SHOOTER_2, MotorType.kBrushless).apply {
    restoreFactoryDefaults()
    setIdleMode(CANSparkMax.IdleMode.kCoast)
    follow(mMaster)
  }

  private val mEncoder: CANEncoder = CANEncoder(mMaster).apply {
    setVelocityConversionFactor(Constants.Values.SHOOTER_GEARING)
  }
  private val mController: CANPIDController = CANPIDController(mMaster).apply {
    setP(Constants.Values.SHOOTER_VELOCITY_GAINS.P)
    setI(Constants.Values.SHOOTER_VELOCITY_GAINS.I)
    setD(Constants.Values.SHOOTER_VELOCITY_GAINS.D)
    setFF(Constants.Values.SHOOTER_VELOCITY_GAINS.F)
  }

  init {
    register()
  }

  public fun setRPM(RPM: Double) {
    mController.setReference(RPM, ControlType.kVelocity)
  }

  public fun setMotor(perc: Double) {
    mMaster.set(perc)
  }

  override fun periodic() {
    // TODO: FILL ME WIT DAT DATA
  }

}