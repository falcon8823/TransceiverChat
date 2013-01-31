package net.falconsrv.tranchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class PacketReceiver extends Thread {
	private int port;
	private DatagramSocket recv_socket;
	private PacketListener p_listener;

	public PacketReceiver(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		DatagramPacket recv_packet;
		byte[] buffer = new byte[PacketBase.PACKET_LENGTH];

		// port番号のポートでソケットをバインド
		try {
			// バインド
			recv_socket = new DatagramSocket(port);
			recv_packet = new DatagramPacket(buffer, buffer.length);

			while(!recv_socket.isClosed()) {
				try {
					recv_socket.receive(recv_packet);

					// 受信したパケットをリスナーに通知
					this.p_listener.messageReceived(MessagePacket.fromDatagramPacket(recv_packet));
				} catch(IOException e) {
				}
			}

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	public void close() {
		if(!this.recv_socket.isClosed()) this.recv_socket.close();
	}

	public void addPacketListner(PacketListener l) {
		this.p_listener = l;
	}

}
