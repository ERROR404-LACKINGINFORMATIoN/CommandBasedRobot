package frc.robot;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Wrist extends SubsystemBase {

  public SparkMax wristMotor = new SparkMax(10, MotorType.kBrushless);

  public Wrist() {

    SparkMaxConfig WristConfig = new SparkMaxConfig();
    wristMotor.configure(
        WristConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
}
