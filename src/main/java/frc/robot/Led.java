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

public class Led {

  AddressableLED m_led = new AddressableLED(0);

  AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(94);

  private final LEDPattern m_rainbow = LEDPattern.rainbow(255, 128);

  // Our LED strip has a density of 120 LEDs per meter
  private static final Distance kLedSpacing = Meters.of(1 / 6.0);

  // Create a new pattern that scrolls the rainbow pattern across the LED strip, moving at a speed
  // of 1 meter per second.
  private final LEDPattern m_scrollingRainbow =
      m_rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(3), kLedSpacing);
  LEDPattern gradient =
      LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kRed, Color.kBlue);

  public Command runPattern() {
    return Commands.sequence(
        Commands.runOnce(() -> m_led.setLength(m_ledBuffer.getLength())),
        Commands.runOnce(() -> m_scrollingRainbow.applyTo(m_ledBuffer)), // push tshirt
        Commands.runOnce(() -> m_led.setData(m_ledBuffer)),
        Commands.runOnce(() -> m_led.start()));
  }

  public Command runGradient() {
    return Commands.sequence(
        Commands.runOnce(() -> m_led.setLength(m_ledBuffer.getLength())),
        Commands.runOnce(() -> gradient.applyTo(m_ledBuffer)), // push tshirt
        Commands.runOnce(() -> m_led.setData(m_ledBuffer)),
        Commands.runOnce(() -> m_led.start()));
  }
}
