// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Swerve;

import com.revrobotics.spark.SparkMax;
import com.studica.frc.AHRS;
import com.revrobotics.spark.SparkLowLevel.MotorType;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private final AHRS mImu = new AHRS(AHRS.NavXComType.kMXP_SPI);
    private Command m_autonomousCommand;
    private final Swerve aSwerve = new Swerve();
    public double fieldOffset = 0.0;
    public SparkMax intakeMotorLeft;
    public SparkMax intakeMotorRight;
    public SparkMax updown;

    public SparkMax climber;

    public boolean RF;

    public Joystick joystickE = new Joystick(0);
    public Joystick joystick = new Joystick(1);
    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        intakeMotorLeft = new SparkMax(51, MotorType.kBrushless);
        intakeMotorRight = new SparkMax(52, MotorType.kBrushless);
        updown = new SparkMax(50, MotorType.kBrushless);

        RF = false;

        climber = new SparkMax(30, MotorType.kBrushless);
    }

    /**
     * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }

    /** This function is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
    @Override
    public void autonomousInit() {
        
        // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        // // schedule the autonomous command (example)
        //  if (m_autonomousCommand != null) {
        //      m_autonomousCommand.schedule();
        // }
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        System.out.println(Timer.getMatchTime());
        if (Timer.getMatchTime() >= 10){
            aSwerve.drive(0.6, 0, 0, RF);
        }
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        aSwerve.drive(-joystickE.getRawAxis(1)*0.5, -joystickE.getRawAxis(0)*0.5, -joystickE.getRawAxis(4)*0.8, RF);

        if (joystick.getRawButtonPressed(6)) {
            fieldOffset = mImu.getYaw();
            System.out.println("Field orientation reset. New offset: " + fieldOffset);
        }

        if (joystick.getRawButton(1)) { // 按鈕 1 吸入
            intakeMotorLeft.set(1);
            intakeMotorRight.set(-1);
            
        } else if (joystick.getRawButton(2)) { // 按鈕 2 吐出
            intakeMotorLeft.set(-1);
            intakeMotorRight.set(1);
            
        } else { // 停止馬達
            intakeMotorLeft.set(0);
            intakeMotorRight.set(0);
            
        }

        if (joystick.getRawButton(3)) { // 按鈕 1 吸入
            climber.set(1);
            climber.set(-1);
            
        } else if (joystick.getRawButton(4)) { // 按鈕 2 吐出
            climber.set(-1);
            climber.set(1);
            
        } else { // 停止馬達
            climber.set(0);
            climber.set(0);
            
        }

        if (joystick.getRawButton(5)) {
            updown.set(joystick.getRawAxis(1)*0.3);
        }else{
            updown.stopMotor();
        }
        System.out.println(joystick.getRawAxis(2));
    }


    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();


    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {

    }

    /** This function is called once when the robot is first started up. */
    @Override
    public void simulationInit() {}

    /** This function is called periodically whilst in simulation. */
    @Override
    public void simulationPeriodic() {}
}
