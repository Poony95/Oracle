package membertest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import db.ConnectionProvider;

public class MemberTest extends JFrame {
	
	JTextField jtf01;
	JTable table;
	Vector<String> colNames;
	Vector<Vector<String>> rowData;
	
	public MemberTest() {
		jtf01 = new JTextField(10);
		colNames = new Vector<String>();
		colNames.add("이름");
		colNames.add("전화");
		colNames.add("주소");
		colNames.add("나이");		
		rowData = new Vector<Vector<String>>();		
		table = new JTable(rowData, colNames);
		JScrollPane jsp = new JScrollPane(table);
		JPanel p = new JPanel();
		p.add(new JLabel("주소"));
		p.add(jtf01);
		JButton btn = new JButton("검색");
		p.add(btn);
		add(p, BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);
		setSize(400, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				searchMember();				
			}
		});
	}

	public void searchMember() {
		String addr = jtf01.getText();
		String sql =
		"select * from member where addr='"+addr+"'";
		try {
			Connection conn = 
			ConnectionProvider.getConnection();
			Statement stmt = 
					conn.createStatement();
			ResultSet rs 
			= stmt.executeQuery(sql);
			rowData.clear();
			while(rs.next()) {
				String name = rs.getString(1);
				String phone = rs.getString(2);
				String addr2 = rs.getString(3);
				int age = rs.getInt(4);
				
				Vector<String> v
				= new Vector<String>();
				v.add(name);
				v.add(phone);
				v.add(addr2);
				v.add(age+"");
				rowData.add(v);
			}
			
			table.updateUI();
			ConnectionProvider.close(rs, stmt, conn);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		new MemberTest();
	}
}