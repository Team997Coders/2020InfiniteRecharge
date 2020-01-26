package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.commands.*;
import frc.robot.commands.auto.FollowPath;
import frc.robot.pathfollower.PathManager;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;

import org.team997coders.spartanlib.limelight.LimeLight;
import frc.robot.commands.hopper.MoveHopperTimed;
import frc.robot.subsystems.Shooter;

public class Robot extends TimedRobot {
  
  private ArrayList<String> commandList;

  public static long cycles = 0;
  public final boolean verbose = false; //debug variable, set to true for ALL THE DATA
  //public static final boolean isTuning = true;

  public static LimeLight m_limelight;

  private Command m_autonomousCommand;
  public static boolean autoLoadHopper = false;

  Command autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  
  @Override
  public void robotInit() {
    CommandScheduler.getInstance().cancelAll();
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_chooser.setDefaultOption("Do Nothing", new AutoDoNothing());
    m_chooser.addOption("Auto One", new FollowPath(PathManager.getSupplier("GoToPickup"), false));

    m_limelight = new LimeLight();

    OI.getInstance();
    Shooter.getInstance();
    Hopper.getInstance();
    DriveTrain.getInstance().setDefaultCommand(new ArcadeDrive());
    OI.getInstance();
    Climber.getInstance();

    commandList = new ArrayList<String>();
    if (verbose) {
      CommandScheduler.getInstance().onCommandExecute(command -> {
        commandList.add(command.getName());
      });
    }
    DriveTrain.getInstance().putCurrentPID();
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
  }

  @Override
  public void disabledInit() { }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();

    DriveTrain.getInstance().updatePID();
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
        new MoveHopperTimed(Constants.Values.HOPPER_HANDOFF_ROLL_TIME) // 0.13
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
    SmartDashboard.putNumber("Limelight/hasTarget", m_limelight.getDouble(LimeLight.TARGET_VISIBLE, 0));
    SmartDashboard.putNumber("Limelight/targetX", m_limelight.getDouble(LimeLight.TARGET_X, 0));
    SmartDashboard.putNumber("Limelight/targetY", m_limelight.getDouble(LimeLight.TARGET_Y, 0));
  }
}
