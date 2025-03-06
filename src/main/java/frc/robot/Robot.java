package frc.robot;

import com.studica.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.Constants.SwerveConstants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Algae;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;
    public Joystick joystickE = new Joystick(0);
    public Joystick joystick = new Joystick(1);
    private final Swerve aSwerve = new Swerve();
    private final Climber climber = new Climber();
    private final Algae algae = new Algae();

    public boolean RF;

    @Override
    public void robotInit() {
        climber.climberInit();
        algae.algaeInit();

        RF = true;
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        aSwerve.zeroYaw();
        // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        // // schedule the autonomous command (example)
        //  if (m_autonomousCommand != null) {
        //      m_autonomousCommand.schedule();
        // }
    }

    @Override
    public void autonomousPeriodic() {
        System.out.println(Timer.getMatchTime());
        if (Timer.getMatchTime() >= 10){
            aSwerve.drive(0.6, 0, 0, RF);
        }
    }

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
        aSwerve.drive(
            (Math.abs(joystickE.getRawAxis(1))>=Constants.kDeadzone)?-joystickE.getRawAxis(1)*((joystickE.getRawButton(5))?SwerveConstants.kGearTwo:SwerveConstants.kGearOne):0, 
            (Math.abs(joystickE.getRawAxis(0))>=Constants.kDeadzone)?-joystickE.getRawAxis(0)*((joystickE.getRawButton(5))?SwerveConstants.kGearTwo:SwerveConstants.kGearOne):0, 
            (Math.abs(joystickE.getRawAxis(4))>=Constants.kDeadzone)? joystickE.getRawAxis(4)*0.8:0, 
            RF);

        if (joystickE.getRawButtonPressed(6)) {
            aSwerve.zeroYaw();
        }
        climber.climb(joystick.getRawButton(6));
        algae.algaeIntake(joystick.getRawButton(1), joystick.getRawButton(2), joystick.getRawAxis(1));
        
    }
}
