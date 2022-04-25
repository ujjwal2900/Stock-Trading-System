import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SecondPage extends JFrame implements ActionListener {
    JButton submit_button;
//    JTextField passwordTextField;
    JPasswordField passwordTextField;
    //PossibleMain2 main;
    private String userId;
    
    int choice;
    SecondPage(int choice) throws FileNotFoundException, JSONException {
        this.choice = choice;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500,600);

        submit_button = new JButton("Submit ");
        submit_button.setBounds(100,300,120,25);
        submit_button.addActionListener(this);
        submit_button.setBorder(BorderFactory.createEtchedBorder());
        submit_button.setBackground(new Color(23,23,23));
        submit_button.setFont(new Font("Comic Sans",Font.ITALIC,15));
        submit_button.setForeground(Color.WHITE);
        submit_button.setFocusable(false);
//        passwordTextField = new JTextField();
        passwordTextField=new JPasswordField();
        passwordTextField.setBounds(100,150,120,25);



        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(30,150,120,25);
        passwordLabel.setForeground(Color.WHITE);

        this.setSize(500,600);
        this.add(submit_button);

        this.add(passwordLabel);
        this.add(passwordTextField);
        this.getContentPane().setBackground(new Color(64,179,162));
        this.setVisible(true);
        if(choice==1){
            this.logsin();
        }
        else if(choice==2){
            this.createAc();
        }

    }
    JTextField UserID_TextField;
    JLabel UserID_JLabel;
    public void logsin(){

        UserID_JLabel = new JLabel("User Id");
        UserID_JLabel.setBounds(30,120,120,25);
        UserID_JLabel.setForeground(Color.WHITE);

        UserID_TextField = new JTextField();
        UserID_TextField.setBounds(100,120,120,25);
        UserID_TextField.setPreferredSize(new Dimension(120,25));

        JLabel H_LogIn_Jlabel = new JLabel("Log In");
        H_LogIn_Jlabel.setBounds(10,10,120,25);
        H_LogIn_Jlabel.setForeground(Color.WHITE);
        this.add(H_LogIn_Jlabel);
        this.add(UserID_TextField);
        this.add(UserID_JLabel);

    }
    JTextField Username_TextField, name_TextField, Balance_TextField;
    public void createAc(){
        JLabel H_CreateAccount_JLabel = new JLabel("Create Account ");
        H_CreateAccount_JLabel.setBounds(10,10,120,25);
        H_CreateAccount_JLabel.setForeground(Color.WHITE);

        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setBounds(30,120,120,25);
        userNameLabel.setForeground(Color.WHITE);

        Username_TextField = new JTextField();
        Username_TextField.setBounds(100,120,120,25);
        Username_TextField.setPreferredSize(new Dimension(120,25));
        //Name
        JLabel NameLabel = new JLabel("Name");
        NameLabel.setBounds(30,210,120,25);
        NameLabel.setForeground(Color.WHITE);

        name_TextField = new JTextField();
        name_TextField.setBounds(100,210,120,25);
        name_TextField.setPreferredSize(new Dimension(120,25));

        Balance_TextField = new JTextField();
        Balance_TextField.setBounds(100,180,120,25);
        Balance_TextField.setPreferredSize(new Dimension(120,25));

        JLabel userBalance_JLabel = new JLabel("Balance");
        userBalance_JLabel.setBounds(30,180,120,25);
        userBalance_JLabel.setForeground(Color.WHITE);

        this.add(userNameLabel);
        this.add(H_CreateAccount_JLabel);
        this.add(userBalance_JLabel);
        this.add(Username_TextField);
        this.add(name_TextField);
        this.add(Balance_TextField);
        this.add(NameLabel);

    }
    public void displayMessage(String message) {
		JFrame f=new JFrame();
		JOptionPane.showMessageDialog(f,message);
	}


    @Override
    public void actionPerformed(ActionEvent e) {
        LogIn log= null;
        try {
            log = new LogIn();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        String password_entered = passwordTextField.getText();
        if(e.getSource()==submit_button){
        if (choice ==1){

                String userID_entered = UserID_TextField.getText();
                Account acc = new Account(userID_entered,password_entered);
                boolean check= false;
                try {
                    check = log.SignIN(acc);
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                if(check) {
                    System.out.println("welcome");
                    OnLogin login=new OnLogin(userID_entered);
                }
                else {
//                    System.out.println("Invalid credentials");
                    displayMessage("Invalid credentials");

                }
        }
        else if(choice ==2){

            String UserName_entered = Username_TextField.getText();
            if(name_TextField.getText().equals("")) {
            	displayMessage("Please enter Name");
            	return;
            }
            if(UserName_entered.equals("")) {
            	displayMessage("Please enter a valid user name");
            	return;
            }
            if(Balance_TextField.getText().equals("")) {
            	displayMessage("Please enter balance");
            	return;
            }
            if(password_entered.equals("")) {
            	displayMessage("Please enter a valid password");
            	return;
            }
            double balance_entered = Double.parseDouble(Balance_TextField.getText());
            Account acc=new Account(UserName_entered, password_entered,balance_entered,name_TextField.getText());
            LogIn log2= null;
            try {
                log2 = new LogIn();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            try {
                log2.SignUp(acc);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            System.out.println("Your UserId is "+acc.getUserId());
            displayMessage("Your UserId is "+acc.getUserId()+"\nPlease note it for future references");
//			}
            OnLogin login=new OnLogin(acc.getUserId());
            // after sign up, you're logged in , do other stuff here
        }

    }
}}
