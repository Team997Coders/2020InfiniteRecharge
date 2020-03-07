package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import org.team997coders.spartanlib.commands.UpdateModule;
import org.team997coders.spartanlib.helpers.SwerveMixerData;
import org.team997coders.spartanlib.helpers.threading.SpartanRunner;
import org.team997coders.spartanlib.motion.pathfollower.PathManager;
import org.team997coders.spartanlib.swerve.SwerveDrive;
import org.team997coders.spartanlib.swerve.module.SwerveModule;

import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.swerve.module.TeslaModule;

public class DriveTrain extends SwerveDrive {

  private AHRS navx;

  private DriveTrain() {
    super(Constants.Values.WHEEL_BASE, Constants.Values.TRACK_WIDTH);

    navx = new AHRS(Port.kUSB);

    mModules = new SwerveModule[4];
    for (int i = 0; i < 4; i++) {
      mModules[0] = new TeslaModule(0, Constants.Ports.AZIMUTH_PORTS[0], Constants.Ports.DRIVE_PORTS[0], Constants.Ports.MODULE_ENCODERS[0],
        Constants.Values.MODULE_ZEROS[0], Constants.Values.AZIMUTH_GAINS[0], Constants.Values.DRIVE_GAINS[0]);
    }

    for (int i = 0; i < mModules.length; i++) {
      mModules[i].setDriveBrakeMode(true);
      SpartanRunner.LockThread();
      Robot.mRunner.AddAction(new UpdateModule(mModules[i], this));
      SpartanRunner.UnlockThread();
    }
  }

  public void stopOrchestra() { } //TODO

  public void playOrchestra(String song) { } //TODO

  public double getGyroAngle() { return navx.getYaw(); }

  public void resetGyro() { navx.reset(); }

  @Override
  public void periodic() { }

  public void updateSmartDashboard() {
    if (Robot.verbose) {

    }
  }

  public void setCoast() {
    for (int i = 0; i < mModules.length; i++) {
      mModules[i].setDriveBrakeMode(false);
      SpartanRunner.LockThread();
      Robot.mRunner.AddAction(new UpdateModule(mModules[i], this));
      SpartanRunner.UnlockThread();
    }
  }

  public void setBrake() {
    for (int i = 0; i < mModules.length; i++) {
      mModules[i].setDriveBrakeMode(true);
      SpartanRunner.LockThread();
      Robot.mRunner.AddAction(new UpdateModule(mModules[i], this));
      SpartanRunner.UnlockThread();
    }
  }

  public SwerveMixerData toSwerveMixerData(SwerveModuleState[] moduleStates) {
    SwerveMixerData dat = new SwerveMixerData();
    double[] angles = new double[4], speeds = new double[4];
    for (int i = 0; i < 4; i++) {
      angles[i] = moduleStates[i].angle.getDegrees();
      speeds[i] = PathManager.m2f(moduleStates[i].speedMetersPerSecond);
    }
    dat.setAngles(angles);
    dat.setSpeeds(speeds);
    return dat;
  }

  private static DriveTrain instance;
  public static DriveTrain getInstance() { if (instance == null) { instance = new DriveTrain(); } return instance; }
}