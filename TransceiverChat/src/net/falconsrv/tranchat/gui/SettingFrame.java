package net.falconsrv.tranchat.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import net.falconsrv.tranchat.TransceiverChat;

public class SettingFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField dstPortField;
	private JTextField nameField;
	private JLabel label_2;
	private JTextField dstAddrField;

	/**
	 * Create the dialog.
	 */
	public SettingFrame() {
		setResizable(false);
		setBounds(100, 100, 301, 203);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);

		dstPortField = new JTextField();
		contentPanel.add(dstPortField);
		dstPortField.setColumns(10);

		JLabel label_1 = new JLabel("宛先ポート:");
		sl_contentPanel.putConstraint(SpringLayout.WEST, label_1, 48, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, label_1, -6, SpringLayout.WEST, dstPortField);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, dstPortField, -3, SpringLayout.NORTH, label_1);
		contentPanel.add(label_1);
		{
			label_2 = new JLabel("表示名:");
			sl_contentPanel.putConstraint(SpringLayout.WEST, label_2, 63, SpringLayout.WEST, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.EAST, label_2, 101, SpringLayout.WEST, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, label_2, 23, SpringLayout.NORTH, contentPanel);
			contentPanel.add(label_2);
		}
		{
			nameField = new JTextField();
			sl_contentPanel.putConstraint(SpringLayout.WEST, dstPortField, 0, SpringLayout.WEST, nameField);
			sl_contentPanel.putConstraint(SpringLayout.EAST, dstPortField, 0, SpringLayout.EAST, nameField);
			sl_contentPanel.putConstraint(SpringLayout.EAST, nameField, 237, SpringLayout.WEST, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, nameField, 20, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, nameField, 107, SpringLayout.WEST, contentPanel);
			nameField.setColumns(10);
			contentPanel.add(nameField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TransceiverChat.port = Integer.parseInt(dstPortField.getText());
						TransceiverChat.user_name = nameField.getText();
						try {
							TransceiverChat.dst_addr = Inet4Address.getByName(dstAddrField.getText());
							dispose();
						} catch (UnknownHostException e1) {
							// TODO 自動生成された catch ブロック
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "IPアドレスを確認してください");
						}

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}



		{
			dstAddrField = new JTextField();
			sl_contentPanel.putConstraint(SpringLayout.WEST, dstAddrField, -178, SpringLayout.EAST, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, dstAddrField, -6, SpringLayout.NORTH, dstPortField);
			sl_contentPanel.putConstraint(SpringLayout.EAST, dstAddrField, 0, SpringLayout.EAST, dstPortField);
			dstAddrField.setText("0");
			dstAddrField.setColumns(10);
			contentPanel.add(dstAddrField);
		}
		{
			JLabel label = new JLabel("宛先アドレス:");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, label_1, 10, SpringLayout.SOUTH, label);
			sl_contentPanel.putConstraint(SpringLayout.WEST, label, 40, SpringLayout.WEST, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.EAST, label, 101, SpringLayout.WEST, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.NORTH, label, 12, SpringLayout.SOUTH, label_2);
			contentPanel.add(label);
		}

		// 現在の設定を読み込み
		nameField.setText(TransceiverChat.user_name);
		dstPortField.setText(String.valueOf(TransceiverChat.port));
		dstAddrField.setText(TransceiverChat.dst_addr.getHostAddress());
	}
}
