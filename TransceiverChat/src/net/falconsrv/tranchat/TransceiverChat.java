package net.falconsrv.tranchat;

import java.awt.EventQueue;

import javax.swing.UIManager;

import net.falconsrv.tranchat.gui.ChatFrame;

public class TransceiverChat {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					ChatFrame frame = new ChatFrame();
					frame.setVisible(true);

					PacketReceiver receiver = new PacketReceiver();
					receiver.addPacketListner(frame);
					receiver.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
