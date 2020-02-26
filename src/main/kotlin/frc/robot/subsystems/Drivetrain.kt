package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.SerialPort
import edu.wpi.first.wpilibj2.command.Subsystem
import frc.robot.Constants
import frc.robot.utils.Gains

object Drivetrain: Subsystem {

  private val mGyro: AHRS = AHRS(SerialPort.Port.kUSB).apply {
    reset()
  }

  private val mLeft1: TalonFX = TalonFX(Constants.Ports.DRIVETRAIN_LEFT_1).apply {
    configFactoryDefault(10)
    setInverted(true)
    configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 40.0, 0.0, 0.0))
    configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10)
    setSelectedSensorPosition(0, 0, 10)

    config_kP(0, Constants.Values.DRIVE_VELOCITY_GAINS.P, 10)
    config_kI(0, Constants.Values.DRIVE_VELOCITY_GAINS.I, 10)
    config_kD(0, Constants.Values.DRIVE_VELOCITY_GAINS.D, 10)
    config_kF(0, Constants.Values.DRIVE_VELOCITY_GAINS.F, 10)

    setNeutralMode(NeutralMode.Brake)
  }
  private val mLeft2: TalonFX = TalonFX(Constants.Ports.DRIVETRAIN_LEFT_2).apply {
    configFactoryDefault(10)
    setInverted(true)
    configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 40.0, 0.0, 0.0))

    follow(mLeft1)

    // config_kP(0, Constants.Values.DRIVE_VELOCITY_GAINS.P, 10)
    // config_kI(0, Constants.Values.DRIVE_VELOCITY_GAINS.I, 10)
    // config_kD(0, Constants.Values.DRIVE_VELOCITY_GAINS.D, 10)
    // config_kF(0, Constants.Values.DRIVE_VELOCITY_GAINS.F, 10)

    setNeutralMode(NeutralMode.Brake)
  }
  private val mRight1: TalonFX = TalonFX(Constants.Ports.DRIVETRAIN_RIGHT_1).apply {
    configFactoryDefault(10)
    setInverted(false)
    configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 40.0, 0.0, 0.0))
    configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10)
    setSelectedSensorPosition(0, 0, 10)

    config_kP(0, Constants.Values.DRIVE_VELOCITY_GAINS.P, 10)
    config_kI(0, Constants.Values.DRIVE_VELOCITY_GAINS.I, 10)
    config_kD(0, Constants.Values.DRIVE_VELOCITY_GAINS.D, 10)
    config_kF(0, Constants.Values.DRIVE_VELOCITY_GAINS.F, 10)

    setNeutralMode(NeutralMode.Brake)
  }
  private val mRight2: TalonFX = TalonFX(Constants.Ports.DRIVETRAIN_RIGHT_2).apply {
    configFactoryDefault(10)
    setInverted(false)
    configSupplyCurrentLimit(SupplyCurrentLimitConfiguration(true, 40.0, 0.0, 0.0))

    follow(mRight1)

    // config_kP(0, Constants.Values.DRIVE_VELOCITY_GAINS.P, 10)
    // config_kI(0, Constants.Values.DRIVE_VELOCITY_GAINS.I, 10)
    // config_kD(0, Constants.Values.DRIVE_VELOCITY_GAINS.D, 10)
    // config_kF(0, Constants.Values.DRIVE_VELOCITY_GAINS.F, 10)

    setNeutralMode(NeutralMode.Brake)
  }

  init {
    register()
  }

  public fun setMotors(left: Double, right: Double) {
    mLeft1.set(ControlMode.PercentOutput, left)
    mRight1.set(ControlMode.PercentOutput, right)
  }

  /**
   * Plz give in native units cuz im lazyyy
   */
  public fun setVelocity(left: Double, right: Double) {
    mLeft1.set(ControlMode.Velocity, left)
    mRight1.set(ControlMode.Velocity, right)
  }

  public fun getLeftPosition(): Int {
    return mLeft1.getSelectedSensorPosition(0)
  }

  public fun getRightPosition(): Int {
    return mRight1.getSelectedSensorPosition(0)
  }

  public fun resetEncoders() {
    mLeft1.setSelectedSensorPosition(0, 0, 10)
    mRight1.setSelectedSensorPosition(0, 0, 10)
  }

  public fun setNeutralMode(n: NeutralMode) {
    mLeft1.setNeutralMode(n)
    mLeft2.setNeutralMode(n)
    mRight1.setNeutralMode(n)
    mRight2.setNeutralMode(n)
  }

  override fun periodic() {
    // TODO: POPULATE ME WITH DATA
  }

}