package frc.robot.subsystems.Secondary;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ProtoMotoB extends SubsystemBase {

    public CANSparkMax protoMotoB;

    public ProtoMotoB() {
        protoMotoB =  new CANSparkMax(16, MotorType.kBrushless);
    }
    
    public Command ProtoMotoBCmd(double ProtoMotoBSetpoint) {
        // implicitly require `this`
        return this.runOnce(() -> protoMotoB.set(ProtoMotoBSetpoint));
    }

}