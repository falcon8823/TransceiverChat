package net.falconsrv.tranchat;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

// パケットクラスのベースクラス
// DatagramPacketと実データを媒介するモデルクラス
public class PacketBase {
	// パケット長の定義
	public static final int KIND_LENGTH = 1;
	public static final int NAME_LENGTH = 30;
	public static final int DATA_LENGTH = 1024;
	public static final int PACKET_LENGTH = KIND_LENGTH + NAME_LENGTH + DATA_LENGTH;
	public static final Charset CHAR_SET = Charset.forName("UTF-8");

	protected PacketKind kind;
	protected String sender_name;
	protected byte[] data;

	PacketBase() {
		this.data = new byte[DATA_LENGTH];
	}

	// パケットからバイト列を取得
	public void getBytes(byte[] buffer) {
		ByteBuffer buf = ByteBuffer.wrap(buffer);

		buf.put((byte)this.kind.ordinal());
		buf.put(this.sender_name.getBytes(CHAR_SET));
		buf.put(new byte[NAME_LENGTH - this.sender_name.getBytes(CHAR_SET).length]); // padding
		buf.put(this.data);
	}

	// DatagramPacketからPacketBaseへの変換
	public static PacketBase fromDatagramPacket(DatagramPacket p) {
		return fromByteArray(p.getData());
	}

	// バイト列からPacketBaseへの変換
	public static PacketBase fromByteArray(byte[] raw_bytes) {
		PacketBase p = new PacketBase();

		ByteBuffer buf = ByteBuffer.wrap(raw_bytes);
		buf.order(ByteOrder.BIG_ENDIAN);

		// PacketKind取得(1byte)
		p.setKind(PacketKind.values()[buf.get()]);

		// Sender Name取得(30byte)
		byte[] name_buf = new byte[NAME_LENGTH];
		buf.get(name_buf, 0, NAME_LENGTH);
		// 0パディングを取り除く
		int i;
		for(i = 0; i < name_buf.length; i++) if(name_buf[i] == 0) break;
		p.setSender_name(new String(name_buf, 0, i, CHAR_SET));

		// Data部取得(1024byte)
		buf.get(p.getData(), 0, DATA_LENGTH);

		return p;
	}

	public PacketKind getKind() {
		return kind;
	}

	public void setKind(PacketKind kind) {
		this.kind = kind;
	}

	public String getSender_name() {
		return sender_name;
	}

	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}


}
