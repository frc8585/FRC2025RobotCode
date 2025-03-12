package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.SwerveConstants;
import frc.robot.subsystems.Swerve;

public class ManualDrive extends Command {
    private final Swerve mSwerve;
    private final XboxController mController;
    
    public ManualDrive(Swerve drive, XboxController controller) {
        mSwerve = drive;
        mController = controller;
        
        // Adds the Swerve subsystem as a requirement to the command
        // 加入 swerve 為這條命令的必要條件
        addRequirements(mSwerve);
    }

    @Override
    public void execute() {
        // mSwerve.drive(
        //     (Math.abs(mController.getRawAxis(1))>=Constants.kDeadzone)?-mController.getRawAxis(1)*((mController.getRawButton(5))?SwerveConstants.kGearTwo:SwerveConstants.kGearOne):0, 
        //     (Math.abs(mController.getRawAxis(0))>=Constants.kDeadzone)?-mController.getRawAxis(0)*((mController.getRawButton(5))?SwerveConstants.kGearTwo:SwerveConstants.kGearOne):0, 
        //     (Math.abs(mController.getRawAxis(2))>=Constants.kDeadzone)? mController.getRawAxis(2)*0.8:0, 
        //     false);
    }
}