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
  private final Timer timer = new Timer();
  private long counter = 0;
  private final int ControllerPort = 0;
  CommandXboxController robotController = new CommandXboxController(ControllerPort);

  public RobotContainer() {
    timer.start();
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

    // A counter that ticks up once per loop.
    counter++;
    Logger.recordOutput("Counter", counter);

    // A pose that drives in a circle around the middle of the field.
    double seconds = timer.get();
    double radius = 2.0;
    double omega = 1.0;
    double x = 4.0 + radius * Math.cos(omega * seconds);
    double y = 4.0 + radius * Math.sin(omega * seconds);
    Rotation2d heading = new Rotation2d(omega * seconds + Math.PI / 2);
    Logger.recordOutput("RobotPose", new Pose2d(x, y, heading));
  }
}
