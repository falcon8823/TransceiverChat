package net.falconsrv.tranchat.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.falconsrv.tranchat.MessagePacket;
import net.falconsrv.tranchat.PacketListener;
import net.falconsrv.tranchat.PacketSender;

public class ChatFrame extends JFrame implements PacketListener {

	private JPanel contentPane;
	private JSplitPane splitPane_1;
	private JSplitPane splitPane;
	private JScrollPane scrollPane_1;
	private JButton sendBtn;
	private JScrollPane streamScrollPane;
	private JTextArea chatStreamBox;
	private JTextArea sendTextBox;
	private JTextField nameTextField;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JButton btnSet;

	/**
	 * Create the frame.
	 */
	public ChatFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 589);
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
					PacketSender sender = new PacketSender(InetAddress.getByName("127.0.0.255"), 12345);
					MessagePacket p = new MessagePacket("hoge", sendTextBox.getText());
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

		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);

		lblNewLabel = new JLabel("Name: ");
		panel.add(lblNewLabel);

		nameTextField = new JTextField();
		panel.add(nameTextField);
		nameTextField.setColumns(10);

		btnSet = new JButton("Set");
		panel.add(btnSet);
	}

	@Override
	public void messageReceived(MessagePacket packet) {
		String line = "[" + packet.getSender_name() + "]: " + packet.getMsgBody() + "\n";
		//System.out.print(line);
		chatStreamBox.append(line);
		chatStreamBox.setCaretPosition(chatStreamBox.getText().length());


	}

}
