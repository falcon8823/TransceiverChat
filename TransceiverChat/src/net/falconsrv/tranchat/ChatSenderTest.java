package net.falconsrv.tranchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;

public class ChatSenderTest {

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO 自動生成されたメソッド・スタブ
		InetAddress bcast = Inet4Address.getByName("127.0.0.255");
		PacketSender ps = new PacketSender(bcast, 12345);

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader istream = new BufferedReader(isr);

		while(true) {
			//String msg = istream.readLine();
			String msg = "こんにちは";
			MessagePacket mp = new MessagePacket("falcon", msg);
			//System.out.println(mp.getMsgBody());
			ps.sendPacket(mp);
			Thread.sleep(1000);
		}
	}

}
