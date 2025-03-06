package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Climber {
    public SparkMax climber;

    public void climberInit() {
        climber = new SparkMax(30, MotorType.kBrushless);
    }

    public void climb(boolean down) {
        if (down) {
            climber.set(-1);
        } else {
            climber.set(0);
        }
    }
}
