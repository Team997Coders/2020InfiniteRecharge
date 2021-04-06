package frc.robot.swerve.module;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team997coders.spartanlib.helpers.PIDConstants;
import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.math.Vector2;
import org.team997coders.spartanlib.swerve.module.SwerveModule;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Gains;

public class TeslaModule extends SwerveModule<SpartanPID, TalonSRX, TalonFX> {

  public boolean enabled = true;

// Milliseconds until I start complaining
  private final double ALIGNMENT_TOLERANCE = 2.5; // Tolerance in degrees

  private double mLastUpdate = Double.NaN;
  private double mLastGoodAlignment;

  public TeslaModule(int pID, int pAziID, int pDriID, int pEncoderID, double pEncoderZero, PIDConstants pAziConsts, PIDConstants pDriConsts) {
    super(pID, pEncoderID, pEncoderZero);

    mAzimuth = new TalonSRX(pAziID);
    mDrive = new TalonFX(pDriID);

    mAzimuth.configFactoryDefault(10);
    mDrive.configFactoryDefault(10);

    mAzimuth.setInverted(true); // Just do it

    mDrive.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    mDrive.setSelectedSensorPosition(0, 0, 10);

    SupplyCurrentLimitConfiguration driLims = new SupplyCurrentLimitConfiguration(true, 45, 60, 750);
    SupplyCurrentLimitConfiguration aziLims = new SupplyCurrentLimitConfiguration(true, 30, 40, 375);

    mDrive.configSupplyCurrentLimit(driLims, 10);
    mAzimuth.configSupplyCurrentLimit(aziLims, 10);

    mAzimuthController = new SpartanPID(pAziConsts);
    mAzimuthController.setMinOutput(-1);
    mAzimuthController.setMaxOutput(1);
    mAzimuthController.setIntegralRange(-1); //because its wrong in SpartanPID

  }

  @Override
  protected void setAzimuthSpeed(double pSpeed) {
    if (enabled) mAzimuth.set(ControlMode.PercentOutput, pSpeed);
    else mAzimuth.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  protected void setDriveSpeed(double pSpeed) {
    if (enabled) mDrive.set(ControlMode.PercentOutput, pSpeed);
    else mDrive.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void invertDrive(boolean pA) {
    mDrive.setInverted(pA);
  }

  @Override
  public void invertDrive(boolean pA, boolean internal) {
    mDrive.setInverted(pA);
    driveInverted = internal;
  }

  @Override
  public void invertAzimuth(boolean pA) {
    mAzimuth.setInverted(pA);
  }

  @Override
  public void setDriveBrakeMode(boolean pMode) {
    if (pMode) mDrive.setNeutralMode(NeutralMode.Brake);
    mDrive.setNeutralMode(NeutralMode.Coast);
  }

  @Override
  public void update() {

    mAzimuthController.setSetpoint(mTargetAngle);

    double deltaT = 0.0;
    double now = System.currentTimeMillis();
    if (Double.isFinite(mLastUpdate)) deltaT = (now - mLastUpdate) * 1000;
    mLastUpdate = now;
    // System.out.println("DeltaT: " + deltaT);

    double adjustedTheta = getAngle();
    while (adjustedTheta < mTargetAngle - 180) adjustedTheta += 360;
    while (adjustedTheta >= mTargetAngle + 180) adjustedTheta -= 360;

    double error = mTargetAngle - adjustedTheta;
    SmartDashboard.putNumber("Swerve/[" + mID + "]/Module Error", error);
    
    double output = mAzimuthController.WhatShouldIDo(adjustedTheta, deltaT);
    SmartDashboard.putNumber("Swerve/[" + mID + "]/Module Spin Speed", output);
    setAzimuthSpeed(output);
    setDriveSpeed(getTargetSpeed() * mMaxSpeed);
  }

  @Override
  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Swerve/[" + mID + "]/Azimuth Current", mAzimuth.getSupplyCurrent());
    SmartDashboard.putNumber("Swerve/[" + mID + "]/Drive Current", mDrive.getSupplyCurrent());
  }

  @Override
  public void updateAzimuthPID(double pP, double pI, double pD) {
    mAzimuthController.setP(pP);
    mAzimuthController.setI(pI);
    mAzimuthController.setD(pD);
  }

  @Override
  public double getDriveSpeed() {
    return mDrive.getSelectedSensorPosition();
  }

  @Override
  public Vector2 getSpeedVector() {
    double speed = (getDriveSpeed() * (driveInverted ^ mDrive.getInverted() ? -1 : 1));
    double x = speed * Math.sin((getAngle() * Math.PI) / 180);
    double y = speed * Math.cos((getAngle() * Math.PI) / 180);
    return new Vector2(x, y);
  }

}