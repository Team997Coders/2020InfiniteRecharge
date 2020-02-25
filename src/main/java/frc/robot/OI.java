package frc.robot;

import frc.robot.commands.shooter.*;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LEDManager;
import frc.robot.commands.climber.ClimberMove;
import frc.robot.commands.drivetrain.AutoFaceTargetAndDrive;
import frc.robot.commands.hopper.*;
import frc.robot.commands.vision.*;

import org.team997coders.spartanlib.limelight.LimeLight;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.intake.IntakeMove;
import frc.robot.commands.intake.toggleIntakePiston;

public class OI {
  private double axisPos;
  public XboxController gamepad1, gamepad2;
  private JoystickButton buttonA, buttonB, buttonX, buttonY, buttonStart,
    buttonA2, buttonB2, buttonX2, buttonY2, buttonRightBumper, buttonLeftBumper, 
    buttonRightBumper2, buttonLeftBumper2, buttonStart2;

  private OI() {
    gamepad1 = new XboxController(0);
    gamepad2 = new XboxController(1);

    buttonA = new JoystickButton(gamepad1, XboxController.Button.kA.value);
    buttonB = new JoystickButton(gamepad1, XboxController.Button.kB.value);
    buttonX = new JoystickButton(gamepad1, XboxController.Button.kX.value);
    buttonY = new JoystickButton(gamepad1, XboxController.Button.kY.value);
    buttonRightBumper = new JoystickButton(gamepad1, XboxController.Button.kBumperRight.value);
    buttonLeftBumper = new JoystickButton(gamepad1, XboxController.Button.kBumperLeft.value);
    buttonStart = new JoystickButton(gamepad1, XboxController.Button.kStart.value);

    buttonRightBumper.whileHeld(new IntakeMove(Constants.Values.INTAKE_IN, true)/*new ShooterStream(Constants.Values.SHOOTER_RPM)*/);
    buttonLeftBumper.whileHeld(new IntakeMove(Constants.Values.INTAKE_EJECT, false));//7.5 /*new ShooterStreamAutoTarget(Constants.Values.SHOOTER_RPM)*/

    buttonA2 = new JoystickButton(gamepad2, XboxController.Button.kA.value);
    buttonB2 = new JoystickButton(gamepad2, XboxController.Button.kB.value);
    buttonX2 = new JoystickButton(gamepad2, XboxController.Button.kX.value);
    buttonY2 = new JoystickButton(gamepad2, XboxController.Button.kY.value);
    buttonRightBumper2 = new JoystickButton(gamepad2, XboxController.Button.kBumperRight.value);
    buttonLeftBumper2 = new JoystickButton(gamepad2, XboxController.Button.kBumperLeft.value);
    buttonStart2 = new JoystickButton(gamepad2, XboxController.Button.kStart.value);

    buttonStart.whenPressed(() -> Intake.getInstance().togglePiston());

    buttonA.whileHeld(new Compass());
    buttonB.whileHeld(new AutoFaceTargetAndDrive());
    buttonRightBumper2.whileHeld(new ShooterBasic(1)/*new ShooterStream(Constants.Values.SHOOTER_RPM)*/);
    buttonLeftBumper2.whileHeld(new ShooterBasic(0.66));//7.5 /*new ShooterStreamAutoTarget(Constants.Values.SHOOTER_RPM)*/

    buttonA2.whileHeld(new ClimberMove(Constants.Values.CLIMBER_UP));
    buttonB2.whileHeld(new ClimberMove(Constants.Values.CLIMBER_DOWN));
    buttonX2.whileHeld(new HopperMove(Constants.Values.HOPPER_EJECT_SPEED));
    buttonY2.whileHeld(new HopperMove(Constants.Values.HOPPER_INTAKE_SPEED));
    buttonStart2.whenPressed(new InstantCommand());

    /*
    buttonA.whileHeld(new ClimberMove(Constants.Values.CLIMBER_DOWN));
    buttonB.whileHeld(new ClimberMove(Constants.Values.CLIMBER_UP));
    buttonY.whileHeld(new AutoFaceTargetAndDrive());
    buttonRightBumper.whileHeld(new ShooterBasic(0.55));

    buttonY2.whileHeld(new ShooterStream(Constants.Values.SHOOTER_RPM));
    buttonRightBumper2.whileHeld(new IntakeMove(Constants.Values.INTAKE_IN, true));
    buttonLeftBumper2.whileHeld(new IntakeMove(Constants.Values.INTAKE_EJECT, false));
    */
    
    
  }

  public double getGamepad1Axis(int portNum) {
    axisPos = gamepad1.getRawAxis(portNum);
    if (Math.abs(axisPos) < 0.05) {
      axisPos = 0;
    }
    return axisPos;
  }

  public double getGamepad2Axis(int portNum) {
    axisPos = gamepad2.getRawAxis(portNum);
    if (Math.abs(axisPos) < 0.05) {
      axisPos = 0;
    }
    return axisPos;
  }

  public void update() {
    if (gamepad1.getXButtonPressed()) {
      LEDManager.getInstance().setColor(120, 255, 255);
    }
    if (gamepad1.getYButtonPressed()) {
      LEDManager.getInstance().setColor(0, 255, 255);
    }
  }

  private static OI instance;
  public static OI getInstance() { if (instance == null) instance = new OI(); return instance; }

}
