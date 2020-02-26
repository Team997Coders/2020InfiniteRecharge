package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj2.command.Subsystem
import frc.robot.Constants

object Hopper: Subsystem {

  private val mIntakeIR: DigitalInput = DigitalInput(Constants.Ports.INTAKE_IR)
  private val mOverflowIR: DigitalInput = DigitalInput(Constants.Ports.OVERFLOW_IR)
  private val mShooterIR: DigitalInput = DigitalInput(Constants.Ports.SHOOTER_IR)

  private val mTop: VictorSPX = VictorSPX(Constants.Ports.HOPPER_TOP).apply {
    configFactoryDefault(10)
    setNeutralMode(NeutralMode.Brake)
    setInverted(true)
  }
  private val mBottom: VictorSPX = VictorSPX(Constants.Ports.HOPPER_BOTTOM).apply {
    configFactoryDefault(10)
    setNeutralMode(NeutralMode.Brake)
    setInverted(true)
    follow(mTop)
  }

  init {
    register()
  }

  public fun setSpeed(speed: Double) {
    mTop.set(ControlMode.PercentOutput, speed)
  }

  public fun getIntakeBall(): Boolean { return !mIntakeIR.get() }
  public fun getOverflowBall(): Boolean { return !mOverflowIR.get() }
  public fun getShooterBall(): Boolean { return !mShooterIR.get() }

  override fun periodic() {
    // TODO: PUPOLATAEE
  }

}