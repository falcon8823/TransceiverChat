package net.falconsrv.tranchat;

import java.awt.EventQueue;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.UIManager;

import net.falconsrv.tranchat.gui.ChatFrame;

public class TransceiverChat {
	public static InetAddress DEFAULT_ADDR;
	public static final int DEFAULT_PORT = 12345;
	public static final String DEFAULT_NAME = "Annonymous";
	public static int port;
	public static String user_name;
	public static InetAddress dst_addr;

	public static void main(String[] args) {
		try {
			DEFAULT_ADDR = Inet4Address.getByName("255.255.255.255");
			port = DEFAULT_PORT;
			user_name = DEFAULT_NAME;
			dst_addr = DEFAULT_ADDR;
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					ChatFrame frame = new ChatFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
