// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  XboxController controller;

  TalonSRX azimuth;
  TalonFX drive;
  AnalogInput rotationPos;

  private double getAxis(int axisPort)
  {
    if (Math.abs(controller.getRawAxis(axisPort)) >= Constants.DEADBAND) return controller.getRawAxis(axisPort);
    else return 0;

  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

    controller = new XboxController(Constants.CONTROLLER_PORT);
    
    azimuth = new TalonSRX(Constants.AZIMUTH_PORTS[0]);
    drive = new TalonFX(Constants.DRIVE_PORTS[0]);
    rotationPos = new AnalogInput(Constants.MODULE_ENCODERS[0]);

    azimuth.configFactoryDefault(10);
    drive.configFactoryDefault(10);

    SupplyCurrentLimitConfiguration driLims = new SupplyCurrentLimitConfiguration(true, 45, 60, 750);
    SupplyCurrentLimitConfiguration aziLims = new SupplyCurrentLimitConfiguration(true, 30, 40, 375);

    drive.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
    drive.setSelectedSensorPosition(0, 0, 10);

    drive.configSupplyCurrentLimit(driLims, 10);
    azimuth.configSupplyCurrentLimit(aziLims, 10);

    drive.setInverted(true);

    drive.setNeutralMode(NeutralMode.Coast);
    azimuth.setNeutralMode(NeutralMode.Coast);


  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    drive.set(ControlMode.PercentOutput, getAxis(Constants.Y_AXIS_PORT));
    azimuth.set(ControlMode.PercentOutput, getAxis(Constants.Z_AXIS_PORT));

    SmartDashboard.putNumber("Drive input", getAxis(Constants.Y_AXIS_PORT));
    SmartDashboard.putNumber("Azimuth input", getAxis(Constants.Z_AXIS_PORT));

    SmartDashboard.putNumber("Drive output", drive.getSelectedSensorPosition());
    SmartDashboard.putNumber("Azimuth output", rotationPos.getVoltage());

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
