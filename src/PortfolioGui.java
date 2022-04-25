
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONException;

public class PortfolioGui extends JFrame implements ActionListener{

	private JFrame frmPortfolio;
	private String userId;
	JButton port;
	JButton purchase;
	JButton sale;
	JButton stockDetails;
	JTextField text;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		PortfolioGui gui=new PortfolioGui("EsNN");
	}

	/**
	 * Create the application.
	 */
	public PortfolioGui(String userId) {
		this.userId=userId;
		initialize();
	}
	public void displayMessage(String message) {
		JFrame f=new JFrame();
		JOptionPane.showMessageDialog(f,message);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPortfolio = this;
		frmPortfolio.setTitle("Portfolio");
		frmPortfolio.getContentPane().setForeground(Color.DARK_GRAY);
		frmPortfolio.setBounds(100, 100, 800, 600);
		frmPortfolio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmPortfolio.getContentPane().setLayout(null);
		frmPortfolio.getContentPane().setBackground(new Color(133,205,202));
		frmPortfolio.setVisible(true);
		
		port = new JButton("View Portfolio");
		purchase = new JButton("Purchase History");
		sale = new JButton("Sale History");
		stockDetails = new JButton(" View Details");
		text = new JTextField();
		
		
		port.setFont(new Font("Tahoma", Font.PLAIN, 18));
		port.setBounds(71, 62, 177, 43);
		frmPortfolio.getContentPane().add(port);
		
		purchase.setFont(new Font("Tahoma", Font.PLAIN, 18));
		purchase.setBounds(71, 143, 210, 43);
		frmPortfolio.getContentPane().add(purchase);
		
		sale.setFont(new Font("Tahoma", Font.PLAIN, 18));
		sale.setBounds(71, 233, 177, 43);
		frmPortfolio.getContentPane().add(sale);
		
		stockDetails.setFont(new Font("Tahoma", Font.PLAIN, 18));
		stockDetails.setBounds(498, 143, 177, 43);
		frmPortfolio.getContentPane().add(stockDetails);
		
		text.setBounds(498, 62, 177, 43);
		frmPortfolio.getContentPane().add(text);
		
		port.addActionListener(this);
		sale.addActionListener(this);
		purchase.addActionListener(this);
		stockDetails.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Portfolio p=null;
		try {
			p=new Portfolio(userId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			if(e.getSource()==port) {
				p.displayFullPortfolio();
			}
			if(e.getSource()==purchase) {
				p.displayPurchaseHistory();
			}
			if(e.getSource()==sale) {
				p.displaySaleHistory();
			}
			if(e.getSource()==stockDetails) {
				String stock=text.getText();
				if(stock.equals("")) {
					displayMessage("Please enter a stock id");
					return;
				}
				Stock s=new Stock(stock);
				p.displayStockPortfolio(s);
			}
		}catch (JSONException e1) {
			e1.printStackTrace();
		}
		
	}
}
