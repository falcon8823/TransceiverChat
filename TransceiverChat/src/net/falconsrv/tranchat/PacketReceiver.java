package net.falconsrv.tranchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class PacketReceiver implements Runnable {

	@Override
	public void run() {
		final int port = 12345;
		DatagramSocket recv_socket;
		DatagramPacket recv_packet;
		byte[] buffer = new byte[1055];

		// port番号のポートでソケットをバインド
		try {
			recv_socket = new DatagramSocket(port);
			recv_packet = new DatagramPacket(buffer, buffer.length);


			while(true) {
				recv_socket.receive(recv_packet);
				System.out.println();
			}

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		//recv_socket.close();

	}

}
