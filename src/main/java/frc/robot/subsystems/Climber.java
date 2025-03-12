package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Climber {
    public SparkMax climber;
    public SparkMax finshing;

    public void climberInit() {
        climber = new SparkMax(30, MotorType.kBrushless);
        finshing = new SparkMax(40, MotorType.kBrushless);
    }

    public void climb(boolean down) {
        if (down) {
            climber.set(-0.6);
        } else {
            climber.set(0);
        }
    }
}
