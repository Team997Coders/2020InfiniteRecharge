package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import org.team997coders.spartanlib.commands.UpdateModule;
import org.team997coders.spartanlib.helpers.threading.SpartanRunner;
import org.team997coders.spartanlib.swerve.SwerveDrive;
import org.team997coders.spartanlib.swerve.module.SwerveModule;

import edu.wpi.first.wpilibj.SerialPort.Port;
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

  public void setMotors(double leftSpeed, double rightSpeed) { }

  public void setVelocity(double leftFeetPerSecond, double rightFeetPerSecond) { }

  public void simpleAccelControl(double left, double right) { }

  public void stopOrchestra() { }

  public void playOrchestra(String song) { }

  public double getLeftSensor() { return 0; }

  public double getRightSensor() { return 0;  }

  public double getGyroAngle() { return navx.getYaw(); }

  public double getLeftFeet() { return 0; }

  public double getFeet(TalonFX m) { return 0; }

  public void resetEncoders() { }

  public void resetGyro() { navx.reset(); }

  @Override
  public void periodic() { }

  public void updateSmartDashboard() { }

  public void setCoast() { }

  public void setBrake() { }

  private static DriveTrain instance;
  public static DriveTrain getInstance() {
    if (instance == null) {
      instance = new DriveTrain();
    }
    return instance;
  }
}