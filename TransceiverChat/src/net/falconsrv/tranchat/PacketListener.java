package net.falconsrv.tranchat;

// パケットリスナインターフェースの定義
public interface PacketListener {
	public void messageReceived(MessagePacket packet);
}
