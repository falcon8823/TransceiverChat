package net.falconsrv.tranchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class PacketSender {
	private DatagramSocket send_socket;
	private InetAddress dst_addr;
	private int dst_port;

	public PacketSender(InetAddress dst_addr, int port) throws SocketException {
		send_socket = new DatagramSocket();
		this.dst_addr = dst_addr;
		this.dst_port = port;
	}

	public void sendPacket(PacketBase p) throws IOException {
		byte[] buffer = new byte[PacketBase.PACKET_LENGTH];
		p.getBytes(buffer);

		DatagramPacket packet = new DatagramPacket(buffer, PacketBase.PACKET_LENGTH);

		packet.setAddress(this.dst_addr);
		packet.setPort(this.dst_port);

		this.send_socket.send(packet);
	}
}
