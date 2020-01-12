package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.commands.*;
import frc.robot.subsystems.Colorsensor;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  Command autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  Colorsensor colorSensor1;

  @Override
  public void robotInit() {
    CommandScheduler.getInstance().cancelAll();
    colorSensor1 = new Colorsensor();

    m_chooser.setDefaultOption("Do Nothing", new AutoDoNothing());

    System.out.println("blueTarget: " + Constants.Colors.blueTarget.red + ", G: " + Constants.Colors.blueTarget.green + ", B: " + Constants.Colors.blueTarget.blue);
    System.out.println("greenTarget: " + Constants.Colors.greenTarget.red + ", G: " + Constants.Colors.greenTarget.green + ", B: " + Constants.Colors.greenTarget.blue);
    System.out.println("redTarget: " + Constants.Colors.redTarget.red + ", G: " + Constants.Colors.redTarget.green + ", B: " + Constants.Colors.redTarget.blue);
    System.out.println("yellowTarget: " + Constants.Colors.yellowTarget.red + ", G: " + Constants.Colors.yellowTarget.green + ", B: " + Constants.Colors.yellowTarget.blue);
  }

  @Override
  public void robotPeriodic() { 
    colorSensor1.UpdateSmartDashboard();
  }

  @Override
  public void disabledInit() { }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
