package com.neucrack.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class ListRenderer extends JPanel implements ListCellRenderer<ListItemDanMu>{

	
	private JLabel userName;
	private JLabel symbolAfterUserName;
	private JLabel message;
	private JLabel giftNumber;
	private JLabel giftUnit;
	private JLabel giftName;
	
	public ListRenderer() {
		userName = new JLabel();
		symbolAfterUserName = new JLabel();
		message = new JLabel();
		giftNumber = new JLabel();
		giftUnit = new JLabel();
		giftName = new JLabel();
		
		FlowLayout layout=new FlowLayout();
		setLayout(layout);
		layout.setAlignment(FlowLayout.LEFT);
		
		add(userName);
		add(symbolAfterUserName);
		add(message);
		add(giftNumber);
		add(giftUnit);
		add(giftName);
		
		
		
		userName.setForeground(new Color(0x6D40DB));
		symbolAfterUserName.setForeground(Color.yellow);
		message.setForeground(Color.white);
		giftNumber.setForeground(new Color(0xFF3C61));
		giftUnit.setForeground(Color.white);
		giftName.setForeground(new Color(0x24B073));
		
		userName.setFont(new Font("微软雅黑 ", Font.PLAIN, 12));
		symbolAfterUserName.setFont(new Font("微软雅黑 ", Font.PLAIN, 12));
		message.setFont(new Font("微软雅黑 ", Font.PLAIN, 12));
		giftNumber.setFont(new Font("微软雅黑 ", Font.PLAIN, 12));
		giftUnit.setFont(new Font("微软雅黑 ", Font.PLAIN, 12));
		giftName.setFont(new Font("微软雅黑 ", Font.PLAIN, 12));
		
		setOpaque(false);
	}
	
	public Component getListCellRendererComponent(
			JList<? extends ListItemDanMu> list, ListItemDanMu value,
			int index, boolean isSelected, boolean cellHasFocus) {

		if(value.isPhone())
			userName.setIcon(value.getPhoneIcon());	
		else
			userName.setIcon(null);
		userName.setText(value.getUserName());
		symbolAfterUserName.setText(value.getSymbolAfterUserName());
		message.setText(value.getMessage());
		if(value.isGift()){
			giftNumber.setText(value.getGiftNumber());
			giftUnit.setText(value.getGiftUnit());
			giftName.setText(value.getGiftName());
		}
		else{
			giftNumber.setText("");
			giftUnit.setText("");
			giftName.setText("");
		}
		return this;
	}
	
	
}
