package frc.robot;

import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
public class Launcher extends SubsystemBase {

    PneumaticsModuleType moduleType=PneumaticsModuleType.REVPH;;
    Solenoid solenoid1= new Solenoid( moduleType,  6);
    Solenoid solenoid2= new Solenoid( moduleType,  7);
    Solenoid solenoid3= new Solenoid( moduleType,  8);
    Solenoid solenoid4= new Solenoid( moduleType,  9);
    Solenoid solenoid5= new Solenoid( moduleType,  10);
    private final PneumaticHub hub = new PneumaticHub(1);
    public Launcher(){

        }
}

