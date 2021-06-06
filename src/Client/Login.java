package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DB.UserDB;
//import loginUI.LoginUI;

public class Login extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6256528270698337060L;
	private JTextField userName; // �û���
	private JPasswordField password; // ����
	private JLabel lableUser;
	private JLabel lablePwd;
	private JButton btnLogin; // ��ť
	private JButton btnRegister;
	private int wx, wy;
	private boolean isDraging = false;
	private JPanel contentPane;

	public static void main(String[] args) {
		Login frame = new Login();
		frame.setVisible(true);
	}

	public Login() {

		// �����ޱ�����
		setUndecorated(true);
		// ������� ȷ�������ܹ���ק
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				isDraging = true;
				wx = e.getX();
				wy = e.getY();
			}

			public void mouseReleased(MouseEvent e) {
				isDraging = false;
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isDraging) {
					int left = getLocation().x;
					int top = getLocation().y;
					setLocation(left + e.getX() - wx, top + e.getY() - wy);
				}
			}
		});

		setBounds(100, 100, 439, 369);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//
		btnLogin = new JButton("��¼");
		btnLogin.setBounds(225, 285, 170, 40);
		btnLogin.setBackground(new Color(0xFFC0CB));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBorder(null);
		btnLogin.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
		btnLogin.setPreferredSize(new Dimension(170, 40));
		btnLogin.setFocusPainted(false);
		contentPane.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = userName.getText();
				String pwd = String.valueOf(password.getPassword());
				// �������ݿ�ƥ��
				UserDB c = new UserDB(name, pwd);
				if (c.selectsql() == true) {
					setVisible(false);
					new Client(name);
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "�û��������벻ƥ�䣡", "����", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//
		btnRegister = new JButton("ע��");
		btnRegister.setBounds(37, 285, 170, 40);
		btnRegister.setFocusPainted(false);
		btnRegister.setBackground(new Color(0xFFC0CB));
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setBorder(null);
		btnRegister.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
		btnRegister.setPreferredSize(new Dimension(170, 40));
		contentPane.add(btnRegister);

		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Register();
			}
		});
		//
		// lableBg = new JLabel();
		// lableBg.setBounds(25, 20, 200, 200);
		// // �û������¼�����
		userName = new JTextField("10000");
		userName.setBounds(170, 167, 219, 35);
		userName.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
		userName.setBorder(null);
		contentPane.add(userName);
		// ��¼������Աߵ�����
		lableUser = new JLabel("�û���");
		lableUser.setBounds(37, 170, 126, 27);
		lableUser.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
		lableUser.setForeground(Color.WHITE);
		contentPane.add(lableUser);
		// ���������
		password = new JPasswordField("10000");
		password.setBounds(170, 212, 219, 35);
		password.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 30));
		password.setBorder(null);
		contentPane.add(password);
		// // ����������Աߵ�����
		lablePwd = new JLabel("����");
		lablePwd.setBounds(37, 215, 126, 27);
		lablePwd.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
		lablePwd.setForeground(Color.WHITE);
		contentPane.add(lablePwd);
		//
		
		JLabel lblv = new JLabel("SOCKET_");
		lblv.setForeground(Color.WHITE);
		lblv.setBackground(Color.WHITE);
		lblv.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 58));
		lblv.setBounds(37, 60, 357, 80);
		contentPane.add(lblv);
		
	}
}