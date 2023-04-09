package com.koibots.robot.utilities;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import edu.wpi.first.wpilibj.DriverStation;

public class DashboardConnection {
    /*
     * The game manual specifes UDP/TCP 5800-5810 are for team use. 
     * 1140 is Robot to Dashboard status data.
     */

    private static DatagramSocket[] m_sockets = new DatagramSocket[11];

    public static void StartConnection() {
        try {
            for (DatagramSocket socket : m_sockets) {
                socket.connect(InetAddress.getLocalHost(), 0);
            }
        } catch(UnknownHostException e) {
            
        }
    }

}