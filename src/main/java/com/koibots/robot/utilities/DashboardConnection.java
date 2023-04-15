package com.koibots.robot.utilities;

import edu.wpi.first.hal.simulation.DriverStationDataJNI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class DashboardConnection {
    /*
     * The game manual specifies UDP/TCP 5800-5810 are for team use.
     */
    private static DatagramSocket[] sockets = new DatagramSocket[11];

    public static boolean isOfficialMatch;


    static {
        isOfficialMatch = DriverStation.isFMSAttached();
    }

    public static void StartConnection() throws SocketException, SecurityException, IllegalArgumentException, IOException {
        sockets =
            new DatagramSocket[] {
              new DatagramSocket(5800),
              new DatagramSocket(5801),
              new DatagramSocket(5802),
              new DatagramSocket(5803),
              new DatagramSocket(5804),
              new DatagramSocket(5805),
              new DatagramSocket(5806),
              new DatagramSocket(5807),
              new DatagramSocket(5808),
              new DatagramSocket(5809),
              new DatagramSocket(5810) // Mode notifications
            };

        DatagramPacket packet = new DatagramPacket(new byte[1], 1);
        if (isOfficialMatch) {
          new Notifier(
              () -> {
                try {
                  sockets[10].receive(packet);
                  sockets[10].getSoTimeout();// Wait for a packet
                } catch (IOException e) {
                  e.printStackTrace();
                }

                switch (packet.getData()[0]) {
                    case 0x00:
                        DriverStationDataJNI.setEnabled(true);
                        break;
                    case 0x01:
                        DriverStationDataJNI.setAutonomous(true);
                        break;
                    case 0x02:
                        DriverStationDataJNI.setTest(true);
                        break;
                    case 0x03:
                        DriverStationDataJNI.setEnabled(false);
                        break;
                  default:
                      DriverStation.reportWarning("Unknown mode", false);
                      DriverStationDataJNI.setEnabled(false);
                    break;
                }
              });
        }
    }

    public static void endConnection() {
        for (DatagramSocket socket : sockets) {
            socket.close();
        }
    }

}