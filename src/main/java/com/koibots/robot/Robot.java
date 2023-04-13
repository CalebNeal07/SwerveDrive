package com.koibots.robot;

import com.koibots.robot.utilities.NAVX;
import com.koibots.robot.utilities.SparkMaxSettings;

import static com.koibots.robot.Constants.DriveSettings.*;

import com.koibots.robot.subsystems.drive.Drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import static com.koibots.robot.Constants.RobotParameters.*;
import static com.koibots.robot.Constants.ControllerConstants.*;
import static java.lang.Math.*;

public class Robot extends TimedRobot {
    private static Drivetrain m_drive;
    private PS4Controller m_controller;
    private PIDController m_angleToAngularVelocityPID;
    private PowerDistribution m_PDH;

    protected Robot(double period) {
        super(period);
    }

    @Override
    public void robotInit() {
        
        new SparkMaxSettings(
            "RotationSettings", 
            FRONT_LEFT_ROTATION_PORT,
            FRONT_RIGHT_ROTATION_PORT,
            BACK_LEFT_ROTATION_PORT,
            BACK_RIGHT_ROTATION_PORT)
            .loadSettings(true);

        new SparkMaxSettings(
            "DriveSettings", 
            FRONT_LEFT_DRIVE_PORT,
            FRONT_RIGHT_DRIVE_PORT,
            BACK_LEFT_DRIVE_PORT,
            BACK_RIGHT_DRIVE_PORT)
            .loadSettings(true);

        System.gc();

        m_controller = new PS4Controller(0);

        m_drive = new Drivetrain();

        m_PDH = new PowerDistribution(PDH_PORT, ModuleType.kRev);
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void disabledInit() {
        // TODO Auto-generated method stub
        super.disabledInit();
    }

    @Override
    public void disabledPeriodic() {
        // TODO Auto-generated method stub
        super.disabledPeriodic();
    }

    @Override
    public void disabledExit() {
        // TODO Auto-generated method stub
        super.disabledExit();
    }


    @Override
    public void autonomousInit() {
        // TODO Auto-generated method stub
        super.autonomousInit();
    }

    @Override
    public void autonomousPeriodic() {
        // TODO Auto-generated method stub
        super.autonomousPeriodic();
    }

    @Override
    public void autonomousExit() {
        // TODO Auto-generated method stub
        super.autonomousExit();
    }

    @Override
    public void teleopInit() {
        m_angleToAngularVelocityPID = new PIDController(BACK_LEFT_ROTATION_PORT, BACK_LEFT_DRIVE_PORT, kDefaultPeriod);
    }

    @Override
    public void teleopPeriodic() {
        m_drive.setStates(
            abs(m_controller.getLeftY()) > LEFT_JOYSTICK_DEADZONE ? m_controller.getLeftY() * MAX_TRANSLATION_SPEED_MPS : 0, 
            abs(m_controller.getLeftX()) > RIGHT_JOYSTICK_DEADZONE ? m_controller.getLeftX() * MAX_TRANSLATION_SPEED_MPS : 0, 
            m_angleToAngularVelocityPID.calculate(
                NAVX.get().getYaw(),
                m_controller.getPOV() == -1 ? 
                    pow(m_controller.getRightY(), 2) + pow(m_controller.getRightX(), 2) > ROTATION_DEADZONE ? 
                        new Rotation2d(
                            m_controller.getRightY(),
                            m_controller.getRightX()
                        ).getDegrees()
                        : NAVX.get().getYaw()
                    : m_controller.getPOV()));
    }

    @Override
    public void teleopExit() {
        // TODO Auto-generated method stub
        super.teleopExit();
    }

    @Override
    public void testInit() {
        // TODO Auto-generated method stub
        super.testInit();
    }

    @Override
    public void testPeriodic() {
        // TODO Auto-generated method stub
        super.testPeriodic();
    }

    @Override
    public void testExit() {
        // TODO Auto-generated method stub
        super.testExit();
    }


    @Override
    public void simulationInit() {
        m_drive.simulationInit();
    }

    @Override
    public void simulationPeriodic() {
        // TODO Auto-generated method stub
        super.simulationPeriodic();
    }

    public static Drivetrain getDrive() {
        return m_drive;
    }
}
