package frc.robot;

import frc.robot.commands.shooter.ShooterStream;
import frc.robot.commands.shooter.ShooterStreamAutoTarget;
import frc.robot.commands.climber.ClimberMove;
import frc.robot.commands.drivetrain.AutoFaceTargetAndDrive;
import frc.robot.commands.hopper.HopperMove;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.intake.IntakeMove;

public class OI {
  private double axisPos;
  public XboxController gamepad1, gamepad2;
  private JoystickButton buttonA, buttonB, buttonX, buttonY,
    buttonA2, buttonB2, buttonX2, buttonY2;
  private JoystickButton buttonRightBumper, buttonLeftBumper,
    buttonRightBumper2, buttonLeftBumper2;

  private OI() {
    gamepad1 = new XboxController(0);
    gamepad2 = new XboxController(1);

    buttonA2 = new JoystickButton(gamepad2, XboxController.Button.kA.value);
    buttonB2 = new JoystickButton(gamepad2, XboxController.Button.kB.value);
    buttonX2 = new JoystickButton(gamepad2, XboxController.Button.kX.value);
    buttonY2 = new JoystickButton(gamepad2, XboxController.Button.kY.value);
    buttonA = new JoystickButton(gamepad1, XboxController.Button.kA.value);
    buttonX = new JoystickButton(gamepad1, XboxController.Button.kX.value);
    buttonY = new JoystickButton(gamepad1, XboxController.Button.kY.value);
    buttonB = new JoystickButton(gamepad1, XboxController.Button.kB.value);
    buttonRightBumper = new JoystickButton(gamepad2, XboxController.Button.kBumperRight.value);
    buttonLeftBumper = new JoystickButton(gamepad2, XboxController.Button.kBumperLeft.value);
    buttonRightBumper2 = new JoystickButton(gamepad2, XboxController.Button.kBumperRight.value);
    buttonLeftBumper2 = new JoystickButton(gamepad2, XboxController.Button.kBumperLeft.value);

    buttonB.whenPressed(new AutoFaceTargetAndDrive());
    buttonRightBumper.whileHeld(new ShooterStream(Constants.Values.SHOOTER_RPM));
    buttonLeftBumper.whileHeld(new ShooterStreamAutoTarget(Constants.Values.SHOOTER_RPM));

    buttonRightBumper2.whileHeld(new IntakeMove(Constants.Values.INTAKE_IN, true));
    buttonLeftBumper2.whileHeld(new IntakeMove(Constants.Values.INTAKE_EJECT, false));
    buttonA2.whenPressed(new ClimberMove(Constants.Values.CLIMBER_UP));
    buttonB2.whenPressed(new ClimberMove(Constants.Values.CLIMBER_DOWN));
    buttonX2.whileHeld(new HopperMove(Constants.Values.HOPPER_EJECT_SPEED));
    buttonY2.whileHeld(new HopperMove(Constants.Values.HOPPER_INTAKE_SPEED));
  }

  public double getGamepad1Axis(int portNum) {
    axisPos = gamepad1.getRawAxis(portNum);
    if (Math.abs(axisPos) < 0.05) {
      axisPos = 0;
    }
    return axisPos;
  }

  private static OI instance;
  public static OI getInstance() { if (instance == null) instance = new OI(); return instance; }

}