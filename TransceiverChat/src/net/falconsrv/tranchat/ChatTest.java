package net.falconsrv.tranchat;

public class ChatTest {

	/**
	 * @param args
	 */
	public static void main(String[] args)  {
		// TODO 自動生成されたメソッド・スタブ
		PacketReceiver recv = new PacketReceiver();
		recv.addPacketListner(new PacketListener() {

			@Override
			public void messageReceived(MessagePacket packet) {
				String str = packet.getMsgBody();
				System.out.println(str);

			}
		});

		recv.run();
	}

}
