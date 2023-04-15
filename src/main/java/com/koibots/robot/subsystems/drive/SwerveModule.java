package com.koibots.robot.subsystems.drive;

import static com.koibots.robot.Constants.DriveSettings.*;
import static java.lang.Math.*;

import com.pathplanner.lib.auto.PIDConstants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder.Type;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule {
    private final SimpleMotorFeedforward feedforward;
    private final CANSparkMax driveMotor;
    private final CANSparkMax rotationMotor;
    private final RelativeEncoder rotationEncoder;
    private final RelativeEncoder driveEncoder;
    private final PIDController rotationPID;
    private final SparkMaxPIDController velocityPID;

    /**
     * Object for controlling a swerve module
     *
     * @param driveMotor T
     * @param rotationMotor The azimuth motor with its encoder plugged into the alternative encoder port on the CANSparkMax
     * @param pidConstants PID constants for turning to an angle
     * @param driveRampRate The ramp rate for the drive motor
     * @param ks feed forward constants
     * @param kv Simple feed forward constants
     * @param ka Simple feed forward constants
     */
    protected SwerveModule (
        CANSparkMax driveMotor,
        CANSparkMax rotationMotor,
        PIDConstants pidConstants,
        double driveRampRate,
        double ks,
        double kv,
        double ka
    ) {

        this.driveMotor = driveMotor;
        this.rotationMotor = rotationMotor;
        this.rotationPID = new PIDController(pidConstants.kP, pidConstants.kI, pidConstants.kD);
        this.driveMotor.setClosedLoopRampRate(driveRampRate);
        this.feedforward = new SimpleMotorFeedforward(ks, kv, ka);
        this.rotationEncoder = this.rotationMotor.getAlternateEncoder(Type.kQuadrature, 1);
        this.velocityPID = rotationMotor.getPIDController();
        this.driveEncoder = this.driveMotor.getAlternateEncoder(8192);
        this.rotationEncoder.setPositionConversionFactor((double) 360 / 8192);
        this.driveEncoder.setPositionConversionFactor(PI * WHEEL_DIAMETER_METERS);
    }

    /**
     * Returns the position of the drive motor's encoder.
     */
    public double getDrivePosition() {
        return this.driveEncoder.getPosition();
    }

    /**
     * Returns the rotation of a swerve module.
     */
    public double getRotation() {
        return this.rotationEncoder.getPosition();
    }

    /**
     * Returns the speed and rotation of a swerve module.
     */
    public SwerveModuleState getModuleState() {
        return new SwerveModuleState(this.driveEncoder.getVelocity(), new Rotation2d(getRotation()));
    }

    /**
     * Returns the drive position and rotation of a swerve module.
     */
    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(getDrivePosition(), new Rotation2d(getRotation()));
    }

    public void setDriveRampRate(double rate) {
        this.driveMotor.setClosedLoopRampRate(rate);
    }

    /**
     * Sets the velocity of the drive motor through PID.
     * 
     * @param speed the speed (in mps) to set the drive motor to.
     */
    protected void setDriveVelocity(double speed) {
        this.velocityPID.setReference(speed, ControlType.kSmartVelocity);
    }

    /**
     * Sets a swerve modules rotation and velocity.
     * 
     * @param setState the state to set the module to.
     */
    protected void setState(SwerveModuleState setState) {
        this.driveMotor.setVoltage(feedforward.calculate(setState.speedMetersPerSecond));
        this.rotationMotor.setVoltage(rotationPID.calculate(rotationEncoder.getPosition(), setState.angle.getDegrees()));
    }

    /**
     * Sets the rotation of a swerve module.
     *
     * @param angle the angle to set the module to in degrees.
     */
    public void setRotation(double angle) {
        this.rotationMotor.setVoltage(rotationPID.calculate(rotationEncoder.getPosition(), angle));
    }
}