// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the manifest file in the resource directory.
 */
public class Robot extends TimedRobot {
  private final PWMSparkMax m_leftDrive = new PWMSparkMax(0);
  private final PWMSparkMax m_rightDrive = new PWMSparkMax(1);
  private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(m_leftDrive::set, m_rightDrive::set);
  private final XboxController m_controller = new XboxController(0);
  private final Timer m_timer = new Timer();
  private SparkMax elevatorMotor = new SparkMax(2, MotorType.kBrushless);
  private PIDController pidController;
  private RelativeEncoder elevatorEncoder;
  private double targetHeight = 0;
  private Joystick joystick = new Joystick(0);
  private final double drumRadius = 0.05;
  private final int pulleyMultiplier = 1;

  /** Called once at the beginning of the robot program. */
  public Robot() {
    SendableRegistry.addChild(m_robotDrive, m_leftDrive);
    SendableRegistry.addChild(m_robotDrive, m_rightDrive);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightDrive.setInverted(true);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void robotInit() {
    elevatorEncoder = elevatorMotor.getEncoder();
    elevatorEncoder.setPosition(0);
    pidController = new PIDController(0.1, 0, 0);
    pidController.setTolerance(0.03);
  }
  
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
        // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

 /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
    targetHeight = getCurrentHeight();
    pidController.setSetpoint(targetHeight);
    pidController.reset();
  }

    /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(-m_controller.getLeftY(), -m_controller.getRightX());

    double joystickInput = joystick.getRawAxis(5);
    if (Math.abs(joystickInput) > 0.05) {
      targetHeight += joystickInput * 0.1;
      targetHeight = Math.max(0, Math.min(targetHeight, 1.9));
      pidController.setSetpoint(targetHeight);
    }

    if (joystick.getRawButtonPressed(1)) 
      setElevatorHeight(0.46);    
    if (joystick.getRawButtonPressed(2)) 
      setElevatorHeight(0.81);
    if (joystick.getRawButtonPressed(3)) 
      setElevatorHeight(1.21);
    if (joystick.getRawButtonPressed(4)) 
      setElevatorHeight(1.83);
    if (joystick.getRawButtonPressed(5)) 
      setElevatorHeight(0.95);
      
    double currentHeight = getCurrentHeight();
    double pidOutput = pidController.calculate(currentHeight);
    pidOutput = Math.max(-1.0, Math.min(1.0, pidOutput));
    elevatorMotor.set(pidOutput);
  }

  /** This function is called once each time the robot enters test mode. */
  private double getCurrentHeight() {
    double rotations = elevatorEncoder.getPosition();
    return (rotations * 2 * Math.PI * drumRadius) / pulleyMultiplier;
  }

  private void setElevatorHeight(double height) {
    targetHeight = height;
    pidController.setSetpoint(targetHeight);
  }

  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
