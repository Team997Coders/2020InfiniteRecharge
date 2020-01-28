package frc.robot;

import java.util.ArrayList;

import org.team997coders.spartanlib.limelight.LimeLight;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.commands.*;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;

public class Robot extends TimedRobot {
  private ArrayList<String> commandList;

  public static long cycles = 0;
  public final boolean verbose = false; //debug variable, set to true for ALL THE DATA
  //public static final boolean isTuning = true;

  private Command m_autonomousCommand;
  public static OI oi;
  Command autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  
  @Override
  public void robotInit() {
    CommandScheduler.getInstance().cancelAll();
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_chooser.addOption("Do Nothing", new AutoDoNothing());

    Hopper.getInstance();
    DriveTrain.getInstance().setDefaultCommand(new ArcadeDrive());
    DriveTrain.getInstance().register();
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
    SmartDashboard.putNumber("Target X (tx)", LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0));
    SmartDashboard.putNumber("HasTarget", LimeLight.getInstance().getDouble(LimeLight.TARGET_VISIBLE, 69));

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

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Limelight/hasTarget", LimeLight.getInstance().getDouble(LimeLight.TARGET_VISIBLE, 0));
    SmartDashboard.putNumber("Limelight/targetX", LimeLight.getInstance().getDouble(LimeLight.TARGET_X, 0));
    SmartDashboard.putNumber("Limelight/targetY", LimeLight.getInstance().getDouble(LimeLight.TARGET_Y, 0));
  }
}
