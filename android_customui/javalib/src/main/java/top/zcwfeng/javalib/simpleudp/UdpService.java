package top.zcwfeng.javalib.simpleudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public

class UdpService {

    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(12307);
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(bytes,bytes.length);
        datagramSocket.receive(datagramPacket);
        System.out.println("接受到的信息："+new String(datagramPacket.getData()));
    }
}
