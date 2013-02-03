package net.falconsrv.tranchat.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import net.falconsrv.tranchat.MessagePacket;
import net.falconsrv.tranchat.PacketListener;
import net.falconsrv.tranchat.PacketReceiver;
import net.falconsrv.tranchat.PacketSender;
import net.falconsrv.tranchat.TransceiverChat;

public class ChatFrame extends JFrame implements PacketListener, WindowListener {

	private JPanel contentPane;
	private JSplitPane splitPane_1;
	private JSplitPane splitPane;
	private JScrollPane scrollPane_1;
	private JButton sendBtn;
	private JScrollPane streamScrollPane;
	private JTextArea chatStreamBox;
	private JTextArea sendTextBox;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem menuItem;
	private JSeparator separator;
	private JMenuItem menuItem_1;

	private PacketReceiver receiver;
	private SettingFrame dialog;

	/**
	 * Create the frame.
	 */
	public ChatFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 589);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnNewMenu = new JMenu("接続");
		menuBar.add(mnNewMenu);

		menuItem = new JMenuItem("設定");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(true);
			}
		});
		mnNewMenu.add(menuItem);

		separator = new JSeparator();
		mnNewMenu.add(separator);

		menuItem_1 = new JMenuItem("終了");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(menuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		splitPane_1 = new JSplitPane();
		splitPane_1.setDividerSize(2);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane_1);

		splitPane = new JSplitPane();
		splitPane.setDividerSize(2);
		splitPane_1.setRightComponent(splitPane);

		scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);

		sendTextBox = new JTextArea();
		sendTextBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendBtn.doClick();
				}
			}
		});

		scrollPane_1.setViewportView(sendTextBox);

		sendBtn = new JButton("Send");
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PacketSender sender = new PacketSender(TransceiverChat.dst_addr, TransceiverChat.port);
					MessagePacket p = new MessagePacket(TransceiverChat.user_name, sendTextBox.getText());
					sender.sendPacket(p);
					sendTextBox.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		splitPane.setRightComponent(sendBtn);
		splitPane.setDividerLocation(400);

		streamScrollPane = new JScrollPane();
		splitPane_1.setLeftComponent(streamScrollPane);

		chatStreamBox = new JTextArea();
		chatStreamBox.setEditable(false);
		streamScrollPane.setViewportView(chatStreamBox);
		splitPane_1.setDividerLocation(400);


		// パケットレシーバ開始
		receiver = new PacketReceiver(TransceiverChat.port);
		receiver.addPacketListner(this);
		receiver.start();

		dialog = new SettingFrame();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.addWindowListener(this);
	}

	@Override
	public void messageReceived(MessagePacket packet) {
		String line = "[" + packet.getSender_name() + "]: " + packet.getMsgBody() + "\n";
		chatStreamBox.append(line);
		chatStreamBox.setCaretPosition(chatStreamBox.getText().length());
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {
		receiver.close();
		receiver = new PacketReceiver(TransceiverChat.port);
		receiver.addPacketListner(this);
		receiver.start();
	}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

}
