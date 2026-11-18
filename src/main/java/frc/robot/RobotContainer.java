package frc.robot;
//look into /*https://docs.advantagekit.org/getting-started/template-projects/spark-swerve-template
// and
// https://github.com/Mechanical-Advantage/AdvantageKit/tree/main/template_projects/sources/spark_swerve */
import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.subsystems.CommandTankDrive;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * The robot's behavior lives here. {@link Robot} handles the AdvantageKit logging setup and calls
 * {@link #robotPeriodic()} every loop, so you can focus on your code below.
 *
 * <p>This starter logs a counter that increments every loop and a robot pose that drives in a
 * circle, both through AdvantageKit's {@link Logger}. Open them in AdvantageScope to confirm that
 * edit -> run -> telemetry works end to end, then start replacing this with your own code.
 */
public class RobotContainer {
  private final int ControllerPort = 0;
  CommandXboxController robotController = new CommandXboxController(ControllerPort);
  Turret turret = new Turret();
  CommandTankDrive drivetrain = new CommandTankDrive();
  Wrist wrist = new Wrist();
  Launcher launcher = new Launcher();

  public RobotContainer() {
    drivetrain.setDefaultCommand(
        Commands.run(
            () ->
                drivetrain.drivetrainTank.tankDrive(
                    robotController.getLeftY(), robotController.getRightY()),
            drivetrain));
    robotController
        .povUp()
        .whileTrue(
            Commands.startEnd(
                () -> wrist.wristMotor.set(.3), () -> wrist.wristMotor.set(0), wrist));
    robotController
        .povDown()
        .whileTrue(
            Commands.startEnd(
                () -> wrist.wristMotor.set(-.3), () -> wrist.wristMotor.set(0), wrist));
    robotController
        .povLeft()
        .whileTrue(
            Commands.startEnd(
                () -> turret.turretMotor.set(.3), () -> turret.turretMotor.set(0), turret));

    robotController
        .povRight()
        .whileTrue(
            Commands.startEnd(
                () -> turret.turretMotor.set(-.3), () -> turret.turretMotor.set(0), turret));

    robotController.a().onTrue(launcher.shootSequence());
    ;
    robotController.b().onTrue(Commands.runOnce(() -> launcher.Switch(), launcher));
    // Trigger isPressureHighEnough = new Trigger(() -> launcher.phCompressor.getPressure() > 40.0);
    // isPressureHighEnough.onTrue();
    // m_led.setData(m_buffer);
  }
}
