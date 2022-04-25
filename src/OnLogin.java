
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import org.json.JSONException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class OnLogin extends JFrame implements ActionListener{

	private JFrame frmStockAccount;
//	PossibleMain2 mainObj;
	JButton Trade_Button, My_Stocks_Button , Portfolio_Button;
	JButton Market_Performance_Button, Wishlist_Button, My_Account_Button;
	JLabel textLabel,welcomeLabel;
	private Portfolio port;
	private JTable table;
//	private String userName;

	String UserID;
	public OnLogin(String UserID) {
		this.UserID = UserID;
//		Account acc=new Account(UserID);
//		userName=acc.getUserName();
		try {
			port=new Portfolio(UserID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
//		mainObj=new PossibleMain2();
	}
	public static void main(String args[]) {

	}
	public void displayMessage(String message) {
		JFrame f=new JFrame();
		JOptionPane.showMessageDialog(f,message);
	}
	public void displayStocks() throws Exception {
//		StockAccount acc=new StockAccount(UserID);
		Portfolio port=new Portfolio(UserID);
		LinkedList<String> list=new LinkedList<>();
//		list=acc.StockList();
		list=port.displayStockList();
		String str="Your stock list";
		for(int i=0;i<list.size();i++) {
			str+="\n"+(i+1)+"."+list.get(i);
		}
		str+="\nPlease check the portfolio for more details";
		displayMessage(str);
//
	}
	
	private void initialize() {

		frmStockAccount = this;
		frmStockAccount.getContentPane().setForeground(Color.DARK_GRAY);
		frmStockAccount.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 18));
		frmStockAccount.getContentPane().setName("Buy Stock");
		frmStockAccount.setTitle("Stock Account");
		frmStockAccount.setBounds(100, 100, 900, 600);
		frmStockAccount.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmStockAccount.getContentPane().setBackground(new Color(220,105,102));
		frmStockAccount.setVisible(true);
		
		My_Stocks_Button = new JButton("My Stocks");
		Portfolio_Button = new JButton("Portfolio");
		My_Account_Button = new JButton("My Account");
		Wishlist_Button = new JButton("Wishlist");
		Market_Performance_Button = new JButton("Market Watch");
		Trade_Button = new JButton("Trade");
		welcomeLabel = new JLabel("Welcome "+UserID);
		textLabel=new JLabel();
		
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		welcomeLabel.setBounds(312, 10, 240, 30);
		frmStockAccount.getContentPane().add(welcomeLabel);
		
		Trade_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Trade_Button.setBounds(47, 73, 147, 38);
		frmStockAccount.getContentPane().add(Trade_Button);
		
		My_Stocks_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		My_Stocks_Button.setBounds(312, 73, 147, 38);
		frmStockAccount.getContentPane().add(My_Stocks_Button);
		
		Portfolio_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Portfolio_Button.setBounds(573, 73, 170, 38);
		frmStockAccount.getContentPane().add(Portfolio_Button);
		
		Wishlist_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Wishlist_Button.setBounds(47, 157, 170, 38);
		frmStockAccount.getContentPane().add(Wishlist_Button);
		
		My_Account_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		My_Account_Button.setBounds(312, 157, 170, 38);
		frmStockAccount.getContentPane().add(My_Account_Button);
		
		Market_Performance_Button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Market_Performance_Button.setBounds(573, 157, 220, 38);
		frmStockAccount.getContentPane().add(Market_Performance_Button);
		
		textLabel.setBounds(47, 244, 702, 281);
		String intro="<html><br><br><br>1.Share of stock means you have ownership into a company.<br>2.Stock prices vary throughout the day and everyday.";
		intro+="<br>3.A stock is an investment that can lose money.<br>4.It's good to buy low and sell high.<br>";
		intro+="<br>Symbols of some popular stocks are:<br>TSLA - Tesla<br>GOOGL - Google<br>AMZN - Amazon</html>";
		textLabel.setText(intro);
		textLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frmStockAccount.getContentPane().add(textLabel);
		
		Trade_Button.addActionListener(this);
		My_Account_Button.addActionListener(this);
		Market_Performance_Button.addActionListener(this);
		Portfolio_Button.addActionListener(this);
		My_Stocks_Button.addActionListener(this);
		Wishlist_Button.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	if(e.getSource()==Trade_Button){
		//trade here
		System.out.println("Trading ! ");
		Trade trade=new Trade(UserID);
	}
	if(e.getSource()==My_Stocks_Button){
		// my stocks
		System.out.println("My Stocks ! ");
		try {
			displayStocks();
		} catch (JSONException | IOException e2) {
			e2.printStackTrace();
		}
 catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		frmStockAccount.getContentPane().add(textLabel);
	}
	if(e.getSource()==Market_Performance_Button){
		// market performance
		System.out.println("Market performance");
		MarketWatch watch=new MarketWatch();
	}
	if(e.getSource()==My_Account_Button){
		// My account
		System.out.println("My Account ! ");
		try {
			MyAccount acc=new MyAccount(UserID);
		} catch (JSONException | IOException e1) {
			e1.printStackTrace();
		}
	}
	if(e.getSource()==Portfolio_Button){
		//portfolio
		System.out.println("Portfolio ! ");
		PortfolioGui port=new PortfolioGui(UserID);
	}
	if(e.getSource()==Wishlist_Button){
		//wishlist
		System.out.println("Wishlist ! ");
		Wishlist wish=new Wishlist(UserID);
	}

	}
}
