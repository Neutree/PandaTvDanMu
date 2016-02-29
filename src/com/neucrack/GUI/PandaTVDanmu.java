package com.neucrack.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.sun.awt.AWTUtilities;

import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PandaTVDanmu extends JFrame {

	private JPanel contentPane;
	static Point origin = new Point();
	private JTextField mRoomID;
	private JButton mButtonClose;
	private JButton mButtonConnect ;
	private JButton mButtonLock;
	private JList<String> mMessageList;
	private boolean mLock=false;
	DefaultListModel<String> listModel;
	int mMessagelastIndex=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
					UIManager.setLookAndFeel(lookAndFeel);
					PandaTVDanmu frame = new PandaTVDanmu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PandaTVDanmu() {
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1100, 250, 272, 323);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		mButtonClose = new JButton("✘");
		mButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		GridBagConstraints gbc_mButtonClose = new GridBagConstraints();
		gbc_mButtonClose.anchor = GridBagConstraints.EAST;
		gbc_mButtonClose.insets = new Insets(0, 0, 5, 0);
		gbc_mButtonClose.gridx = 3;
		gbc_mButtonClose.gridy = 0;
		panel.add(mButtonClose, gbc_mButtonClose);
		
		JLabel mRoomLabel = new JLabel("房间");
		mRoomLabel.setForeground(Color.WHITE);
		GridBagConstraints gbc_mRoomLabel = new GridBagConstraints();
		gbc_mRoomLabel.insets = new Insets(0, 0, 0, 5);
		gbc_mRoomLabel.anchor = GridBagConstraints.EAST;
		gbc_mRoomLabel.gridx = 0;
		gbc_mRoomLabel.gridy = 1;
		panel.add(mRoomLabel, gbc_mRoomLabel);
		
		mRoomID = new JTextField();
		mRoomID.setBackground(Color.DARK_GRAY);
		mRoomID.setForeground(Color.WHITE);
		GridBagConstraints gbc_mRoomID = new GridBagConstraints();
		gbc_mRoomID.insets = new Insets(0, 0, 0, 5);
		gbc_mRoomID.fill = GridBagConstraints.HORIZONTAL;
		gbc_mRoomID.gridx = 1;
		gbc_mRoomID.gridy = 1;
		panel.add(mRoomID, gbc_mRoomID);
		mRoomID.setColumns(10);
		
		mButtonConnect = new JButton("连接");
		mButtonConnect.setForeground(Color.DARK_GRAY);
		mButtonConnect.setBackground(Color.WHITE);
		mButtonConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		GridBagConstraints gbc_mButtonConnect = new GridBagConstraints();
		gbc_mButtonConnect.insets = new Insets(0, 0, 0, 5);
		gbc_mButtonConnect.gridx = 2;
		gbc_mButtonConnect.gridy = 1;
		panel.add(mButtonConnect, gbc_mButtonConnect);
		
		mButtonLock = new JButton("锁定");
		mButtonLock.setForeground(Color.DARK_GRAY);
		mButtonLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mLock){
					mRoomID.setEnabled(true);
					mButtonClose.setEnabled(true);
					mButtonConnect.setEnabled(true);
					mMessageList.setEnabled(true);
					mButtonLock.setText("锁定");
				}
				else{
					mRoomID.setEnabled(false);
					mButtonClose.setEnabled(false);
					mButtonConnect.setEnabled(false);
					mMessageList.setEnabled(false);
					mButtonLock.setText("解锁");
				}
				mLock=!mLock;
			}
		});
		GridBagConstraints gbc_mButtonLock = new GridBagConstraints();
		gbc_mButtonLock.anchor = GridBagConstraints.EAST;
		gbc_mButtonLock.gridx = 3;
		gbc_mButtonLock.gridy = 1;
		panel.add(mButtonLock, gbc_mButtonLock);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		mMessageList = new JList<String>();
		mMessageList.setForeground(Color.WHITE);
		mMessageList.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(mMessageList);
		
		
		this.setAlwaysOnTop(true);//窗口置顶
		this.setTitle("PandaTVDanMu");
		this.setUndecorated(true);
		AWTUtilities.setWindowOpacity(this, 0.5f);//设置透明度
		this.validate();
		mRoomID.setText("313180");//default value of room ID
		
		//simulate message data
		listModel=new DefaultListModel<String>();
		mMessageList.setModel(listModel);
		UpdateDanMu("测试评论。。。");
		
		final JFrame parentPanel=this;
		this.addMouseListener(new MouseAdapter() {
			// 按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
			public void mousePressed(MouseEvent e) {
				// 当鼠标按下的时候获得窗口当前的位置
				if(!mLock){
					origin.x = e.getX();
					origin.y = e.getY();
				}
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			// 拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
			public void mouseDragged(MouseEvent e) {
				// 当鼠标拖动时获取窗口当前位置
				if(!mLock){
					Point p =parentPanel.getLocation();
					// 设置窗口的位置
					// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
					parentPanel.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
				}
			}
		});
		
		
	}
	
	public void UpdateDanMu(String messageContent){
		listModel.addElement(messageContent);
		mMessagelastIndex = mMessageList.getModel().getSize() - 1;
		if (mMessagelastIndex >= 0) {
			mMessageList.ensureIndexIsVisible(mMessagelastIndex);
		}
	}

}
