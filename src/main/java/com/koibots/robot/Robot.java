package com.koibots.robot;

import com.koibots.robot.utilities.NAVX;
import com.koibots.robot.utilities.SparkMaxSettings;

import static com.koibots.robot.Constants.DriveSettings.*;

import com.koibots.robot.subsystems.drive.DriveSubsystem;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
    private static DriveSubsystem m_drive;
    private PS4Controller m_controller;
    private PIDController m_angleToAngularVelocityPID;    

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

        m_drive = new DriveSubsystem();
    }

    @Override
    public void robotPeriodic() {
        // TODO Auto-generated method stub
        super.robotPeriodic();
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
            m_controller.getLeftY(), 
            m_controller.getLeftX(), 
            m_angleToAngularVelocityPID.calculate(
                NAVX.get().getYaw(), 
                new Rotation2d(m_controller.getRightY(), m_controller.getRightX()).getDegrees()));
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
        // TODO Auto-generated method stub
        super.simulationInit();
    }

    @Override
    public void simulationPeriodic() {
        // TODO Auto-generated method stub
        super.simulationPeriodic();
    }

    public static DriveSubsystem getDrive() {
        return m_drive;
    }
}
