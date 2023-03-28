package frc.robot.utilities;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder.Type;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.SlewRateLimiter;
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
     * Common interface for setting the speed of a motor controller.
     *
     * @param speed The speed to set. Value should be between -1.0 and 1.0.
     */
    public SwerveModule(
        CANSparkMax driveMotor,
        CANSparkMax rotationMotor,
        double accelerationRate,
        double kp,
        double ki,
        double kd
    ) {
        this.m_driveMotor = driveMotor;
        this.m_rotationMotor = rotationMotor;
        this.m_rateLimiter = new SlewRateLimiter(accelerationRate, -accelerationRate, 0);
        this.m_rotationEncoder = m_rotationMotor.getAlternateEncoder(Type.kQuadrature, 8192);
        this.m_driveEncoder = m_driveMotor.getAlternateEncoder(0);
        this.m_pidController = new PIDController(kp, ki, kd);
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
}
