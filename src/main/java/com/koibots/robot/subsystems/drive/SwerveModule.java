package com.koibots.robot.subsystems.drive;

import com.pathplanner.lib.auto.PIDConstants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
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
    private SparkMaxPIDController m_velocityPID;

    /**
     * Object for controlling a swerve module
     *
     * @param driveMotor 
     * @param rotationMotor the motor that controls 
     * @param accelerationRate How fast the drive motor should accelerate
     * @param pidConstants PID constants for turning to an angle
     * @param ks feed forward constants
     * @param kv Simple feed forward constants
     * @param ka Simple feed forward constants
     */
    protected SwerveModule (
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
        this.m_velocityPID = rotationMotor.getPIDController();
        this.m_driveEncoder = m_driveMotor.getAlternateEncoder(8192);
        this.m_rotationEncoder.setPositionConversionFactor(360 / 8192);
    }

    /**
     * Returns the position of the drive motor's encoder.
     */
    public double getDrivePosition() {
        return this.m_driveEncoder.getPosition();
    }

    /**
     * Returns the rotation of a swerve module.
     */
    public double getRotation() {
        return this.m_rotationEncoder.getPosition();
    }

    /**
     * Returns the speed and rotation of a swerve module.
     */
    public SwerveModuleState getModuleState() {
        return new SwerveModuleState(this.m_driveEncoder.getVelocity(), new Rotation2d(getRotation()));
    }

    /**
     * Returns the drive position and rotation of a swerve module.
     */
    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(getDrivePosition(), new Rotation2d(getRotation()));
    }

    /**
     * Sets the velocity of the drive motor through PID.
     * 
     * @param speed the speed (in mps) to set the drive motor to.
     */
    public void setDriveVelocity(double speed) {
        this.m_velocityPID.setReference(speed, ControlType.kSmartVelocity);
    }

    /**
     * Sets a swerve modules rotation and velocity.
     * 
     * @param setState the state to set the module to.
     */
    protected void setState(SwerveModuleState setState) {
        this.m_driveMotor.setVoltage(m_feedforward.calculate(m_rateLimiter.calculate(setState.speedMetersPerSecond)));
        this.m_rotationMotor.setVoltage(m_pidController.calculate(m_rotationEncoder.getPosition(), setState.angle.getDegrees()));
    }
}