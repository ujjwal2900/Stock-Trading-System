import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class MyAccount extends JFrame implements ActionListener {
    String UserId;
    JButton History_button,Add_Money_button,Deduct_Money_button;
    JTextField money_TextField_MA;
    private StockAccount userStockAccountMA;
    private JLabel balanceLabel_data;
    
    public static void main(String args[]) {

    }

    public void userDetails() throws JSONException {


        JLabel usernameLabel = new JLabel("Username");
        JLabel usernameLabel_data = new JLabel(userStockAccountMA.getUsername());
        usernameLabel.setBounds(30 + 40,20,120,25);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        usernameLabel_data.setBounds(100+20+ 40,20,120,25);
        usernameLabel_data.setForeground(Color.WHITE);
        usernameLabel_data.setFont(new Font("Tahoma", Font.PLAIN, 15));

        JLabel userIdlabel = new JLabel("User Id");
        JLabel userIdlabel_data = new JLabel(userStockAccountMA.getUserID());
        userIdlabel.setBounds(30+ 40,50,120,25);
        userIdlabel.setForeground(Color.WHITE);
        userIdlabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        userIdlabel_data.setBounds(100+20+ 40,50,120,25);
        userIdlabel_data.setForeground(Color.WHITE);
        userIdlabel_data.setFont(new Font("Tahoma", Font.PLAIN, 15));

        JLabel passwordLabel = new JLabel("Password");
        JLabel passwordLabel_data = new JLabel(userStockAccountMA.getPassword());
        passwordLabel.setBounds(30+ 40,80,120,25);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        passwordLabel_data.setBounds(100+20+ 40,80,120,25);
        passwordLabel_data.setForeground(Color.WHITE);
        passwordLabel_data.setFont(new Font("Tahoma", Font.PLAIN, 15));

        JLabel balanceLabel = new JLabel("Balance");
        balanceLabel_data = new JLabel(String.valueOf(userStockAccountMA.getBalance()));
        balanceLabel.setBounds(30+ 40,80+30,120,25);
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        balanceLabel_data.setBounds(100+20+ 40,80+30,120,25);
        balanceLabel_data.setForeground(Color.WHITE);
        balanceLabel_data.setFont(new Font("Tahoma", Font.PLAIN, 15));



        this.add(usernameLabel);
        this.add(usernameLabel_data);
        this.add(userIdlabel);
        this.add(userIdlabel_data);
        this.add(passwordLabel);
        this.add(passwordLabel_data);
        this.add(balanceLabel);
        this.add(balanceLabel_data);
    }
    MyAccount(String UserId) throws JSONException, IOException {

        userStockAccountMA = new StockAccount(UserId);
        this.UserId = UserId;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500,600);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(133,205,202));


        History_button = new JButton("History");
        History_button.setBounds(70,108+200,120,25);
        History_button.addActionListener(this);
        History_button.setBackground(new Color(23, 23, 23));
        History_button.setFont(new Font("Comic Sans",Font.ITALIC,15));
        History_button.setForeground(Color.WHITE);
        History_button.setBorder(BorderFactory.createEtchedBorder());
        History_button.setFocusable(false);

        Add_Money_button = new JButton("Add");
        Add_Money_button.setBounds(70,108+250 - 150,120,25);
        Add_Money_button.setBackground(new Color(23, 23, 23));
        Add_Money_button.setFont(new Font("Comic Sans",Font.ITALIC,15));
        Add_Money_button.setForeground(Color.WHITE);
        Add_Money_button.setBorder(BorderFactory.createEtchedBorder());
        Add_Money_button.setFocusable(false);
        Add_Money_button.addActionListener(this);

        Deduct_Money_button = new JButton("Deduct");
        Deduct_Money_button.setBounds(70,108+280  - 150,120,25);
        Deduct_Money_button.setBackground(new Color(23, 23, 23));
        Deduct_Money_button.setFont(new Font("Comic Sans",Font.ITALIC,15));
        Deduct_Money_button.setForeground(Color.WHITE);
        Deduct_Money_button.setBorder(BorderFactory.createEtchedBorder());
        Deduct_Money_button.setFocusable(false);
        Deduct_Money_button.addActionListener(this);

        money_TextField_MA = new JTextField();
        money_TextField_MA.setBounds(150+50,373  - 150,120,25);
        money_TextField_MA.setPreferredSize(new Dimension(120,25));
        money_TextField_MA.setForeground(new Color(102, 12, 86));
        money_TextField_MA.setBackground(Color.WHITE);

        this.add(History_button);
        this.add(Add_Money_button);
        this.add(Deduct_Money_button);
        this.add(money_TextField_MA);
        this.setVisible(true);

        this.userDetails();
    }
    public void displayMessage(String message) {
		JFrame f=new JFrame();
		JOptionPane.showMessageDialog(f,message);
	}
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==History_button){
            // History
            System.out.println("History Of Stocks traded :");
            LinkedList userHistory = userStockAccountMA.StockList();
            String str="History Of Stocks traded :";
            for(int i=0;i<userHistory.size();i++){
            	String s=userHistory.get(i).toString();
                System.out.println(s);
                str+="\n"+(i+1)+"."+s;
            }
            displayMessage(str);
        }
        if(e.getSource()==Add_Money_button){
            // add money call method
            //money_TextField_MA.getText() is the money entered

            try {
                userStockAccountMA.addMoney(Double.parseDouble(money_TextField_MA.getText()));
//                balanceLabel_data = new JLabel(String.valueOf(userStockAccountMA.getBalance()));
                balanceLabel_data.setText(String.valueOf(userStockAccountMA.getBalance()));
                System.out.println(String.format("Added %s to account balance ! ",money_TextField_MA.getText()));
                displayMessage(String.format("Added %s to account balance ! ",money_TextField_MA.getText()));
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if(e.getSource()==Deduct_Money_button){
            // deduct money call method
            //money_TextField_MA.getText() is the money entered

            try {
                userStockAccountMA.deductMoney(Double.parseDouble(money_TextField_MA.getText()));
//                balanceLabel_data = new JLabel(String.valueOf(userStockAccountMA.getBalance()));
                balanceLabel_data.setText(String.valueOf(userStockAccountMA.getBalance()));
                System.out.println(String.format("Deducted %s from account balance ! ",money_TextField_MA.getText()));
                displayMessage(String.format("Deducted %s from account balance ! ",money_TextField_MA.getText()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }
    }
}