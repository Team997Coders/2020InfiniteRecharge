package frc.robot;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Set;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;
import org.team997coders.spartanlib.helpers.SwerveMixerData;
import org.team997coders.spartanlib.helpers.threading.SpartanRunner;
import org.team997coders.spartanlib.limelight.LimeLight;
import org.team997coders.spartanlib.motion.pathfollower.PathManager;
import org.team997coders.spartanlib.motion.pathfollower.data.PathData;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.commands.auto.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.hopper.*;

import frc.robot.commands.shooter.ShootBadly;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

  public static SpartanRunner mRunner = new SpartanRunner(20);

  private static double lastUpdate = 0.0;
  public static double initAngle = 0.0;
  private ArrayList<String> commandList;

  public static long cycles = 0;
  public static final boolean verbose = false; //debug variable, set to true for command logging in the console and non-essential smartdashboard bits.

  private Command m_autonomousCommand;
  private Command mHopperCommand;
  public static boolean autoLoadHopper = false;

  Command autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  
  @Override
  public void robotInit() {

    // CameraServer.getInstance().startAutomaticCapture(0);
    requestPaths();

    LimeLight.getInstance().setDouble(LimeLight.LED_MODE, LimeLight.LEDState.ForceOff);

    CommandScheduler.getInstance().cancelAll();

    m_chooser.setDefaultOption("Do Nothing", new AutoDoNothing());
    m_chooser.addOption("Auto One", new FollowPath("TrenchPivot", false));
    m_chooser.addOption("s1ck0 m0d3", new AutoSickoMode());
    m_chooser.addOption("Shoot Balls", new AutoStreamUntilEmpty(Constants.Values.SHOOTER_RPM, true));
    m_chooser.addOption("Drive Forward", new FollowPath("DriveForward", false));
    m_chooser.addOption("AutoNav One", new FollowPath("BarrelRacing", false));
    m_chooser.addOption("AutoNav Two", new FollowPath("Slalom", false));
    m_chooser.addOption("AutoNav Three", new FollowPath("Bounce", false));

    LimeLight.getInstance().mController = new SpartanPID(new PIDConstants(
      Constants.Values.VISION_TURNING_P,
      Constants.Values.VISION_TURNING_I,
      Constants.Values.VISION_TURNING_D
    ));

    Shooter.getInstance();
    Hopper.getInstance();
    DriveTrain.getInstance();
    DriveTrain.getInstance().setDefaultCommand(new SwerveMixer());
    Climber.getInstance();

    LEDManager.getInstance();

    OI.getInstance();

    commandList = new ArrayList<String>();
    if (verbose) {
      CommandScheduler.getInstance().onCommandExecute(command -> {
        commandList.add(command.getName());
      });
    }

    SmartDashboard.putData(m_chooser);
    SmartDashboard.putNumber("Driver/Set Initial Angle", 0.0); // set the init angle of the robot in disabled with this. 0 is straight forwards.

    mHopperCommand = new HopperAutoIndex();
  }

  @Override
  public void robotPeriodic() {
    
    if (verbose) {
      if (commandList.size() > 0) {
        for (String cmdName : commandList) {
          System.out.println("Ran " + cmdName + " on cycle " + cycles);
        }
      }
    }
    commandList.clear();
    cycles++;

    lastUpdate = getCurrentSeconds();

    OI.getInstance().update();
  }

  @Override
  public void disabledInit() {

    DriveTrain.getInstance().setCoast();

    LimeLight.getInstance().setDouble(LimeLight.LED_MODE, LimeLight.LEDState.ForceOff);
    Intake.getInstance().setPiston(false);

    if (mHopperCommand != null) mHopperCommand.cancel();
    mHopperCommand = new HopperAutoIndex();

    LEDManager.getInstance().setColor(1, 0, 100);

  }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();

    Hopper.getInstance().updateBallCount();
    initAngle = NetworkTableInstance.getDefault().getTable("SmartDashboard").getEntry("Driver/Set Init Angle").getDouble(0.0);
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

    DriveTrain.getInstance().setBrake();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    LEDManager.getInstance().setColorToAlliance();

    mHopperCommand.schedule();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  public void requestPaths() {
    for (Map.Entry<String, PathData> entry : Constants.PathFollower.PATH_DATA.entrySet()) {
      PathManager.getInstance().queueData(entry.getValue());
    }
  }

  @Override
  public void testPeriodic() { }

  public static double getDeltaT() { return getCurrentSeconds() - lastUpdate; }

  public static double getCurrentSeconds() { return System.currentTimeMillis() / 1000.0; }

}
