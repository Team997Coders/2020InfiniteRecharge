# 2020InfiniteRecharge
Team 997's, Spartan Robotics, repository for the 2020 FRC game, Infinite Recharge

Make sure you have the correct version of Team997Coders/SpartanLib in the same directory as this repo

This is a summary of all changes made to the default/master swerve code during the bring-up of the actual
robot and the debugging od the code. I went through the code in git to extract all the changes and compare 
the code (2021dev <> master):

* Completely comment out the Climber and the LED manager, they are not on this robot.
* Ignore the use of the Limelight until it is installed.
* Update the constants:
  - Move the Intake to use only one motor (Neo) on CAN ID 10
  - Move the Intake Solenoid to channel 0 (single acting solenoid, default action is up into the robot)
  - Remove the top hopper motor definition, there is only one motor controller (VictorSPX) on CAN ID 5.
  - Update the module zero points (1.0, 0.95, 1.4, 1.15), module 4 needs to be checked
  - Update the module azimuth gains (P=0.01, I=0, D=0 as a starting point).  Tuning still is required.
  - Replace usage of 'Gains' with 'PIDConstants' becuase of a mismatch in the SpartanLib swerve code.
* Update the Intake to not try in pull in the intake (change the solenoid state) each time we gather, instead 
just make it a toggle, and don't engage the intake if it is up.
* Update the Hopper to only use the bottom motor controller and relabel the code to make it clearer.
* Update the OI block to make remove all of the old auto routines and ignore the Limelight.
* Update the drivetrain
  - Use Constants.Values.AZIMUTH_PID[n] instead of Constants.Values.AZIMUTH_GANS which is now unused.
  - Remove some overused Smartdashboard calls.
  - TeslaModule:
    + Swap PIDConstants library for Gains
    + Add in the mAzimuthController constructor in the main method.
    ```
      mAzimuthController = new SpartanPID(pAziConsts);
      mAzimuthController.setMinOutput(-1);
      mAzimuthController.setMaxOutput(1);
    ```
   

