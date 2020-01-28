package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.subsystems.Intake;
import frc.robot.commands.*;



import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.DriveTrain;
import frc.robot.commands.AutoDriveForward;
import frc.robot.subsystems.Climber;


import org.team997coders.spartanlib.limelight.LimeLight;

public class Robot extends TimedRobot {
  private ArrayList<String> commandList;


  public static long cycles = 0;
  public final boolean verbose = false; //debug variable, set to true for ALL THE DATA
  //public static final boolean isTuning = true;

  public static LimeLight m_limelight;

  private Command m_autonomousCommand;
  public static OI oi;

  public static OI m_oi;
  public static Intake intake; 

  Command autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  
  @Override
  public void robotInit() {
    CommandScheduler.getInstance().cancelAll();

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_chooser.addOption("Go Forward", new AutoDriveForward(60));
    DriveTrain.getInstance().setDefaultCommand(new ArcadeDrive());
    
    Climber.getInstance();
    Shooter.getInstance();
    Hopper.getInstance();
    Intake.getInstance();
    m_chooser.setDefaultOption("Do Nothing", new AutoDoNothing());
    OI.getInstance();
    
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
    SmartDashboard.putNumber("Limelight/hasTarget", m_limelight.getDouble(LimeLight.TARGET_VISIBLE, 0));
    SmartDashboard.putNumber("Limelight/targetX", m_limelight.getDouble(LimeLight.TARGET_X, 0));
    SmartDashboard.putNumber("Limelight/targetY", m_limelight.getDouble(LimeLight.TARGET_Y, 0));
  }
}
