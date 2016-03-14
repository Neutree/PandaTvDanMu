package com.neucrack.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizerListener;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import com.neucrack.AutoUpdata.AutoUpdata;
import com.neucrack.DataPersistence.PreferenceData;
import com.neucrack.Help_Update.Help;
import com.neucrack.protocol.Bamboo;
import com.neucrack.protocol.ConnectDanMuServer;
import com.neucrack.protocol.Danmu;
import com.neucrack.protocol.Gift;
import com.neucrack.protocol.Platform;
import com.neucrack.protocol.User;
import com.neucrack.protocol.Visitors;

import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.Vector;

import javax.swing.SwingConstants;


public class PandaTVDanmu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4541332569550717643L;
	private JPanel contentPane;
	static Point origin = new Point();
	private JTextField mRoomID;
	private JList<ListItemDanMu> mMessageList;
	final JFrame parentPanel=this;
	private boolean mLock=false;
	private boolean mIsConnectionAlive=false;
	private boolean mIsAutoScroll=true;
	private DefaultListModel<ListItemDanMu> mListItem;
	private int mTransparentValue=0,mTransparentValueBefore=0;
	private boolean mIsChangeFrameSize=false;
	private int mMaxDanMuDisNumber;
	//	DefaultListModel listModel;
	int mMessagelastIndex=0;
	private ConnectDanMuServer mDanMuConnection;
	
	private JLabel mVisitorNum;
	private JPanel panel_header_1;
	private JPanel panel_header_2_left;
	private JLabel label;
	private JLabel mLockHint;
	private JLabel mCloseWindow;
	private JPanel panel_1_left;
	private JPanel panel_2_right;
	private JLabel mStartStopConnection;
	private JLabel mPauseAutoScroll;
	private JScrollPane scrollPane;
	private JLabel mHelp;
	
	SpeechSynthesizer mTts;
	private String mVoiceName;
	private boolean mIsVoicing=false;
	private boolean mIsEnableVoice=false;
	private Vector<String> mVoiceContent = new Vector<String>();
	
	//定义热键标识，用于在设置多个热键时，在事件处理中区分用户按下的热键 
	public static final int FUNC_KEY_MARK = 1;
	private JPanel panel_header_2;
	private JPanel panel_header_2_right;
	private JLabel mSettings;
	
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
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_CONTROL){
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
				}

			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_CONTROL){
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				}
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(PandaTVDanmu.class.getResource("/pic/icon.png")));
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 250, 272, 323);
		
		mDanMuConnection = new ConnectDanMuServer(this);
		
		contentPane = new JPanel();
		contentPane.setBorder(new Border(new Color(0,0,0,0.8f), 5, this));
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(Color.DARK_GRAY);
		panelHeader.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		contentPane.add(panelHeader, BorderLayout.NORTH);
		panelHeader.setOpaque(false);
		panelHeader.setLayout(new BorderLayout(0, 0));
		
		panel_header_1 = new JPanel();
		panelHeader.add(panel_header_1, BorderLayout.NORTH);
		panel_header_1.setOpaque(false);
		panel_header_1.setLayout(new BorderLayout(0, 0));
		
		panel_1_left = new JPanel();
		panel_header_1.add(panel_1_left, BorderLayout.WEST);
		panel_1_left.setOpaque(false);
		
		mVisitorNum = new JLabel("0");
		panel_1_left.add(mVisitorNum);
		mVisitorNum.setHorizontalAlignment(SwingConstants.LEFT);
		mVisitorNum.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/audience.png")));
		mVisitorNum.setBackground(Color.DARK_GRAY);
		mVisitorNum.setForeground(Color.WHITE);
		
		panel_2_right = new JPanel();
		panel_header_1.add(panel_2_right, BorderLayout.EAST);
		panel_2_right.setOpaque(false);
		
		mLockHint = new JLabel("F10锁定");
		panel_2_right.add(mLockHint);
		mLockHint.setForeground(Color.WHITE);
		
		mHelp = new JLabel("");
		mHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JDialog helpDialog = new JDialog(parentPanel, "Neucrack_PandaTV 弹幕助手  帮助", null);
				helpDialog.setBounds(500, 150, 550, 323);
				Help helpInfo=new Help();
				helpDialog.getContentPane().setLayout(new FlowLayout());
				JTextArea helpMessage = new JTextArea(helpInfo.getmHelpMessage());
				JTextArea aboutMaker = new JTextArea(helpInfo.getmAboutMaker());
				helpMessage.setOpaque(false);
				aboutMaker.setOpaque(false);
				helpDialog.getContentPane().add(helpMessage);
				helpDialog.getContentPane().add(aboutMaker);
				helpDialog.setVisible(true);
			}
		});
		
		mSettings = new JLabel("");
		mSettings.setCursor(new Cursor(Cursor.HAND_CURSOR));
		mSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				class MyJDialog extends JDialog implements WindowListener{

					/**
					 * 
					 */
					private static final long serialVersionUID = 6317261328052074874L;

					public MyJDialog(JFrame parentPanel, String string) {
						super(parentPanel,string);
						this.addWindowListener((WindowListener) this);
					}

					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
//						System.out.println("1");
					}

					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
