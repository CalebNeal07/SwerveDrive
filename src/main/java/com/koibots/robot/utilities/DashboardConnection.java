package com.koibots.robot.utilities;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DashboardConnection {

    DatagramSocket controllerPort ;
    DashboardConnection() throws IOException{
        controllerPort = new DatagramSocket(5810);
        controllerPort.receive(new DatagramPacket(null, 0));
        
    }
}