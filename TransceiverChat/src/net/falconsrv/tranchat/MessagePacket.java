package net.falconsrv.tranchat;

public class MessagePacket extends PacketBase {

	public String getMsgBody() {
		return new String(this.data);
	}

	public void setMsgBody(String msg) {
		this.data = msg.getBytes();
	}
}
