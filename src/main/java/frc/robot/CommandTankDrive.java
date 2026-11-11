package frc.robot;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CommandTankDrive extends SubsystemBase {
  private final int leftMotor1 = 2;
  private final int leftMotor2 = 4;
  private final int rightMotor1 = 1;
  private final int rightMotor2 = 3;

  SparkMax leftmotorrobot = new SparkMax(leftMotor1, MotorType.kBrushless);
  SparkMax leftmotorrobotv = new SparkMax(leftMotor2, MotorType.kBrushless);
  SparkMax rightmotorrobot = new SparkMax(rightMotor1, MotorType.kBrushless);
  SparkMax rightmotorrobotv = new SparkMax(rightMotor2, MotorType.kBrushless);
  public DifferentialDrive drivetrainTank = new DifferentialDrive(leftmotorrobot, rightmotorrobot);

  public CommandTankDrive() {

    SparkMaxConfig globalConfig = new SparkMaxConfig();
    SparkMaxConfig leftLeaderConfig = new SparkMaxConfig();
    SparkMaxConfig rightLeaderConfig = new SparkMaxConfig();
    SparkMaxConfig leftElevatorMotorConfig = new SparkMaxConfig();
    SparkMaxConfig rightElevatorMotorConfig = new SparkMaxConfig();
    leftLeaderConfig.apply(globalConfig).inverted(true);
    // Apply the global config and set the leader SPARK for follower mode
    leftElevatorMotorConfig.apply(globalConfig).follow(leftmotorrobot);
    // Apply the global config and set the leader SPARK for follower mode
    rightElevatorMotorConfig.apply(rightElevatorMotorConfig).follow(rightmotorrobot);
    leftmotorrobot.configure(
        globalConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftmotorrobotv.configure(
        leftElevatorMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightmotorrobot.configure(
        rightLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightmotorrobotv.configure(
        rightElevatorMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
}
