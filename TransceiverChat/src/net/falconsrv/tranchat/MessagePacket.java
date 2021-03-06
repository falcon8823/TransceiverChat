package net.falconsrv.tranchat;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

// メッセージパケットを格納するクラス
public class MessagePacket extends PacketBase {
	public MessagePacket() {
		this.kind = PacketKind.Message;
	}

	public MessagePacket(String sender_name) {
		this.kind = PacketKind.Message;
		this.sender_name = sender_name;
	}

	public MessagePacket(String sender_name, String msg) {
		this.kind = PacketKind.Message;
		this.setMsgBody(msg);
		this.sender_name = sender_name;
	}

	// パケットのバイト列から，メッセージ部を取り出す
	public String getMsgBody() {
		int i;
		for(i = 0; i < this.data.length; i++) {
			if(this.data[i] == 0) break;
		}

		return new String(this.data, 0, i, CHAR_SET);
	}

	// Stringからパケットのバイト列にセット
	public void setMsgBody(String msg) {
		this.data = msg.getBytes(CHAR_SET);
	}

	public static MessagePacket fromDatagramPacket(DatagramPacket p) {
		return fromByteArray(p.getData());
	}

	// パケットのバイト列からMessagePacketへ変換するメソッド
	public static MessagePacket fromByteArray(byte[] raw_bytes) {
		MessagePacket p = new MessagePacket();

		ByteBuffer buf = ByteBuffer.wrap(raw_bytes);
		buf.order(ByteOrder.BIG_ENDIAN);

		// PacketKind取得
		p.setKind(PacketKind.values()[buf.get()]);

		// Sender Name取得
		byte[] name_buf = new byte[NAME_LENGTH];
		buf.get(name_buf, 0, NAME_LENGTH);
		// 0パディングを取り除く
		int i;
		for(i = 0; i < name_buf.length; i++) if(name_buf[i] == 0) break;
		p.setSender_name(new String(name_buf, 0, i, CHAR_SET));

		// Data部取得
		buf.get(p.getData(), 0, DATA_LENGTH);

		return p;
	}
}
