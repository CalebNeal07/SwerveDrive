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
    private final SwerveModule frontLeftModule;
    private final SwerveModule frontRightModule;
    private final SwerveModule backLeftModule;
    private final SwerveModule backRightModule;
    private final SwerveDrivePoseEstimator odometry;
    private final SwerveDriveKinematics kinematics;

    public Drivetrain() {
        CANSparkMax m_frontLeftDriveMotor = new CANSparkMax(FRONT_LEFT_DRIVE_PORT, MotorType.kBrushless);
        CANSparkMax m_frontLeftRotationMotor = new CANSparkMax(FRONT_LEFT_ROTATION_PORT, MotorType.kBrushless);
        CANSparkMax frontRightDriveMotor = new CANSparkMax(FRONT_RIGHT_DRIVE_PORT, MotorType.kBrushless);
        CANSparkMax m_frontRightRotationMotor = new CANSparkMax(FRONT_RIGHT_ROTATION_PORT, MotorType.kBrushless);
        CANSparkMax m_backLeftDriveMotor = new CANSparkMax(BACK_LEFT_DRIVE_PORT, MotorType.kBrushless);
        CANSparkMax m_backLeftRotationMotor = new CANSparkMax(BACK_LEFT_ROTATION_PORT, MotorType.kBrushless);
        CANSparkMax m_backRightDriveMotor = new CANSparkMax(BACK_RIGHT_DRIVE_PORT, MotorType.kBrushless);
        CANSparkMax m_backRightRotationMotor = new CANSparkMax(BACK_RIGHT_ROTATION_PORT, MotorType.kBrushless);

        frontLeftModule = new SwerveModule(
                m_frontLeftDriveMotor,
                m_frontLeftRotationMotor,
                ROTATION_PID,
                DRIVE_RAMP_RATE,
                KS_DRIVE,
                KV_DRIVE,
                KA_DRIVE);

        frontRightModule = new SwerveModule(
                frontRightDriveMotor,
                m_frontRightRotationMotor,
                ROTATION_PID,
                DRIVE_RAMP_RATE,
                KS_DRIVE,
                KV_DRIVE,
                KA_DRIVE);

        backLeftModule = new SwerveModule(
                m_backLeftDriveMotor,
                m_backLeftRotationMotor,
                ROTATION_PID,
                DRIVE_RAMP_RATE,
                KS_DRIVE,
                KV_DRIVE,
                KA_DRIVE);

        backRightModule = new SwerveModule(
                m_backRightDriveMotor,
                m_backRightRotationMotor,
                ROTATION_PID,
                DRIVE_RAMP_RATE,
                KS_DRIVE,
                KV_DRIVE,
                KA_DRIVE);
    
        kinematics = new SwerveDriveKinematics(
            FRONT_LEFT_POSITION,
            FRONT_RIGHT_POSITION,
            BACK_LEFT_POSITION,
            BACK_RIGHT_POSITION
        );

        odometry = new SwerveDrivePoseEstimator(
                kinematics,
            NAVX.get().getRotation2d(),
            new SwerveModulePosition[] {
                frontLeftModule.getModulePosition(),
                frontRightModule.getModulePosition(),
                backLeftModule.getModulePosition(),
                backRightModule.getModulePosition()},
            new Pose2d());
    }

    public void setStates(double xMetersPerSecond, double yMetersPerSecond, double angularVelocity, Translation2d rotationOffset) {
        SwerveModuleState[] newStates = kinematics.toSwerveModuleStates(
            new ChassisSpeeds(
                xMetersPerSecond,
                yMetersPerSecond,
                angularVelocity),
            rotationOffset);

        SwerveDriveKinematics.desaturateWheelSpeeds(
            newStates, 
            MAX_DRIVE_SPEED_MPS);

        frontLeftModule.setState(newStates[0]);
        frontRightModule.setState(newStates[1]);
        backLeftModule.setState(newStates[2]);
        backRightModule.setState(newStates[3]);
    }

    public void setStates(double xMetersPerSecond, double yMetersPerSecond, double angularVelocity) {
        SwerveModuleState[] newStates = kinematics.toSwerveModuleStates(
            new ChassisSpeeds(
                xMetersPerSecond,
                yMetersPerSecond,
                angularVelocity));

        SwerveDriveKinematics.desaturateWheelSpeeds(
            newStates, 
            MAX_DRIVE_SPEED_MPS);

        frontLeftModule.setState(newStates[0]);
        frontRightModule.setState(newStates[1]);
        backLeftModule.setState(newStates[2]);
        backRightModule.setState(newStates[3]);
    }

    @Override
    public void periodic() {
        odometry.update(
            NAVX.get().getRotation2d(), 
            new SwerveModulePosition[] {
                frontLeftModule.getModulePosition(),
                frontRightModule.getModulePosition(),
                backLeftModule.getModulePosition(),
                backRightModule.getModulePosition()});
    }

    public void simulationInit() {}

}