// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot.subsystems;


import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.subsystems.DriveIO.DriveIOInputs;

import com.revrobotics.spark.config.SparkMaxConfig;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;
import frc.robot.Util.SparkUtil;
/**
 * This drive implementation is for Spark devices. It defaults to brushless control, but can be
 * easily adapted for brushed motors and external encoders. Spark Flexes can be used by swapping all
 * instances of "SparkMax" with "SparkFlex".
 */
public class DriveIOSpark implements DriveIO{  
    SparkMax leftLeader = new SparkMax(2, MotorType.kBrushless);
  SparkMax leftFollower = new SparkMax(4, MotorType.kBrushless);
  SparkMax rightLeader = new SparkMax(1, MotorType.kBrushless);
  SparkMax rightmotorrobotv = new SparkMax(3, MotorType.kBrushless);
  private final RelativeEncoder leftEncoder = leftLeader.getEncoder();
  private final RelativeEncoder rightEncoder = rightLeader.getEncoder();
  private final SparkClosedLoopController leftController = leftLeader.getClosedLoopController();
  private final SparkClosedLoopController rightController = rightLeader.getClosedLoopController();

  public DriveIOSpark() {
    // Create config
    
    SparkMaxConfig globalConfig = new SparkMaxConfig();
    SparkMaxConfig leftLeaderConfig = new SparkMaxConfig();
    SparkMaxConfig rightLeaderConfig = new SparkMaxConfig();
    SparkMaxConfig leftElevatorMotorConfig = new SparkMaxConfig();
    SparkMaxConfig rightElevatorMotorConfig = new SparkMaxConfig();
    leftLeaderConfig.apply(globalConfig).inverted(true);
    // Apply the global config and set the leader SPARK for follower mode
    leftElevatorMotorConfig.apply(globalConfig).follow(leftLeader);
    // Apply the global config and set the leader SPARK for follower mode
    rightElevatorMotorConfig.apply(rightElevatorMotorConfig).follow(rightLeader);
    leftLeader.configure(
        globalConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftFollower.configure(
        leftElevatorMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightLeader.configure(
        rightLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightmotorrobotv.configure(
        rightElevatorMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void updateInputs(DriveIOInputs inputs) {
    frc.robot.Util.SparkUtil.ifOk(leftLeader, leftEncoder::getPosition, (value) -> inputs.leftPositionRad = value);
    frc.robot.Util.SparkUtil.ifOk(leftLeader, leftEncoder::getVelocity, (value) -> inputs.leftVelocityRadPerSec = value);
    frc.robot.Util.SparkUtil.ifOk(
        leftLeader,
        new DoubleSupplier[] {leftLeader::getAppliedOutput, leftLeader::getBusVoltage},
        (values) -> inputs.leftAppliedVolts = values[0] * values[1]);
    frc.robot.Util.SparkUtil.ifOk(
        leftLeader,
        new DoubleSupplier[] {leftLeader::getOutputCurrent, leftFollower::getOutputCurrent},
        (values) -> inputs.leftCurrentAmps = values);

    frc.robot.Util.SparkUtil.ifOk(rightLeader, rightEncoder::getPosition, (value) -> inputs.rightPositionRad = value);
    frc.robot.Util.SparkUtil.ifOk(rightLeader, rightEncoder::getVelocity, (value) -> inputs.rightVelocityRadPerSec = value);
    frc.robot.Util.SparkUtil.ifOk(
        rightLeader,
        new DoubleSupplier[] {rightLeader::getAppliedOutput, rightLeader::getBusVoltage},
        (values) -> inputs.rightAppliedVolts = values[0] * values[1]);
    frc.robot.Util.SparkUtil.ifOk(
        rightLeader,
        new DoubleSupplier[] {rightLeader::getOutputCurrent, rightLeader::getOutputCurrent},
        (values) -> inputs.rightCurrentAmps = values);
  }

  @Override
  public void setVoltage(double leftVolts, double rightVolts) {
    leftLeader.setVoltage(leftVolts);
    rightLeader.setVoltage(rightVolts);
  }

  @Override
  public void setVelocity(
      double leftRadPerSec, double rightRadPerSec, double leftFFVolts, double rightFFVolts) {
    leftController.setSetpoint(
        leftRadPerSec, ControlType.kVelocity, ClosedLoopSlot.kSlot0, leftFFVolts);
    rightController.setSetpoint(
        rightRadPerSec, ControlType.kVelocity, ClosedLoopSlot.kSlot0, rightFFVolts);
  }
}