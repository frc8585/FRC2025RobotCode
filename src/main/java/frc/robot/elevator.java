package frc.robot;

import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class elevator extends TimedRobot {
    //private CANSparkMax elevatorMotor = new CANSparkMax(2, MotorType.kBrushless);
    private PIDController pidController;
    private RelativeEncoder elevatorEncoder;
    private Joystick joystick = new Joystick(0); 
    private double targetHeight = 0;
    private final double drumRadius = 0.05; // 捲筒半徑（米）
    private final int pulleyMultiplier = 2; // 動滑輪倍率

    @Override
    public void robotInit() {
        elevatorMotor.restoreFactoryDefaults();
        elevatorEncoder = elevatorMotor.getEncoder();
        elevatorEncoder.setPosition(0);
        pidController = new PIDController(0.1, 0, 0);
        pidController.setTolerance(0.03); 
    }

    @Override
    public void teleopInit() {
        targetHeight = getCurrentHeight();
        pidController.setSetpoint(targetHeight);
        pidController.reset();
    }

    @Override
    public void teleopPeriodic() {
        //double joystickInput = joystick.getRawAxis(5);
        /*if (Math.abs(joystickInput) > 0.05) { 
            targetHeight += joystickInput * 0.1; 
            targetHeight = Math.max(0, Math.min(targetHeight, 1.9)); 
            pidController.setSetpoint(targetHeight);
        }*/

        //if (joystick.getRawButtonPressed(1)) targetHeight = 0.46;
        //if (joystick.getRawButtonPressed(2)) targetHeight = 0.81;
        //if (joystick.getRawButtonPressed(3)) targetHeight = 1.21;
        //if (joystick.getRawButtonPressed(4)) targetHeight = 1.83;
        pidController.setSetpoint(targetHeight);

        double currentHeight = getCurrentHeight();
        double pidOutput = pidController.calculate(currentHeight, targetHeight);
        pidOutput = Math.max(-1.0, Math.min(1.0, pidOutput)); 
        elevatorMotor.set(pidOutput);  
    }

    private double getCurrentHeight() {
        double rotations = elevatorEncoder.getPosition(); // 轉換旋轉圈數
        return (rotations * 2 * Math.PI * drumRadius) / pulleyMultiplier; // 轉換為實際高度
    }
}