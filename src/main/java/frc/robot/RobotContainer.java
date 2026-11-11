package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * The robot's behavior lives here. {@link Robot} handles the AdvantageKit logging setup and calls
 * {@link #robotPeriodic()} every loop, so you can focus on your code below.
 *
 * <p>This starter logs a counter that increments every loop and a robot pose that drives in a
 * circle, both through AdvantageKit's {@link Logger}. Open them in AdvantageScope to confirm that
 * edit -> run -> telemetry works end to end, then start replacing this with your own code.
 */
public class RobotContainer {
  private long counter = 0;
  private final int ControllerPort = 0;
  CommandXboxController robotController = new CommandXboxController(ControllerPort);

  public RobotContainer() {
    CommandTankDrive drivetrain = new CommandTankDrive();
    drivetrain.setDefaultCommand(
        Commands.run(
            () ->
                drivetrain.drivetrainTank.tankDrive(
                    robotController.getLeftY(), robotController.getRightY()),
            drivetrain));
    Wrist wrist = new Wrist();
    robotController.povUp().whileTrue(
            Commands.startEnd(
                () -> wrist.wristMotor.set(.1), () -> wrist.wristMotor.set(0), wrist));
    Turret turret = new Turret();
    robotController.povLeft().whileTrue(
            Commands.startEnd(
                () -> turret.turretMotor.set(.1), () -> turret.turretMotor.set(0), turret));
            }
  /** Called every loop while the robot is running. Add your own logic here. */
  public void robotPeriodic() {
    // Run the command scheduler so subsystems and commands you add keep working.
    CommandScheduler.getInstance().run();
  }
}
