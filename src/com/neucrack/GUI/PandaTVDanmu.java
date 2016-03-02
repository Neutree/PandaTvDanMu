package com.neucrack.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.neucrack.protocol.Bamboo;
import com.neucrack.protocol.ConnectDanMuServer;
import com.neucrack.protocol.Danmu;
import com.neucrack.protocol.Gift;
import com.neucrack.protocol.Platform;
import com.neucrack.protocol.User;
import com.neucrack.protocol.Visitors;
import com.sun.awt.AWTUtilities;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

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
import java.awt.Font;

import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PandaTVDanmu extends JFrame {

	private JPanel contentPane;
	static Point origin = new Point();
	private JTextField mRoomID;
	private JButton mButtonClose;
	private JButton mButtonConnect ;
	private JButton mButtonLock;
	private JList<String> mMessageList;
	private boolean mLock=false;
	private boolean mIsConnectionAlive=false;
	DefaultListModel<String> listModel;
	int mMessagelastIndex=0;
	private ConnectDanMuServer mDanMuConnection;
	
	static PandaTVDanmu frame;
	private JLabel mVisitorNum;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
					UIManager.setLookAndFeel(lookAndFeel);
					frame = new PandaTVDanmu();
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
		setIconImage(Toolkit.getDefaultToolkit().getImage("./resources/pic/icon.png"));
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1100, 250, 272, 323);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(Color.DARK_GRAY);
		contentPane.add(panelHeader, BorderLayout.NORTH);
		GridBagLayout gbl_panelHeader = new GridBagLayout();
		gbl_panelHeader.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panelHeader.rowHeights = new int[]{0, 0};
		gbl_panelHeader.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelHeader.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelHeader.setLayout(gbl_panelHeader);
		
		mButtonClose = new JButton("✘");
		mButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		mVisitorNum = new JLabel("0");
		mVisitorNum.setIcon(new ImageIcon("./resources/pic/audience.png"));
		mVisitorNum.setBackground(Color.DARK_GRAY);
		mVisitorNum.setForeground(Color.WHITE);
		GridBagConstraints gbc_mVisitorNum = new GridBagConstraints();
		gbc_mVisitorNum.insets = new Insets(0, 0, 5, 5);
		gbc_mVisitorNum.gridx = 0;
		gbc_mVisitorNum.gridy = 0;
		panelHeader.add(mVisitorNum, gbc_mVisitorNum);
		GridBagConstraints gbc_mButtonClose = new GridBagConstraints();
		gbc_mButtonClose.anchor = GridBagConstraints.EAST;
		gbc_mButtonClose.insets = new Insets(0, 0, 5, 0);
		gbc_mButtonClose.gridx = 3;
		gbc_mButtonClose.gridy = 0;
		panelHeader.add(mButtonClose, gbc_mButtonClose);
		
		JLabel mRoomLabel = new JLabel("房间");
		mRoomLabel.setForeground(Color.WHITE);
		GridBagConstraints gbc_mRoomLabel = new GridBagConstraints();
		gbc_mRoomLabel.insets = new Insets(0, 0, 0, 5);
		gbc_mRoomLabel.anchor = GridBagConstraints.EAST;
		gbc_mRoomLabel.gridx = 0;
		gbc_mRoomLabel.gridy = 1;
		panelHeader.add(mRoomLabel, gbc_mRoomLabel);
		
		mRoomID = new JTextField();
		mRoomID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					if(mIsConnectionAlive)
						CloseConnection();
					StartConnection();
				}
			}
		});
		mRoomID.setBackground(Color.DARK_GRAY);
		mRoomID.setForeground(Color.WHITE);
		GridBagConstraints gbc_mRoomID = new GridBagConstraints();
		gbc_mRoomID.insets = new Insets(0, 0, 0, 5);
		gbc_mRoomID.fill = GridBagConstraints.HORIZONTAL;
		gbc_mRoomID.gridx = 1;
		gbc_mRoomID.gridy = 1;
		panelHeader.add(mRoomID, gbc_mRoomID);
		panelHeader.setOpaque(false);//设置透明
		mRoomID.setColumns(10);
		
		mButtonConnect = new JButton("连接");
		mButtonConnect.setForeground(Color.DARK_GRAY);
		mButtonConnect.setBackground(new Color(34, 139, 34));
		mButtonConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!mIsConnectionAlive){//未连接
					StartConnection();
				}else{//已经连接
					CloseConnection();
				}
			}
		});
		GridBagConstraints gbc_mButtonConnect = new GridBagConstraints();
		gbc_mButtonConnect.insets = new Insets(0, 0, 0, 5);
		gbc_mButtonConnect.gridx = 2;
		gbc_mButtonConnect.gridy = 1;
		panelHeader.add(mButtonConnect, gbc_mButtonConnect);
		
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
		panelHeader.add(mButtonLock, gbc_mButtonLock);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		scrollPane.setOpaque(false);//设置透明
		scrollPane.getViewport().setOpaque(false);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		mMessageList = new JList<String>();
		mMessageList.setBorder(null);
		mMessageList.setBackground(new Color(0, 0, 0, 0));
		mMessageList.setOpaque(false);//设置透明
		((JLabel)mMessageList.getCellRenderer()).setOpaque(false);//设置jlist条目透明
		mMessageList.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		mMessageList.setForeground(Color.WHITE);
		mMessageList.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(mMessageList);
		
		
		this.setAlwaysOnTop(true);//窗口置顶
		this.setTitle("PandaTVDanMu");
		this.setUndecorated(true);
		//AWTUtilities.setWindowOpacity(this, 1f);//设置透明度
		this.setOpacity(1f);
		this.validate();
		mRoomID.setText("313180");//default value of room ID
		
		//simulate message data
		listModel=new DefaultListModel<String>();
		mMessageList.setModel(listModel);
		
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
	private void StartConnection(){
		mButtonConnect.setText("连接中");
		mDanMuConnection = new ConnectDanMuServer(frame);
		if(mDanMuConnection.ConnectToDanMuServer(mRoomID.getText().trim())){//连接成功
			mIsConnectionAlive=true;
			mButtonConnect.setText("断开");
			UpdateDanMu("连接弹幕服务器成功");
		}
		else{
			mIsConnectionAlive=false;
			mButtonConnect.setText("连接");
			UpdateDanMu("连接弹幕服务器失败！！");
		}
	}
	private void CloseConnection(){
		if(mDanMuConnection!=null)
			mDanMuConnection.Close();
		mIsConnectionAlive=false;
		mButtonConnect.setText("连接");
		UpdateDanMu("与弹幕服务器断开连接成功");
	}
	public void UpdateDanMu(String messageContent){
		listModel.addElement(messageContent);
		mMessagelastIndex = mMessageList.getModel().getSize() - 1;
		if (mMessagelastIndex >= 0) {
			mMessageList.ensureIndexIsVisible(mMessagelastIndex);
		}
	}
	
	//显示数据
	public void UpdateDanMu(Object message){
		String msgDis="";
		/*if(message==null)
			return;
		*/
		if(message.getClass().equals(Danmu.class)){//弹幕
			Danmu danmu = (Danmu) message;
			if(danmu.mPlatform.equals(Platform.PLATFORM_Android)||danmu.mPlatform.equals(Platform.PLATFORM_Ios)){
				msgDis+="☎";
				
			}
			msgDis+=danmu.mNickName;
			if(Integer.parseInt(danmu.mIdentity)>=60){
				if(danmu.mIdentity.equals(User.ROLE_MANAGER)){//管理员
					msgDis+="(管理)";
				}
				else if(danmu.mIdentity.equals(User.ROLE_HOSTER)){//主播
					msgDis+="(主播)";
				}
				else if(danmu.mIdentity.equals(User.ROLE_SUPER_MANAGER)){//超管
					msgDis+="(超管)";
				}
			}
			msgDis+="：";
			msgDis+=danmu.mContent;
		}
		else if(message.getClass().equals(Bamboo.class)){//竹子
			Bamboo bamboo = (Bamboo) message;
			msgDis+=bamboo.mNickName+"送给主播"+bamboo.mContent+"个竹子";			
		}
		else if(message.getClass().equals(Visitors.class)){//访客数量
			Visitors visitor = (Visitors) message;
			mVisitorNum.setText(visitor.mContent);
			return;
		}
		else if(message.getClass().equals(Gift.class)){//礼物
			Gift gift = (Gift) message;
			msgDis+=gift.mNickName+"送给主播"+gift.mContentCombo+"个"+gift.mContentName;
		}
		UpdateDanMu(msgDis);
	}

}
