import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.InputMethodListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.json.JSONException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

import javax.swing.JTable;

public class Trade extends JFrame implements ActionListener{
	private StockAccount stAcc;
	private String userId;
	private String transaction;
	private String stockId;
	private int qty;
	private JFrame frmBuyStock;
	private JTextField StockId;
	private JTextField textField;
	private JButton btnClear;
	private JButton btnConfirm;
	private JComboBox tradeType;
	private JTextArea billArea;
	private JButton billButton;
	private JLabel account;
	private JCheckBox chckbxNewCheckBox;
	

	public static void main(String[] args) {

	}

	public Trade(String userId) {
		this.userId=userId;
		try {
			stAcc=new StockAccount(userId);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}
	
	public boolean qtyExists(String stock,int qty) {
		int no=stAcc.qtyStock(stock);
		if(no>=qty)return true;
		return false;
	}
	
	private void initialize() {
		frmBuyStock = this;
		frmBuyStock.setTitle("Trade Stock");
		frmBuyStock.setBounds(100, 100, 800, 600);
		frmBuyStock.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBuyStock.getContentPane().setLayout(null);
		frmBuyStock.getContentPane().setBackground(new Color(133,205,202));
//		frmBuyStock.setVisible(true);
		
		StockId = new JTextField();
		StockId.setFont(new Font("Tahoma", Font.PLAIN, 18));
		StockId.setBounds(161, 116, 147, 36);
		frmBuyStock.getContentPane().add(StockId);
		StockId.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Stock Id");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(28, 119, 127, 33);
		frmBuyStock.getContentPane().add(lblNewLabel);
		
		JLabel lblNoOfShares = new JLabel("No of shares");
		lblNoOfShares.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNoOfShares.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNoOfShares.setBounds(28, 177, 127, 33);
		frmBuyStock.getContentPane().add(lblNoOfShares);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setColumns(10);
		textField.setBounds(161, 175, 147, 36);
		frmBuyStock.getContentPane().add(textField);
		
		JLabel lblTransaction = new JLabel("Transaction");
		lblTransaction.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTransaction.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTransaction.setBounds(28, 72, 127, 33);
		frmBuyStock.getContentPane().add(lblTransaction);
		
		tradeType = new JComboBox();
		tradeType.setModel(new DefaultComboBoxModel(new String[] {"Buy", "Sell"}));
		tradeType.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tradeType.setToolTipText("Buy");
		tradeType.setEditable(true);
		tradeType.setBackground(new Color(255, 255, 255));
		tradeType.setBounds(161, 71, 127, 34);
		frmBuyStock.getContentPane().add(tradeType);
		
		chckbxNewCheckBox = new JCheckBox("Send confirmation message");
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 22));
		chckbxNewCheckBox.setBounds(130,231 , 346, 50);
		frmBuyStock.getContentPane().add(chckbxNewCheckBox);
		
		String text="<html>Account details<br><br>User Id: "+userId;
		try {
			double bal=stAcc.getBalance();
			String val=String.format("%.2f", bal);
			text+="<br>Balance: "+val+"</html>";
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		account = new JLabel(text);
		account.setHorizontalAlignment(SwingConstants.CENTER);
		account.setFont(new Font("Tahoma", Font.PLAIN, 18));
		account.setBounds(427, 20, 349, 190);
		frmBuyStock.getContentPane().add(account);
		
		billButton = new JButton("Generate bill");
		billButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		billButton.setBounds(116, 301, 171, 44);
		frmBuyStock.getContentPane().add(billButton);
		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnClear.setBounds(511, 301, 171, 44);
		frmBuyStock.getContentPane().add(btnClear);
		
		billArea = new JTextArea();
		billArea.setEnabled(true);
		billArea.setEditable(false);
		billArea.setLineWrap(true);
		billArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		billArea.setBounds(117, 402, 504, 135);
		frmBuyStock.getContentPane().add(billArea);
		
		btnConfirm = new JButton("Confirm");
		this.setVisible(true);
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnConfirm.setBounds(319, 301, 171, 44);
		frmBuyStock.getContentPane().add(btnConfirm);
		btnConfirm.addActionListener(this);
		btnClear.addActionListener(this);
		billButton.addActionListener(this);
		
	}

	public void displayMessage(String message) {
		JFrame f=new JFrame();
		JOptionPane.showMessageDialog(f,message);
	}
	
	public double possibleAmount(String stockId,int qty) {
		double price;
		double total=0;
		try {
			Stock stock=new Stock(stockId);
			price=stock.getClosePrice();
			total=qty*price;
		} catch (JSONException e1) {
			System.out.println("Enter a valid stock symbol");
			e1.printStackTrace();
		}
		return total;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		transaction=tradeType.getSelectedItem().toString();
		stockId=StockId.getText();
		String message="";
		if(stockId.equals("")) {
//			System.out.println("Please enter a valid stock symbol");
			displayMessage("Please enter a valid stock symbol");
			return;
		}
		stockId=stockId.toUpperCase();
		String num=textField.getText();
		if(num.equals("")) {
//			System.out.println("Please enter a valid number");
			displayMessage("Please enter a valid number");
			return;
		}
		qty=Integer.parseInt(num);
		if(e.getSource()==btnConfirm) {
			double diff=0;
			double prev=0;
			boolean success=true;
			try {
				prev=stAcc.getBalance();
				System.out.println("Balance before transaction:"+String.format("%.2f", prev));
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			double after=prev;
			
			if(transaction.equals("Buy")) {
				if(possibleAmount(stockId, qty)>prev) {
					displayMessage("Transaction failed!\nYou do not have enough balance");
					return;
				}
				try {
					stAcc.buyStock(stockId, qty);
					after=stAcc.getBalance();
				} catch (JSONException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(transaction.equals("Sell")) {
				if(!stAcc.stockExists(stockId)) {
					success=false;
				}
				if(!qtyExists(stockId, qty)) {
					success=false;
				}
				
				if(success) {
					try {
						stAcc.sellStock(stockId, qty);
						after=stAcc.getBalance();
					} catch (JSONException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}
			
			String bal=String.format("%.2f", after);
			System.out.println("Balance after transaction:"+bal);
			diff=after-prev;
			String diffstr=String.format("%.2f", Math.abs(diff));

			String text="<html>Account Details<br><br>User Id: "+userId+"<br>Balance: "+bal+"</html>";
			account.setText(text);
			
			if(chckbxNewCheckBox.isSelected()) {
				message="Transaction successful!";
				if(!success||diff==0) {
					if(transaction.equals("Sell"))message="Transaction failed!\nYou do not own enough stocks";
					else message="Transaction failed!";
				}
				else if(diff>0)message+="\r\n"+diffstr+" added to your account";
				else message+="\r\n"+diffstr+" deducted from your account";
				displayMessage(message);
			}
		}
		if(e.getSource()==billButton) {
			double price=0;
			double total=0;
			try {
				Stock s=new Stock(stockId);
				price=s.getClosePrice();
				total=qty*price;
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			String stprice=String.format("%.2f", price);
//			double total=possibleAmount(stockId, qty);
			String totalstr=String.format("%.2f", total);
			billArea.setText("Stock symbol:"+stockId+"\r\nTransaction type: "+transaction+"\r\nStock price: "+stprice+"\r\nQuantity: "+qty+"\r\nAmount transacted: "+totalstr+"\r\nDate: "+LocalDate.now().toString());
		}
		if(e.getSource()==btnClear) {
			StockId.setText("");
			textField.setText("");
			billArea.setText("");
		}
	}
}



