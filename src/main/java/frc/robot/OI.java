package frc.robot;




import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import edu.wpi.first.wpilibj.XboxController;

import frc.robot.commands.*;
import frc.robot.commands.shooter.*;


public class OI {


  private double axisPos;
  private  Joystick gamepad1, gamepad2;
  private  JoystickButton buttonA2, buttonB2, buttonY2, buttonX2, buttonX, buttonY, buttonA, buttonB;
    private OI() {
    //public OI() {
    gamepad1 = new Joystick(0);
    gamepad2 = new Joystick(1);

    buttonA2 = new JoystickButton(gamepad2, Constants.Ports.buttonA);
    buttonB2 = new JoystickButton(gamepad2, Constants.Ports.buttonB);
    buttonX2 = new JoystickButton(gamepad2, Constants.Ports.buttonX);
    buttonY2 = new JoystickButton(gamepad2, Constants.Ports.buttonY);
    buttonA = new JoystickButton(gamepad1, Constants.Ports.buttonA);
    buttonX = new JoystickButton(gamepad1, Constants.Ports.buttonX);
    buttonY = new JoystickButton(gamepad1, Constants.Ports.buttonY);
    buttonB = new JoystickButton(gamepad1, Constants.Ports.buttonB);

    buttonA2.whileHeld(new Shoot());
    //buttonB2.whenPressed(new StopShooting());
    buttonB2.whileHeld(new UpperConveyorMotor(-Constants.Values.shooterBeltSpeed));
    buttonX2.whileHeld(new LowerConveyorMotor(Constants.Values.shooterBeltSpeed));
    buttonY2.whileHeld(new UpperConveyorMotor(Constants.Values.shooterBeltSpeed));

    buttonA.whenPressed(new ClimberUp());
    buttonX.whenPressed(new ClimberDown());

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
  public static OI getInstance() {if(instance == null) instance = new OI(); return instance;}

}

