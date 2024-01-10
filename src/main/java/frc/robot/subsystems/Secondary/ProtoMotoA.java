package frc.robot.subsystems.Secondary;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ProtoMotoA extends SubsystemBase {

    public CANSparkMax protoMotoA;

    public ProtoMotoA() {
        protoMotoA =  new CANSparkMax(15, MotorType.kBrushless);
    }
    
    public Command ProtoMotoACmd(double ProtoMotoASetpoint) {
        // implicitly require `this`
        return this.runOnce(() -> protoMotoA.set(ProtoMotoASetpoint));
    }

}