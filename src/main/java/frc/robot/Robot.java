package frc.robot;

import java.util.ArrayList;

import org.team997coders.spartanlib.controllers.SpartanPID;
import org.team997coders.spartanlib.helpers.PIDConstants;
import org.team997coders.spartanlib.limelight.LimeLight;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.commands.auto.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.hopper.*;
import frc.robot.commands.leds.Pong;
import frc.robot.commands.leds.ScrollImage;
import frc.robot.commands.shooter.ShootBadly;
import frc.robot.subsystems.*;
import frc.robot.util.CRGB;
import frc.robot.images.ImageLoader;

public class Robot extends TimedRobot {

  private static double lastUpdate = 0.0;
  private ArrayList<String> commandList;

  public static long cycles = 0;
  public static final boolean verbose = false; //debug variable, set to true for command logging in the console and non-essential smartdashboard bits.

  private Command m_autonomousCommand;
  private Command mHopperCommand;
  public static boolean autoLoadHopper = false;
  private int visionDelay = 0;

  Command autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  
  @Override
  public void robotInit() {

    //CameraServer.getInstance().startAutomaticCapture(0);

    LimeLight.getInstance().setDouble(LimeLight.LED_MODE, LimeLight.LEDState.ForceOff);

    CommandScheduler.getInstance().cancelAll();

    SequentialCommandGroup seq = new SequentialCommandGroup(
      new DriveBadly(7.5),
      new ShootBadly()
    );

    m_chooser.setDefaultOption("Do Nothing", new AutoDoNothing());
    m_chooser.addOption("Auto One", new FollowPath("TrenchPivot", false));
    m_chooser.addOption("s1ck0 m0d3", new AutoSickoMode());
    m_chooser.addOption("Shoot Balls", new AutoStreamUntilEmpty(Constants.Values.SHOOTER_RPM, true));
    m_chooser.addOption("Bad Auto", seq);

    LimeLight.getInstance().mController = new SpartanPID(new PIDConstants(
      Constants.Values.VISION_TURNING_P,
      Constants.Values.VISION_TURNING_I,
      Constants.Values.VISION_TURNING_D
    ));

    Shooter.getInstance();
    Hopper.getInstance();
    DriveTrain.getInstance();
    DriveTrain.getInstance();//.setDefaultCommand(new ArcadeDrive());
    Climber.getInstance();

    LEDManager.getInstance().setDefaultCommand(new Pong());

    OI.getInstance();

    commandList = new ArrayList<String>();
    if (verbose) {
      CommandScheduler.getInstance().onCommandExecute(command -> {
        commandList.add(command.getName());
      });
    }

    SmartDashboard.putData(m_chooser);

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

    ImageLoader img = new ImageLoader("/home/lvuser/deploy/images/us.bmp");
    img.changeBrightness(-128);
    if (img.hasImage()) LEDManager.getInstance().setColorArray(img.getColorArray());
    LEDManager.getInstance().writeLeds();

    
    DriveTrain.getInstance().setCoast();

    LimeLight.getInstance().setDouble(LimeLight.LED_MODE, LimeLight.LEDState.ForceOff);
    Intake.getInstance().setPiston(false);

    if (mHopperCommand != null) mHopperCommand.cancel();
    mHopperCommand = new HopperAutoIndex();

    LEDManager.getInstance().setColor(CRGB.WHITE);

  }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();

    Hopper.getInstance().updateBallCount();

    if (OI.getInstance().gamepad1.getAButton()) {
      LimeLight.getInstance().setDouble(LimeLight.LED_MODE, 3.0);
      if ((visionDelay % 10) == 0) LEDManager.getInstance().target();
      visionDelay++;
    } else {
      LimeLight.getInstance().setDouble(LimeLight.LED_MODE, 1.0);
      visionDelay = 0;
    }
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

  @Override
  public void testPeriodic() { }

  public static double getDeltaT() { return getCurrentSeconds() - lastUpdate; }

  public static double getCurrentSeconds() { return System.currentTimeMillis() / 1000.0; }

}
