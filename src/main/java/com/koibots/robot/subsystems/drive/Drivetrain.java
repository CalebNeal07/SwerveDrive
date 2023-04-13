package com.koibots.robot.subsystems.drive;

import com.koibots.robot.utilities.NAVX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static com.koibots.robot.Constants.DriveSettings.*;

public class Drivetrain extends SubsystemBase {
    CANSparkMax m_frontRightDriveMotor;
    CANSparkMax m_frontRightRotationMotor;
    CANSparkMax m_frontLeftDriveMotor;
    CANSparkMax m_frontLeftRotationMotor;
    CANSparkMax m_backRightDriveMotor;
    CANSparkMax m_backRightRotationMotor;
    CANSparkMax m_backLefttDriveMotor;
    CANSparkMax m_backLeftRotationMotor;
    SwerveModule m_frontLeftModule; 
    SwerveModule m_frontRightModule;
    SwerveModule m_backLeftModule;
    SwerveModule m_backRightModule;
    SwerveModule[] m_SwerveModules = {
        m_frontLeftModule,
        m_frontRightModule,
        m_backLeftModule,
        m_backRightModule};
    SwerveDrivePoseEstimator m_driveOdometry;
    SwerveDriveKinematics m_driveKinematics;

    public Drivetrain() {
        m_frontLeftDriveMotor = new CANSparkMax(FRONT_LEFT_DRIVE_PORT, MotorType.kBrushless);
        m_frontLeftRotationMotor = new CANSparkMax(FRONT_LEFT_ROTATION_PORT, MotorType.kBrushless);
        m_frontRightDriveMotor = new CANSparkMax(FRONT_RIGHT_DRIVE_PORT, MotorType.kBrushless);
        m_frontRightRotationMotor = new CANSparkMax(FRONT_RIGHT_ROTATION_PORT, MotorType.kBrushless);
        m_backLefttDriveMotor = new CANSparkMax(BACK_LEFT_DRIVE_PORT, MotorType.kBrushless);
        m_backLeftRotationMotor = new CANSparkMax(BACK_LEFT_ROTATION_PORT, MotorType.kBrushless);
        m_backRightDriveMotor = new CANSparkMax(BACK_RIGHT_DRIVE_PORT, MotorType.kBrushless);
        m_backRightRotationMotor = new CANSparkMax(BACK_RIGHT_ROTATION_PORT, MotorType.kBrushless);

        m_frontLeftModule = new SwerveModule(
            m_frontLeftDriveMotor,
            m_frontLeftRotationMotor,
            DRIVE_RAMP_RATE,
            ROTATION_PID,
            KS_DRIVE,
            KV_DRIVE,
            KA_DRIVE);

        m_frontRightModule = new SwerveModule(
            m_frontRightDriveMotor,
            m_frontRightRotationMotor,
            DRIVE_RAMP_RATE,
            ROTATION_PID,
            KS_DRIVE,
            KV_DRIVE,
            KA_DRIVE);

        m_backLeftModule = new SwerveModule(
            m_backLefttDriveMotor,
            m_backLefttDriveMotor,
            DRIVE_RAMP_RATE,
            ROTATION_PID,
            KS_DRIVE,
            KV_DRIVE,
            KA_DRIVE);

        m_backRightModule = new SwerveModule(
            m_backRightDriveMotor,
            m_backRightRotationMotor,
            DRIVE_RAMP_RATE,
            ROTATION_PID,
            KS_DRIVE,
            KV_DRIVE,
            KA_DRIVE);
    
        m_driveKinematics = new SwerveDriveKinematics(
            FRONT_LEFT_POSITION,
            FRONT_RIGHT_POSITION,
            BACK_LEFT_POSITION,
            BACK_RIGHT_POSITION
        );

        m_driveOdometry = new SwerveDrivePoseEstimator(
            m_driveKinematics,
            NAVX.get().getRotation2d(),
            new SwerveModulePosition[] {
                m_frontLeftModule.getModulePosition(), 
                m_frontRightModule.getModulePosition(),
                m_backLeftModule.getModulePosition(),
                m_backRightModule.getModulePosition()},
            new Pose2d());
    }

    public void setStates(double xMetersPerSecond, double yMetersPerSecond, double angularVelocity, Translation2d rotationOffset) {
        SwerveModuleState[] newStates = m_driveKinematics.toSwerveModuleStates(
            new ChassisSpeeds(
                xMetersPerSecond,
                yMetersPerSecond,
                angularVelocity),
            rotationOffset);

        SwerveDriveKinematics.desaturateWheelSpeeds(
            newStates, 
            MAX_DRIVE_SPEED_MPS);

        m_frontLeftModule.setState(newStates[0]);
        m_frontRightModule.setState(newStates[1]);
        m_backLeftModule.setState(newStates[2]);
        m_backRightModule.setState(newStates[3]);
    }

    public void setStates(double xMetersPerSecond, double yMetersPerSecond, double angularVelocity) {
        SwerveModuleState[] newStates = m_driveKinematics.toSwerveModuleStates(
            new ChassisSpeeds(
                xMetersPerSecond,
                yMetersPerSecond,
                angularVelocity));

        SwerveDriveKinematics.desaturateWheelSpeeds(
            newStates, 
            MAX_DRIVE_SPEED_MPS);

        m_frontLeftModule.setState(newStates[0]);
        m_frontRightModule.setState(newStates[1]);
        m_backLeftModule.setState(newStates[2]);
        m_backRightModule.setState(newStates[3]);
    }

    @Override
    public void periodic() {
        m_driveOdometry.update(
            NAVX.get().getRotation2d(), 
            new SwerveModulePosition[] {
                m_frontLeftModule.getModulePosition(), 
                m_frontRightModule.getModulePosition(),
                m_backLeftModule.getModulePosition(),
                m_backRightModule.getModulePosition()});
    }

    public void simulationInit() {}

    @Override
    public void simulationPeriodic() {}
}