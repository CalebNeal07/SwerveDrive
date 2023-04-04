package com.koibots.robot.utilities;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DashboardConnection {

    public enum ControllerCodes {
        a(new byte[] {
            0b00, 
        });

        public final byte[] label;

        private ControllerCodes(byte[] label) {
            this.label = label;
        }
    }

    DatagramSocket controllerPort ;
    DashboardConnection() throws IOException{
        controllerPort = new DatagramSocket(5810);
        controllerPort.receive(new DatagramPacket(null, 0));
        
    }



}
