package com.koibots.robot;

import com.koibots.robot.utilities.SparkMaxSettings;
import static com.koibots.robot.Constants.DriveSettings.*;

import com.koibots.robot.subsystems.drive.DriveSubsystem;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
    private static DriveSubsystem m_drive;

    @Override
    public void robotInit() {
        System.gc();

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
            .loadSettings();

        m_drive = new DriveSubsystem();
    }

    @Override
    public void robotPeriodic() {
        // TODO Auto-generated method stub
        super.robotPeriodic();
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
        // TODO Auto-generated method stub
        super.teleopInit();
    }

    @Override
    public void teleopPeriodic() {
        m_drive.setStates(kDefaultPeriod, kDefaultPeriod, kDefaultPeriod);
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
