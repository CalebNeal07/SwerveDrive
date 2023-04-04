package com.koibots.robot.utilities;

import com.pathplanner.lib.auto.PIDConstants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder.Type;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule {

    private SimpleMotorFeedforward m_feedforward;
    private CANSparkMax m_driveMotor;
    private CANSparkMax m_rotationMotor;
    private RelativeEncoder m_rotationEncoder;
    private RelativeEncoder m_driveEncoder;
    private SlewRateLimiter m_rateLimiter;
    private PIDController m_pidController;

    /**
     * Object for controlling a swerve module
     *
     * @param driveMotor 
     * @param rotationMotor the motor that controls 
     * @param accelerationRate How fast the drive motor should accelerate
     * @param kp for PID for rotating to an angle
     * @param ki 
     * @param kd
     * @param ks 
     * @param kv
     * @param ka
     */
    public SwerveModule(
        CANSparkMax driveMotor,
        CANSparkMax rotationMotor,
        double accelerationRate,
        PIDConstants pidConstants,
        double ks,
        double kv,
        double ka 
    ) {
        this.m_driveMotor = driveMotor;
        this.m_rotationMotor = rotationMotor;
        this.m_rateLimiter = new SlewRateLimiter(accelerationRate, -accelerationRate, 0);
        this.m_pidController = new PIDController(pidConstants.kP, pidConstants.kI, pidConstants.kD);
        this.m_feedforward = new SimpleMotorFeedforward(ks, kv, ka);
        this.m_rotationEncoder = m_rotationMotor.getAlternateEncoder(Type.kQuadrature, 1);
        this.m_driveEncoder = m_driveMotor.getAlternateEncoder(8192);
        this.m_rotationEncoder.setPositionConversionFactor(360 / 8192);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getDrivePosition(), null);
    }

    public void setState(SwerveModuleState setState) {
        m_driveMotor.setVoltage(m_feedforward.calculate(m_rateLimiter.calculate(setState.speedMetersPerSecond)));
        m_rotationMotor.setVoltage(m_pidController.calculate(m_rotationEncoder.getPosition(), setState.angle.getDegrees()));
    }

    public double getDrivePosition() {
        return m_driveEncoder.getPosition();
    }

    public double getRotation() {
        return m_rotationEncoder.getPosition();
    }

    public SwerveModuleState getModuleState() {
        return new SwerveModuleState(m_driveEncoder.getVelocity(), new Rotation2d(getRotation()));
    }

    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(getDrivePosition(), new Rotation2d(getRotation()));
    }
}