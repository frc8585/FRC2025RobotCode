package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Robot;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;

public class Algae {
    public SparkMax intakeMotorLeft;
    public SparkMax intakeMotorRight;
    public SparkMax updown;
    public double updownP;
    private SparkClosedLoopController m_controller;
    public double kP, kI, kD, kMinOutput, kMaxOutput;

    public void algaeInit() {
        intakeMotorLeft = new SparkMax(51, MotorType.kBrushless);
        intakeMotorRight = new SparkMax(52, MotorType.kBrushless);
        updown = new SparkMax(50, MotorType.kBrushless);
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
        
        updownP = -2.0;
    }

    public void algaeIntake(boolean in, boolean out, boolean positionUP, boolean positionDOWN, double back) {
        if (in) {
            intakeMotorLeft.set(1);
            intakeMotorRight.set(-1);
        } else if (out) {
            intakeMotorLeft.set(-1);
            intakeMotorRight.set(1);
        } else if (back > 0.01) {
            m_controller.setReference(1, ControlType.kPosition);
        } else {
            intakeMotorLeft.set(0);
            intakeMotorRight.set(0);
        }

        // if (position == true){
        //     m_controller.setReference(-0.67, ControlType.kPosition);
        // }else if (position == false && Robot.joystick.getRawAxis(2) == 0){
        //     m_controller.setReference(-2.0, ControlType.kPosition);
        // }

        if (positionUP) {
            updownP+=0.2;
        }else if (positionDOWN){
            updownP-=0.2;
        }
        m_controller.setReference(updownP, ControlType.kPosition);
        }
    }
