package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.SwerveModule;

public class Drive extends SubsystemBase{
    SwerveModule m_frontRightModule;
    SwerveModule m_frontLeftModule;
    SwerveModule m_backRightModule;
    SwerveModule m_backLeftModule;
    SwerveDriveKinematics m_driveKinematics;

    public Drive() {

        m_frontRightModule = new SwerveModule(
            null, 
            null, 
            0, 
            0, 
            0, 
            0);

        m_frontLeftModule = new SwerveModule(
            null, 
            null, 
            0, 
            0, 
            0, 
            0);

        m_backRightModule = new SwerveModule(
            null, 
            null, 
            0, 
            0, 
            0, 
            0);

        m_backLeftModule = new SwerveModule(
            null, 
            null, 
            0, 
            0, 
            0, 
            0);

        m_driveKinematics = new SwerveDriveKinematics(
            new Translation2d(0, 0),
            new Translation2d(0, 0),
            new Translation2d(0, 0),
            new Translation2d(0, 0)
        );

    }

    @Override
    public void periodic() {}

    @Override
    public void simulationPeriodic() {}

}
