package frc.robot;

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
}
