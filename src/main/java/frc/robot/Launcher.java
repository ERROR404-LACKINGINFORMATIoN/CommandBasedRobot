package frc.robot;

import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
public class Launcher extends SubsystemBase {
    int index=1;
    PneumaticsModuleType moduleType=PneumaticsModuleType.REVPH;;
    Solenoid solenoid1= new Solenoid( moduleType,  0);
    Solenoid solenoid2= new Solenoid( moduleType,  1);
    Solenoid solenoid3= new Solenoid( moduleType,  2);
    Solenoid solenoid4= new Solenoid( moduleType,  3);
    Solenoid solenoid5= new Solenoid( moduleType,  4);
    public final PneumaticHub hub = new PneumaticHub(1);
    Solenoid currentSolenoid=solenoid1;
    public Launcher(){

        }
        //buttons don't matter
    public void Switch(){
        if (index==1){
            currentSolenoid=solenoid2;
            index=index+1;
        }if (index==2){
            currentSolenoid=solenoid3;
            index=index+1;
        } if (index==3){
            currentSolenoid=solenoid4;
        } else{
            index=0;
            currentSolenoid=solenoid1;
        }
    }
    public void StopRobot(){
        Commands.run(()->currentSolenoid.set(false));
        //Commands.run(()->currentSolenoid.set(false))
    }
    public void Start(){
        Commands.run(()->currentSolenoid.set(true));
    }
    }

