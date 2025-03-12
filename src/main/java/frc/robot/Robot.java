package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import static edu.wpi.first.units.Units.Degree;

import com.studica.frc.AHRS;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.util.PixelFormat;
import frc.robot.Constants.SwerveConstants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Algae;
import frc.robot.subsystems.Coral;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;
    private RobotContainer m_robotContainer;
    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto1 = "A1";
    private static final String kCustomAuto2 = "A2";
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();
    private UsbCamera cam1;
    private UsbCamera cam2;
    private VideoSink camServer;

    public Joystick joystickE = new Joystick(0);
    public final static Joystick joystick = new Joystick(1);
    private final AHRS mImu = new AHRS(AHRS.NavXComType.kMXP_SPI);

    private final Swerve aSwerve = new Swerve();
    private final Climber climber = new Climber();
    private final Algae algae = new Algae();
    private final Coral coral = new Coral();

    public boolean RF;
    public int sAngle;

    @Override
    public void robotInit() {
        cam1 = CameraServer.startAutomaticCapture(0);
        cam2 = CameraServer.startAutomaticCapture(1);
        camServer = CameraServer.getServer();
        cam1.setVideoMode(PixelFormat.kMJPEG, 160, 120, 1);
        cam2.setVideoMode(PixelFormat.kMJPEG, 160, 120, 1);
        cam1.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        cam2.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        climber.climberInit();
        algae.algaeInit();
        coral.coralInit();


        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("Auto1", kCustomAuto1);
        m_chooser.addOption("Auto2", kCustomAuto2);
        SmartDashboard.putData("Auto choices", m_chooser);
          
        RF = true;
        sAngle = (int)mImu.getAngle();
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
        // m_autoSelected = m_chooser.getSelected();
        // System.out.println("Auto selected: " + m_autoSelected);
        // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        // //  schedule the autonomous command (example)
        //  if (m_autonomousCommand != null) {
        //      m_autonomousCommand.schedule();
        // }
    }

    @Override
    public void autonomousPeriodic() {
        System.out.println(Timer.getMatchTime());
        switch (m_autoSelected) {
            case kCustomAuto1:
                if (Timer.getMatchTime() >= 12){
                    aSwerve.drive(-0.1, 0, 0, RF);
                } else if (Timer.getMatchTime() >= 11){
                    aSwerve.drive(0, 0, 0, RF);
                }
                break;
            case kCustomAuto2:
                if (Timer.getMatchTime() >= 7){
                    aSwerve.drive(-0.1, 0, 0, RF);
                } else if (Timer.getMatchTime() >= 6){
                    aSwerve.drive(0, 0, 0, RF);
                }
                break;
            case kDefaultAuto:

            default:
            
                break;
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
            (Math.abs(joystickE.getRawAxis(2))>=Constants.kDeadzone)? joystickE.getRawAxis(2)*0.8:0, 
            RF);

        if (joystickE.getRawButtonPressed(1)) {
            aSwerve.zeroYaw();
        }
        if (joystickE.getRawButton(2)){
            System.out.println("cam1");
            camServer.setSource(cam1);
        }else if (joystickE.getRawButton(1)){
            System.out.println("cam2");
            camServer.setSource(cam2);
        }
        climber.climb(joystick.getRawButton(9));
        algae.algaeIntake(joystick.getRawButton(1), joystick.getRawButton(2), joystick.getRawButton(5), joystick.getRawButton(6), joystick.getRawAxis(2));
        coral.coralControl(joystick.getRawButton(3), joystick.getRawButton(4), joystick.getRawButton(6));
    }
}
