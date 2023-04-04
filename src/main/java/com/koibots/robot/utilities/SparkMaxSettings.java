package com.koibots.robot.utilities;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController.AccelStrategy;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;

public class SparkMaxSettings{
    private Map<String, String[]> settings = new HashMap<String, String[]>();
    private CANSparkMax m_sparkMax;
    private Gson gson = new Gson();
    private ArrayList<CANSparkMax> sparkMaxs;

    @SuppressWarnings("unchecked")
    public SparkMaxSettings(String file, int... ids) {
        for (int id : ids) {
            this.sparkMaxs.add(new CANSparkMax(id, MotorType.kBrushless));
        }
        try {
            this.settings = gson.fromJson(Files.readString(
                Paths.get(
                    Filesystem.getDeployDirectory().getName(), 
                    "SparxMaxSettings",
                    file + ".json")),
                settings.getClass());
        } catch (Exception e) {
            DriverStation.reportError(e.getMessage(), false);
        }
    }

    public void loadSettings() {
        this.loadSettings(false);
    }

    public void loadSettings(boolean burnFlash) {
        for (CANSparkMax sparkMax : sparkMaxs) {
            m_sparkMax = sparkMax;
            settings.forEach(
                this::loadSetting
            );
            if (burnFlash) {
                m_sparkMax.burnFlash();
            }
            m_sparkMax.close();
        }
    }

    private void loadSetting(String key, String[] values) {
        SparkMaxPIDController pidController = m_sparkMax.getPIDController();

        if (values.length == 0) {
            DriverStation.reportError("Json file not formated correctly", false);
            return;
        }

        switch(key) {
            case "Voltage Compensation":
                if (values[0].equalsIgnoreCase("True")) {
                    m_sparkMax.enableVoltageCompensation(Integer.parseInt(values[1]));
                } else {
                    m_sparkMax.disableVoltageCompensation();
                }
            case "External USB Control":
                CANSparkMax.enableExternalUSBControl(values[0].equalsIgnoreCase("True"));
            case "CAN Timeout":
                m_sparkMax.setCANTimeout(Integer.parseInt(values[0]));
            case "Idle Mode":
                m_sparkMax.setIdleMode(values[0].equalsIgnoreCase("Brake") ? IdleMode.kBrake : IdleMode.kCoast); 
            case "Control Frame Period":
                m_sparkMax.setControlFramePeriodMs(Integer.parseInt(values[0]));
            case "Inverted":
                m_sparkMax.setInverted(values[0].equalsIgnoreCase("True"));
            case "Open Loop Ramp Rate":
                m_sparkMax.setOpenLoopRampRate(Integer.parseInt(values[0]));
            case "Soft Limit":
                m_sparkMax.setSoftLimit(
                    values[0].equalsIgnoreCase("Forwards") ? SoftLimitDirection.kForward : SoftLimitDirection.kReverse, 
                    Integer.parseInt(values[1]));
                if (values.length > 2) {
                    m_sparkMax.setSoftLimit(
                    values[2].equalsIgnoreCase("Forwards") ? SoftLimitDirection.kForward : SoftLimitDirection.kReverse, 
                    Integer.parseInt(values[3]));
                }
            case "Smart Current Limit":
                if (values.length == 1) {
                    m_sparkMax.setSmartCurrentLimit(Integer.parseInt(values[0]));
                } else if (values.length == 2) {
                    m_sparkMax.setSmartCurrentLimit(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                } else {
                    m_sparkMax.setSmartCurrentLimit(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                }
            case "Secondary Current Limit":
                if (values.length == 1) {
                   m_sparkMax.setSecondaryCurrentLimit(Integer.parseInt(values[0]));
                } else {
                    m_sparkMax.setSecondaryCurrentLimit(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                }
            case "PIDF - kP":
                if (values.length == 1) {
                    pidController.setP(Integer.parseInt(values[0]));
                } else {
                    pidController.setP(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                }
            case "PIDF - kI":
                if (values.length == 1) {
                    pidController.setI(Integer.parseInt(values[0]));
                } else {
                    pidController.setI(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                }
            case "PIDF - kD":
                if (values.length == 1) {
                    pidController.setD(Integer.parseInt(values[0]));
                } else {
                    pidController.setD(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                }
            case "PIDF - kFF":
                if (values.length == 1) {
                    pidController.setFF(Integer.parseInt(values[0]));
                } else {
                    pidController.setFF(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                }
            case "PIDF - IZone":
                if (values.length == 1) {
                    pidController.setIZone(Integer.parseInt(values[0]));
                } else {
                    pidController.setIZone(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                }
            case "PIDF - Output Range":
                if (values.length == 2) {
                    pidController.setOutputRange(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                } else {
                    pidController.setOutputRange(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                }
            case "PIDF - Iaccum":
                pidController.setIAccum(Integer.parseInt(values[0]));
            case "PIDF - Max Iaccum":
                pidController.setIMaxAccum(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            case "SmartMotion Acceleration Strategy":
                pidController.setSmartMotionAccelStrategy(
                    values[0].equalsIgnoreCase("Trapezoidal") ? AccelStrategy.kTrapezoidal : AccelStrategy.kSCurve, 
                    Integer.parseInt(values[1]));
            case "Allowed Closed Loop Error":
                pidController.setSmartMotionAllowedClosedLoopError(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            case "SmartMotion Max Acceleration":
                pidController.setSmartMotionMaxAccel(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            case "SmartMotion Max Velocity":
                pidController.setSmartMotionMaxVelocity(Integer.parseInt(values[0]), Integer.parseUnsignedInt(values[1]));
            case "SmartMotion Min Velocity":
                pidController.setSmartMotionMinOutputVelocity(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
        }
    }
}