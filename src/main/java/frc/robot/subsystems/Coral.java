package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;

public class Coral {
    private SparkMax updown;
    private SparkMax intake;
    private SparkClosedLoopController m_controller;
    public double kP, kI, kD, kMinOutput, kMaxOutput;

    public void coralInit() {
        updown = new SparkMax(61, MotorType.kBrushless);
        intake = new SparkMax(60, MotorType.kBrushless);
        m_controller = updown.getClosedLoopController();
        m_controller.setReference(0, ControlType.kPosition);

        SparkMaxConfig config = new SparkMaxConfig();
        config
            .smartCurrentLimit(50)
            .idleMode(IdleMode.kBrake);
        config.closedLoop
            .p(kP = 0.1)
            .i(kI = 0)
            .d(kD = 0)
            .outputRange(kMinOutput = -0.3, kMaxOutput = 0.3);

        updown.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void coralControl(Boolean in, boolean out, Boolean position) {
        if (position == true){
            m_controller.setReference(-7.5, ControlType.kPosition);
        }else if (position == false){
            m_controller.setReference(0, ControlType.kPosition);
        }
        
        if (in) {
            intake.set(1);
        } else if (out) {
            intake.set(-1);
        } else {
            intake.set(0);
        }
    }
}
