package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.Joystick;

public class Algae {
    // 定義兩個馬達用於吸入與吐出
    private SparkMax intakeMotorLeft;
    private SparkMax intakeMotorRight;

    // 定義 Joystick 控制器
    private Joystick joystick;

    public Algae(Joystick joystick) {
        // 初始化馬達
        intakeMotorLeft = new SparkMax(7, MotorType.kBrushless);
        intakeMotorRight = new SparkMax(8, MotorType.kBrushless);

        this.joystick = joystick;
    }

    public void controlIntake() {
        if (joystick.getRawButton(1)) { // 按鈕 1 吸入
            intakeMotorLeft.set(0.5);
            intakeMotorRight.set(0.5);
            
        } else if (joystick.getRawButton(2)) { // 按鈕 2 吐出
            intakeMotorLeft.set(-0.5);
            intakeMotorRight.set(-0.5);
            
        } else { // 停止馬達
            intakeMotorLeft.set(0);
            intakeMotorRight.set(0);
            
        }
    }
}