package frc.robot;


import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.ArrayList;
import edu.wpi.first.wpilibj.Compressor;
public class Launcher extends SubsystemBase {
  PneumaticsModuleType moduleType = PneumaticsModuleType.REVPH;
  public final PneumaticHub hub = new PneumaticHub(1);
  public final Solenoid solenoid1 = hub.makeSolenoid(0);
  public final Solenoid solenoid2 = hub.makeSolenoid(1);
  public final Solenoid solenoid3 = hub.makeSolenoid(2);
  public final Solenoid solenoid4 = hub.makeSolenoid(3);
  public final Solenoid solenoid5 = hub.makeSolenoid(4);
  public final Compressor phCompressor=hub.makeCompressor();
  Solenoid currentSolenoid = solenoid1;
  ArrayList<Solenoid> solenoidList = new ArrayList<Solenoid>(5);


  public Launcher() {
    solenoidList.add(solenoid1);
    solenoidList.add(solenoid2);
    solenoidList.add(solenoid3);
    solenoidList.add(solenoid4);
    solenoidList.add(solenoid5);
  }


  // buttons don't matter
  public void Switch() {
    currentSolenoid = solenoidList.get((solenoidList.indexOf(currentSolenoid) + 1) % 5);
  }


  public Command shootSequence() {
    return Commands.sequence(
        Commands.runOnce(() -> currentSolenoid.set(true)), // push tshirt
        Commands.waitSeconds(0.05), // Wait 1.5s for spin-up
        Commands.runOnce(() -> currentSolenoid.set(false)) // close pressure
        );
  }
}





