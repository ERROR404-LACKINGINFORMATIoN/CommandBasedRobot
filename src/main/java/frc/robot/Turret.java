package frc.robot;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {

  public SparkMax turretMotor = new SparkMax(7, MotorType.kBrushless);

  public Turret() {

    SparkMaxConfig WristConfig = new SparkMaxConfig();
    turretMotor.configure(
        WristConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
}
