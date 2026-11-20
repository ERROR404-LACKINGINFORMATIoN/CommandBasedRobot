package frc.robot;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Led extends SubsystemBase {

  AddressableLED m_led = new AddressableLED(0);

  AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(94);

  private final LEDPattern m_rainbow = LEDPattern.rainbow(255, 128);

  // Our LED strip has a density of 120 LEDs per meter
  private static final Distance kLedSpacing = Meters.of(1 / 2.0);

  // Create a new pattern that scrolls the rainbow pattern across the LED strip, moving at a speed
  // of 1 meter per second.
  private final LEDPattern m_scrollingRainbow =
      m_rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(23), kLedSpacing);
  LEDPattern gradient =
      LEDPattern.gradient(
          LEDPattern.GradientType.kDiscontinuous,
          Color.kFirstRed,
          Color.kRed,
          Color.kDarkRed,
          Color.kChartreuse);
  // of 1 meter per second.
  private final LEDPattern m_scrollingGradiant =
      gradient.scrollAtAbsoluteSpeed(MetersPerSecond.of(23), kLedSpacing);

  public Command runPattern() {
    return Commands.run(
            () -> {
              m_scrollingRainbow.applyTo(m_ledBuffer);
              m_led.setData(m_ledBuffer);
            })
        .beforeStarting(
            () -> {
              m_led.setLength(m_ledBuffer.getLength());
              m_led.start();
            })
        .withTimeout(5);
  }

  public Command runScrollGradiant() {
    return Commands.run(
            () -> {
              m_scrollingGradiant.applyTo(m_ledBuffer);
              m_led.setData(m_ledBuffer);
            })
        .beforeStarting(
            () -> {
              m_led.setLength(m_ledBuffer.getLength());
              m_led.start();
            })
        .withTimeout(5);
  }

  public Command runGradient() {
    return Commands.sequence(
        Commands.runOnce(() -> m_led.setLength(m_ledBuffer.getLength())),
        Commands.runOnce(() -> gradient.applyTo(m_ledBuffer)), // push tshirt
        Commands.runOnce(() -> m_led.setData(m_ledBuffer)),
        Commands.runOnce(() -> m_led.start()));
  }
}
