package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.drivetrain.FollowPath;
import frc.robot.commands.intake.IntakeBallToCount;

public class AutoSickoMode extends SequentialCommandGroup {

  public AutoSickoMode() {
    addCommands(new FollowPath("GoToPickup", false));
    addCommands(new ParallelCommandGroup(
      new FollowPath("Pickup3", false),
      new IntakeBallToCount(3)
    ));
    addCommands(new FollowPath("TrenchPivot", true));
    addCommands(new FollowPath("GoToShootPos", false));
    addCommands(new ParallelRaceGroup(
      new AutoStreamUntilEmpty(Constants.Values.SHOOTER_RPM, true)
    ));
  }

}