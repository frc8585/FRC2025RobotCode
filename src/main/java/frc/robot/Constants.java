// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;


public  class Constants {

    // Swerve constants
    public static  class SwerveConstants {

        // Rotor IDs
        public static  int kLeftFrontRotorID = 10;
        public static  int kRightFrontRotorID = 12;
        public static  int kLeftRearRotorID = 16;
        public static  int kRightRearRotorID = 14;

        // Throttle IDs
        public static  int kLeftFrontThrottleID = 11;
        public static  int kRightFrontThrottleID = 13;
        public static  int kLeftRearThrottleID = 17;
        public static  int kRightRearThrottleID = 15;

        // Rotor encoder IDs
        public static  int kLeftFrontCANCoderID = 18;
        public static  int kRightFrontCANCoderID = 19;
        public static  int kLeftRearCANCoderID = 21;
        public static  int kRightRearCANCoderID = 20;

        // Rotor encoder & motor inversion
        public static  SensorDirectionValue kRotorEncoderDirection = SensorDirectionValue.CounterClockwise_Positive;
        public static  InvertedValue kRotorMotorInversion = InvertedValue.CounterClockwise_Positive;

        // Rotor encoder offsets
        // Rotor encoder 偏移量
        public static  double kLeftFrontRotorOffset = -0.39208984375;
        public static  double kRightFrontRotorOffset = -0.311279296875;
        public static  double kLeftRearRotorOffset =  0.26611328125*2;
        public static  double kRightRearRotorOffset = 0.459228515625;


        // Swerve kinematics (order: left front, right front, left rear, right rear)
        // Swerve kinematics（順序：左前，右前，左後，右後）
        public static  SwerveDriveKinematics kSwerveKinematics = new SwerveDriveKinematics(
            new Translation2d(0.34605, 0.2762), 
            new Translation2d(0.34605, -0.2762), 
            new Translation2d(-0.34605, 0.2762),
            new Translation2d(-0.34605, -0.2762)
        );

        // Rotor PID constants
        public static  double kRotor_kP = 5.0;
        public static  double kRotor_kI = 3.0;
        public static  double kRotor_kD = 0.13;

        // Velocity & acceleration of swerve
        // Swerve 最大速度 / 加速度
        public static  double kMaxVelocityMetersPerSecond = 4;
        public static  double kMaxAccelerationMetersPerSecond = 0;

        // Wheel diameter
        // 輪徑
        public static  double kWheelDiameterMeters = 99.5; // wheel diameter
        
        // Throttle gear ratio
        // (number of turns it takes the motor to rotate the rotor one revolution)
        // Throttle 齒輪比率
        // （馬達轉動輪子一圈所需的圈數）
        public static  double kThrottleGearRatio = 6.0; 

        // Throttle velocity conversion constant
        // Throttle 速度轉換 Constant
        // This value will be multiplied to the raw encoder velocity of the throttle motor
        // and should convert it to meters per second
        // This is the general formula: 
        //     (1.0 / GEAR RATIO) * WHEEL DIAMETER * Math.PI;
        public static  double kThrottleVelocityConversionFactor = 
            (1/kThrottleGearRatio)/2048*kWheelDiameterMeters*Math.PI;

        public static  double kThrottlePositionConversionFactor = 
            (1/kThrottleGearRatio)/2048*kWheelDiameterMeters*Math.PI;
        
        // Pathing PID constants 
        public static double kPathingX_kP = 5.0;
        public static double kPathingX_kI = 3.0;
        public static double kPathingX_kD = 0.13;

        public static double kPathingY_kP = 5.0;
        public static double kPathingY_kI = 3.0;
        public static double kPathingY_kD = 0.13;

        public static double kPathingTheta_kP = 5.0;
        public static double kPathingTheta_kI = 3.0;
        public static double kPathingTheta_kD = 0.13;
    }
    // Voltage compensation
    public static  double kVoltageCompensation = 12.0;
    
    // Controller port
    public static  int kControllerPort = 0;

    public static  double kLongTimeoutMs = 100.0;
}
 