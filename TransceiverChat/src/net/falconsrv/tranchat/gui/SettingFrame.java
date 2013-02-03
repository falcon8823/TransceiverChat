package net.falconsrv.tranchat.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.falconsrv.tranchat.BcastGetter;
import net.falconsrv.tranchat.TransceiverChat;

public class SettingFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField dstPortField;
	private JTextField nameField;
	private JComboBox<String> addrListBox;
	private List<String> bcast_list;


	public SettingFrame() {
		// ブロードキャストアドレスの取得
		try {
			bcast_list = BcastGetter.GetBcastAddr();
		} catch (SocketException e2) {
		}

		setResizable(false);
		setBounds(100, 100, 269, 164);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);


		dstPortField = new JTextField();
		dstPortField.setBounds(94, 68, 130, 19);
		contentPanel.add(dstPortField);
		dstPortField.setColumns(10);

		addrListBox = new JComboBox<String>();
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		for(String a : bcast_list) comboBoxModel.addElement(a);
		addrListBox.setModel(comboBoxModel);
		addrListBox.setBounds(94, 39, 130, 19);
		contentPanel.add(addrListBox);

		JLabel label_1 = new JLabel("宛先ポート:");
		label_1.setBounds(29, 71, 53, 13);
		contentPanel.add(label_1);

		JLabel label_2 = new JLabel("表示名:");
		label_2.setBounds(44, 13, 38, 13);
		contentPanel.add(label_2);

		JLabel label = new JLabel("宛先アドレス:");
		label.setBounds(21, 42, 61, 13);
		contentPanel.add(label);

		nameField = new JTextField();
		nameField.setBounds(94, 10, 130, 19);
		nameField.setColumns(10);
		contentPanel.add(nameField);


		JButton okButton = new JButton("OK");

		// OKボタン押下時の動作
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 設定パラメータを保存
				TransceiverChat.port = Integer.parseInt(dstPortField.getText());
				TransceiverChat.user_name = nameField.getText();
				try {
					// コンボボックスからブロードキャストアドレスを取得
					TransceiverChat.dst_addr = Inet4Address.getByName((String)addrListBox.getSelectedItem());
					dispose();
				} catch (UnknownHostException e1) {
					JOptionPane.showMessageDialog(null, "IPアドレスを確認してください");
				}

			}
		});

		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);


		// 現在の設定を読み込み
		nameField.setText(TransceiverChat.user_name);
		dstPortField.setText(String.valueOf(TransceiverChat.port));
	}
}
