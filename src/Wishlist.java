import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.json.JSONException;

public class Wishlist extends JFrame implements ActionListener{

	private JFrame frmWishlist;
	private JTextArea textField;
	private JTextField withdrawText;
	private JTextField addPrice;
	private JTextField addWishText;
	private JButton newWishBtn;
	private JButton btnWithdraw;
	private JComboBox<String> list;
	private JButton btnDisplay;
	
	private String userId;
	private String wishName;
	private String wishPrice;
	private HashMap<String, String> map;

	/**
	 * Launch the application.
	 */
	public HashMap<String, String> getWishMap() {
		return map;
	}
	
	public void addWish(String wishName,String wishPrice) {
		
		map.put(wishName, wishPrice);
	}
	public void withdraw(String wishName) {
		map.remove(wishName);
	}
	public static void main(String[] args) {

	}

	/**
	 * Create the application.
	 */
	public void display(String stockId) {
		String price=map.get(stockId);
		double wishPrice=Double.parseDouble(price);
		String remarks="None";
		double realPrice;
		double bal=0;
		try {
			Stock stock=new Stock(stockId);
			try {
				StockAccount acc=new StockAccount(userId);
				bal=acc.getBalance();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			realPrice=stock.getClosePrice();
			if(bal>realPrice) {
				int no=(int) (bal/realPrice);
				remarks="Congratulations!You have enough money\n in your account to purchase "+no+" stocks";
			}
			else if(wishPrice>=realPrice)remarks="Your wishprice is higher than the market price!";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text="Wish name: "+stockId+"\nWish price: "+price+"\nRemarks: "+remarks;
		textField.setText(text);
	}
	
	public Wishlist(String userId) {
		this.userId=userId;
		map=new HashMap<>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWishlist = this;
		frmWishlist.getContentPane().setName("Your Wishlist");
		frmWishlist.setTitle("Wishlist!");
		frmWishlist.setBounds(100, 100, 800, 600);
		frmWishlist.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmWishlist.getContentPane().setBackground(new Color(133,205,202));
		frmWishlist.getContentPane().setLayout(null);
		
//		list = new JComboBox();
		list = new JComboBox<>(new String[] {});
		list.setEditable(true);
		list.setName("Your wishlist");
		list.setToolTipText("Your Wishlist");
		list.setFont(new Font("Tahoma", Font.PLAIN, 18));
		list.setBounds(526, 47, 156, 31);
		frmWishlist.getContentPane().add(list);
		
		newWishBtn = new JButton("Add wish");
		newWishBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		newWishBtn.setBounds(132, 328, 136, 31);
		frmWishlist.getContentPane().add(newWishBtn);
		
		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnWithdraw.setBounds(488, 328, 136, 31);
		frmWishlist.getContentPane().add(btnWithdraw);
		
		textField = new JTextArea();
		textField.setBounds(50, 47, 360, 108);
		frmWishlist.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel topLabel = new JLabel("WISHLIST");
		topLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		topLabel.setBounds(92, 10, 266, 27);
		frmWishlist.getContentPane().add(topLabel);
		
		withdrawText = new JTextField();
		withdrawText.setBounds(488, 283, 136, 31);
		frmWishlist.getContentPane().add(withdrawText);
		withdrawText.setColumns(10);
		
		addPrice = new JTextField();
		addPrice.setColumns(10);
		addPrice.setBounds(132, 283, 136, 31);
		frmWishlist.getContentPane().add(addPrice);
		
		addWishText = new JTextField();
		addWishText.setColumns(10);
		addWishText.setBounds(132, 230, 136, 31);
		frmWishlist.getContentPane().add(addWishText);
		
		JLabel lblNewLabel_1 = new JLabel("Stock Id");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(352, 282, 115, 27);
		frmWishlist.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Price");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(10, 282, 101, 27);
		frmWishlist.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Stock Id");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_2.setBounds(0, 229, 112, 27);
		frmWishlist.getContentPane().add(lblNewLabel_1_2);
		
		btnDisplay = new JButton("Display details");
		btnDisplay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDisplay.setBounds(526, 128, 179, 31);
		getContentPane().add(btnDisplay);
		
		newWishBtn.addActionListener(this);
		btnWithdraw.addActionListener(this);
		btnDisplay.addActionListener(this);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		System.out.println(map);
		if(e.getSource()==newWishBtn) {
			String wish=addWishText.getText();
			String price=addPrice.getText();
			for(int i=0;i<list.getItemCount();i++) {
				if(list.getItemAt(i).toString().equals(wish))list.removeItem(wish);
			}
//				if(list.getItemAt(i).toString().equals(wish))list.remove(i);
			addWish(wish, price);
			list.addItem(wish);
			addWishText.setText("");
			addPrice.setText("");
		}
		if(e.getSource()==btnWithdraw) {
			String wish=withdrawText.getText();
			withdraw(wish);
			list.removeItem(wish);
			withdrawText.setText("");
		}
		if(e.getSource()==btnDisplay) {
			if(list.getItemCount()==0) {
				System.out.println("Wishlist is empty!");
				return;
			}
			String stockId=list.getSelectedItem().toString();
			stockId=stockId.toUpperCase();
			
			display(stockId);
		}
	}
}
