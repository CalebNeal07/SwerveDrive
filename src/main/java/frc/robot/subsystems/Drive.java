package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveSettings;
import frc.robot.utilities.SwerveModule;

public class Drive extends SubsystemBase{
    SwerveModule m_frontRightModule;
    SwerveModule m_frontLeftModule;
    SwerveModule m_backRightModule;
    SwerveModule m_backLeftModule;
    CANSparkMax m_frontRightDriveMotor;
    CANSparkMax m_frontRightRotationMotor;
    CANSparkMax m_frontLeftDriveMotor;
    CANSparkMax m_frontLeftRotationMotor;
    CANSparkMax m_backRightDriveMotor;
    CANSparkMax m_backRightRotationMotor;
    CANSparkMax m_backLefttDriveMotor;
    CANSparkMax m_backLeftRotationMotor;
    SwerveDriveKinematics m_driveKinematics;
    SwerveDriveOdometry m_driveOdometry;

    public Drive() {
        m_frontRightDriveMotor = new CANSparkMax(DriveSettings.FRONT_RIGHT_DRIVE_PORT, MotorType.kBrushless);
        m_frontRightRotationMotor = new CANSparkMax(DriveSettings.FRONT_RIGHT_ROTATION_PORT, MotorType.kBrushless);
        m_frontLeftDriveMotor = new CANSparkMax(DriveSettings.FRONT_LEFT_DRIVE_PORT, MotorType.kBrushless);
        m_frontLeftRotationMotor = new CANSparkMax(DriveSettings.FRONT_LEFT_ROTATION_PORT, MotorType.kBrushless);
        m_backRightDriveMotor = new CANSparkMax(DriveSettings.BACK_RIGHT_DRIVE_PORT, MotorType.kBrushless);
        m_backRightRotationMotor = new CANSparkMax(DriveSettings.BACK_RIGHT_ROTATION_PORT, MotorType.kBrushless);
        m_backLefttDriveMotor = new CANSparkMax(DriveSettings.BACK_LEFT_DRIVE_PORT, MotorType.kBrushless);
        m_backLeftRotationMotor = new CANSparkMax(DriveSettings.BACK_LEFT_ROTATION_PORT, MotorType.kBrushless);

        m_frontRightModule = new SwerveModule(
            m_frontRightDriveMotor,
            m_frontRightRotationMotor,
            DriveSettings.DRIVE_RAMP_RATE,
            DriveSettings.KP_ROTATION,
            DriveSettings.KI_ROTATION,
            DriveSettings.KD_ROTATION);

        m_frontLeftModule = new SwerveModule(
            m_frontLeftDriveMotor,
            m_frontLeftRotationMotor,
            DriveSettings.DRIVE_RAMP_RATE,
            DriveSettings.KP_ROTATION,
            DriveSettings.KI_ROTATION,
            DriveSettings.KD_ROTATION);

        m_backRightModule = new SwerveModule(
            m_backRightDriveMotor,
            m_backRightRotationMotor,
            DriveSettings.DRIVE_RAMP_RATE,
            DriveSettings.KP_ROTATION,
            DriveSettings.KI_ROTATION,
            DriveSettings.KD_ROTATION);

        m_backLeftModule = new SwerveModule(
            m_backLefttDriveMotor,
            m_backLefttDriveMotor,
            DriveSettings.DRIVE_RAMP_RATE,
            DriveSettings.KP_ROTATION,
            DriveSettings.KI_ROTATION,
            DriveSettings.KD_ROTATION);

        m_driveKinematics = new SwerveDriveKinematics(
        );

    }

    @Override
    public void periodic() {}

    @Override
    public void simulationPeriodic() {}
}
