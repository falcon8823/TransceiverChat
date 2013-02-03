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
	private JPanel buttonPane;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label;
	private JButton okButton;

	// コンポーネントの初期化処理
	private void InitializeCompnent() {
		buttonPane = new JPanel();
		dstPortField = new JTextField();
		label_1 = new JLabel("宛先ポート:");
		label_2 = new JLabel("表示名:");
		label = new JLabel("宛先アドレス:");
		nameField = new JTextField();
		addrListBox = new JComboBox<String>();
		okButton = new JButton("OK");


		// レイアウト
		setResizable(false);
		setBounds(100, 100, 269, 164);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		dstPortField.setBounds(94, 68, 130, 19);
		contentPanel.add(dstPortField);
		dstPortField.setColumns(10);
		addrListBox.setBounds(94, 39, 130, 19);
		contentPanel.add(addrListBox);
		label_1.setBounds(29, 71, 53, 13);
		contentPanel.add(label_1);
		label_2.setBounds(44, 13, 38, 13);
		contentPanel.add(label_2);
		label.setBounds(21, 42, 61, 13);
		contentPanel.add(label);
		nameField.setBounds(94, 10, 130, 19);
		nameField.setColumns(10);
		contentPanel.add(nameField);
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	}

	public SettingFrame() {
		InitializeCompnent();

		// ブロードキャストアドレスの取得
		try {
			bcast_list = BcastGetter.GetBcastAddr();
		} catch (SocketException e2) {
		}

		// コンボボックスにアドレスの一覧を設定
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		for(String a : bcast_list) comboBoxModel.addElement(a);
		addrListBox.setModel(comboBoxModel);


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

		// 現在の設定を読み込み
		nameField.setText(TransceiverChat.user_name);
		dstPortField.setText(String.valueOf(TransceiverChat.port));
	}
}
