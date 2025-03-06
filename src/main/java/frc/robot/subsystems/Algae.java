package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Algae {
    public SparkMax intakeMotorLeft;
    public SparkMax intakeMotorRight;
    public SparkMax updown;

    public void algaeInit() {
        intakeMotorLeft = new SparkMax(51, MotorType.kBrushless);
        intakeMotorRight = new SparkMax(52, MotorType.kBrushless);
        updown = new SparkMax(50, MotorType.kBrushless);
    }

    public void algaeIntake(boolean in, boolean out, double updownSpeed) {
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

        if (updownSpeed > Math.abs(0.02)) {
            updown.set(updownSpeed*0.3);
        }else{
            updown.stopMotor();
        }
    }
}
