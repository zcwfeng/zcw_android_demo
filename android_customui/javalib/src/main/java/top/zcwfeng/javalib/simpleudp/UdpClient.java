package top.zcwfeng.javalib.simpleudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public
class UdpClient {
    public static void main(String[] args) throws IOException {
        String msg  = "hello server";
        DatagramSocket datagramSocket = new DatagramSocket();
        //创建datagramPacket 发送消息
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(),msg.getBytes().length, InetAddress.getLocalHost(),12307);
        datagramSocket.send(datagramPacket);
    }
}
