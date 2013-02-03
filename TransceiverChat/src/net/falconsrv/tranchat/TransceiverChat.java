package net.falconsrv.tranchat;

import java.awt.EventQueue;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.UIManager;

import net.falconsrv.tranchat.gui.ChatFrame;

// メインクラス
public class TransceiverChat {
	// プログラム全体で共通のstatic変数
	public static InetAddress DEFAULT_ADDR;
	public static final int DEFAULT_PORT = 12345;
	public static final String DEFAULT_NAME = "Anonymous";
	public static int port;
	public static String user_name;
	public static InetAddress dst_addr;

	// プログラムのエントリポイント
	public static void main(String[] args) {
		// デフォルトパラメータの設定
		try {
			DEFAULT_ADDR = Inet4Address.getByName("255.255.255.255");
			port = DEFAULT_PORT;
			user_name = DEFAULT_NAME;
			dst_addr = DEFAULT_ADDR;
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		// メインウィンドウの起動
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
