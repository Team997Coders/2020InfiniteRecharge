package frc.robot;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;
import org.team997coders.spartanlib.limelight.LimeLight;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.commands.auto.AutoDoNothing;
import frc.robot.commands.auto.AutoSickoMode;
import frc.robot.commands.auto.AutoStreamUntilEmpty;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.FollowPath;
import frc.robot.commands.hopper.HopperTimedMove;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;

import frc.robot.subsystems.Shooter;

public class Robot extends TimedRobot {
  
  private ArrayList<String> commandList;
  private static ArrayList<String> messages = new ArrayList<String>();

  private static Lock ugh = new ReentrantLock();

  public static long cycles = 0;
  public final boolean verbose = false; //debug variable, set to true for ALL THE DATA
  //public static final boolean isTuning = true;

  private Command m_autonomousCommand;
  public static boolean autoLoadHopper = false;

  Command autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  
  @Override
  public void robotInit() {
    CommandScheduler.getInstance().cancelAll();
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    SequentialCommandGroup seq = new SequentialCommandGroup(
      new FollowPath("Pickup3", false),
      new FollowPath("TrenchPivot", true)
    );

    m_chooser.setDefaultOption("Do Nothing", new AutoDoNothing());
    m_chooser.addOption("Auto One", new FollowPath("TrenchPivot", false));
    m_chooser.addOption("s1ck0 m0d3", new AutoSickoMode());
    m_chooser.addOption("Shoot Balls", new AutoStreamUntilEmpty(Constants.Values.SHOOTER_RPM, true));
    m_chooser.addOption("s1ck0 m0d3 b0n3l355", seq);

    LimeLight.getInstance().mController = new SpartanPID(new PIDConstants(
      Constants.Values.VISION_TURNING_P,
      Constants.Values.VISION_TURNING_I,
      Constants.Values.VISION_TURNING_D
    ));

    Shooter.getInstance();
    Hopper.getInstance();
    DriveTrain.getInstance().setDefaultCommand(new ArcadeDrive());
    Climber.getInstance();

    OI.getInstance();

    commandList = new ArrayList<String>();
    if (verbose) {
      CommandScheduler.getInstance().onCommandExecute(command -> {
        commandList.add(command.getName());
      });
    }
    DriveTrain.getInstance().putCurrentPID();

    SmartDashboard.putData(m_chooser);
  }

  @Override
  public void robotPeriodic() {
    if (verbose) {
      if (commandList.size() > 0) {
        for (String cmdName : commandList) {
          //System.out.println("Ran " + cmdName + " on cycle " + cycles);
        }
      }
    }
    commandList.clear();
    cycles++;

    ugh.lock();
    Object[] msgs = messages.toArray();
    for (Object a : msgs) {
      System.out.println(a.toString());
    }
    messages = new ArrayList<String>();
    ugh.unlock();
  }

  @Override
  public void disabledInit() { }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();

    //DriveTrain.getInstance().updatePID();
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

    //#region Auto Indexing

    boolean intakeBall = Hopper.getInstance().getIntakeBall();
    boolean shooterBall = Hopper.getInstance().getShooterBall();

    SmartDashboard.putBoolean("Hopper/Used", Hopper.getInstance().autoIndexMoving);

    if ((!shooterBall && Robot.autoLoadHopper) && (intakeBall && !Hopper.getInstance().autoIndexMoving)) {
      CommandScheduler.getInstance().schedule(
        new WaitCommand(Constants.Values.HOPPER_HANDOFF_DELAY).andThen( // 0.2
        new HopperTimedMove(Constants.Values.HOPPER_HANDOFF_ROLL_TIME) // 0.13
      ));
      Hopper.getInstance().mBallCount++;
    }

    if (Hopper.getInstance().getShooterBall() && !lastInShooter) {
      Hopper.getInstance().mBallCount--;
    }
    lastInShooter = Hopper.getInstance().getShooterBall();

    //#endregion

    CommandScheduler.getInstance().run();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() { }

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Limelight/hasTarget", LimeLight.getInstance().getDouble(LimeLight.TARGET_VISIBLE, 0));
    SmartDashboard.putNumber("Limelight/targetX", LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0));
    SmartDashboard.putNumber("Limelight/targetY", LimeLight.getInstance().getDouble(LimeLight.TARGET_Y, 0));
  }

  public static void Add(String m) {
    ugh.lock();
    messages.add(m);
    ugh.unlock();
  }
}
