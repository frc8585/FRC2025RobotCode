// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the manifest file in the resource directory.
 */
public class Robot extends TimedRobot {
  private final SparkMax m_leftDrive = new SparkMax(0, MotorType.kBrushed);
  private final SparkMax m_rightDrive = new SparkMax(1, MotorType.kBrushed);
  private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(m_leftDrive, m_rightDrive);
  private final XboxController m_controller = new XboxController(0);
  private final Timer m_timer = new Timer();
  // Define two arm motors
  private SparkMax armMotorLeft;
  private SparkMax armMotorRight;

  
  private RelativeEncoder encoderLeft;
  private RelativeEncoder encoderRight;



  // Target position
  private double targetPosition = 0.0;
  // Manual mode switch
  private boolean manualMode = false;

  /** Called once at the beginning of the robot program. */
  public Robot() {
    SendableRegistry.addChild(m_robotDrive, m_leftDrive);
    SendableRegistry.addChild(m_robotDrive, m_rightDrive);

    // Invert the right side of the drivetrain so that positive voltages
    // result in both sides moving forward.
    m_rightDrive.setInverted(true);
  }

  @Override
  public void robotInit() {
      // Initialize two arm motors
      armMotorLeft = new SparkMax(5, MotorType.kBrushless);
      armMotorRight = new SparkMax(6, MotorType.kBrushless);


      encoderLeft = armMotorLeft.getEncoder();
      encoderRight = armMotorRight.getEncoder();
    
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // Stop robot
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(-m_controller.getLeftY(), -m_controller.getRightX());
    // **Press X button to toggle manual/automatic mode**
    if (m_controller.getXButtonPressed()) {
      manualMode = !manualMode;
    }

    if (manualMode) {
      // **Manual mode**
      double manualPower = m_controller.getLeftY() * 0.5;
      armMotorLeft.set(-manualPower);
      armMotorRight.set(-manualPower);
    } else {
      // **PID automatic mode**
      if (m_controller.getAButton()) {
        targetPosition = 50;
      } else if (m_controller.getBButton()) {
        targetPosition = 0;
      }

      // Limit the angle range
      targetPosition = Math.max(0, Math.min(50, targetPosition));

      // Calculate the error to synchronize both motors
      double positionLeft = encoderLeft.getPosition();
      double positionRight = encoderRight.getPosition();
      double positionError = positionLeft - positionRight;
    }
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
