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
	private JScrollPane streamScrollPane;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JSeparator separator;
	private JMenuItem closeMI;
	private JMenuItem settingMI;

	private JButton sendBtn;
	private JTextArea chatStreamBox;
	private JTextArea sendTextBox;
	private PacketReceiver receiver;
	private SettingFrame dialog;

	// コンポーネントの初期化処理
	private void InitializeComponent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 589);

		menuBar = new JMenuBar();
		mnNewMenu = new JMenu("接続");
		settingMI = new JMenuItem("設定");
		closeMI = new JMenuItem("終了");

		contentPane = new JPanel();
		splitPane_1 = new JSplitPane();
		splitPane = new JSplitPane();
		scrollPane_1 = new JScrollPane();
		streamScrollPane = new JScrollPane();
		separator = new JSeparator();

		sendBtn = new JButton("Send");
		sendTextBox = new JTextArea();
		chatStreamBox = new JTextArea();
		chatStreamBox.setEditable(false);

		// レイアウト
		setJMenuBar(menuBar);
		menuBar.add(mnNewMenu);
		mnNewMenu.add(settingMI);
		mnNewMenu.add(separator);
		mnNewMenu.add(closeMI);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		splitPane_1.setDividerSize(2);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane_1);
		splitPane.setDividerSize(2);
		splitPane_1.setRightComponent(splitPane);
		splitPane.setLeftComponent(scrollPane_1);
		scrollPane_1.setViewportView(sendTextBox);
		splitPane.setRightComponent(sendBtn);
		splitPane.setDividerLocation(400);
		splitPane_1.setLeftComponent(streamScrollPane);
		streamScrollPane.setViewportView(chatStreamBox);
		splitPane_1.setDividerLocation(400);
	}

	public ChatFrame() {
		// ウィンドウの初期化
		InitializeComponent();

		// 設定ダイアログ
		dialog = new SettingFrame();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.addWindowListener(this);


		// 設定メニュー押下時の処理
		settingMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(true);
			}
		});

		// 閉じるメニュー押下時の処理
		closeMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// 送信テキストボックスのエンターキー処理
		sendTextBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					// Ctrl + Enterで送信
					sendBtn.doClick();
				}
			}
		});

		// 送信ボタン押下時の処理
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// パケットを作成
					PacketSender sender = new PacketSender(TransceiverChat.dst_addr, TransceiverChat.port);
					MessagePacket p = new MessagePacket(TransceiverChat.user_name, sendTextBox.getText());

					sender.sendPacket(p); // 送信
					sendTextBox.setText("");
				} catch (IOException e1) {}
			}
		});

		// パケット受信開始
		receiver = new PacketReceiver(TransceiverChat.port);
		receiver.addPacketListner(this);
		receiver.start();
	}

	// メッセージパケット受信時
	@Override
	public void messageReceived(MessagePacket packet) {
		// テキストフィールドに表示
		String line = "[" + packet.getSender_name() + "]: " + packet.getMsgBody() + "\n";
		chatStreamBox.append(line);
		chatStreamBox.setCaretPosition(chatStreamBox.getText().length());
	}

	// 設定ダイアログが閉じられた際の処理
	@Override
	public void windowClosed(WindowEvent e) {
		// 受信スレッドを停止
		receiver.close();
		// 受信スレッドを新規作成し，開始
		receiver = new PacketReceiver(TransceiverChat.port);
		receiver.addPacketListner(this);
		receiver.start();
	}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}

}
