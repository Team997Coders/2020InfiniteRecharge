package frc.robot;

import frc.robot.commands.shooter.Shoot;
import frc.robot.commands.ClimberUp;
import frc.robot.commands.AutoFaceTargetAndDrive;
import frc.robot.commands.AutoTurnTowardsVision;
import frc.robot.commands.ClimberDown;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.intake.IntakeMove;

public class OI {
  private double axisPos;
  private  XboxController gamepad1, gamepad2;
  private  JoystickButton buttonA, buttonB, buttonX,
    buttonY, buttonA2, buttonB2;
  private JoystickButton buttonRightBumper, buttonLeftBumper;

  private OI() {
    gamepad1 = new XboxController(0);
    gamepad2 = new XboxController(1);

    buttonA2 = new JoystickButton(gamepad2, XboxController.Button.kA.value);
    buttonB2 = new JoystickButton(gamepad2, XboxController.Button.kB.value);
    buttonA = new JoystickButton(gamepad1, XboxController.Button.kA.value);
    buttonX = new JoystickButton(gamepad1, XboxController.Button.kX.value);
    buttonY = new JoystickButton(gamepad1, XboxController.Button.kY.value);
    buttonB = new JoystickButton(gamepad1, XboxController.Button.kB.value);
    buttonRightBumper = new JoystickButton(gamepad2, XboxController.Button.kBumperRight.value);
    buttonLeftBumper = new JoystickButton(gamepad2, XboxController.Button.kBumperLeft.value);

    
    buttonA.whenPressed(new ClimberUp());
    buttonB.whenPressed(new AutoTurnTowardsVision());
    buttonX.whenPressed(new ClimberDown());
    buttonRightBumper.whileHeld(new IntakeMove(Constants.Values.INTAKE_IN));
    buttonLeftBumper.whileHeld(new IntakeMove(Constants.Values.INTAKE_EJECT));

    buttonA2.whileHeld(new Shoot());
    //buttonB.whenPressed(new AutoTurnTowardsVision());
    buttonB.whenPressed(new AutoFaceTargetAndDrive());
  }

  public double getGamepad1Axis(int portNum) {
    axisPos = gamepad1.getRawAxis(portNum);
    if (Math.abs(axisPos) <= 0.05) {
      axisPos = 0;
    }
    return axisPos;
  }

  private static OI instance;
  public static OI getInstance() { if (instance == null) instance = new OI(); return instance; }

}