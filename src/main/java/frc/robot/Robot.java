package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.commands.*;
import frc.robot.commands.hopper.MoveHopperTimed;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  public static boolean autoLoadHopper = false;

  Command autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  
  @Override
  public void robotInit() {
    CommandScheduler.getInstance().cancelAll();

    OI.getInstance();
    Shooter.getInstance();
    Hopper.getInstance();
    m_chooser.setDefaultOption("Do Nothing", new AutoDoNothing());
  }

  @Override
  public void robotPeriodic() { }

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

  boolean lastInShooter = false;

  @Override
  public void teleopPeriodic() {

    boolean intakeBall = !Hopper.getInstance().frontIRsensor.get();
    boolean shooterBall = !Hopper.getInstance().shooterIRsensor.get();

    SmartDashboard.putBoolean("Hopper/Used", Hopper.used);

    if ((!shooterBall && Robot.autoLoadHopper) && (intakeBall && !Hopper.used)) {
      
      SmartDashboard.putBoolean("Hopper/Really?", true);
      CommandScheduler.getInstance().schedule(
      new WaitCommand(0.25).andThen( // 0.2
      new MoveHopperTimed(0.4))); // 0.13
      Hopper.getInstance().mBallCount++;
    }

    if (!Shooter.getInstance().inTheShooter.get() && !lastInShooter) {
      Hopper.getInstance().mBallCount--;
    }

    lastInShooter = !Shooter.getInstance().inTheShooter.get();
    
    SmartDashboard.putBoolean("Hopper/Really?", false);

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
