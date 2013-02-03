package net.falconsrv.tranchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

// パケット受信クラス
// 別スレッドで受信を行い，リスナーを介して受信時に通知する非同期型．
public class PacketReceiver extends Thread {
	private int port;
	private DatagramSocket recv_socket;
	private PacketListener p_listener;

	// コンストラクタ
	public PacketReceiver(int port) {
		this.setPort(port);
	}

	// 受信時の処理
	@Override
	public void run() {
		DatagramPacket recv_packet;
		byte[] buffer = new byte[PacketBase.PACKET_LENGTH];

		// port番号のポートでソケットをバインド
		try {
			// バインド
			recv_socket = new DatagramSocket(getPort());
			recv_packet = new DatagramPacket(buffer, buffer.length);

			while(!recv_socket.isClosed()) {
				try {
					recv_socket.receive(recv_packet);

					// 受信したパケットをリスナーに通知
					this.p_listener.messageReceived(MessagePacket.fromDatagramPacket(recv_packet));
				} catch(IOException e) {
				}
			}

		} catch (IOException e) {}

	}

	// 受信停止処理
	public void close() {
		if(!this.recv_socket.isClosed()) {
			this.recv_socket.close();
		}
	}

	// 受信通知リスナの設定
	public void addPacketListner(PacketListener l) {
		this.p_listener = l;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
