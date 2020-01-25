package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.shooter.Shoot;
import frc.robot.commands.shooter.StopShooting;
import frc.robot.commands.ClimberUp;
import frc.robot.commands.AutoTurnTowardsVision;
import frc.robot.commands.ClimberDown;

public class OI {
  private double axisPos;
  private  Joystick gamepad1, gamepad2;
  private  JoystickButton buttonA2, buttonB2, buttonX, buttonY, buttonA, buttonB;

  private OI() {
    gamepad1 = new Joystick(0);
    gamepad2 = new Joystick(1);

    buttonA2 = new JoystickButton(gamepad2, Constants.Ports.buttonA);
    buttonB2 = new JoystickButton(gamepad2, Constants.Ports.buttonB);
    buttonA = new JoystickButton(gamepad1, Constants.Ports.buttonA);
    buttonX = new JoystickButton(gamepad1, Constants.Ports.buttonX);
    buttonY = new JoystickButton(gamepad1, Constants.Ports.buttonY);
    buttonB = new JoystickButton(gamepad1, Constants.Ports.buttonB);

    buttonA2.whenPressed(new Shoot());
    buttonB2.whenPressed(new StopShooting());

    buttonA.whenPressed(new ClimberUp());
    buttonX.whenPressed(new ClimberDown());

    buttonB.whenPressed(new AutoTurnTowardsVision());
    
  }

  public double getGamepad1Axis(int portNum) {
    axisPos = gamepad1.getRawAxis(portNum);
    if (Math.abs(axisPos) <= 0.05) {
      axisPos = 0;
    }
    return axisPos;
  }

  private static OI instance;
  public static OI getInstance() {if(instance == null) instance = new OI(); return instance;}

}

