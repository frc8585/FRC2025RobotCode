package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.controller.PIDController;

public class elevator {
    private final SparkMax motor;
    private final RelativeEncoder encoder;
    private final PIDController pidController;
    private double targetHeight = 0.0;
    private final double drumRadius = 0.05; 
    private final int pulleyMultiplier = 1; 

    public elevator(int motorID) {
        motor = new SparkMax(motorID, MotorType.kBrushless);
        encoder = motor.getEncoder();
        pidController = new PIDController(0.1, 0.0, 0.0);
        pidController.setTolerance(0.025); 
        encoder.setPosition(0); 
    }

    public void setHeight(double height) {
        targetHeight = height;
        pidController.setSetpoint(height);
    }

    public double getTargetHeight() {
        return targetHeight;
    }

    public void update() {
        double currentHeight = getCurrentHeight();
        double output = pidController.calculate(currentHeight);
        output = Math.max(-1.0, Math.min(1.0, output)); 
        motor.set(output);
    }

    public double getCurrentHeight() {
        double rotations = encoder.getPosition();
        return (rotations * 2 * Math.PI * drumRadius) / pulleyMultiplier;
    }
}