//						System.out.println("2");
					}

					@Override
					public void windowClosed(WindowEvent e) {
//						System.out.println("3");
					}

					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
//						System.out.println("4");
					}

					@Override
					public void windowDeiconified(WindowEvent e) {
//						System.out.println("5");
						
					}

					@Override
					public void windowActivated(WindowEvent e) {
//						System.out.println("6");
						mTransparentValueBefore = mTransparentValue;
					}

					@Override
					public void windowDeactivated(WindowEvent e) {
//						System.out.println("7");
						parentPanel.setOpacity((float) ((mTransparentValueBefore+100-PreferenceData.MAXTRASPARENTVALUE)/100.0));
					}
					
				}
				
				MyJDialog settingsDialog = new MyJDialog(parentPanel, "Neucrack_PandaTV 弹幕助手  设置");
				settingsDialog.setBounds(500, 150, 350, 250);
				settingsDialog.getContentPane().setLayout(new GridLayout(0, 1));
				JPanel transparentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				JPanel rememberRoomIDPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				JPanel disNumber = new JPanel(new FlowLayout(FlowLayout.CENTER));
				JPanel voiceSetting = new JPanel(new FlowLayout(FlowLayout.CENTER));
				JPanel applyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				final JLabel  transparentLabel=new JLabel("透明度 0%");
				final JSlider slider = new JSlider(0, PreferenceData.MAXTRASPARENTVALUE, PreferenceData.MAXTRASPARENTVALUE);
				final JCheckBox isRemenberRoomID = new JCheckBox("保存房间号");
				JLabel disNumberName = new JLabel("保留消息个数");
				final JTextField disNumberInput = new JTextField();
				final JCheckBox isEnableVoice = new JCheckBox("使能语音");
				JLabel voiceName=new JLabel("人物");				
				Vector<String> str = new Vector<String>();
				str.add("小燕");
				str.add("小宇");
				str.add("小峰");
				str.add("小梅");
				str.add("小蓉");
				final JComboBox<String> comboBox = new JComboBox<String>(str);
				disNumberInput.setColumns(6);
				JButton apply = new JButton("应用");
				slider.setCursor(new Cursor(Cursor.HAND_CURSOR));
				PreferenceData prefData=new PreferenceData();
				mTransparentValue = prefData.getmTransparentValue();
				if(prefData.IsSaveRoomID())
					isRemenberRoomID.setSelected(true);
				else
					isRemenberRoomID.setSelected(false);
				transparentLabel.setText("透明度  "+(PreferenceData.MAXTRASPARENTVALUE-mTransparentValue)+"% ");
				slider.setValue(PreferenceData.MAXTRASPARENTVALUE-mTransparentValue);
				
				mIsEnableVoice = prefData.GetIsEnableVoice();
				if(mIsEnableVoice)
					isEnableVoice.setSelected(true);
				else
					isEnableVoice.setSelected(false);
				mVoiceName = prefData.GetVoiceName();
				if(mVoiceName.equals("xiaoyan"))
					comboBox.setSelectedItem("小燕");
				else if(mVoiceName.equals("xiaoyu"))
					comboBox.setSelectedItem("小宇");
				else if(mVoiceName.equals("xiaofeng"))
					comboBox.setSelectedItem("小峰");
				else if(mVoiceName.equals("xiaomei"))
					comboBox.setSelectedItem("小梅");
				else if(mVoiceName.equals("xiaorong"))
					comboBox.setSelectedItem("小蓉");
				
				
				transparentPanel.add(transparentLabel);
				transparentPanel.add(slider);
				rememberRoomIDPanel.add(isRemenberRoomID);
				disNumber.add(disNumberName);
				disNumber.add(disNumberInput);
				voiceSetting.add(isEnableVoice);
				voiceSetting.add(voiceName);
				voiceSetting.add(comboBox);
				applyPanel.add(apply);
				
				settingsDialog.getContentPane().add(transparentPanel);
				settingsDialog.getContentPane().add(rememberRoomIDPanel);
				settingsDialog.getContentPane().add(disNumber);
				settingsDialog.getContentPane().add(voiceSetting);
				settingsDialog.getContentPane().add(applyPanel);
				
				
				disNumberInput.setText(mMaxDanMuDisNumber+"");
				
						
				settingsDialog.setVisible(true);
				
				
				
				slider.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						mTransparentValue = PreferenceData.MAXTRASPARENTVALUE - slider.getValue();
						transparentLabel.setText("透明度  "+(PreferenceData.MAXTRASPARENTVALUE-mTransparentValue)+"% ");
						parentPanel.setOpacity((float) ((mTransparentValue+100-PreferenceData.MAXTRASPARENTVALUE)/100.0));
					}
				});
				apply.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						parentPanel.setOpacity((float) ((mTransparentValue+100-PreferenceData.MAXTRASPARENTVALUE)/100.0));
						mTransparentValueBefore = mTransparentValue;
						PreferenceData prefData=new PreferenceData();
						prefData.SaveTransparentValue(mTransparentValue);
						if(isRemenberRoomID.isSelected()){
							prefData.SaveIsSaveRoomID(true);
							prefData.SaveRoomID(mRoomID.getText());
						}
						else
							prefData.SaveIsSaveRoomID(false);
						try{
							mMaxDanMuDisNumber = Integer.parseInt(disNumberInput.getText().trim());
						}catch(Exception err){
							JDialog errDialog = new JDialog(parentPanel, "错误", null);
							errDialog.setBounds(520, 160, 200, 150);
							JLabel hint = new JLabel("格式填写错误！");
							errDialog.add(hint);
							errDialog.setVisible(true);
						}
						prefData.SaveDanMuDisNumber(mMaxDanMuDisNumber);
						
						if(comboBox.getSelectedItem().equals("小燕"))
							mVoiceName = "xiaoyan";
						else if(comboBox.getSelectedItem().equals("小宇"))
							mVoiceName = "xiaoyu";
						else if(comboBox.getSelectedItem().equals("小峰"))
							mVoiceName = "xiaofeng";
						else if(comboBox.getSelectedItem().equals("小梅"))
							mVoiceName = "xiaomei";
						else if(comboBox.getSelectedItem().equals("小蓉"))
							mVoiceName = "xiaorong";
						prefData.SaveVoiceName(mVoiceName);
						mTts.setParameter(SpeechConstant.VOICE_NAME, mVoiceName);//设置发音人
						
						if(isEnableVoice.isSelected()){
							mIsEnableVoice = true;
							prefData.SaveIsEnableVoicee(true);
						}
						else{
							mIsEnableVoice = false;
							prefData.SaveIsEnableVoicee(false);
						}
						
					}
				}); 
				
			}
		});
		
		mSettings.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/settings.png")));
		panel_2_right.add(mSettings);
		panel_2_right.add(mHelp);
		mHelp.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/help.png")));
		mHelp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		mCloseWindow = new JLabel("");
		panel_2_right.add(mCloseWindow);
		mCloseWindow.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/close.png")));
		mCloseWindow.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		panel_header_2 = new JPanel();
		panel_header_2.setOpaque(false);
		panelHeader.add(panel_header_2, BorderLayout.SOUTH);
		panel_header_2.setLayout(new BorderLayout(0, 0));
		
		panel_header_2_left = new JPanel();
		panel_header_2.add(panel_header_2_left, BorderLayout.WEST);
		panel_header_2_left.setOpaque(false);
		GridBagLayout gbl_panel_header_2_left = new GridBagLayout();
		gbl_panel_header_2_left.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_header_2_left.rowHeights = new int[]{0, 0};
		gbl_panel_header_2_left.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_header_2_left.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_header_2_left.setLayout(gbl_panel_header_2_left);
		
		label = new JLabel("房间");
		label.setForeground(Color.WHITE);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel_header_2_left.add(label, gbc_label);
		
		mRoomID = new JTextField();
		mRoomID.setBorder(new EmptyBorder(0,0,0,0));
		
		
		//加载持久化数据
		PreferenceData prefData=new PreferenceData();
		if(prefData.IsSaveRoomID()){
			mRoomID.setText(prefData.GetRoomID());
		}
		else{
			mRoomID.setText(PreferenceData.DEFAULT_ROOMID);
		}
		mMaxDanMuDisNumber = prefData.GetDanMuDisNumber();
		
		GridBagConstraints gbc_mRoomID = new GridBagConstraints();
		gbc_mRoomID.insets = new Insets(0, 0, 0, 5);
		gbc_mRoomID.gridx = 1;
		gbc_mRoomID.gridy = 0;
		panel_header_2_left.add(mRoomID, gbc_mRoomID);
		mRoomID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					if(mIsConnectionAlive){
						CloseConnection();
						mStartStopConnection.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/StartConnection.png")));
						mPauseAutoScroll.setVisible(false);
					}
					StartConnection();
					mStartStopConnection.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/StopConnection.png")));
					mPauseAutoScroll.setVisible(true);
					mPauseAutoScroll.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/StopAutoscroll.png")));
					PreferenceData prefData = new PreferenceData();
					if(prefData.IsSaveRoomID())
						prefData.SaveRoomID(mRoomID.getText());
					else
						prefData.SaveRoomID(PreferenceData.DEFAULT_ROOMID);
				}
			}
		});
		mRoomID.setBackground(Color.DARK_GRAY);
		mRoomID.setForeground(Color.WHITE);
		mRoomID.setColumns(10);
		
		
		mStartStopConnection = new JLabel("");
		mStartStopConnection.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/StartConnection.png")));
		mStartStopConnection.setCursor(new Cursor(Cursor.HAND_CURSOR));
		mStartStopConnection.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(mIsConnectionAlive){
					CloseConnection();
				}
				else{
					StartConnection();
					mStartStopConnection.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/StopConnection.png")));
					mPauseAutoScroll.setVisible(true);
					mPauseAutoScroll.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/StopAutoscroll.png")));
				}
			}
		});
		GridBagConstraints gbc_mStartStopConnection = new GridBagConstraints();
		gbc_mStartStopConnection.insets = new Insets(0, 0, 0, 5);
		gbc_mStartStopConnection.gridx = 2;
		gbc_mStartStopConnection.gridy = 0;
		panel_header_2_left.add(mStartStopConnection, gbc_mStartStopConnection);
		
		mPauseAutoScroll = new JLabel("");
		mPauseAutoScroll.setCursor(new Cursor(Cursor.HAND_CURSOR));
		mPauseAutoScroll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(mIsAutoScroll){//目前是自动滚屏
					mIsAutoScroll=false;
					mPauseAutoScroll.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/StartConnection.png")));
				}
				else{
					mIsAutoScroll=true;
					mPauseAutoScroll.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/StopAutoscroll.png")));
				}
			}
		});
		GridBagConstraints gbc_mPauseAutoScroll = new GridBagConstraints();
		gbc_mPauseAutoScroll.insets = new Insets(0, 0, 0, 5);
		gbc_mPauseAutoScroll.gridx = 3;
		gbc_mPauseAutoScroll.gridy = 0;
		panel_header_2_left.add(mPauseAutoScroll, gbc_mPauseAutoScroll);
		
		panel_header_2_right = new JPanel();
		panel_header_2_right.setOpaque(false);
		panel_header_2.add(panel_header_2_right, BorderLayout.EAST);
		mCloseWindow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.exit(0);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				mCloseWindow.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/close_hover.png")));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				mCloseWindow.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/close_pressed.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mCloseWindow.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/close.png")));
			}
		});
		
		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		scrollPane.setOpaque(false);//设置透明
		scrollPane.getViewport().setOpaque(false);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		mListItem = new DefaultListModel<ListItemDanMu>();
		mMessageList = new JList<ListItemDanMu>(mListItem);
		mMessageList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_CONTROL){
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_CONTROL){
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				}
			}
		});
		mMessageList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 当鼠标按下的时候获得窗口当前的位置
				if(!mLock){
					if(mIsChangeFrameSize)
						return;	
					origin.x = e.getX();
					origin.y = e.getY();
				}
			}
		});
		mMessageList.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// 当鼠标拖动时获取窗口当前位置
				if(!mLock){
					if(mIsChangeFrameSize)
						return;					
					Point p =parentPanel.getLocation();
					// 设置窗口的位置
					// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
					parentPanel.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
				}
			}
		});
		mMessageList.setCellRenderer(new ListRenderer());
		mMessageList.setBorder(null);
		mMessageList.setBackground(new Color(0, 0, 0, 0));
		mMessageList.setOpaque(false);//设置透明
		mMessageList.setBorder(new EmptyBorder(0,0,0,0));
		
		//((JLabel)mMessageList.getCellRenderer()).setOpaque(false);//设置jlist条目透明，不是自己构造listrenderer时使用
		scrollPane.setViewportView(mMessageList);
		
		
		this.setAlwaysOnTop(true);//窗口置顶
		this.setTitle("PandaTVDanMu");
		this.setUndecorated(true);
		//AWTUtilities.setWindowOpacity(this, 1f);//设置透明度
		mTransparentValue = prefData.getmTransparentValue();
		this.setOpacity((float) ((mTransparentValue+100-PreferenceData.MAXTRASPARENTVALUE)/100.0));
		this.validate();
		
		
		
		this.addMouseListener(new MouseAdapter() {
			// 按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
			public void mousePressed(MouseEvent e) {
				// 当鼠标按下的时候获得窗口当前的位置
				if(!mLock){
					if(mIsChangeFrameSize)
						return;	
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
					if(mIsChangeFrameSize)
						return;	
					Point p =parentPanel.getLocation();
					// 设置窗口的位置
					// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
					parentPanel.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
				}
			}
		});
		
		
		
		//全局热键
		JIntellitype.getInstance().registerHotKey(FUNC_KEY_MARK,0, KeyEvent.VK_F10);
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
			
			@Override
			public void onHotKey(int arg0) {
				switch (arg0) { 
				case FUNC_KEY_MARK:	
					if(mLock){
						UnLock();
						mLock = false;
					}
					else{
						Lock();
						mLock=true;
					}
					break;
				}
			}
		});
		
		mIsEnableVoice = prefData.GetIsEnableVoice();
		mVoiceName = prefData.GetVoiceName();
		SpeechUtility.createUtility(SpeechConstant.APPID+"="+PreferenceData.TTS_APPID);

		//1.创建 SpeechSynthesizer 对象
		mTts= SpeechSynthesizer.createSynthesizer( );
		//2.合成参数设置，详见《iFlytek MSC Reference Manual》SpeechSynthesizer 类
		mTts.setParameter(SpeechConstant.VOICE_NAME, mVoiceName);//设置发音人
		mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
		mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
		//设置合成音频保存位置（可自定义保存位置），保存在“./iflytek.pcm”
		//如果不需要保存合成音频，注释该行代码
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, null);
		
		
		//自动检测工版本更新
		final String latestVersionAddr = AutoUpdata.DetectLatestVersion();
		if(latestVersionAddr!=null){//有最新版
			JDialog hint = new JDialog(parentPanel, "有新版本发布啦。。。。");
			hint.setBounds(600, 250, 250, 150);
			hint.setLayout(new GridLayout(0, 1) );
			JLabel text = new JLabel("当前版本："+AutoUpdata.VERSION+"\r\n最新:"+latestVersionAddr.substring(latestVersionAddr.indexOf("download/V")+9, latestVersionAddr.indexOf("/", latestVersionAddr.indexOf("download/V")+10)));
			JButton confirmButton =   new JButton("更新", null);
			hint.add(text);
			hint.add(confirmButton);
			hint.setVisible(true);
			hint.setFocusable(true);
			confirmButton.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					 
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					java.net.URI uri = java.net.URI.create(latestVersionAddr);
					// 获取当前系统桌面扩展 
				    java.awt.Desktop dp = java.awt.Desktop.getDesktop(); 
				    // 判断系统桌面是否支持要执行的功能 
				    if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) { 
				     //File file = new File("D:\\aa.txt"); 
				     //dp.edit(file);// 　编辑文件 
				      try {
						dp.browse(uri);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}// 获取系统默认浏览器打开链接 
				     // dp.open(file);// 用默认方式打开文件 
				     // dp.print(file);// 用打印机打印文件 
				    }
				}
			});
			
			
		}
	}
	//合成监听器
		private SynthesizerListener mSynListener = new SynthesizerListener(){
		//会话结束回调接口，没有错误时，error为null
		public void onCompleted(SpeechError error) {
			if(mVoiceContent.size()>0){
				mTts.startSpeaking(mVoiceContent.get(0), mSynListener);
				mVoiceContent.remove(0);//移除已经添加到语音中的
			}
			else{
				mIsVoicing = false;
			}
			int speed = mVoiceContent.size()*10+49;
			if(speed>100)
				speed=100;
			mTts.setParameter(SpeechConstant.SPEED,speed+"");
//			System.out.println("complete");
		}
		//缓冲进度回调
		//percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在
		public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
//			System.out.println("buffer progress");
		}
		//开始播放
		public void onSpeakBegin() {
//			System.out.println("speak begin");
		}
		//暂停播放
		public void onSpeakPaused() {
//			System.out.println("speak paused");
		}
		//播放进度回调
		//percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
