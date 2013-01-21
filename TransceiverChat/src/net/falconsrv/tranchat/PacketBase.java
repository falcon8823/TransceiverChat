package net.falconsrv.tranchat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PacketBase {
	public static final int KIND_LENGTH = 1;
	public static final int NAME_LENGTH = 30;
	public static final int DATA_LENGTH = 1024;
	public static final int PACKET_LENGTH = KIND_LENGTH + NAME_LENGTH + DATA_LENGTH;

	private PacketKind kind;
	private String sender_name;
	private byte[] data;

	public PacketBase(byte[] raw_bytes) {
		ByteBuffer buf = ByteBuffer.wrap(raw_bytes);
		buf.order(ByteOrder.BIG_ENDIAN);

		this.kind = PacketKind.values()[buf.get()];

		byte[] name_buf = new byte[NAME_LENGTH];
		buf.get(name_buf, 0, NAME_LENGTH);
		this.sender_name = new String(name_buf);

		this.data = new byte[DATA_LENGTH];
		buf.get(this.data, 0, DATA_LENGTH);
	}

}
