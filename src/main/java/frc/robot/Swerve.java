package frc.robot;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;

import edu.wpi.first.units.measure.Velocity;

public class Swerve {
    // Rotor IDs
    public static final int kLeftFrontRotorID = 10;
    public static final int kRightFrontRotorID = 12;
    public static final int kLeftRearRotorID = 16;
    public static final int kRightRearRotorID = 14;
    
    // Throttle IDs
    public static final int kLeftFrontThrottleID = 11;
    public static final int kRightFrontThrottleID = 13;
    public static final int kLeftRearThrottleID = 17;
    public static final int kRightRearThrottleID = 15;

    // Rotor Encoder IDs
    public static final int kLeftFrontRotorEncoderID = 18;
    public static final int kRightFrontRotorEncoderID = 19;
    public static final int kLeftRearRotorEncoderID = 21;
    public static final int kRightRearRotorEncoderID = 20;
    
    // other constants
    public static final double kMaxVelocityMetersPerSecond = 0.0; // 最大速度（米）
    public static final double kMaxAccelerationMetersPerSecond = 0.0; //最大加速度（米/s）
    
    public static final double kWheelDiameterMeters = 0.0; //輪徑(m)

    public static final double kThrottleGearRatio = 6.0;


    /*
        1/2048 is used to convert from Falcon encoder ticks to rotations
        * 10 is used to convert from rotations per 100ms to rotations per second
    */ 
    // Position Constant
    public static final double kThrottlePositionConversionFactor =
        1 / kThrottleGearRatio / 2048 * kWheelDiameterMeters * Math.PI;
    // Velocity Constant
    public static final double kThrottleVelocityConversionFactor =
        1 / kThrottleGearRatio /2048 * kWheelDiameterMeters * Math.PI * 10;

    
    // Rotor FX
    private static final TalonFX m_leftFrontRotor = new TalonFX(kLeftFrontRotorID);
    private static final TalonFX m_rightFrontRotor = new TalonFX(kRightFrontRotorID);
    private static final TalonFX m_leftRearRotor = new TalonFX(kLeftRearRotorID);
    private static final TalonFX m_rightRearRotor = new TalonFX(kRightRearRotorID);

    // Throttle FX
    private static final TalonFX m_leftFrontThrottle = new TalonFX(kLeftFrontThrottleID);
    private static final TalonFX m_rightFrontThrottle = new TalonFX(kRightFrontThrottleID);
    private static final TalonFX m_leftRearThrottle = new TalonFX(kLeftRearThrottleID);
    private static final TalonFX m_rightRearThrottle = new TalonFX(kRightRearThrottleID);

    // Rotor Can coder
    private static final CANcoder m_leftFrontRotorEncoder = new CANcoder(kLeftFrontRotorEncoderID);
    private static final CANcoder m_rightFrontRotorEncoder = new CANcoder(kRightFrontRotorEncoderID);
    private static final CANcoder m_leftRearRotorEncoder = new CANcoder(kLeftRearRotorEncoderID);
    private static final CANcoder m_rightRearRotorEncoder = new CANcoder(kRightRearRotorEncoderID);


}