//			System.out.println("speak progress");
		}
		//恢复播放回调接口
		public void onSpeakResumed() {
//			System.out.println("speak resumed");
		}
		};

	private void StartConnection(){
		UpdateDanMu(new ListItemDanMu(false, false, null, "", "", "连接中。。。", null, null, null));
		if(mDanMuConnection.ConnectToDanMuServer(mRoomID.getText().trim())){//连接成功
			mIsConnectionAlive=true;
			UpdateDanMu(new ListItemDanMu(false, false, null, "", "", "连接弹幕服务器成功", null, null, null));
		}
		else{
			mIsConnectionAlive=false;
			mPauseAutoScroll.setVisible(false);
			mStartStopConnection.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/StartConnection.png")));
			UpdateDanMu(new ListItemDanMu(false, false, null, "", "", "连接弹幕服务器失败！！", null, null, null));
		}
	}
	public void CloseConnection(){
		UpdateDanMu(new ListItemDanMu(false, false, null, "", "", "断开连接中。。。", null, null, null));
		if(mDanMuConnection!=null)
			mDanMuConnection.Close();
		mIsConnectionAlive=false;
		mStartStopConnection.setIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/StartConnection.png")));
		mPauseAutoScroll.setVisible(false);
		UpdateDanMu(new ListItemDanMu(false, false, null, "", "", "与弹幕服务器断开连接成功", null, null, null));
		mVoiceContent.clear();
	}
	public void UpdateDanMu(ListItemDanMu message){
		while(mListItem.size()>=mMaxDanMuDisNumber)
			mListItem.removeElementAt(0);
		mListItem.addElement(message);
		if(mIsAutoScroll){
			mMessagelastIndex = mListItem.getSize() - 1;
			if (mMessagelastIndex >= 0) {
				mMessageList.ensureIndexIsVisible(mMessagelastIndex);
			}
		}
	}
	
	public void Lock(){
		mRoomID.setEnabled(false);
		mMessageList.setEnabled(false);
		mStartStopConnection.setVisible(false);
		mPauseAutoScroll.setVisible(false);
		mMessageList.setEnabled(false);
		mHelp.setVisible(false);
		mCloseWindow.setVisible(false);
		mSettings.setVisible(false);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mLockHint.setText("F10解锁");
	}
	public void UnLock(){
		mRoomID.setEnabled(true);
		mMessageList.setEnabled(true);
		mStartStopConnection.setVisible(true);
		mPauseAutoScroll.setVisible(true);
		mMessageList.setEnabled(true);
		mHelp.setVisible(true);
		mCloseWindow.setVisible(true);
		parentPanel.setEnabled(true);
		mSettings.setVisible(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mLockHint.setText("F10锁定");
	}
	//显示数据
	public void UpdateDanMu(Object message){
		ListItemDanMu danMuMessage=new ListItemDanMu();
		if(message.getClass().equals(Danmu.class)){//弹幕
			Danmu danmu = (Danmu) message;
			danMuMessage.setGift(false);
			if(danmu.mPlatform.equals(Platform.PLATFORM_Android)||danmu.mPlatform.equals(Platform.PLATFORM_Ios)){
				danMuMessage.setPhone(true);
				danMuMessage.setPhoneIcon(new ImageIcon(PandaTVDanmu.class.getResource("/pic/mobile.png")));
			}
			String userName=danmu.mNickName;
			if(Integer.parseInt(danmu.mIdentity)>=60){
				if(danmu.mIdentity.equals(User.ROLE_MANAGER)){//管理员
					danMuMessage.setSymbolAfterUserName("(管理) ");
				}
				else if(danmu.mIdentity.equals(User.ROLE_HOSTER)){//主播
					danMuMessage.setSymbolAfterUserName("(主播) ");
				}
				else if(danmu.mIdentity.equals(User.ROLE_SUPER_MANAGER)){//超管
					danMuMessage.setSymbolAfterUserName("(超管) ");
				}
			}
			else
				danMuMessage.setSymbolAfterUserName(" ");
			danMuMessage.setUserName(userName);
			danMuMessage.setMessage(danmu.mContent);
			if(mIsEnableVoice){
				if(!mIsVoicing){
					mIsVoicing = true;
					//3.开始合成
					mTts.startSpeaking(danmu.mContent, mSynListener);
				}
				else
				{
					mVoiceContent.add(danmu.mContent);
				}
			}
		}
		else if(message.getClass().equals(Bamboo.class)){//竹子
			Bamboo bamboo = (Bamboo) message;
			danMuMessage.setPhone(false);
			danMuMessage.setGift(true);
			danMuMessage.setUserName(bamboo.mNickName);
			danMuMessage.setSymbolAfterUserName("");
			danMuMessage.setMessage("送给主播");
			danMuMessage.setGiftNumber(bamboo.mContent);
			danMuMessage.setGiftUnit("个");
			danMuMessage.setGiftName("竹子");
			if(mIsEnableVoice){
				if(!mIsVoicing){
					mIsVoicing = true;
					//3.开始合成
					mTts.startSpeaking("感谢 ！ "+bamboo.mNickName+"送的"+bamboo.mContent+"个竹子", mSynListener);
				}
				else
				{
					mVoiceContent.add("感谢 ！"+bamboo.mNickName+"送的"+bamboo.mContent+"个竹子");
				}
			}
		}
		else if(message.getClass().equals(Visitors.class)){//访客数量
			Visitors visitor = (Visitors) message;
			mVisitorNum.setText(visitor.mContent);
			return;
		}
		else if(message.getClass().equals(Gift.class)){//礼物
			Gift gift = (Gift) message;
			danMuMessage.setPhone(false);
			danMuMessage.setGift(true);
			danMuMessage.setUserName(gift.mNickName);
			danMuMessage.setSymbolAfterUserName("");
			danMuMessage.setMessage("送给主播");
			danMuMessage.setGiftNumber(gift.mContentCombo);
			danMuMessage.setGiftUnit("个");
			danMuMessage.setGiftName(gift.mContentName);
			if(mIsEnableVoice){
				if(!mIsVoicing){
					mIsVoicing = true;
					//3.开始合成
					mTts.startSpeaking("感谢 ！ "+gift.mNickName+"送的"+gift.mContentCombo+"个"+gift.mContentName, mSynListener);
				}
				else
				{
					mVoiceContent.add("感谢 ！"+gift.mNickName+"送的"+gift.mContentCombo+"个"+gift.mContentName);
				}
			}
		}
		UpdateDanMu(danMuMessage);
	}

	
	
	//边框缩放
	class Border extends LineBorder implements MouseInputListener {
		private static final long serialVersionUID = 1L;

		private JFrame frame;
		private int delta;

		private Point sp;
		private Point cp;
		private int width;
		private int height;

		private boolean top, bottom, left, right, topLeft, topRight,
				bottomLeft, bottomRight;

		public Border(Color color, int delta, JFrame frame) {
			super(color, delta);
			this.delta = delta;
			this.frame = frame;

			addMouseMotionListener(this);
			addMouseListener(this);
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(mLock)
				return;
			mIsChangeFrameSize=true;
			Point dp = e.getLocationOnScreen();
			// 拖动时的组件原点
			int ox = dp.x - cp.x;
			int oy = dp.y - cp.y;

			// 静止的 原点
			int x = sp.x - cp.x;
			int y = sp.y - cp.y;

			int h = height;
			int w = width;

			if (top) {
				ox = x;
				h = height + (-dp.y + sp.y);
			} else if (bottom) {
				oy = y;
				ox = x;
				h = height + (dp.y - sp.y);
			} else if (left) {
				oy = y;
				w = width + (-dp.x + sp.x);
			} else if (right) {
				oy = y;
				ox = x;
				w = width + (dp.x - sp.x);
			} else if (topLeft) {
				h = height + (-dp.y + sp.y);
				w = width + (-dp.x + sp.x);
			} else if (topRight) {
				ox = x;
				h = height + (-dp.y + sp.y);
				w = width + (dp.x - sp.x);
			} else if (bottomLeft) {
				oy = y;
				h = height + (-dp.y + sp.y);
				w = width + (dp.x - sp.x);
			} else if (bottomRight) {
				ox = x;
				oy = y;
				h = height + (dp.y - sp.y);
				w = width + (+dp.x - sp.x);
			}
			frame.setLocation(ox, oy);
			frame.setSize(w, h);
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			if(mLock)
				return;
			sp = arg0.getLocationOnScreen();
			cp = arg0.getPoint();
			width = frame.getWidth();
			height = frame.getHeight();

			top = cp.x > delta && cp.x < width - delta && cp.y <= delta;
			bottom = cp.x > delta && cp.x < width - delta
					&& cp.y >= height - delta;
			left = cp.x <= delta && cp.y > delta && cp.y < height - delta;
			right = cp.x >= width - delta && cp.y > delta
					&& cp.y < height - delta;

			topLeft = cp.x <= delta && cp.y <= delta;
			topRight = cp.x >= width - delta && cp.y <= delta;

			bottomLeft = cp.x <= delta && cp.y >= height - delta;
			bottomRight = cp.x >= width - delta && cp.y >= height - delta;

			if (top) {
				frame.setCursor(Cursor
						.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
				return;
			} else if (bottom) {
				frame.setCursor(Cursor
						.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			} else if (left) {
				frame.setCursor(Cursor
						.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
			} else if (right) {
				frame.setCursor(Cursor
						.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
			} else if (topLeft) {
				frame.setCursor(Cursor
						.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			} else if (topRight) {
				frame.setCursor(Cursor
						.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
			} else if (bottomLeft) {
				frame.setCursor(Cursor
						.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
			} else if (bottomRight) {
				frame.setCursor(Cursor
						.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			if(mLock)
				return;
			mIsChangeFrameSize=true;
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			if(mLock)
				return;
			mIsChangeFrameSize=false;
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

	}
	
		
}
