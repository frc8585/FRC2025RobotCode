package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;

public class Algae {
    public SparkMax intakeMotorLeft;
    public SparkMax intakeMotorRight;
    public SparkMax updown;
    private SparkClosedLoopController m_controller;
    public double kP, kI, kD, kMinOutput, kMaxOutput;

    public void algaeInit() {
        intakeMotorLeft = new SparkMax(51, MotorType.kBrushless);
        intakeMotorRight = new SparkMax(52, MotorType.kBrushless);
        updown = new SparkMax(53, MotorType.kBrushless);
        m_controller = updown.getClosedLoopController();
        m_controller.setReference(1, ControlType.kPosition);

        SparkMaxConfig config = new SparkMaxConfig();
        config
            .smartCurrentLimit(50)
            .idleMode(IdleMode.kBrake);
        config.closedLoop
            .p(kP = 0.3)
            .i(kI = 0)
            .d(kD = 0)
            .outputRange(kMinOutput, kMaxOutput);

        updown.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void algaeIntake(boolean in, boolean out, boolean position) {
        if (in) {
            intakeMotorLeft.set(1);
            intakeMotorRight.set(-1);
        } else if (out) {
            intakeMotorLeft.set(-1);
            intakeMotorRight.set(1);
        } else {
            intakeMotorLeft.set(0);
            intakeMotorRight.set(0);
        }

        if (position == true){
            m_controller.setReference(0, ControlType.kPosition);
        }else if (position == false){
            m_controller.setReference(0, ControlType.kPosition);
        }
        }
    }
