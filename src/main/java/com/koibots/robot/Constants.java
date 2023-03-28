// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.koibots.robot;

import edu.wpi.first.math.geometry.Translation2d;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    
  }
  public static class DriveSettings {
    public static final int FRONT_RIGHT_DRIVE_PORT = 0;
    public static final int FRONT_RIGHT_ROTATION_PORT = 1;
    public static final int FRONT_LEFT_DRIVE_PORT = 2;
    public static final int FRONT_LEFT_ROTATION_PORT = 3;
    public static final int BACK_RIGHT_DRIVE_PORT = 4;
    public static final int BACK_RIGHT_ROTATION_PORT = 5;
    public static final int BACK_LEFT_DRIVE_PORT = 6;
    public static final int BACK_LEFT_ROTATION_PORT = 7;

    public static final double KP_ROTATION = 0;
    public static final double KI_ROTATION = 0;
    public static final double KD_ROTATION = 0;

    public static final double DRIVE_RAMP_RATE = 0;

    public static final Translation2d FRONT_RIGHT_POSITION = new Translation2d(0, 0);
    public static final Translation2d FRONT_LEFT_POSITION = new Translation2d(0, 0);
    public static final Translation2d BACK_RIGHT_POSITION = new Translation2d(0, 0);
    public static final Translation2d BACK_LEFT_POSITION = new Translation2d(0, 0);
  }
}
