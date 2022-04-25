import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FirstPageGUI implements ActionListener {
    JButton button,button2,button3;
    public FirstPageGUI(){


        button = new JButton("Create Account");
        button.setBounds(180,50+100,120,25);
        button.addActionListener(this);
        button.setBackground(new Color(23,23,23));
        button.setFont(new Font("Comic Sans",Font.ITALIC,15));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setFocusable(false);

        button2 = new JButton("Log In");
        button2.setBounds(180,79+100,120,25);
        button2.addActionListener(this);
        button2.setBackground(new Color(23,23,23));
        button2.setFont(new Font("Comic Sans",Font.ITALIC,15));
        button2.setForeground(Color.WHITE);
        button2.setBorder(BorderFactory.createEtchedBorder());
        button2.setFocusable(false);

        button3 = new JButton("Exit ");
        button3.setBounds(180,108+100,120,25);
        button3.addActionListener(this);
        button3.setBackground(new Color(23, 23, 23));
        button3.setFont(new Font("Comic Sans",Font.ITALIC,15));
        button3.setForeground(Color.WHITE);
        button3.setBorder(BorderFactory.createEtchedBorder());
        button3.setFocusable(false);



        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setSize(500,600);
        myFrame.setLayout(null);
        myFrame.getContentPane().setBackground(new Color(133,205,202));
        myFrame.add(button);
        myFrame.add(button2);
        myFrame.add(button3);
        myFrame.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

//        PossibleMain2 ps = new PossibleMain2();


        if(e.getSource()==button){
            System.out.println("Create Account");
            try {
                SecondPage sp = new SecondPage(2);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }

        }
        else if(e.getSource()==button2){
            System.out.println("Log In");
            try {
                SecondPage sp = new SecondPage(1);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }

        }
        else if(e.getSource()==button3){
            System.out.println("Exit ! ");
//            ps.exit();
        }
    }
}
